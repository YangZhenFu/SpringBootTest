package com.stylefeng.guns.modular.air.dto;

import java.io.Serializable;
import java.util.Date;

/**  
 * <p>Title: AirSensorDataDto</p>  
 * <p>Description: 数据显示封装类</p>  
 * @author YangZhenfu  
 * @date 2018年4月26日  
 */
public class AirSensorDataDto implements Serializable{

	
	/** serialVersionUID*/
	private static final long serialVersionUID = -3970573635660019835L;
	/**
     * 唯一标识
     */
    private String id;
    /**
     * 传感器编号
     */
    private String code;
    /**
     * 传感器名称
     */
    private String tName;
    
    
    /**
     * 传感器类型名称
     */
    private String  typeName;
   
   /**
    * 检测单位
    */
    private String unit;
   
    /**
     * 最小值
     */
    private String minNumerical;
    /**
     * 最小值时间
     */
    private Date minTime;
   
    
    /**
     * 最小值
     */
    private String maxNumerical;
    /**
     * 最小值时间
     */
    private Date maxTime;
    
    /**
     * 当前数值
     */
    private String nowNumerical;
    
    //图标
    private String icon;
    
    
	/**
	 * @return the id  
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the tName  
	 */
	public String gettName() {
		return tName;
	}
	/**
	 * @param tName the tName to set
	 */
	public void settName(String tName) {
		this.tName = tName;
	}
	/**
	 * @return the typeName  
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @return the unit  
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the minNumerical  
	 */
	public String getMinNumerical() {
		return minNumerical;
	}
	/**
	 * @param minNumerical the minNumerical to set
	 */
	public void setMinNumerical(String minNumerical) {
		this.minNumerical = minNumerical;
	}
	/**
	 * @return the minTime  
	 */
	public Date getMinTime() {
		return minTime;
	}
	/**
	 * @param minTime the minTime to set
	 */
	public void setMinTime(Date minTime) {
		this.minTime = minTime;
	}
	/**
	 * @return the maxNumerical  
	 */
	public String getMaxNumerical() {
		return maxNumerical;
	}
	/**
	 * @param maxNumerical the maxNumerical to set
	 */
	public void setMaxNumerical(String maxNumerical) {
		this.maxNumerical = maxNumerical;
	}
	/**
	 * @return the maxTime  
	 */
	public Date getMaxTime() {
		return maxTime;
	}
	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(Date maxTime) {
		this.maxTime = maxTime;
	}
	/**
	 * @return the nowNumerical  
	 */
	public String getNowNumerical() {
		return nowNumerical;
	}
	/**
	 * @param nowNumerical the nowNumerical to set
	 */
	public void setNowNumerical(String nowNumerical) {
		this.nowNumerical = nowNumerical;
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	@Override
	public String toString() {
		return "AirSensorDataDto [id=" + id + ", code=" + code + ", tName=" + tName + ", typeName=" + typeName
				+ ", unit=" + unit + ", minNumerical=" + minNumerical + ", minTime=" + minTime + ", maxNumerical="
				+ maxNumerical + ", maxTime=" + maxTime + ", nowNumerical=" + nowNumerical + "]";
	}
	
	
	
    
	
}
