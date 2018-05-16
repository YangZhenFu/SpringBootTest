package com.stylefeng.guns.modular.air.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.common.constant.SensorTypeEnum;
import com.stylefeng.guns.core.common.constant.WindDirection;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.model.SensorType;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationDataService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;

/**  
 * <p>Title: AirMapMonitorController</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月20日  
 */
@RequestMapping("air/map")
@Controller
public class AirMapMonitorController {

	private String PREFIX = "/air/monitor/";
	
	@Autowired
	private IAirStationService airStationService;
	@Autowired
	private IAirSensorService airSensorService;
	@Autowired
	private IAirStationDataService airStationDataService;
	@Autowired
	private ISensorTypeService sensorTypeService;
	
	@RequestMapping
	public String main(Model model){
		List<AirStation> list = airStationService.selectList(new EntityWrapper<AirStation>().eq("valid", "0"));
		//查询所有控制器的坐标
		List<String> result=Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(list)){
			for(AirStation station : list){
				result.add(station.getLongitude()+","+station.getLatitude());
			}
		}
		model.addAttribute("result", result);
		
		return PREFIX+"map.html";
	}
	
	/**
	 * <p>Title: selectAirStationByCoords</p>  
	 * <p>Description: 根据坐标查询气象站</p>  
	 * @param lng
	 * @param lat
	 * @return
	 */
	@RequestMapping(value="select",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> selectAirStationByCoords(String lng,String lat){
		AirStation station = airStationService.selectOne(new EntityWrapper<AirStation>().eq("longitude", lng).eq("latitude", lat).eq("valid", "0"));
		Map<String,Object> result=Maps.newHashMap();
		result.put("station", station);
		return result;
	}
	
	
	/**
	 * <p>Title: queryAirSensorData</p>  
	 * <p>Description: 查询传感器数据</p>  
	 * @param lng
	 * @param lat
	 * @return
	 */
//	@RequestMapping(value="queryData",method=RequestMethod.POST)
//	@ResponseBody
//	public Map<String,Object> queryAirSensorData(String lng,String lat){
//		Map<String,Object> result=Maps.newHashMap();
//		AirStation station = airStationService.selectOne(new EntityWrapper<AirStation>().eq("longitude", lng).eq("latitude", lat).eq("valid", "0"));
//		if(station!=null){
//			List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
//			if(CollectionUtils.isNotEmpty(sensors)){
//				for(AirSensor sensor : sensors){
//					List<AirSensorData> datas = airSensorDataService.selectList(new EntityWrapper<AirSensorData>().eq("sensor_id", sensor.getId()).eq("valid", "0").orderBy("heartbeat_time desc"));
//					if(CollectionUtils.isNotEmpty(datas)){
//						sensor.setSensorData(datas.get(0));
//					}
//				}
//				result.put("sensors", sensors);
//				result.put("refreshTime", new Date());
//			}
//			
//		}
//		result.put("station", station);
//		return result;
//	}
	
	
	/**
	 * <p>Title: queryAirSensorData</p>  
	 * <p>Description: 查询传感器数据</p>  
	 * @param lng
	 * @param lat
	 * @return
	 */
	@RequestMapping(value="queryData",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAirSensorData(String lng,String lat){
		Map<String,Object> result=Maps.newHashMap();
		AirStation station = airStationService.selectOne(new EntityWrapper<AirStation>().eq("longitude", lng).eq("latitude", lat).eq("valid", "0"));
		if(station!=null){
			List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
			if(CollectionUtils.isNotEmpty(sensors)){
				
				//查询气象站当前数据
				List<AirStationData> datas = airStationDataService.selectList(new EntityWrapper<AirStationData>().eq("station_id", station.getId()).orderBy("heartbeat_time desc"));
				if(CollectionUtils.isNotEmpty(datas)){
					AirStationData nowData = datas.get(0);
					for(AirSensor sensor : sensors){
						//查询传感器类型
						SensorType type = sensorTypeService.selectById(sensor.getTypeId());
						SensorTypeEnum typeEnum = SensorTypeEnum.findSensorTypeByName(type.gettName());
						if(typeEnum!=null){
							//检测数值
							String numerical = String.valueOf(SensorTypeEnum.findDataBySensorType(typeEnum.getCode(), nowData));
							if(type.gettName().contains("风向")){
								sensor.setNumerical(WindDirection.findWindDirectionByMark(numerical).getMsg());
							}else{
								sensor.setNumerical(numerical);
							}
						}
					}
					result.put("sensors", sensors);
					result.put("refreshTime", nowData.getHeartbeatTime());
				}
				
			}
			
		}
		result.put("station", station);
		return result;
	}
	
	
	/**
	 * <p>Title: layerAddStation</p>  
	 * <p>Description: 弹出气象站添加页面</p>  
	 * @return
	 */
	@RequestMapping(value="station_add",method=RequestMethod.GET)
	public String layerAddStation(){
		return PREFIX+"station_add.html";
	}
	
	
	
	
}
