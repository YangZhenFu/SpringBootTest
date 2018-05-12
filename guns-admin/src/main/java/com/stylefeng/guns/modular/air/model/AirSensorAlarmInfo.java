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
 * 传感器告警信息表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-10
 */
@TableName("air_sensor_alarm_info")
public class AirSensorAlarmInfo extends Model<AirSensorAlarmInfo> {

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
     * 告警名称
     */
    @TableField("t_name")
    private String tName;
    /**
     * 序号
     */
    @TableField("sort_code")
    private Integer sortCode;
    /**
     * 是否可用(0:正常,1:禁用)
     */
    private String valid;
    /**
     * 创建者id
     */
    @TableField("create_id")
    private String createId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改者id
     */
    @TableField("update_id")
    private String updateId;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 传感器Id
     */
    @TableField("sensor_id")
    private Long sensorId;
    /**
     * 告警类型
     */
    @TableField("alarm_type")
    private String alarmType;
    /**
     * 告警信息
     */
    @TableField("alarm_info")
    private String alarmInfo;
    /**
     * 告警时间
     */
    @TableField("alarm_time")
    private Date alarmTime;
    /**
     * 处理状态（0：未恢复，1：已恢复）
     */
    @TableField("handle_state")
    private String handleState;
    /**
     * 处理人
     */
    @TableField("handle_name")
    private String handleName;
    /**
     * 处理内容
     */
    @TableField("handle_content")
    private String handleContent;
    /**
     * 处理时间
     */
    @TableField("handle_time")
    private Date handleTime;
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

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getHandleState() {
        return handleState;
    }

    public void setHandleState(String handleState) {
        this.handleState = handleState;
    }

    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

    public String getHandleContent() {
        return handleContent;
    }

    public void setHandleContent(String handleContent) {
        this.handleContent = handleContent;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
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
        return "AirSensorAlarmInfo{" +
        "id=" + id +
        ", code=" + code +
        ", tName=" + tName +
        ", sortCode=" + sortCode +
        ", valid=" + valid +
        ", createId=" + createId +
        ", createTime=" + createTime +
        ", updateId=" + updateId +
        ", updateTime=" + updateTime +
        ", sensorId=" + sensorId +
        ", alarmType=" + alarmType +
        ", alarmInfo=" + alarmInfo +
        ", alarmTime=" + alarmTime +
        ", handleState=" + handleState +
        ", handleName=" + handleName +
        ", handleContent=" + handleContent +
        ", handleTime=" + handleTime +
        ", remark=" + remark +
        "}";
    }
}
