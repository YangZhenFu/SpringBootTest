package com.stylefeng.guns.modular.air.dto;

import java.io.Serializable;
import java.util.Date;

/**  
 * <p>Title: AirSensorCurveDataDto</p>  
 * <p>Description: 传感器曲线分析dto</p>  
 * @author YangZhenfu  
 * @date 2018年4月27日  
 */
public class AirSensorCurveDataDto implements Serializable{

	/** serialVersionUID*/
	private static final long serialVersionUID = 4068124433224204712L;
	
	
	
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
     * 检测数值
     */
    private String numerical;
    
    /**
     * 接受时间
     */
    private Date receiveTime;
    
   //观测日期（用于Echarts显示格式）
    private String dateStr;
    
    //风向信息（用于Echarts显示）
    private String windDirectionMsg;

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
	 * @return the numerical  
	 */
	public String getNumerical() {
		return numerical;
	}

	/**
	 * @param numerical the numerical to set
	 */
	public void setNumerical(String numerical) {
		this.numerical = numerical;
	}

	/**
	 * @return the receiveTime  
	 */
	public Date getReceiveTime() {
		return receiveTime;
	}

	/**
	 * @param receiveTime the receiveTime to set
	 */
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	/**
	 * @return the dateStr  
	 */
	public String getDateStr() {
		return dateStr;
	}

	/**
	 * @param dateStr the dateStr to set
	 */
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	/**
	 * @return the windDirectionMsg  
	 */
	public String getWindDirectionMsg() {
		return windDirectionMsg;
	}

	/**
	 * @param windDirectionMsg the windDirectionMsg to set
	 */
	public void setWindDirectionMsg(String windDirectionMsg) {
		this.windDirectionMsg = windDirectionMsg;
	}

	@Override
	public String toString() {
		return "AirSensorCurveDataDto [id=" + id + ", code=" + code + ", tName=" + tName + ", typeName=" + typeName
				+ ", unit=" + unit + ", numerical=" + numerical + ", receiveTime=" + receiveTime + ", dateStr="
				+ dateStr + ", windDirectionMsg=" + windDirectionMsg + "]";
	}
    

}
