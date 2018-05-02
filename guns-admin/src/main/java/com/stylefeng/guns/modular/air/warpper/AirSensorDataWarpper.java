package com.stylefeng.guns.modular.air.warpper;

import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.air.service.IAirSensorService;

/**  
 * <p>Title: AirSensorDataWarpper</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月25日  
 */
public class AirSensorDataWarpper extends BaseControllerWarpper{

	private IAirSensorService airSensorService=SpringContextHolder.getBean(IAirSensorService.class);
	/**  
	 * <p>Title: </p>  
	 * <p>Description: </p>  
	 * @param obj  
	 */  
	public AirSensorDataWarpper(Object obj) {
		super(obj);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {
		map.put("sensorName", airSensorService.selectById((Long)map.get("sensorId")).gettName());
		map.put("unit", airSensorService.selectById((Long)map.get("sensorId")).getUnit());
	}

}
