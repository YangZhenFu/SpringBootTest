package com.stylefeng.guns.core.common.constant;

import org.apache.commons.lang3.StringUtils;

/**  
 * <p>Title: SensorTypeEnum</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年5月4日  
 */
public enum SensorTypeEnum {

	air_temperature("air_temperature","大气温度"),
	air_humidity("air_humidity","大气湿度"),
	soil_temperature("soil_temperature","土壤温度"),
	soil_humidity("soil_humidity","土壤湿度"),
	illuminance("illuminance","照度"),
	rainfall("rainfall","雨量"),
	air_pressure("air_pressure","大气压"),
	wind_speed("wind_speed","风速"),
	wind_direction("wind_direction","风向"),
	noise("noise","噪声"),
	pm_10("pm_10","PM10"),
	pm_25("pm_25","PM2.5"),
	pm_1("pm_1","PM1.0"),
	co("co","CO"),
	o3("o3","O3"),
	so2("so2","SO2"),
	no2("no2","NO2"),
	radiation("radiation","辐射"),
	negative_oxygen_ion("negative_oxygen_ion","负氧离子");
	
	private String code;
	private String msg;
	
	private SensorTypeEnum(String code,String msg){
		this.code=code;
		this.msg=msg;
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
	
	
	public static SensorTypeEnum findSensorTypeByName(String msg){
		SensorTypeEnum[] enums = SensorTypeEnum.values();
		for(SensorTypeEnum type : enums){
			if(StringUtils.equals(type.getMsg(), msg)){
				return type;
			}
		}
		return null;
	}
	
	
}
