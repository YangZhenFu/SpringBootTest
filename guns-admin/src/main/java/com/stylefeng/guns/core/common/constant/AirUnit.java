package com.stylefeng.guns.core.common.constant;

public enum AirUnit {
	
	TEMPERATURE("℃"),
	SOIL_TEMPERATURE("℃"),
	HUMIDITY("%RH"),
	SOIL_HUMIDITY("%RH"),
	ILLUMINANCE("Lux"),
	RAINFALL("mm"),
	AIR_PRESSURE("kPa"),
	WIND_SPEED("m/s"),
	PM("μg/m3"),
	CO2("ppm"),
	GAS("ppm"),
	CO("mg/m3"),
	WIND_DIRECTION("。"),
	RADIATION("w/m2"),
	negative_oxygen_ion("个/m3"),
	NOISE("dB");
	
	private String unit;
	
	private AirUnit(String unit){
		this.setUnit(unit);
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
