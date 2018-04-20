package com.stylefeng.guns.core.beetl.function;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.common.constant.Constant;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.service.IAirStationService;

/**  
 * <p>Title: AirStationFunction</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月17日  
 */
@Component
@DependsOn("springContextHolder")
public class AirStationFunction {
	
	private IAirStationService airStationService = SpringContextHolder.getBean(IAirStationService.class);

	//获取所有气象站类型
	public Map<String,String> findAllAirStationType(){
		return Constant.AIR_STATION_TYPE;
	}
	
	//获取所有的气象站通讯方式
	public List<String> findAllConnMethod(){
		return Constant.machine_conn_mode;
	}
	
	
	public AirStation findById(Long id){
		return airStationService.selectById(id);
	}
	
	public Map<Long,AirStation> findAllAirStation(){
		Map<Long,AirStation> result=Maps.newHashMap();
		List<AirStation> list = airStationService.selectList(new EntityWrapper<AirStation>().eq("valid", "0"));
		if(CollectionUtils.isNotEmpty(list)){
			for(AirStation station : list){
				result.put(station.getId(), station);
			}
		}
		return result;
	}
	
}
