package com.stylefeng.guns.core.common.constant;

/**
 * 	空气质量等级
 * @author fuwuqi
 *
 */
public enum AirQualityGrade {
	
	Level_1("#096","优",1),
	Level_2("#ffde33","良",2),
	Level_3("#ff9933","轻度污染",3),
	Level_4("#cc0033","中度污染",4),
	Level_5("#660099","重度污染",5),
	Level_6("#7e0023","严重污染",6);
	
	private String color;
	private String msg;
	private int level;
	
	private AirQualityGrade(String color,String msg,int level){
	
		this.color=color;
		this.msg=msg;
		this.level=level;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public static AirQualityGrade calculateGrade(Double code){
		if(code<=50){
			return AirQualityGrade.Level_1;
		}else if(code>50 && code<=100){
			return AirQualityGrade.Level_2;
		}else if(code>100 && code<=150){
			return AirQualityGrade.Level_3;
		}else if(code>150 && code<=200){
			return AirQualityGrade.Level_4;
		}else if(code>200 && code<=300){
			return AirQualityGrade.Level_5;
		}else if(code>300){
			return AirQualityGrade.Level_6;
		}
		return null;
	}

	

}
