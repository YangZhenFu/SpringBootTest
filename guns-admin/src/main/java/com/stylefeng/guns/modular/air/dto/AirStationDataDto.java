package com.stylefeng.guns.modular.air.dto;

import java.io.Serializable;


/**  
 * <p>Title: AirSensorDataDto</p>  
 * <p>Description: 数据显示封装类</p>  
 * @author YangZhenfu  
 * @date 2018年4月26日  
 */
public class AirStationDataDto implements Serializable{

	
	/** serialVersionUID*/
	private static final long serialVersionUID = -3970573635660019835L;
	/**
     * 唯一标识
     */
    private String id;
    
    /**
     * 气象站编号
     */
    private String code;
    
    /**
     * 气象站名称
     */
    private String tName;
   
    /**
     * 传感器个数
     */
    private Integer sensorNum;
    
    /**
     * 平均空气质量指数
     */
    private Integer AQI;
    
    /**
     * 空气质量等级
     */
    private Integer airGrade;
    
    
    /**
     * 大气温度（单位：℃）
     */
    private String airTemperature;
    /**
     * 大气湿度（单位：%rh）
     */
    private String airHumidity;
    /**
     * 土壤温度（单位：℃）
     */
    private String soilTemperature;
    /**
     * 土壤湿度（单位：%rh）
     */
    private String soilHumidity;
    /**
     * 照度（单位：Lux）
     */
    private String illuminance;
    /**
     * 雨量（单位：mm）
     */
    private String rainfall;
    /**
     * 气压（单位：hPa）
     */
    private String airPressure;
    /**
     * 风速（单位：m/s）
     */
    private String windSpeed;
    /**
     * 风向
     */
    private String windDirection;
    /**
     * 噪声值（单位：dB）
     */
    private String noise;
    /**
     * PM_10（单位：μm/m3）
     */
    private String pm10;
    /**
     * PM_2.5（单位：μm/m3）
     */
    private String pm25;
    /**
     * PM_1.0（单位：μm/m3）
     */
    private String pm1;
    /**
     * CO浓度（单位：ppm）
     */
    private String co;
    /**
     * O3浓度（单位：ppm）
     */
    private String o3;
    /**
     * SO2浓度（单位：ppm）
     */
    private String so2;
    /**
     * NO2浓度（单位：ppm）
     */
    private String no2;
    /**
     * 总辐射（单位：w/m2）
     */
    private String radiation;
    /**
     * 负氧离子（单位：个/m3）
     */
    private String negativeOxygenIon;
    
    
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
	 * @return the sensorNum  
	 */
	public Integer getSensorNum() {
		return sensorNum;
	}

	/**
	 * @param sensorNum the sensorNum to set
	 */
	public void setSensorNum(Integer sensorNum) {
		this.sensorNum = sensorNum;
	}

	/**
	 * @return the aQI  
	 */
	public Integer getAQI() {
		return AQI;
	}

	/**
	 * @param aQI the aQI to set
	 */
	public void setAQI(Integer aQI) {
		AQI = aQI;
	}

	/**
	 * @return the airGrade  
	 */
	public Integer getAirGrade() {
		return airGrade;
	}

	/**
	 * @param airGrade the airGrade to set
	 */
	public void setAirGrade(Integer airGrade) {
		this.airGrade = airGrade;
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
	 * @return the airTemperature  
	 */
	public String getAirTemperature() {
		return airTemperature;
	}

	/**
	 * @param airTemperature the airTemperature to set
	 */
	public void setAirTemperature(String airTemperature) {
		this.airTemperature = airTemperature;
	}

	/**
	 * @return the airHumidity  
	 */
	public String getAirHumidity() {
		return airHumidity;
	}

	/**
	 * @param airHumidity the airHumidity to set
	 */
	public void setAirHumidity(String airHumidity) {
		this.airHumidity = airHumidity;
	}

	/**
	 * @return the soilTemperature  
	 */
	public String getSoilTemperature() {
		return soilTemperature;
	}

	/**
	 * @param soilTemperature the soilTemperature to set
	 */
	public void setSoilTemperature(String soilTemperature) {
		this.soilTemperature = soilTemperature;
	}

	/**
	 * @return the soilHumidity  
	 */
	public String getSoilHumidity() {
		return soilHumidity;
	}

	/**
	 * @param soilHumidity the soilHumidity to set
	 */
	public void setSoilHumidity(String soilHumidity) {
		this.soilHumidity = soilHumidity;
	}

	/**
	 * @return the illuminance  
	 */
	public String getIlluminance() {
		return illuminance;
	}

	/**
	 * @param illuminance the illuminance to set
	 */
	public void setIlluminance(String illuminance) {
		this.illuminance = illuminance;
	}

	/**
	 * @return the rainfall  
	 */
	public String getRainfall() {
		return rainfall;
	}

	/**
	 * @param rainfall the rainfall to set
	 */
	public void setRainfall(String rainfall) {
		this.rainfall = rainfall;
	}

	/**
	 * @return the airPressure  
	 */
	public String getAirPressure() {
		return airPressure;
	}

	/**
	 * @param airPressure the airPressure to set
	 */
	public void setAirPressure(String airPressure) {
		this.airPressure = airPressure;
	}

	/**
	 * @return the windSpeed  
	 */
	public String getWindSpeed() {
		return windSpeed;
	}

	/**
	 * @param windSpeed the windSpeed to set
	 */
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	/**
	 * @return the windDirection  
	 */
	public String getWindDirection() {
		return windDirection;
	}

	/**
	 * @param windDirection the windDirection to set
	 */
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	/**
	 * @return the noise  
	 */
	public String getNoise() {
		return noise;
	}

	/**
	 * @param noise the noise to set
	 */
	public void setNoise(String noise) {
		this.noise = noise;
	}

	/**
	 * @return the pm10  
	 */
	public String getPm10() {
		return pm10;
	}

	/**
	 * @param pm10 the pm10 to set
	 */
	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}

	/**
	 * @return the pm25  
	 */
	public String getPm25() {
		return pm25;
	}

	/**
	 * @param pm25 the pm25 to set
	 */
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	/**
	 * @return the pm1  
	 */
	public String getPm1() {
		return pm1;
	}

	/**
	 * @param pm1 the pm1 to set
	 */
	public void setPm1(String pm1) {
		this.pm1 = pm1;
	}

	/**
	 * @return the co  
	 */
	public String getCo() {
		return co;
	}

	/**
	 * @param co the co to set
	 */
	public void setCo(String co) {
		this.co = co;
	}

	/**
	 * @return the o3  
	 */
	public String getO3() {
		return o3;
	}

	/**
	 * @param o3 the o3 to set
	 */
	public void setO3(String o3) {
		this.o3 = o3;
	}

	/**
	 * @return the so2  
	 */
	public String getSo2() {
		return so2;
	}

	/**
	 * @param so2 the so2 to set
	 */
	public void setSo2(String so2) {
		this.so2 = so2;
	}

	/**
	 * @return the no2  
	 */
	public String getNo2() {
		return no2;
	}

	/**
	 * @param no2 the no2 to set
	 */
	public void setNo2(String no2) {
		this.no2 = no2;
	}

	/**
	 * @return the radiation  
	 */
	public String getRadiation() {
		return radiation;
	}

	/**
	 * @param radiation the radiation to set
	 */
	public void setRadiation(String radiation) {
		this.radiation = radiation;
	}

	/**
	 * @return the negativeOxygenIon  
	 */
	public String getNegativeOxygenIon() {
		return negativeOxygenIon;
	}

	/**
	 * @param negativeOxygenIon the negativeOxygenIon to set
	 */
	public void setNegativeOxygenIon(String negativeOxygenIon) {
		this.negativeOxygenIon = negativeOxygenIon;
	}

	/* (non-Javadoc)  
	 * <p>Title: toString</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see java.lang.Object#toString()  
	 */
	@Override
	public String toString() {
		return "AirStationDataDto [id=" + id + ", code=" + code + ", tName=" + tName + ", sensorNum=" + sensorNum
				+ ", AQI=" + AQI + ", airGrade=" + airGrade + ", airTemperature=" + airTemperature + ", airHumidity="
				+ airHumidity + ", soilTemperature=" + soilTemperature + ", soilHumidity=" + soilHumidity
				+ ", illuminance=" + illuminance + ", rainfall=" + rainfall + ", airPressure=" + airPressure
				+ ", windSpeed=" + windSpeed + ", windDirection=" + windDirection + ", noise=" + noise + ", pm10="
				+ pm10 + ", pm25=" + pm25 + ", pm1=" + pm1 + ", co=" + co + ", o3=" + o3 + ", so2=" + so2 + ", no2="
				+ no2 + ", radiation=" + radiation + ", negativeOxygenIon=" + negativeOxygenIon + "]";
	}

	
    
	
}
