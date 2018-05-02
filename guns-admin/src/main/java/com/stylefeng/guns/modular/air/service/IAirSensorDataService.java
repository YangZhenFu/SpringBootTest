package com.stylefeng.guns.modular.air.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorData;

/**
 * <p>
 * 传感器检测数据表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-20
 */
public interface IAirSensorDataService extends IService<AirSensorData> {

	/**  
	 * <p>Title: findDataByParams</p>  
	 * <p>Description: </p>  
	 * @param page
	 * @param params
	 * @param orderByField
	 * @param asc
	 * @return  
	 */ 
	public List<Map<String, Object>> findDataByParams(Page<AirSensorData> page, String condition,String areaId,String beginTime,String endTime,
			String orderByField, boolean isAsc);

	/**  
	 * <p>Title: queryDataByParams</p>  
	 * <p>Description: </p>  
	 * @param condition
	 * @param areaId
	 * @param beginTime
	 * @param endTime
	 * @return  
	 */ 
	public List<Map<String, Object>> queryDataByParams(String condition, String areaId, String beginTime,
			String endTime);

	/**  
	 * <p>Title: selectOneDayMaxData</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return  
	 */ 
	public AirSensorData selectOneDayMaxData(Long id);

	/**  
	 * <p>Title: selectOneDayMinData</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return  
	 */ 
	public AirSensorData selectOneDayMinData(Long id);

	/**  
	 * <p>Title: selectMaxHeatbeatTime</p>  
	 * <p>Description: </p>  
	 * @param sensors
	 * @return  
	 */ 
	public Date selectMaxHeatbeatTime(List<AirSensor> sensors);

}
