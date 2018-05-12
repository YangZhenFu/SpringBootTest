package com.stylefeng.guns.core.beetl.function;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.stylefeng.guns.core.common.constant.AirUnit;
import com.stylefeng.guns.core.common.constant.Constant;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.SensorType;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;

/**  
 * <p>Title: AirSensorFunction</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月19日  
 */
@Component
@DependsOn("springContextHolder")
public class AirSensorFunction {

	
	private ISensorTypeService sensorTypeService = SpringContextHolder.getBean(ISensorTypeService.class);
	
	private IAirSensorService airSensorService = SpringContextHolder.getBean(IAirSensorService.class);
	
	public Map<Long,AirSensor> findAllAirSensor(){
		Map<Long,AirSensor> result=Maps.newHashMap();
		List<AirSensor> list=airSensorService.selectList(new EntityWrapper<AirSensor>().eq("valid", "0"));
		if(CollectionUtils.isNotEmpty(list)){
			for(AirSensor sensor : list){
				result.put(sensor.getId(), sensor);
			}
		}
		return result;
	}
	
	public AirSensor findAirSensorById(Long id){
		return airSensorService.selectById(id);
	}
	
	public SensorType findTypeById(Long id){
		return sensorTypeService.selectById(id);
	}
	
	public Map<Long,SensorType> findAllAirSensorType(){
		Map<Long,SensorType> result=Maps.newHashMap();
		List<SensorType> list = sensorTypeService.selectList(new EntityWrapper<SensorType>().eq("valid", "0"));
		if(CollectionUtils.isNotEmpty(list)){
			for(SensorType type : list){
				result.put(type.getId(), type);
			}
		}
		return result;
	}
	
	public Map<String,String> findSensorExpression(){
		return Constant.SENSOR_WARN_EXPRESSION_TYPE;
	}
	
	//获取所有的气象检测单位
	public Set<String> findAllAirUnit(){
		Set<String> units=Sets.newHashSet();
		AirUnit[] values = AirUnit.values();
		if(values!=null){
			for(AirUnit unit : values){
				units.add(unit.getUnit());
			}
		}
		return units;
	}
	
	/**
	 * <p>Title: findSensorExceptionType</p>  
	 * <p>Description: 获取传感器异常告警类型</p>  
	 * @return
	 */
	public Map<String,String> findSensorExceptionType(){
		return Constant.sensor_exception_type;
	}
	
}
