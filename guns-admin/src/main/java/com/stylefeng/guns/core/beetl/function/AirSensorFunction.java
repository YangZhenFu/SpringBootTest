package com.stylefeng.guns.core.beetl.function;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.air.model.SensorType;
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
}
