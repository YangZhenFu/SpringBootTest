package com.stylefeng.guns.modular.air.warpper;

import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.beetl.function.AirSensorFunction;
import com.stylefeng.guns.core.beetl.function.AirStationFunction;
import com.stylefeng.guns.core.common.constant.Constant;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**  
 * <p>Title: AirSensorWarpper</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月19日  
 */
public class AirSensorWarpper extends BaseControllerWarpper{

	private AirStationFunction airStationFunction=SpringContextHolder.getBean(AirStationFunction.class);
	private AirSensorFunction airSensorFunction=SpringContextHolder.getBean(AirSensorFunction.class);
	
	public AirSensorWarpper(Object obj) {
		super(obj);
	}
	@Override
	protected void warpTheMap(Map<String, Object> map) {
		map.put("type", airSensorFunction.findTypeById((Long)map.get("typeId")).gettName());
		map.put("stationName", airStationFunction.findById((Long)map.get("stationId")).gettName());
		map.put("status", Constant.DEVICE_STATUS.get(map.get("status")));
	}

}
