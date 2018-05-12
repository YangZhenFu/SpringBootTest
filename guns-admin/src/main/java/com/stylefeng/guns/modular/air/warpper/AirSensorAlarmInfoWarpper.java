package com.stylefeng.guns.modular.air.warpper;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.Constant;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.air.service.IAirSensorService;

/**  
 * <p>Title: AirSensorAlarmInfoWarpper</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年5月10日  
 */
public class AirSensorAlarmInfoWarpper extends BaseControllerWarpper {

	private IAirSensorService airSensorService=SpringContextHolder.getBean(IAirSensorService.class);
	
	public AirSensorAlarmInfoWarpper(Object obj) {
		super(obj);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {
		map.put("sensorName", airSensorService.selectById((Long)map.get("sensorId")).gettName());
		map.put("alarmType", Constant.sensor_exception_type.get(map.get("alarmType")));
		map.put("handleState", StringUtils.equals((String)map.get("handleState"), "0") ? "未恢复" : "已恢复");
	}

}
