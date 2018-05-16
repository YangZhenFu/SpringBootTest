package com.stylefeng.guns.modular.air.service.impl;

import java.net.DatagramSocket;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.common.constant.Constant;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.modular.air.dao.AirSensorAlarmInfoMapper;
import com.stylefeng.guns.modular.air.dao.AirSensorMapper;
import com.stylefeng.guns.modular.air.dao.AirStationMapper;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorAlarmInfo;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.task.GetUdpDataTask;

/**
 * <p>
 * 传感器表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-19
 */
@Service
public class AirSensorServiceImpl extends ServiceImpl<AirSensorMapper, AirSensor> implements IAirSensorService {

	@Autowired
	private AirStationMapper airStationMapper;
	@Autowired
	private AirSensorAlarmInfoMapper sensorAlarmInfoMapper;
	
	@Override
	public int saveAirSensor(AirSensor airSensor, String installTimes) {
		int count=0;
		if(airSensor!=null){
			
			if(StringUtils.isNotBlank(installTimes)){
				airSensor.setInstallTime(DateUtil.parse(installTimes, "yyyy-MM-DD"));
			}
			
			if(airSensor.getId()==null){
				airSensor.setCode(StringUtil.generatorShort());
				airSensor.setCreateBy(ShiroKit.getUser().getName());
				airSensor.setCreateTime(new Date());
				count=baseMapper.insert(airSensor);
			}else{
				airSensor.setUpdateBy(ShiroKit.getUser().getName());
				airSensor.setUpdateTime(new Date());
				count=baseMapper.updateById(airSensor);
			}
		}
		return count;
	}

	@Override
	public int deleteAirSensor(Integer airSensorId) {
		int count=0;
		if(airSensorId!=null){
			AirSensor airSensor = baseMapper.selectById(airSensorId);
			if(airSensor!=null){
				airSensor.setValid("1");
				airSensor.setUpdateBy(ShiroKit.getUser().getName());
				airSensor.setUpdateTime(new Date());
				count=baseMapper.updateById(airSensor);
			}
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> findListByParams(Page<AirSensor> page, String condition, String orderByField,
			boolean isAsc) {
		return baseMapper.findListByParams(page,condition,orderByField,isAsc);
	}

	/* (non-Javadoc)  
	 * <p>Title: checkOnline</p>  
	 * <p>Description: 检查传感器是否在线</p>  
	 * @param ids
	 * @return  
	 * @see com.stylefeng.guns.modular.air.service.IAirSensorService#checkOnline(java.lang.String)  
	 */
	@Override
	public Map<String, Object> checkOnline(String ids) {
		Map<String,Object> result=Maps.newHashMap();
		ExecutorService service = Executors.newCachedThreadPool();
		DatagramSocket socket=null;
		try {
			socket=new DatagramSocket();
			if(StringUtils.isNotBlank(ids)){
				String[] data = ids.split(";");
				if(data.length>0){
					
					StringBuilder msg1=new StringBuilder();//是否存在
					StringBuilder msg2=new StringBuilder();//是否在线
					for(String id : data){
						AirSensor sensor = baseMapper.selectById(Long.valueOf(id));
						
						if(sensor!=null){
							//查询传感器所属气象站
							AirStation station = airStationMapper.selectById(sensor.getStationId());
							if(station!=null){
								GetUdpDataTask task=new GetUdpDataTask(station.getIpAddr(), Integer.parseInt(station.getPort()));
								task.RETRIES=1;
								
								Future<String> future = service.submit(task.new GetUdpDataThread(socket, sensor.getCommand()));
								//未在线
								if(StringUtils.equals(future.get().substring(0, 23), "00 00 00 00 00 00 00 00")){
									msg2.append(sensor.gettName()).append(" ");
									
									//更新传感器状态
									sensor.setStatus("2");//通讯故障
									sensor.setUpdateBy(ShiroKit.getUser().getName());
									sensor.setUpdateTime(new Date());
									baseMapper.updateById(sensor);
									
									
									//新增设备报警信息
									//查询报警信息是否存在
									List<AirSensorAlarmInfo> alarms = sensorAlarmInfoMapper.selectList(new EntityWrapper<AirSensorAlarmInfo>().eq("sensor_id", sensor.getId()).eq("valid", "0").eq("alarm_type", "0").eq("handle_state", "0"));
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
										sensorAlarmInfoMapper.insert(alarm);
									}
									
									continue;
								}
								
								
								//更新传感器状态
								sensor.setStatus("0");//正常
								sensor.setUpdateBy(ShiroKit.getUser().getName());
								sensor.setUpdateTime(new Date());
								baseMapper.updateById(sensor);
								
								//更改传感器报警信息状态
								//查询报警信息是否存在
								List<AirSensorAlarmInfo> alarms = sensorAlarmInfoMapper.selectList(new EntityWrapper<AirSensorAlarmInfo>().eq("sensor_id", sensor.getId()).eq("valid", "0").eq("alarm_type", "0").eq("handle_state", "0"));
								if(CollectionUtils.isNotEmpty(alarms)){
									for(AirSensorAlarmInfo info : alarms){
										info.setHandleState("1");//已恢复
										info.setHandleContent("手动恢复");
										info.setHandleName(ShiroKit.getUser().getName());
										info.setHandleTime(new Date());
										sensorAlarmInfoMapper.updateById(info);
									}
								}
								
								
								
							}
							
							
						}else{
							msg1.append(id).append(" ");
						}
						
					}
						
					//返回结果
					StringBuilder message=new StringBuilder(); 
					if(StringUtils.isNotBlank(msg1)){
						message.append(msg1).append("传感器不存在！").append("\n");
					}
					if(StringUtils.isNotBlank(msg2)){
						message.append(msg2).append("未在线！请检查").append("\n");
					}
					if(StringUtils.isNotBlank(message)){
						result.put("msg", message.toString());
						return result;
					}
					else{
						result.put("code", "000000");
						return result;
					}
				}
			}else{
				result.put("msg", "参数不能为空!");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "服务器繁忙，请联系管理员！");
			return result;
		} finally{
			service.shutdown();
			if(socket!=null){
				socket.close();
			}
		}
		return result;
	}

}
