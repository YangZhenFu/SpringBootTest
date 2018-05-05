package com.stylefeng.guns.modular.air.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 气象站检测数据表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-02
 */
@TableName("air_station_data")
public class AirStationData extends Model<AirStationData> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 检测名称
     */
    @TableField("t_name")
    private String tName;
    /**
     * 气象站Id
     */
    @TableField("station_id")
    private Long stationId;
    /**
     * 心跳时间
     */
    @TableField("heartbeat_time")
    private Date heartbeatTime;
    /**
     * 大气温度（单位：℃）
     */
    @TableField("air_temperature")
    private String airTemperature;
    /**
     * 大气湿度（单位：%rh）
     */
    @TableField("air_humidity")
    private String airHumidity;
    /**
     * 土壤温度（单位：℃）
     */
    @TableField("soil_temperature")
    private String soilTemperature;
    /**
     * 土壤湿度（单位：%rh）
     */
    @TableField("soil_humidity")
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
    @TableField("air_pressure")
    private String airPressure;
    /**
     * 风速（单位：m/s）
     */
    @TableField("wind_speed")
    private String windSpeed;
    /**
     * 风向
     */
    @TableField("wind_direction")
    private String windDirection;
    /**
     * 噪声值（单位：dB）
     */
    private String noise;
    /**
     * PM_10（单位：μm/m3）
     */
    @TableField("pm_10")
    private String pm10;
    /**
     * PM_2.5（单位：μm/m3）
     */
    @TableField("pm_25")
    private String pm25;
    /**
     * PM_1.0（单位：μm/m3）
     */
    @TableField("pm_1")
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
    @TableField("negative_oxygen_ion")
    private String negativeOxygenIon;
    
    //风向信息
    @TableField(exist=false)
    private String windDirectionMsg;


    public String getWindDirectionMsg() {
        return windDirectionMsg;
    }

    public void setWindDirectionMsg(String windDirectionMsg) {
        this.windDirectionMsg = windDirectionMsg;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Date getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Date heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public String getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(String airTemperature) {
        this.airTemperature = airTemperature;
    }

    public String getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(String airHumidity) {
        this.airHumidity = airHumidity;
    }

    public String getSoilTemperature() {
        return soilTemperature;
    }

    public void setSoilTemperature(String soilTemperature) {
        this.soilTemperature = soilTemperature;
    }

    public String getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(String soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public String getIlluminance() {
        return illuminance;
    }

    public void setIlluminance(String illuminance) {
        this.illuminance = illuminance;
    }

    public String getRainfall() {
        return rainfall;
    }

    public void setRainfall(String rainfall) {
        this.rainfall = rainfall;
    }

    public String getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(String airPressure) {
        this.airPressure = airPressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getNoise() {
        return noise;
    }

    public void setNoise(String noise) {
        this.noise = noise;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getPm1() {
        return pm1;
    }

    public void setPm1(String pm1) {
        this.pm1 = pm1;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getRadiation() {
        return radiation;
    }

    public void setRadiation(String radiation) {
        this.radiation = radiation;
    }

    public String getNegativeOxygenIon() {
        return negativeOxygenIon;
    }

    public void setNegativeOxygenIon(String negativeOxygenIon) {
        this.negativeOxygenIon = negativeOxygenIon;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AirStationData{" +
        "id=" + id +
        ", tName=" + tName +
        ", stationId=" + stationId +
        ", heartbeatTime=" + heartbeatTime +
        ", airTemperature=" + airTemperature +
        ", airHumidity=" + airHumidity +
        ", soilTemperature=" + soilTemperature +
        ", soilHumidity=" + soilHumidity +
        ", illuminance=" + illuminance +
        ", rainfall=" + rainfall +
        ", airPressure=" + airPressure +
        ", windSpeed=" + windSpeed +
        ", windDirection=" + windDirection +
        ", noise=" + noise +
        ", pm10=" + pm10 +
        ", pm25=" + pm25 +
        ", pm1=" + pm1 +
        ", co=" + co +
        ", o3=" + o3 +
        ", so2=" + so2 +
        ", no2=" + no2 +
        ", radiation=" + radiation +
        ", negativeOxygenIon=" + negativeOxygenIon +
        "}";
    }
}
