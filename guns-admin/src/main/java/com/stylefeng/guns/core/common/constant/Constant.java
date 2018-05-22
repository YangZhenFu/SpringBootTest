package com.stylefeng.guns.core.common.constant;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class Constant {
	public static final String STORE_NAME = "环境监测";
	// 删除标记（0：正常；1：删除；2：审核；）
	public static final String FIELD_DEL_FLAG = "delFlag";
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";

	// 资源
	public static final String RESOURCE_STATUS_NORMAL = "0"; // 正常
	public static final String RESOURCE_STATUS_DISABLE = "1"; // 禁用
	public static final String RESOURCE_TYPE_MENU = "0"; // 菜单类型
	public static final String RESOURCE_TYPE_BUTTON = "1"; // 按钮类型
	public static final String RESOURCE_COMMON = "1"; // 公共资源

	// 用户
	public static final String SESSION_LOGIN_USER = "loginUser"; // session中的用户key
	public static final String SESSION_Member_USER = "member"; // session中的用户key
	public static final String SUPER_ADMIN = "1"; // 超级管理员

	// 缓存key
	public static final String CACHE_SYS_RESOURCE = "sysResource_cache"; // 资源的缓存名称
	public static final String CACHE_SYS_OFFICE = "sysOffice_cache"; //机构的缓存名称
	public static final String CACHE_SYS_ROLE = "sysRole_cache"; //角色的缓存名称
	public static final String CACHE_SYS_USER = "sysUser_cache"; //用户缓存名称
	public static final String CACHE_SYS_AREA = "sysArea_cache";//区域的缓存名称
	
	public static final String CACHE_ALL_RESOURCE = "allSysResource"; // 全部资源key
	public static final String CACHE_USER_RESOURCE = "userSysResource"; // 用户权限
	public static final String CACHE_USER_MENU = "userMenuTree"; // 用户菜单树
	public static final String CACHE_USER_ROLE = "userRole"; // 用户角色
	public static final String CACHE_USER_DATASCOPE = "userDataScope"; //用户数据范围
	public static final String CACHE_USER_OFFICE = "userOffice"; //用户机构

	// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
	public static final String DATA_SCOPE_ALL = "1";
	public static final String DATA_SCOPE_COMPANY_AND_CHILD = "2";
	public static final String DATA_SCOPE_COMPANY = "3";
	public static final String DATA_SCOPE_OFFICE_AND_CHILD = "4";
	public static final String DATA_SCOPE_OFFICE = "5";
	public static final String DATA_SCOPE_SELF = "8";
	public static final String DATA_SCOPE_CUSTOM = "9";
	public static final List<String> DATA_SCOPE_ADMIN = Lists.newArrayList("1","2","3","4","5","8","9");
	

	// 显示/隐藏
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	// 是/否
	public static final String YES = "1";
	public static final String NO = "0";
	
	//update or add or delete
	public static final Integer SUCCESS = 1;
	
	public static final String MSG = "msg";
	public static final String SHOPPING_HISTORY = "SHOPPING_HISTORY";

	//设备状态
	public static final Map<String, String> DEVICE_STATUS=ImmutableMap.of("0","正常","1","响应","2","通讯故障","3","设备故障");
	
	//允许上传的文档类型
	public static final String DOCUMENT_TYPES="doc;zip;rarppt;pptx;apk;docx";
	//允许上传文件的大小为400M
	public static final String FILEUPLOAD_FILESIZE="409600000";
	//上传的flash文件
	public static final String FLASH_TYPES="swf";
	//图片播放器类型:imageplay(仅仅图片切换);articleinside(信息文章内切图);javascriptplay(图片文字同时切换);flash(切图效果)
	public static final String IMAGE_PLAY_TYPES="imageplay;articleinside;javascriptplay;flash";
	//图片来源类型:relationImages(来源关联图片);columnImage(自动获取栏目图片)
	public static final String IMAGE_SOURCE_TYPES="columnImage;relationImages";
	//允许上传的图片类型
	public static final String IMAGE_TYPES="jpg;bmp;gif;png";
	//允许上传的视频文件格式
	public static final String MEDIA_TYPES="mp4;mp3;avi";
	
	public static final String ALi_App_Key="24658380";
	public static final String ALi_App_Code="48f5b02c39cc4fafb5baa5cfde87eeb9";
	public static final String ALi_App_Secret="2a55ee8380b4bc1a037e8ef1f692c3af";
	
	//气象站类型
	public static final Map<String,String> AIR_STATION_TYPE = ImmutableMap.<String,String>builder().put("0","校园气象站").put("1","农业气象站").put("2","交通气象站").put("3","光伏气象站").put("4","生态气象站")
																									.put("5","海洋气象站").put("6","森林气象站").put("7","其他气象站").build();
	
	//设备通讯方式
	public static final List<String> machine_conn_mode=ImmutableList.<String>builder()
																	.add("RS485转Tcp/Ip")
																	.add("以太网")
																	.add("3G/4G")
																	.add("GPRS")
																	.add("RS323串口")
																	.add("Wifi")
																	.add("Zigbee")
																	.add("蓝牙")
																	.add("短信").build();
	
	//高德地图API申请key
	public static final String AMAP_KEY="5c70b2b1e8e24e460a500c5f248ab9b3";
	
	//充电桩异常告警类型
	public static final Map<String,String> pile_exception_type=ImmutableMap.<String, String>builder().put("电桩离线", "0").put("上传过流", "1").put("上传欠流", "2").put("上传过压", "3").put("上传欠压", "4").put("上传急停", "5").put("充电停止", "6")
																									 .put("电缆断开", "7").put("抄表失败", "8").put("设备故障", "9").put("接地故障", "10").put("未知错误", "11").build();
	

	
	
	//系统通知类型
	public static final List<String> sys_inform_type=ImmutableList.<String>builder().add("设备故障").add("系统通知").build(); 
	
	
	
	//设备预警参数表达式类型
	public static final Map<String,String> SENSOR_WARN_EXPRESSION_TYPE = ImmutableMap.<String,String>builder().put("0","大于").put("1","小于").put("2","大于等于").put("3","小于等于").put("4","等于")
																										.put("5","不等于").build();
	
	
	
	
	//传感器异常告警类型
	public static final Map<String,String> sensor_exception_type=ImmutableMap.<String, String>builder().put("0","设备离线").put("1","数值异常").put("2","设备故障")
																					   .put("3","电缆断开").put("4","接地故障").put("5","未知错误").build();
	
	
	
	//LED类型
	public static final Map<String,String> air_led_type=ImmutableMap.<String, String>builder().put("0","全彩").put("1","单色").put("2","双色").put("3","三色").build();
	
	//LED控制卡型号
	public static final List<String> control_card_type=ImmutableList.<String>builder().add("BX-6QL").add("BX-6Q0").add("BX-6Q1").add("BX-6Q2").add("BX-6Q2L").add("BX-6Q3").add("BX-6Q3L").add("BX-6QX-M").add("BX-6QX-YD").build(); 

}
