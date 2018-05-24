package com.stylefeng.guns.modular.air.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.common.constant.SensorTypeEnum;
import com.stylefeng.guns.core.common.constant.WindDirection;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.modular.air.dao.AirLedMapper;
import com.stylefeng.guns.modular.air.dao.AirSensorMapper;
import com.stylefeng.guns.modular.air.dao.AirStationDataMapper;
import com.stylefeng.guns.modular.air.dao.AirStationMapper;
import com.stylefeng.guns.modular.air.dao.AirTaskMapper;
import com.stylefeng.guns.modular.air.dao.SensorTypeMapper;
import com.stylefeng.guns.modular.air.model.AirLed;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.model.SensorType;
import com.stylefeng.guns.modular.air.service.IAirLedService;
import com.stylefeng.guns.modular.air.task.JobTask;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import onbon.bx06.Bx6GControllerClient;
import onbon.bx06.Bx6GEnv;
import onbon.bx06.Bx6GResponseCmd;
import onbon.bx06.Bx6GScreen.Result;
import onbon.bx06.Bx6GScreenClient;
import onbon.bx06.Bx6GScreenProfile;
import onbon.bx06.area.BxArea;
import onbon.bx06.area.DateStyle;
import onbon.bx06.area.DateTimeBxArea;
import onbon.bx06.area.TextCaptionBxArea;
import onbon.bx06.area.TimeStyle;
import onbon.bx06.area.page.TextBxPage;
import onbon.bx06.cmd.led.ProgramLockCmd;
import onbon.bx06.file.ProgramBxFile;
import onbon.bx06.message.global.ACK;
import onbon.bx06.message.led.ReturnPingStatus;
import onbon.bx06.utils.DisplayStyleFactory;
import onbon.bx06.utils.TextBinary.Alignment;

/**
 * <p>
 * LED终端表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-18
 */
@Service
public class AirLedServiceImpl extends ServiceImpl<AirLedMapper, AirLed> implements IAirLedService {

	private String logPath = Thread.currentThread().getContextClassLoader().getResource("log.properties").getPath();
	
	private static Logger logger = LoggerFactory.getLogger(AirLedServiceImpl.class);
	
	@Autowired
	private JobTask jobTask;
	@Autowired
	private AirTaskMapper airTaskMapper;
	@Autowired
	private AirStationMapper airStationMapper;
	@Autowired
	private AirStationDataMapper airStationDataMapper;
	@Autowired
	private AirSensorMapper airSensorMapper;
	@Autowired
	private SensorTypeMapper sensorTypeMapper;
	
	@Override
	public int saveAirLed(AirLed airLed) {
		int count=0;
		if(airLed!=null){
			if(airLed.getId()==null){
				airLed.setCode(StringUtil.generatorShort());
				airLed.setCreateBy(ShiroKit.getUser().getName());
				airLed.setCreateTime(new Date());
				count=baseMapper.insert(airLed);
				
			}else{
				airLed.setUpdateBy(ShiroKit.getUser().getName());
				airLed.setUpdateTime(new Date());
				count=baseMapper.updateById(airLed);
			}
		}
		return count;
	}

	

	@Override
	public int deleteAirLed(Integer airLedId) {
		int count=0;
		if(airLedId!=null){
			 AirLed airLed = baseMapper.selectById(airLedId);
			if(airLed!=null){
				airLed.setValid("1");
				airLed.setUpdateBy(ShiroKit.getUser().getName());
				airLed.setUpdateTime(new Date());
				count=baseMapper.updateById(airLed);
			}
		}
		return count;
	}

	/**
	 * 更改屏幕状态
	 */
	@Override
	public Map<String, Object> setScreenStatus(String ids, Integer brightness, String mode) {
		Map<String,Object> result=Maps.newHashMap();
		Bx6GScreenClient bxScreen=null;
		Bx6GControllerClient controller=null;
		try {
			if(StringUtils.isNotBlank(ids)){
				String[] data = ids.split(";");
				if(ArrayUtil.isNotEmpty(data)){
					
					
					StringBuilder msg1=new StringBuilder();//是否存在
					StringBuilder msg2=new StringBuilder();//是否在线
					StringBuilder msg4=new StringBuilder();//是否操作成功
					for(String id : data){
						AirLed led = baseMapper.selectById(Long.valueOf(id));
						if(led!=null){
							Bx6GEnv.initial(logPath, 15000);
							bxScreen = new Bx6GScreenClient("MyScreen");
							controller=new Bx6GControllerClient("MyController");
							//连接LED终端
							if(!bxScreen.connect(led.getIpAddr(), Integer.parseInt(led.getPort())) || 
							   !controller.connect(led.getIpAddr(), Integer.parseInt(led.getPort()))){
								
								msg2.append(led.gettName()).append(" ");
								//LED离线，更改终端状态
								led.setStatus("2");//通讯故障
								led.setUpdateBy(ShiroKit.getUser().getName());
								led.setUpdateTime(new Date());
								baseMapper.updateById(led);
								continue;
							}
							//调整亮度
							if("setLighting".equals(mode)){
								Result<ACK> ack = bxScreen.manualBrightness(brightness.byteValue());
								if(!ack.isOK()){
									//操作失败
									msg4.append(led.gettName()).append(" ");
								}
							}
							//校时
							else if("syncTime".equals(mode)){
								Result<ACK> ack = bxScreen.syncTime();
								if(!ack.isOK()){
									//操作失败
									msg4.append(led.gettName()).append(" ");
								}
							}
							//开机
							else if("turnOn".equals(mode)){
								Result<ACK> ack = bxScreen.turnOn();
								if(!ack.isOK()){
									//操作失败
									msg4.append(led.gettName()).append(" ");
								}
							}
							//关机
							else if("turnOff".equals(mode)){
								Result<ACK> ack = bxScreen.turnOff();
								if(!ack.isOK()){
									//操作失败
									msg4.append(led.gettName()).append(" ");
								}
							}
							
							//再次查询控制卡状态
				            Bx6GResponseCmd<ReturnPingStatus> responseCmd = controller.ping();
				            if(responseCmd.isOK()){
				            	ReturnPingStatus status = responseCmd.reply;
				            	led.setScreenStatus(String.valueOf(status.getCurrentOnOffStatus()));//关机
					        	led.setBrightness(status.getCurrentBrigtness());//屏幕亮度
					        	led.setScreenHeight(String.valueOf(status.getScreenHeight()));//屏幕高度
					        	led.setScreenWidth(String.valueOf(status.getScreenWidth()));//屏幕宽度
				            }
				            
				        	//更新控制卡状态
				        	led.setStatus("0");
				        	led.setUpdateBy(ShiroKit.getUser().getName());
				        	led.setUpdateTime(new Date());
				        	baseMapper.updateById(led);
							
						}else{
							msg1.append(id).append(" ");
						}
						
					}
					
					//返回结果
					StringBuilder message=new StringBuilder(); 
					if(StringUtils.isNotBlank(msg1)){
						message.append(msg1).append("终端不存在！").append("\n");
					}
					if(StringUtils.isNotBlank(msg2)){
						message.append(msg2).append("未在线！请检查").append("\n");
					}
					if(StringUtils.isNotBlank(msg4)){
						message.append(msg4).append("操作失败！").append("\n");
					}
					if(StringUtils.isNotBlank(message)){
						result.put("msg", message.toString());
						return result;
					}
					else if(StringUtils.isBlank(msg2) && "checkOnline".equals(mode)){
						result.put("code", "000000");
						result.put("msg", "选中的终端都在线!");
						return result;
					}
					else{
						result.put("code", "000000");
					}	
					
					
				}else{
					result.put("msg", "参数错误!");
				}
			}else{
				result.put("msg", "参数不能为空!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.put("msg", "服务器繁忙，请联系管理员！");
			return result;
		} finally {
			if(bxScreen!=null){
				bxScreen.disconnect();
			}
			if(controller!=null){
				controller.disconnect();
			}
		}
		return result;
	}



	@Override
	public Map<String, Object> changeLedControlMode(AirLed led) {
		Map<String,Object> map=Maps.newHashMap();
		if(led!=null && led.getId()!=null){
			//查询LED终端
			AirLed airLed = baseMapper.selectById(led.getId());
			
			//查询所属气象站
			AirStation station = airStationMapper.selectById(airLed.getStationId());
			if(station!=null){
				//开启实时发布
				if("0".equals(led.getControlMode())){
					//查询气象最新数值
					List<AirStationData> datas = airStationDataMapper.selectList(new EntityWrapper<AirStationData>().eq("station_id", station.getId()).orderBy("heartbeat_time desc"));
					if(CollectionUtils.isNotEmpty(datas)){
						//发布气象站数据
						map = releaseAirStationData(datas.get(0),airLed);
					}
				}
				//关闭
				else{
					 map=stopReleaseAirData(airLed);
				}
				
			}
			
			if("000000".equals(map.get("code"))){
				led.setUpdateBy(ShiroKit.getUser().getName());
				led.setUpdateTime(new Date());
				baseMapper.updateById(led);
			}
			
		}
		
		return map;
	}
	
	/**  
	 * <p>Title: releaseAirStationData</p>  
	 * <p>Description: 发布气象数据</p>  
	 * @param airStationData
	 * @param airLed  
	 */ 
	public Map<String,Object> releaseAirStationData(Object data, AirLed led) {

		Map<String,Object> result=Maps.newHashMap();
		Bx6GScreenClient bxScreen=null;
		Bx6GControllerClient controller=null;
		try {
			if(led!=null && ObjectUtil.isNotNull(data)){

				Bx6GEnv.initial(logPath, 15000);
				bxScreen = new Bx6GScreenClient("MyScreen");
				controller=new Bx6GControllerClient("MyController");
				//连接LED终端
				if(!bxScreen.connect(led.getIpAddr(), Integer.parseInt(led.getPort())) || 
				   !controller.connect(led.getIpAddr(), Integer.parseInt(led.getPort()))){
					
					//LED离线，更改终端状态
					led.setStatus("2");//通讯故障
					led.setUpdateBy(ShiroKit.getUser().getName());
					led.setUpdateTime(new Date());
					baseMapper.updateById(led);
					
					result.put("msg", led.gettName()+"未在线！请检查");
					return result;
				}
				//删除所有的节目
//				bxScreen.deletePrograms();
//				//删除所有的动态区
//				bxScreen.deleteAllDynamic();
				
				//取得当前屏幕规格
				Bx6GScreenProfile profile = bxScreen.getProfile();
				
				//开机
				Result<ACK> ack = bxScreen.turnOn();
				if(!ack.isOK()){
					result.put("msg", "操作失败！");
					return result;
				}
				
				ProgramBxFile program=null;
				
				//环境监测数据
				if(data instanceof AirStationData){
					AirStationData airData=(AirStationData)data;
					//生成文本页信息
					String msg=generateBxPageMsg(airData);
					//增加环境检测文本页
			        TextBxPage page=new TextBxPage(msg);
			        page.setDisplayStyle(DisplayStyleFactory.getStyle(4));
			        page.setVerticalAlignment(Alignment.CENTER); // 垂直置中
			        page.setHorizontalAlignment(Alignment.CENTER);
			        page.setSpeed(3);
			        page.setForeground(new Color(141,216,94));
			        
			        // 增加圖文區
//			        TextCaptionBxArea area = new TextCaptionBxArea(1, 39, 126, 24, profile);
			        TextCaptionBxArea area = new TextCaptionBxArea(1, 15, 126, 48, profile);
			//        area.setFrameShow(true);
			//        area.setFrameStyle(2);
			        area.addPage(page);
			        
			        
			        //增加天气预报文本页
//			        TextBxPage page1=new TextBxPage("");
//			        page1.setDisplayStyle(DisplayStyleFactory.getStyle(4));
//			        page1.setVerticalAlignment(Alignment.CENTER); // 垂直置中
//			        page1.setHorizontalAlignment(Alignment.CENTER);
//			        page1.setSpeed(3);
//			        page1.setForeground(new Color(255,165,0));
//			        
//			        // 增加圖文區
//			        TextCaptionBxArea area1 = new TextCaptionBxArea(1, 15, 126, 24, profile);
//			        area1.addPage(page1);
			        
			        
			    
			        //增加时间区
			        DateTimeBxArea tArea=new DateTimeBxArea(1, 1, 114,14,profile);
			        tArea.setFont(new Font("宋体", Font.PLAIN, 10));
			        tArea.setForeground(Color.RED);
			        tArea.setMultiline(false);
			        
			        //年月日的显示方式
			        tArea.setDateStyle(DateStyle.YYYY_MM_DD_3);
			        tArea.setTimeStyle(TimeStyle.HH_MM_SS_1);
			        tArea.setWeekStyle(null);
			
			        // 建立節目  生成节目名称
			        program = new ProgramBxFile("P020", profile);
			        program.setFrameShow(true);
			        program.setFrameSpeed(3);
			//        program.loadFrameImage(13);
			        program.addArea(tArea);
			        program.addArea(area);
//			        program.addArea(area1);
					
					
				}
				//异常报警信息
				else if(data instanceof String){
					String msg = String.valueOf(data);
					
					//增加文本页
			        TextBxPage page=new TextBxPage(msg);
			        page.setDisplayStyle(DisplayStyleFactory.getStyle(4));
			        page.setVerticalAlignment(Alignment.CENTER); // 垂直置中
			        page.setHorizontalAlignment(Alignment.CENTER);
			        page.setSpeed(3);
			        page.setForeground(Color.RED);
			        
			        // 增加圖文區
			        TextCaptionBxArea area = new TextCaptionBxArea(1, 15, 126, 48, profile);
			        area.addPage(page);
			    
			        //增加时间区
			        DateTimeBxArea tArea=new DateTimeBxArea(1, 1, 114,14,profile);
			        tArea.setFont(new Font("宋体", Font.PLAIN, 10));
			        tArea.setForeground(Color.RED);
			        tArea.setMultiline(false);
			        
			        //年月日的显示方式
			        tArea.setDateStyle(DateStyle.YYYY_MM_DD_3);
			        tArea.setTimeStyle(TimeStyle.HH_MM_SS_1);
			        tArea.setWeekStyle(null);
			
			        // 建立節目  生成节目名称
			        program = new ProgramBxFile("P020", profile);
			        program.setFrameShow(true);
			        program.setFrameSpeed(3);
			        program.addArea(tArea);
			        program.addArea(area);
				}
				
				
		        
		        // 檢查区域有效性
		        BxArea failed = program.validate(); // 返回第一個超出屏幕範圍的區域
		        if(failed != null) {
		            logger.error("This area is out of range");
		            result.put("msg", "节目生成失败，请联系管理员！");
		            return result;
		        }
		        
		        // 傳送
		        bxScreen.writeProgram(program);
		
		        Thread.sleep(1000);
		
		        bxScreen.disconnect();
		        logger.info("节目发布成功！");
		        
		      //切换到当前节目
		        ProgramLockCmd lock=new ProgramLockCmd("P020",true,10,(byte) 0x01);
		        controller.execute(lock);
				
				//再次查询控制卡状态
	            Bx6GResponseCmd<ReturnPingStatus> responseCmd = controller.ping();
	            if(responseCmd.isOK()){
	            	ReturnPingStatus status = responseCmd.reply;
	            	led.setScreenStatus(String.valueOf(status.getCurrentOnOffStatus()));//关机
		        	led.setBrightness(status.getCurrentBrigtness());//屏幕亮度
		        	led.setScreenHeight(String.valueOf(status.getScreenHeight()));//屏幕高度
		        	led.setScreenWidth(String.valueOf(status.getScreenWidth()));//屏幕宽度
	            }
	            
	        	//更新控制卡状态
	        	led.setStatus("0");
	        	led.setUpdateBy(ShiroKit.isGuest() ? "" : ShiroKit.getUser().getName());
	        	led.setUpdateTime(new Date());
	        	baseMapper.updateById(led);
				
	        	result.put("code", "000000");
	        	result.put("msg", "发布成功！");
			
			}else{
				result.put("msg", "参数不能为空!");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.put("msg", "服务器繁忙，请联系管理员！");
			return result;
		} finally {
			if(bxScreen!=null){
				bxScreen.disconnect();
			}
			if(controller!=null){
				controller.disconnect();
			}
		}
		return result;
	
		
	}



	/**  
	 * <p>Title: generateBxPageMsg</p>  
	 * <p>Description: </p>  
	 * @param airData
	 * @return  
	 */ 
	private String generateBxPageMsg(AirStationData airData) {
		StringBuilder msg=new StringBuilder();
		if(airData!=null){
			//查询气象站
			AirStation station = airStationMapper.selectById(airData.getStationId());
			if(station!=null){
				//查询传感器
				List<AirSensor> sensors = airSensorMapper.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
				if(CollectionUtils.isNotEmpty(sensors)){
					for(AirSensor sensor : sensors){
						//查询类型
						SensorType type = sensorTypeMapper.selectById(sensor.getTypeId());
						SensorTypeEnum typeEnum = SensorTypeEnum.findSensorTypeByName(type.gettName());
						//风向类型
						if(type.gettName().contains("风向")){
							msg.append("风向:").append(WindDirection.findWindDirectionByMark(airData.getWindDirection()).getMsg()).append(" ");
						}else{
							//查询数值
							Double num = Convert.toDouble(SensorTypeEnum.findDataBySensorType(typeEnum.getCode(), airData));
							if(num!=null){
								msg.append(type.gettName()).append(":").append(num).append(sensor.getUnit()).append(" ");
							}
						}
					}
				}
				
			}
			
		}
		return msg.toString();
	}


	/**  
	 * <p>Title: stopReleaseAirData</p>  
	 * <p>Description: 停止发布数据</p>  
	 * @param airLed
	 * @return  
	 */ 
	public Map<String, Object> stopReleaseAirData(AirLed led) {
		Map<String,Object> result=Maps.newHashMap();
		Bx6GScreenClient bxScreen=null;
		Bx6GControllerClient controller=null;
		try {
			if(led!=null){
				Bx6GEnv.initial(logPath, 15000);
				bxScreen = new Bx6GScreenClient("MyScreen");
				controller=new Bx6GControllerClient("MyController");
				//连接LED终端
				if(!bxScreen.connect(led.getIpAddr(), Integer.parseInt(led.getPort())) || 
				   !controller.connect(led.getIpAddr(), Integer.parseInt(led.getPort()))){
					
					//LED离线，更改终端状态
					led.setStatus("2");//通讯故障
					led.setUpdateBy(ShiroKit.getUser().getName());
					led.setUpdateTime(new Date());
					baseMapper.updateById(led);
					
					result.put("msg", led.gettName()+"未在线！请检查");
					return result;
				}
				
				//关机
				Result<ACK> ack = bxScreen.turnOff();
				if(!ack.isOK()){
					result.put("msg", "操作失败！");
					return result;
				}
			
				//再次查询控制卡状态
	            Bx6GResponseCmd<ReturnPingStatus> responseCmd = controller.ping();
	            if(responseCmd.isOK()){
	            	ReturnPingStatus status = responseCmd.reply;
	            	led.setScreenStatus(String.valueOf(status.getCurrentOnOffStatus()));//关机
		        	led.setBrightness(status.getCurrentBrigtness());//屏幕亮度
		        	led.setScreenHeight(String.valueOf(status.getScreenHeight()));//屏幕高度
		        	led.setScreenWidth(String.valueOf(status.getScreenWidth()));//屏幕宽度
	            }
	            
	        	//更新控制卡状态
	        	led.setStatus("0");
	        	led.setUpdateBy(ShiroKit.getUser().getName());
	        	led.setUpdateTime(new Date());
	        	baseMapper.updateById(led);
				
	        	result.put("code", "000000");
	        	result.put("msg", "操作成功！");
			
			}else{
				result.put("msg", "参数不能为空!");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result.put("msg", "服务器繁忙，请联系管理员！");
			return result;
		} finally {
			if(bxScreen!=null){
				bxScreen.disconnect();
			}
			if(controller!=null){
				controller.disconnect();
			}
		}
		return result;
	}




}
