package com.stylefeng.guns.modular.air.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.modular.air.model.AirSensorWarnParam;

/**
 * <p>
 * 传感器预警参数表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-24
 */
public interface IAirSensorWarnParamService extends IService<AirSensorWarnParam> {

	/**  
	 * <p>Title: saveSensorWarnParam</p>  
	 * <p>Description: </p>  
	 * @param airSensorWarnParam
	 * @return  
	 */ 
	public int saveSensorWarnParam(AirSensorWarnParam airSensorWarnParam);

	/**  
	 * <p>Title: deleteWarnParamById</p>  
	 * <p>Description: </p>  
	 * @param airSensorWarnParamId
	 * @return  
	 */ 
	public int deleteWarnParamById(Integer airSensorWarnParamId);


}
