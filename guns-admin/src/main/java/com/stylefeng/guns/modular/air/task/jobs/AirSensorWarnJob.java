package com.stylefeng.guns.modular.air.task.jobs;

import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.common.constant.Constant;
import com.stylefeng.guns.core.common.constant.SensorTypeEnum;
import com.stylefeng.guns.core.other.DateUtil;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.core.util.Contrast;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorAlarmInfo;
import com.stylefeng.guns.modular.air.model.AirSensorWarnParam;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.model.SensorType;
import com.stylefeng.guns.modular.air.service.IAirSensorAlarmInfoService;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirSensorWarnParamService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;
import com.stylefeng.guns.modular.air.task.GetUdpDataTask;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;

/**  
 * <p>Title: AirSensorWarnJob</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年5月9日  
 */
@Component
public class AirSensorWarnJob implements Job{


	private Logger logger = LoggerFactory.getLogger(AirSensorWarnJob.class);
	
	@Autowired
	private IAirStationService airStationService;
	@Autowired
	private IAirSensorService airSensorService;
	@Autowired
	private IAirSensorWarnParamService sensorWarnParamService;
	@Autowired
	private ISensorTypeService sensorTypeService;
	@Autowired
	private IAirSensorAlarmInfoService sensorAlarmInfoService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		logger.info("传感器预警检查：启动任务======================="+DateUtil.formatFullDateTime(new Date()));
	    JobDataMap map = context.getJobDetail().getJobDataMap();
	    String deviceCode = map.getString("deviceCode");
	    logger.info("code --> "+deviceCode);
	    run(deviceCode);
	    logger.info("传感器预警检查：下次执行时间====="+
	        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
	            .format(context.getNextFireTime())+"==============");
	}
	
	
	public void run(String deviceCode){
		
		if(StringUtils.isNotBlank(deviceCode)){
			AirSensor sensor = airSensorService.selectOne(new EntityWrapper<AirSensor>().eq("code", deviceCode).eq("valid", "0"));
			if(sensor!=null){
				logger.info("===================="+sensor.gettName()+"====================");
				//查询传感器预警参数
				List<AirSensorWarnParam> params = sensorWarnParamService.selectList(new EntityWrapper<AirSensorWarnParam>().eq("sensor_id", sensor.getId()).eq("valid", "0").eq("control_mode", "0").orderBy("sort_code"));
				if(CollectionUtils.isNotEmpty(params)){
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					ExecutorService service = Executors.newCachedThreadPool();
					DatagramSocket socket = null;
					try {
						socket=new DatagramSocket();
						
						//查询传感器所属气象站
						AirStation station = airStationService.selectById(sensor.getStationId());
						if(station!=null){
							GetUdpDataTask task=new GetUdpDataTask(station.getIpAddr(), Integer.parseInt(station.getPort()));
							for(AirSensorWarnParam param : params){
								Date startTime = sdf.parse(param.getStartTime());
								Date endTime = sdf.parse(param.getEndTime());
								Date now = sdf.parse(sdf.format(new Date()));
								//判断当前时间是否在预警时间范围内
								boolean flag = cn.hutool.core.date.DateUtil.isIn(now, startTime, endTime);
								if(flag){
									//查询传感器类型
									SensorType type = sensorTypeService.selectById(sensor.getTypeId());
									
									AirStationData data=new AirStationData();
									//查询传感器当前数值
									Future<String> future = service.submit(task.new GetUdpDataThread(socket, sensor.getCommand()));
									Object[] status = task.getEntity(future.get(), data, sensor);
									if((boolean) status[0] && !Contrast.isAllFieldNull(data)){
										
										//根据传感器类型获取数据
										SensorTypeEnum typeEnum = SensorTypeEnum.findSensorTypeByName(type.gettName());
										if(typeEnum!=null){
											Double num = Convert.toDouble(SensorTypeEnum.findDataBySensorType(typeEnum.getCode(), data));
											if(num!=null){
												//将当前数值与预警参数阈值作比较  
												if(compareNumToThreshold(param,num)){
													//TODO 当前数值超出阈值范围内   新增报警信息
													
													//查询报警信息是否存在
													List<AirSensorAlarmInfo> alarms = sensorAlarmInfoService.selectList(new EntityWrapper<AirSensorAlarmInfo>().eq("sensor_id", sensor.getId()).eq("valid", "0").eq("alarm_type", "1").eq("handle_state", "0"));
													if(CollectionUtils.isEmpty(alarms)){
														//新增传感器报警信息
														AirSensorAlarmInfo alarm=new AirSensorAlarmInfo();
														alarm.settName(sensor.gettName()+"-"+Constant.sensor_exception_type.get("1"));
														alarm.setSensorId(sensor.getId());
														alarm.setAlarmType("1");//数值异常
														alarm.setAlarmInfo(String.format("站点[%s]，传感器[%s]值%s设定预警值[%s]，当前值[%s]，请检查", station.gettName(),sensor.gettName(),Constant.SENSOR_WARN_EXPRESSION_TYPE.get(param.getExpression()),param.getThreshold(),num));
														alarm.setAlarmTime(new Date());
														alarm.setCode(StringUtil.generatorShort());
														alarm.setCreateTime(new Date());
														sensorAlarmInfoService.insert(alarm);
														
													}
													
													break;
												}
											}
										}
										
									}else{
										
										//TODO 传感器离线   新增传感器报警信息
										//查询报警信息是否存在
										List<AirSensorAlarmInfo> alarms = sensorAlarmInfoService.selectList(new EntityWrapper<AirSensorAlarmInfo>().eq("sensor_id", sensor.getId()).eq("valid", "0").eq("alarm_type", "0").eq("handle_state", "0"));
										if(CollectionUtils.isEmpty(alarms)){
											//新增传感器报警信息
											AirSensorAlarmInfo alarm=new AirSensorAlarmInfo();
											alarm.settName(sensor.gettName()+"-"+Constant.sensor_exception_type.get("0"));
											alarm.setSensorId(sensor.getId());
											alarm.setAlarmType("0");//设备离线
											alarm.setAlarmInfo(String.format("站点[%s]，传感器[%s]设备离线，请检查", station.gettName(),sensor.gettName()));
											alarm.setAlarmTime(new Date());
											alarm.setCode(StringUtil.generatorShort());
											alarm.setCreateTime(new Date());
											sensorAlarmInfoService.insert(alarm);
											
										}
										
										
										//更新传感器状态
										sensor.setStatus("2");//通讯故障
										sensor.setUpdateTime(new Date());
										airSensorService.updateById(sensor);
										
										continue;
									}
									
									
									//更新传感器状态
									sensor.setStatus("0");//正常
									sensor.setUpdateTime(new Date());
									airSensorService.updateById(sensor);
									
									//更改传感器报警信息状态
									//查询报警信息是否存在
									List<AirSensorAlarmInfo> alarms = sensorAlarmInfoService.selectList(new EntityWrapper<AirSensorAlarmInfo>().eq("sensor_id", sensor.getId()).eq("valid", "0").eq("handle_state", "0"));
									if(CollectionUtils.isNotEmpty(alarms)){
										for(AirSensorAlarmInfo info : alarms){
											info.setHandleState("1");//已恢复
											info.setHandleContent("自动恢复");
											info.setHandleTime(new Date());
											sensorAlarmInfoService.updateById(info);
										}
									}
									
									
								}
								
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage(),e);
					} finally {
						service.shutdown();
						if(socket!=null){
							socket.close();
						}
					}
				}
				
			}
		}
		
		
		
	}
	
	/**  
	 * <p>Title: compareNumToThreshold</p>  
	 * <p>Description: 将当前数值与预警参数阈值作比较</p>  
	 * @param param
	 * @param num  
	 */ 
	public boolean compareNumToThreshold(AirSensorWarnParam param, Double num) {
		Double threshold=Double.valueOf(param.getThreshold());
		boolean flag=false;
		int compare = NumberUtil.compare(num, threshold);
		switch (param.getExpression()) {
			case "0"://大于
				if(compare==1){
					flag=true;
				}
				break;
			case "1"://小于
				if(compare==-1){
					flag=true;
				}
				break;
			case "2"://大于等于
				if(compare!=-1){
					flag=true;
				}
				break;
			case "3"://小于等于
				if(compare!=1){
					flag=true;
				}
				break;
			case "4"://等于
				if(compare==0){
					flag=true;
				}
				break;
			default://不等于
				if(compare!=0){
					flag=true;
				}
				break;
		}
		return flag;
	}


	public static void main(String[] args) {
		AirStationData data=new AirStationData();
		data.setAirHumidity("23.3");
		data.setAirTemperature("32.2");
		data.setRadiation("56.3");
		data.setNegativeOxygenIon("11.1");
		data.setPm25("16.5");
		Map<String, Object> map2 = BeanUtil.beanToMap(data, true, true);
		System.out.println(map2);
		//根据传感器类型获取数据
		SensorTypeEnum typeEnum = SensorTypeEnum.findSensorTypeByName("大气温度");
		
		System.out.println(SensorTypeEnum.findDataBySensorType(typeEnum.getCode(), data));
		
	}

}
