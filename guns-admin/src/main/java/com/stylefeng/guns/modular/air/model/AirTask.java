package com.stylefeng.guns.modular.air.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 气象站任务执行表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-07
 */
@TableName("air_task")
public class AirTask extends Model<AirTask> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(type=IdType.INPUT)
    private String id;
    /**
     * 任务名称
     */
    @TableField("job_name")
    private String jobName;
    /**
     * 表达式
     */
    private String cron;
    /**
     * 状态(0:停止,1:启动)
     */
    private Integer status;
    /**
     * 任务执行类
     */
    @TableField("clazz_path")
    private String clazzPath;
    /**
     * 任务描述
     */
    @TableField("job_desc")
    private String jobDesc;
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
     * 执行设备编号
     */
    @TableField("device_code")
    private String deviceCode;
    
    
    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getClazzPath() {
        return clazzPath;
    }

    public void setClazzPath(String clazzPath) {
        this.clazzPath = clazzPath;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "AirTask{" +
        "id=" + id +
        ", jobName=" + jobName +
        ", cron=" + cron +
        ", status=" + status +
        ", clazzPath=" + clazzPath +
        ", deviceCode=" + deviceCode +
        ", jobDesc=" + jobDesc +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        "}";
    }
}
