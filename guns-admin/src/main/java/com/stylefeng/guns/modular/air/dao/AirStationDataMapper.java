package com.stylefeng.guns.modular.air.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.modular.air.model.AirStationData;

/**
 * <p>
 * 气象站检测数据表 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-02
 */
public interface AirStationDataMapper extends BaseMapper<AirStationData> {

	/**  
	 * <p>Title: selectDataByParams</p>  
	 * <p>Description: </p>  
	 * @param page
	 * @param condition
	 * @param areaId
	 * @param beginTime
	 * @param endTime
	 * @param orderByField
	 * @param isAsc
	 * @return  
	 */ 
	public List<Map<String, Object>> selectPageDataByParams(@Param("page") Page<AirStationData> page, @Param("condition") String condition, @Param("areaId") String areaId,
			@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

	/**  
	 * <p>Title: selectDataByParams</p>  
	 * <p>Description: </p>  
	 * @param code
	 * @param beginTime
	 * @param endTime
	 * @return  
	 */ 
	public List<AirStationData> selectDataByParams(@Param("condition") String condition,@Param("code") String code, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

	/**  
	 * <p>Title: selectMapDataByParams</p>  
	 * <p>Description: </p>  
	 * @param condition
	 * @param areaId
	 * @param beginTime
	 * @param endTime
	 * @return  
	 */ 
	public List<Map<String, Object>> selectMapDataByParams(@Param("condition") String condition, @Param("areaId") String areaId,
			@Param("beginTime") String beginTime, @Param("endTime") String endTime);

	/**  
	 * <p>Title: findOneDayData</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return  
	 */ 
	public List<AirStationData> findOneDayData(@Param("id") Long id);
	
	/**
	 * <p>Title: findFiveDaysData</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return
	 */
	public List<AirStationData> findFiveDaysData(@Param("id") Long id);

}
