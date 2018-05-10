package com.stylefeng.guns.modular.air.warpper;

import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.beetl.function.AirSensorFunction;
import com.stylefeng.guns.core.common.constant.Constant;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.air.service.IAirSensorService;

/**  
 * <p>Title: AirSensorWarnParamWarpper</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月24日  
 */
public class AirSensorWarnParamWarpper extends BaseControllerWarpper{

	private AirSensorFunction airSensorFunction=SpringContextHolder.getBean(AirSensorFunction.class);
	
	private IAirSensorService airSensorService=SpringContextHolder.getBean(IAirSensorService.class);
	
	/**  
	 * <p>Title: </p>  
	 * <p>Description: </p>  
	 * @param obj  
	 */  
	public AirSensorWarnParamWarpper(Object obj) {
		super(obj);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {
		map.put("sensorName", airSensorService.selectById((Long)map.get("sensorId")).gettName());
		map.put("valid", "0".equals(map.get("valid")) ? "启用" : "禁用");
		map.put("expression", Constant.SENSOR_WARN_EXPRESSION_TYPE.get(map.get("expression")));
		map.put("controlMode", "0".equals(map.get("controlMode")) ? "开启" : "关闭");
	}

}
