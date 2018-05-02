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
 * 传感器预警参数表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-24
 */
@TableName("air_sensor_warn_param")
public class AirSensorWarnParam extends Model<AirSensorWarnParam> {

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
     * 预警名称
     */
    @TableField("t_name")
    private String tName;
    /**
     * 顺序位
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
     * 工作开始时间
     */
    @TableField("start_time")
    private String startTime;
    /**
     * 工作结束时间
     */
    @TableField("end_time")
    private String endTime;
    /**
     * 表达式类型
     */
    private String expression;
    /**
     * 阈值
     */
    private String threshold;
    /**
     * 控制方式（0：关闭，1：开启）
     */
    @TableField("control_mode")
    private String controlMode;
    /**
     * 预警间隔（单位：分钟）
     */
    @TableField("warn_interval")
    private Integer warnInterval;
    /**
     * 异常报警时间（单位：分钟）
     */
    @TableField("alarm_time")
    private Integer alarmTime;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getControlMode() {
        return controlMode;
    }

    public void setControlMode(String controlMode) {
        this.controlMode = controlMode;
    }

    public Integer getWarnInterval() {
        return warnInterval;
    }

    public void setWarnInterval(Integer warnInterval) {
        this.warnInterval = warnInterval;
    }

    public Integer getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Integer alarmTime) {
        this.alarmTime = alarmTime;
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
        return "AirSensorWarnParam{" +
        "id=" + id +
        ", code=" + code +
        ", tName=" + tName +
        ", sortCode=" + sortCode +
        ", valid=" + valid +
        ", sensorId=" + sensorId +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", expression=" + expression +
        ", threshold=" + threshold +
        ", controlMode=" + controlMode +
        ", warnInterval=" + warnInterval +
        ", alarmTime=" + alarmTime +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
