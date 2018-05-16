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
 * 气象站表
 * </p>
 *
 * @author YangZhenfu123
 * @since 2018-04-17
 */
@TableName("air_station")
public class AirStation extends Model<AirStation> {

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
     * 气象站名称
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
     * 归属区域
     */
    @TableField("area_id")
    private Long areaId;
    /**
     * 归属部门
     */
    @TableField("dept_id")
    private Long deptId;
    /**
     * 详细地址
     */
    @TableField("detail_address")
    private String detailAddress;
    /**
     * 坐标x
     */
    private String offx;
    /**
     * 坐标y
     */
    private String offy;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 气象站类型（0：校园，1：农业，2：交通，3：光伏，4：生态，5：海洋，6：森林，7：其他）
     */
    private String type;
    /**
     * 通讯方式
     */
    @TableField("conn_method")
    private String connMethod;
    /**
     * ip地址
     */
    @TableField("ip_addr")
    private String ipAddr;
    /**
     * 端口号
     */
    private String port;
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
     * 备注
     */
    private String remark;

    /**
     * 位置分布
     */
    private String location;
    
    /**
     * 数据上传间隔（单位：分钟）
     */
    @TableField("data_upload_interval")
    private String dataUploadInterval;
    
    /**
     * 数据上传状态（0：启动，1：停止）
     */
    @TableField("data_upload_status")
    private Integer dataUploadStatus;
    
    public String getDataUploadInterval() {
        return dataUploadInterval;
    }

    public void setDataUploadInterval(String dataUploadInterval) {
        this.dataUploadInterval = dataUploadInterval;
    }
    
    public Integer getDataUploadStatus() {
        return dataUploadStatus;
    }

    public void setDataUploadStatus(Integer dataUploadStatus) {
        this.dataUploadStatus = dataUploadStatus;
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

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getOffx() {
        return offx;
    }

    public void setOffx(String offx) {
        this.offx = offx;
    }

    public String getOffy() {
        return offy;
    }

    public void setOffy(String offy) {
        this.offy = offy;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConnMethod() {
        return connMethod;
    }

    public void setConnMethod(String connMethod) {
        this.connMethod = connMethod;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Station{" +
        "id=" + id +
        ", code=" + code +
        ", tName=" + tName +
        ", sortCode=" + sortCode +
        ", valid=" + valid +
        ", areaId=" + areaId +
        ", deptId=" + deptId +
        ", detailAddress=" + detailAddress +
        ", location=" + location +
        ", offx=" + offx +
        ", offy=" + offy +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", type=" + type +
        ", connMethod=" + connMethod +
        ", ipAddr=" + ipAddr +
        ", port=" + port +
        ", installer=" + installer +
        ", installTime=" + installTime +
        ", createBy=" + createBy +
        ", createTime=" + createTime +
        ", updateBy=" + updateBy +
        ", updateTime=" + updateTime +
        ", remark=" + remark +
        ", dataUploadInterval=" + dataUploadInterval +
        ", dataUploadStatus=" + dataUploadStatus +
        "}";
    }

	
}
