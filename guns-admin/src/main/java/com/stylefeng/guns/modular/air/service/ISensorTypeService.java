package com.stylefeng.guns.modular.air.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.air.model.SensorType;

/**
 * <p>
 * 传感器类型表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-19
 */
public interface ISensorTypeService extends IService<SensorType> {

	/**  
	 * <p>Title: saveSensorType</p>  
	 * <p>Description: </p>  
	 * @param sensorType
	 * @return  
	 */ 
	public Integer saveSensorType(SensorType sensorType);

	/**  
	 * <p>Title: deleteSensorTypeById</p>  
	 * <p>Description: </p>  
	 * @param sensorTypeId
	 * @return  
	 */ 
	public Integer deleteSensorTypeById(Integer sensorTypeId);

}
