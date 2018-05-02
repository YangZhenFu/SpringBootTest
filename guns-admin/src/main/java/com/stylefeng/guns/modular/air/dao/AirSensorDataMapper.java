package com.stylefeng.guns.modular.air.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorData;

/**
 * <p>
 * 传感器检测数据表 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-20
 */
public interface AirSensorDataMapper extends BaseMapper<AirSensorData> {
	
	
	/**  
	 * <p>Title: selectListByParams</p>  
	 * <p>Description: </p>  
	 * @param page
	 * @param params
	 * @param orderByField
	 * @param isAsc
	 * @return  
	 */ 
	public List<Map<String, Object>> selectListByParams(@Param("page") Page<AirSensorData> page, @Param("condition") String condition,@Param("areaId") String areaId,@Param("beginTime") String beginTime,@Param("endTime") String endTime,
			@Param("orderByField")String orderByField, @Param("isAsc")boolean isAsc);

	/**  
	 * <p>Title: queryListByParams</p>  
	 * <p>Description: </p>  
	 * @param condition
	 * @param areaId
	 * @param beginTime
	 * @param endTime
	 * @return  
	 */ 
	public List<Map<String, Object>> queryListByParams(@Param("condition") String condition, @Param("areaId") String areaId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);

	/**  
	 * <p>Title: findOneDayMaxData</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return  
	 */ 
	public AirSensorData findOneDayMaxData(Long id);

	/**  
	 * <p>Title: findOneDayMinData</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return  
	 */ 
	public AirSensorData findOneDayMinData(Long id);

	/**  
	 * <p>Title: findMaxHeatbeatTime</p>  
	 * <p>Description: </p>  
	 * @param sensors
	 * @return  
	 */ 
	public Date findMaxHeatbeatTime(List<AirSensor> sensors);

}
