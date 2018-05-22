package com.stylefeng.guns.modular.air.warpper;

import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.beetl.function.AirStationFunction;
import com.stylefeng.guns.core.common.constant.Constant;
import com.stylefeng.guns.core.util.SpringContextHolder;

/**  
 * <p>Title: AirLedWarpper</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年5月18日  
 */
public class AirLedWarpper extends BaseControllerWarpper{

	private AirStationFunction airStationFunction=SpringContextHolder.getBean(AirStationFunction.class);
	
	
	public AirLedWarpper(Object obj) {
		super(obj);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {
		map.put("type", Constant.air_led_type.get(map.get("type")));
		map.put("stationId", airStationFunction.findById((Long)map.get("stationId")).gettName());
		map.put("status", Constant.DEVICE_STATUS.get(map.get("status")));
		map.put("screenStatus", "0".equals(map.get("screenStatus")) ? "关机" : "开机");
		map.put("controlMode", "0".equals(map.get("controlMode")) ? "开启" : "关闭");
	}

}
