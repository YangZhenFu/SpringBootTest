package com.stylefeng.guns.core.common.constant;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.stylefeng.guns.core.util.StringConvert;
import com.stylefeng.guns.modular.air.model.AirStationData;

import cn.hutool.core.bean.BeanUtil;

/**  
 * <p>Title: SensorTypeEnum</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年5月4日  
 */
public enum SensorTypeEnum {

	air_temperature("air_temperature","大气温度","wendu"),
	air_humidity("air_humidity","大气湿度","shidu"),
	soil_temperature("soil_temperature","土壤温度","wendu"),
	soil_humidity("soil_humidity","土壤湿度","shidu"),
	illuminance("illuminance","照度",""),
	rainfall("rainfall","雨量","shidu"),
	air_pressure("air_pressure","大气压","qiya"),
	wind_speed("wind_speed","风速","fengsu"),
	wind_direction("wind_direction","风向","fengxiang"),
	noise("noise","噪声","zaosheng"),
	pm_10("pm_10","PM10","PM10"),
	pm_25("pm_25","PM2.5","PM25"),
	pm_1("pm_1","PM1.0",""),
	co("co","CO","CO"),
	o3("o3","O3","O3"),
	so2("so2","SO2","SO2"),
	no2("no2","NO2","NO2"),
	radiation("radiation","辐射",""),
	negative_oxygen_ion("negative_oxygen_ion","负氧离子","");
	
	private String code;
	private String msg;
	//图标
	private String icon;
	
	private SensorTypeEnum(String code,String msg,String icon){
		this.code=code;
		this.msg=msg;
		this.setIcon(icon);
	}

	/**
	 * @return the code  
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the msg  
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	/**
	 * <p>Title: findSensorTypeByName</p>  
	 * <p>Description: 根据类型查询名称</p>  
	 * @param msg
	 * @return
	 */
	public static SensorTypeEnum findSensorTypeByName(String msg){
		SensorTypeEnum[] enums = SensorTypeEnum.values();
		for(SensorTypeEnum type : enums){
			if(StringUtils.equals(type.getMsg(), msg)){
				return type;
			}
		}
		return null;
	}
	
	/**
	 * <p>Title: findDataBySensorType</p>  
	 * <p>Description: 根据传感器类型获取数据</p>  
	 * @param msg
	 * @param data
	 * @return
	 */
	public static Object findDataBySensorType(String code,AirStationData data){
		if(StringUtils.isNotBlank(code)){
			Map<String, Object> map = BeanUtil.beanToMap(data, false, true);
			//将code转为驼峰
			return map.get(StringConvert.underlineToCamelhump(code));
		}
		return null;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
