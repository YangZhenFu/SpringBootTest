package com.stylefeng.guns.modular.air.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.modular.air.model.AirSensor;


/**
 * <p>
 * 传感器表 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-19
 */
public interface AirSensorMapper extends BaseMapper<AirSensor> {

	/**  
	 * <p>Title: findListByParams</p>  
	 * <p>Description: </p>  
	 * @param page
	 * @param condition
	 * @param orderByField
	 * @param isAsc
	 * @return  
	 */ 
	public List<Map<String, Object>> findListByParams(@Param("page") Page<AirSensor> page, @Param("condition") String condition, @Param("orderByField") String orderByField,
			@Param("isAsc") boolean isAsc);

}
