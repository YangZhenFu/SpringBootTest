package com.stylefeng.guns.modular.air.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.air.model.AirSensor;

/**
 * <p>
 * 传感器表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-19
 */
public interface IAirSensorService extends IService<AirSensor> {

	/**  
	 * <p>Title: saveAirSensor</p>  
	 * <p>Description: </p>  
	 * @param airSensor
	 * @param installTimes
	 * @return  
	 */ 
	public int saveAirSensor(AirSensor airSensor, String installTimes);

	/**  
	 * <p>Title: deleteAirSensor</p>  
	 * <p>Description: </p>  
	 * @param airSensorId
	 * @return  
	 */ 
	public int deleteAirSensor(Integer airSensorId);

	/**  
	 * <p>Title: findListByParams</p>  
	 * <p>Description: </p>  
	 * @param page
	 * @param condition
	 * @param orderByField
	 * @param asc
	 * @return  
	 */ 
	public List<Map<String, Object>> findListByParams(Page<AirSensor> page, String condition, String orderByField,
			boolean isAsc);

	/**  
	 * <p>Title: checkOnline</p>  
	 * <p>Description: </p>  
	 * @param ids
	 * @return  
	 */ 
	public Map<String, Object> checkOnline(String ids);

}
