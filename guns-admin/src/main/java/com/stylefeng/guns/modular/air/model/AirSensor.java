package com.stylefeng.guns.modular.air.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;

import org.springframework.data.annotation.Transient;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 传感器表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-19
 */
@TableName("air_sensor")
public class AirSensor extends Model<AirSensor> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 编号
     */
    private String code;
    /**
     * 传感器名称
     */
    @TableField("t_name")
    private String tName;
    /**
     * 排序
     */
    @TableField("sort_code")
    private Long sortCode;
    /**
     * 是否可用(0:正常,1:禁用)
     */
    private String valid;
    /**
     * 所属气象站
     */
    @TableField("station_id")
    private Long stationId;
    /**
     * 传感器类型id
     */
    @TableField("type_id")
    private Long typeId;
    /**
     * 产品型号
     */
    @TableField("sensor_model")
    private String sensorModel;
    /**
     * 寄存器地址
     */
    @TableField("rtu_id")
    private String rtuId;
    /**
     * 状态(0:正常，1:响应，2：通讯故障，3：设备故障)
     */
    private String status;
    /**
     * 电压（单位：V）
     */
    private String voltage;
    /**
     * 电流（单位：mA）
     */
    private String electricity;
    /**
     * 安装者
     */
    private String installer;
    /**
     * 安装时间
     */
    @TableField("install_time")
    private Date installTime;
    /**
     * 创建ID
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新者ID
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 传感器图标
     */
    private String icon;
    /**
     * 备注
     */
    private String remark;
    
    //传感器数据
    @TableField(exist=false)
    private AirSensorData sensorData;

    public AirSensorData getSensorData() {
        return sensorData;
    }

    public void setSensorData(AirSensorData sensorData) {
        this.sensorData = sensorData;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public Long getSortCode() {
        return sortCode;
    }

    public void setSortCode(Long sortCode) {
        this.sortCode = sortCode;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getSensorModel() {
        return sensorModel;
    }

    public void setSensorModel(String sensorModel) {
        this.sensorModel = sensorModel;
    }

    public String getRtuId() {
        return rtuId;
    }

    public void setRtuId(String rtuId) {
        this.rtuId = rtuId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getInstaller() {
        return installer;
    }

    public void setInstaller(String installer) {
        this.installer = installer;
    }

    public Date getInstallTime() {
        return installTime;
    }

    public void setInstallTime(Date installTime) {
        this.installTime = installTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AirSensor{" +
        "id=" + id +
        ", code=" + code +
        ", tName=" + tName +
        ", sortCode=" + sortCode +
        ", valid=" + valid +
        ", stationId=" + stationId +
        ", typeId=" + typeId +
        ", sensorModel=" + sensorModel +
        ", rtuId=" + rtuId +
        ", status=" + status +
        ", voltage=" + voltage +
        ", electricity=" + electricity +
        ", installer=" + installer +
        ", installTime=" + installTime +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", icon=" + icon +
        ", remark=" + remark +
        "}";
    }
}
