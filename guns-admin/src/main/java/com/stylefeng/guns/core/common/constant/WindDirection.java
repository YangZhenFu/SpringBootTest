package com.stylefeng.guns.core.common.constant;

import org.apache.commons.lang.StringUtils;

public enum WindDirection {

	
	N("000F","正北","N"),
	NORTHEAST("0001","东北","NE"),
	EAST("0003","正东","E"),
	SOUTHEAST("0005","东南","SE"),
	SOUTH("0007","正南","S"),
	SOUTHWEST("0009","西南","SW"),
	WEST("000B","正西","W"),
	NORTHWEST("000D","西北","NW"),
	NORTHEAST_BY_NORTH("0000","东北偏北","NNE"),
	NORTHEAST_BY_EAST("0002","东北偏东","ENE"),
	SOUTHEAST_BY_EAST("0004","东南偏东","ESE"),
	SOUTHEAST_BY_SOUTH("0006","东南偏南","SSE"),
	SOUTHWEST_BY_SOUTH("0008","西南偏南","SSW"),
	SOUTHWEST_BY_WEST("000A","西南偏西","WSW"),
	NORTHWEST_BY_WEST("000C","西北偏西","WNW"),
	NORTHWEST_BY_NORTH("000E","西北偏北","NNW");
	
	private String code;
	private String msg;
	private String mark;
	
	private WindDirection(String code, String msg, String mark) {
		this.code=code;
		this.msg=msg;
		this.mark=mark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public static WindDirection findWindDirectionByMark(String mark){
		WindDirection[] values = WindDirection.values();
		for(WindDirection direct : values){
			if(StringUtils.equals(direct.getMark(), mark)){
				return direct;
			}
		}
		return null;
	}
	
}
