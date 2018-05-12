package com.stylefeng.guns.modular.air.service;

import com.stylefeng.guns.modular.air.model.AirSensorAlarmInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 传感器告警信息表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-10
 */
public interface IAirSensorAlarmInfoService extends IService<AirSensorAlarmInfo> {

	/**  
	 * <p>Title: saveSensorAlarmInfo</p>  
	 * <p>Description: </p>  
	 * @param airSensorAlarmInfo
	 * @return  
	 */ 
	public int saveSensorAlarmInfo(AirSensorAlarmInfo airSensorAlarmInfo,String alarmTimes);

	/**  
	 * <p>Title: deleteSensorAlarmInfo</p>  
	 * <p>Description: </p>  
	 * @param airSensorAlarmInfoId
	 * @return  
	 */ 
	public int deleteSensorAlarmInfo(Integer airSensorAlarmInfoId);

	/**  
	 * <p>Title: handleAlarmInfo</p>  
	 * <p>Description: </p>  
	 * @param alarm
	 * @return  
	 */ 
	public int handleAlarmInfo(AirSensorAlarmInfo alarm);

}
