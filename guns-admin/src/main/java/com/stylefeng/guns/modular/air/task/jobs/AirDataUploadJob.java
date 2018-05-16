package com.stylefeng.guns.modular.air.task.jobs;

import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.stylefeng.guns.core.other.DateUtil;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.core.util.Contrast;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorAlarmInfo;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.service.IAirSensorAlarmInfoService;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationDataService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.task.GetUdpDataTask;

/**  
 * <p>Title: GetUdpDataTask</p>  
 * <p>Description: 气象站数据上传任务</p>  
 * @author YangZhenfu  
 * @date 2018年5月8日  
 */
@Component
public class AirDataUploadJob implements Job{

	@Autowired
	private IAirStationService airStationService;
	@Autowired
	private IAirSensorService airSensorService;
	@Autowired
	private IAirStationDataService airStationDataService;
	@Autowired
	private IAirSensorAlarmInfoService sensorAlarmInfoService;
	
	private Logger logger = LoggerFactory.getLogger(AirDataUploadJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("气象站数据上传：启动任务======================="+DateUtil.formatFullDateTime(new Date()));
	    JobDataMap map = context.getJobDetail().getJobDataMap();
	    String deviceCode = map.getString("deviceCode");
	    logger.info("code --> "+deviceCode);
	    run(deviceCode);
	    logger.info("气象站数据上传：下次执行时间====="+
	        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
	            .format(context.getNextFireTime())+"==============");
	}
	
	public void run(String deviceCode){
		if(StringUtils.isNotBlank(deviceCode)){
			//查询气象站
			AirStation station = airStationService.selectOne(new EntityWrapper<AirStation>().eq("code", deviceCode).eq("valid", "0"));
			if(station!=null){
				System.out.println("============="+station.gettName()+"=============");
				
				//查询所有的传感器
				List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()));
				if(CollectionUtils.isNotEmpty(sensors)){
					GetUdpDataTask task = new GetUdpDataTask(station.getIpAddr(), Integer.parseInt(station.getPort()));
					DatagramSocket socket=null;
					ExecutorService service = Executors.newCachedThreadPool();
					try {
						socket = new DatagramSocket();
						
						AirStationData data=new AirStationData();
						for(AirSensor sensor : sensors){
							//获取传感器查询指令
							String command = sensor.getCommand();
							if(StringUtils.isNotBlank(command)){
								//发送查询指令
								Future<String> future = service.submit(task.new GetUdpDataThread(socket, command));
								
								Object[] status = task.getEntity(future.get(), data,sensor);
								if(!Convert.toBool(status[0])){
									
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
								}else{
									//更新传感器状态
									sensor.setStatus("0");//正常
									sensor.setUpdateTime(new Date());
									airSensorService.updateById(sensor);
									
									//更改传感器报警信息状态
									//查询报警信息是否存在
									List<AirSensorAlarmInfo> alarms = sensorAlarmInfoService.selectList(new EntityWrapper<AirSensorAlarmInfo>().eq("sensor_id", sensor.getId()).eq("valid", "0").eq("alarm_type", "0").eq("handle_state", "0"));
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
						//如果数据不为空  则存入数据库
						if(!Contrast.isAllFieldNull(data)){
							//将数据存入数据库
							data.setStationId(station.getId());
							data.setHeartbeatTime(new Date());
							data.settName(station.gettName()+"-"+DateUtil.formatFullDateTime(new Date()));
							airStationDataService.insert(data);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage(), e);
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
	
	
	
}
