package com.stylefeng.guns.modular.air.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 传感器检测数据表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-20
 */
@TableName("air_sensor_data")
public class AirSensorData extends Model<AirSensorData> {

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
     * 检测名称
     */
    @TableField("t_name")
    private String tName;
    /**
     * 排序
     */
    @TableField("sort_code")
    private Integer sortCode;
    /**
     * 状态(0:正常,1:禁用)
     */
    private String valid;
    /**
     * 传感器id
     */
    @TableField("sensor_id")
    private Long sensorId;
    /**
     * 信号强度
     */
    @TableField("signal_strength")
    private Integer signalStrength;
    /**
     * 检测数值
     */
    private String numerical;
   
    
    /**
     * 心跳时间
     */
    @TableField("heartbeat_time")
    private Date heartbeatTime;
    /**
     * 创建者id
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改者id
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;


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

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Integer signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getNumerical() {
        return numerical;
    }

    public void setNumerical(String numerical) {
        this.numerical = numerical;
    }


    public Date getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Date heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
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
        return "AirSensorData{" +
        "id=" + id +
        ", code=" + code +
        ", tName=" + tName +
        ", sortCode=" + sortCode +
        ", valid=" + valid +
        ", sensorId=" + sensorId +
        ", signalStrength=" + signalStrength +
        ", numerical=" + numerical +
        ", heartbeatTime=" + heartbeatTime +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
