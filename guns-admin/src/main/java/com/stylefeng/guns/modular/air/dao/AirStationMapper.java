package com.stylefeng.guns.modular.air.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.modular.air.model.AirStation;

/**
 * <p>
 * 气象站表 Mapper 接口
 * </p>
 *
 * @author YangZhenfu123
 * @since 2018-04-17
 */
public interface AirStationMapper extends BaseMapper<AirStation> {

	/**  
	 * <p>Title: selectByParams</p>  
	 * <p>Description: </p>  
	 * @param page
	 * @param condition
	 * @param orderByField
	 * @param asc
	 * @return  
	 */ 
	public List<Map<String, Object>> selectByParams(@Param("page") Page<AirStation> page, @Param("condition") String condition, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

}
