package com.stylefeng.guns.modular.air.service;

import com.stylefeng.guns.modular.air.model.AirLed;
import com.stylefeng.guns.modular.air.model.AirStationData;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * LED终端表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-18
 */
public interface IAirLedService extends IService<AirLed> {

	/**  
	 * <p>Title: saveAirLed</p>  
	 * <p>Description: </p>  
	 * @param airLed
	 * @return  
	 */ 
	public int saveAirLed(AirLed airLed);

	/**  
	 * <p>Title: deleteAirLed</p>  
	 * <p>Description: </p>  
	 * @param airLedId
	 * @return  
	 */ 
	public int deleteAirLed(Integer airLedId);

	/**  
	 * <p>Title: setScreenStatus</p>  
	 * <p>Description: </p>  
	 * @param ids
	 * @param brightness
	 * @param mode
	 * @return  
	 */ 
	public Map<String, Object> setScreenStatus(String ids, Integer brightness, String mode);

	/**
	 * <p>Title: releaseAirStationData</p>  
	 * <p>Description: </p>  
	 * @param airStationData
	 * @param airLed
	 */
	public Map<String,Object> releaseAirStationData(Object data, AirLed airLed) ;
	
	/**
	 * <p>Title: stopReleaseAirData</p>  
	 * <p>Description: </p>  
	 * @param led
	 * @return
	 */
	public Map<String, Object> stopReleaseAirData(AirLed led);

	/**  
	 * <p>Title: changeLedControlMode</p>  
	 * <p>Description: </p>  
	 * @param led
	 * @return  
	 */ 
	public Map<String, Object> changeLedControlMode(AirLed led);
	
	

}
