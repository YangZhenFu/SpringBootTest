package com.stylefeng.guns.core.beetl.function;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.stylefeng.guns.core.common.constant.Constant;

/**  
 * <p>Title: AirStationFunction</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月17日  
 */
@Component
@DependsOn("springContextHolder")
public class AirStationFunction {

	//获取所有气象站类型
	public Map<String,String> findAllAirStationType(){
		return Constant.AIR_STATION_TYPE;
	}
	
	//获取所有的气象站通讯方式
	public List<String> findAllConnMethod(){
		return Constant.machine_conn_mode;
	}
}
