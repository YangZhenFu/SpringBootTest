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
 * LED终端表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-18
 */
@TableName("air_led")
public class AirLed extends Model<AirLed> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * led编号
     */
    private String code;
    /**
     * LED屏名称
     */
    @TableField("t_name")
    private String tName;
    /**
     * 排序
     */
    @TableField("sort_code")
    private Integer sortCode;
    /**
     * (0:正常，1:禁用)
     */
    private String valid;
    /**
     * LED类型（0：全彩，1：单色，2：双色，3：三色）
     */
    private String type;
    /**
     * 气象站id
     */
    @TableField("station_id")
    private Long stationId;
    /**
     * 状态（0：正常，1：响应，2：通讯故障，3：设备故障）
     */
    private String status;
    /**
     * LEDIP地址
     */
    @TableField("ip_addr")
    private String ipAddr;
    /**
     * LED端口号
     */
    private String port;
    /**
     * 控制卡编号
     */
    @TableField("unit_code")
    private String unitCode;
    /**
     * 控制卡型号
     */
    @TableField("unit_type")
    private String unitType;
    /**
     * 屏幕高度
     */
    @TableField("screen_height")
    private String screenHeight;
    /**
     * 屏幕宽度
     */
    @TableField("screen_width")
    private String screenWidth;
    /**
     * 屏幕状态（0：关机，1：开机）
     */
    @TableField("screen_status")
    private String screenStatus;
    /**
     * 屏幕亮度
     */
    private Integer brightness;
    /**
     * 实时发布（0：开启，1：关闭）
     */
    @TableField("control_mode")
    private String controlMode;
    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改者
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getScreenStatus() {
        return screenStatus;
    }

    public void setScreenStatus(String screenStatus) {
        this.screenStatus = screenStatus;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public String getControlMode() {
        return controlMode;
    }

    public void setControlMode(String controlMode) {
        this.controlMode = controlMode;
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
        return "AirLed{" +
        "id=" + id +
        ", code=" + code +
        ", tName=" + tName +
        ", sortCode=" + sortCode +
        ", valid=" + valid +
        ", type=" + type +
        ", stationId=" + stationId +
        ", status=" + status +
        ", ipAddr=" + ipAddr +
        ", port=" + port +
        ", unitCode=" + unitCode +
        ", unitType=" + unitType +
        ", screenHeight=" + screenHeight +
        ", screenWidth=" + screenWidth +
        ", screenStatus=" + screenStatus +
        ", brightness=" + brightness +
        ", controlMode=" + controlMode +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        "}";
    }
}
