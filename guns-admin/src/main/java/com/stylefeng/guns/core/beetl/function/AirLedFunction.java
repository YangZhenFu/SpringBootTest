package com.stylefeng.guns.core.beetl.function;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.stylefeng.guns.core.common.constant.Constant;

/**  
 * <p>Title: AirLedFunction</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年5月18日  
 */
@Component
@DependsOn("springContextHolder")
public class AirLedFunction {

	
	public Map<String,String> findAllLedType(){
		return Constant.air_led_type;
	}
	
	public List<String> findAllControlCardType(){
		return Constant.control_card_type;
	}
}
