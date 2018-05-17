package com.stylefeng.guns.modular.air.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.SensorTypeEnum;
import com.stylefeng.guns.core.common.constant.WindDirection;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.modular.air.dto.AirSensorDataDto;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorAlarmInfo;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.model.SensorType;
import com.stylefeng.guns.modular.air.service.IAirSensorAlarmInfoService;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationDataService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;
import com.stylefeng.guns.modular.air.warpper.AirSensorAlarmInfoWarpper;

import cn.hutool.core.util.NumberUtil;

/**  
 * <p>Title: AirMonitorController</p>  
 * <p>Description: </p> 
 * @author YangZhenfu  
 * @date 2018年4月20日  
 */
@RequestMapping("air/monitor")
@Controller
public class AirMonitorController extends BaseController{

	private String PREFIX = "/air/monitor/";
	
	@Autowired
	private IAirStationService airStationService;
	@Autowired
	private IAirSensorService airSensorService;
	@Autowired
	private ISensorTypeService sensorTypeService;
	@Autowired
	private IAirStationDataService airStationDataService;
	@Autowired
	private IAirSensorAlarmInfoService sensorAlarmInfoService;
	
	 /**
     * 跳转到监控首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "monitor.html";
    }
    
    @RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
    public Object list(@RequestParam(required=false)String condition){
    	
    	Page<AirStation> page=new PageFactory<AirStation>().defaultPage();
    	page=airStationService.selectPage(page, new EntityWrapper<AirStation>().like("t_name", condition).eq("valid", "0"));
    	return packForBT(page);
    }
    
    /**
     * 
     * <p>Title: queryRealTimeData</p>  
     * <p>Description: 查询气象站实时数据</p>  
     * @param stationCode
     * @return
     */
//    @RequestMapping(value="queryData",method=RequestMethod.POST)
//    @ResponseBody
//    public Map<String,Object> queryRealTimeData(@RequestParam(required=false) String stationCode){
//    	Map<String,Object> result=Maps.newHashMap();
//    	if(StringUtils.isNotBlank(stationCode)){
//    		//查询气象站
//    		AirStation station = airStationService.selectOne(new EntityWrapper<AirStation>().eq("code", stationCode).eq("valid", "0"));
//    		if(station!=null){
//    			//查询所有的传感器
//    			List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
//    			if(CollectionUtils.isNotEmpty(sensors)){
//    				List<AirSensorDataDto> dtos=Lists.newArrayList();
//    				
//    				for(AirSensor sensor : sensors){
//    					//查询24小时之内最大值
//    					AirSensorData maxData=airSensorDataService.selectOneDayMaxData(sensor.getId());
//    					//查询24小时之内最小值
//    					AirSensorData minData=airSensorDataService.selectOneDayMinData(sensor.getId());
//    					//查询当前最新数据
//    					List<AirSensorData> list = airSensorDataService.selectList(new EntityWrapper<AirSensorData>().eq("sensor_id", sensor.getId()).orderBy("heartbeat_time desc"));
//    					
//    					AirSensorDataDto dto =new AirSensorDataDto();
//    					dto.setId(StringUtil.generatorShort());
//    					dto.setCode(sensor.getCode());
//    					dto.settName(sensor.gettName());
//    					dto.setTypeName(sensorTypeService.selectById(sensor.getTypeId()).gettName());
//    					dto.setUnit(sensor.getUnit());
//    					if(maxData!=null){
//    						
//    						if(dto.getTypeName().contains("风向")){
//    							dto.setMaxNumerical(WindDirection.findWindDirectionByMark(maxData.getNumerical()).getMsg());
//    						}else{
//    							dto.setMaxNumerical(maxData.getNumerical());
//    						}
//    						dto.setMaxTime(maxData.getHeartbeatTime());
//    					}
//    					if(minData!=null){
//    						if(dto.getTypeName().contains("风向")){
//    							dto.setMinNumerical(WindDirection.findWindDirectionByMark(minData.getNumerical()).getMsg());
//    						}else{
//    							dto.setMinNumerical(minData.getNumerical());
//    						}
//    						dto.setMinTime(minData.getHeartbeatTime());
//    					}
//    					if(CollectionUtils.isNotEmpty(list)){
//    						AirSensorData nowData = list.get(0);
//    						if(dto.getTypeName().contains("风向")){
//    							dto.setNowNumerical(WindDirection.findWindDirectionByMark(nowData.getNumerical()).getMsg());
//    						}else{
//    							dto.setNowNumerical(nowData.getNumerical());
//    						}
//    					}
//    					dtos.add(dto);
//    					
//    				}
//    				
//    				//查询数据更新时间
//    				Date refreshTime=airSensorDataService.selectMaxHeatbeatTime(sensors);
//    				result.put("refreshTime", refreshTime);
//    				result.put("data", dtos);
//    				return result;
//    			}
//    			
//    		}
//    	}
//    	
//    	
//    	return result;
//    	
//    	
//    }
    
    
    /**
     * 
     * <p>Title: queryRealTimeData</p>  
     * <p>Description: 查询气象站实时数据</p>  
     * @param stationCode
     * @return
     */
    @RequestMapping(value="queryData",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryRealTimeData(@RequestParam(required=false) String stationCode){
    	Map<String,Object> result=Maps.newHashMap();
    	if(StringUtils.isNotBlank(stationCode)){
    		//查询气象站
    		AirStation station = airStationService.selectOne(new EntityWrapper<AirStation>().eq("code", stationCode).eq("valid", "0"));
    		if(station!=null){
    			
    			//查询所有的传感器
    			List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
    			if(CollectionUtils.isNotEmpty(sensors)){
    				List<AirSensorDataDto> dtos=Lists.newArrayList();
    				
    				
    				List<AirSensorAlarmInfo> alarms=Lists.newArrayList();
    				//查询全部数据
    				List<AirStationData> allData = airStationDataService.selectList(new EntityWrapper<AirStationData>().eq("station_id", station.getId()).orderBy("heartbeat_time desc"));
    				
    				//查询24小时数据
    				List<AirStationData> data = airStationDataService.findOneDayData(station.getId());
        				for(AirSensor sensor : sensors){
        					//查询传感器类型
        					SensorType type = sensorTypeService.selectById(sensor.getTypeId());
        					
        					//传感器数据类型
        					SensorTypeEnum typeEnum = SensorTypeEnum.findSensorTypeByName(type.gettName());
        					String code = typeEnum.getCode();
        					
        					//查询传感器报警信息
        					List<Map<String, Object>> sensorAlarm = sensorAlarmInfoService.selectMaps(new EntityWrapper<AirSensorAlarmInfo>().eq("valid", "0").eq("sensor_id", sensor.getId()).eq("handle_state", "0"));
        					alarms.addAll((List<AirSensorAlarmInfo>)new AirSensorAlarmInfoWarpper(sensorAlarm).warp());
        					
        					AirStationData nowData = null;//当前数值
        					AirStationData minData = null;//最大数值
    						AirStationData maxData = null;//最小数值
        					
    						if(CollectionUtils.isNotEmpty(allData)){
    							//当前数值
            					nowData = allData.get(0);
    						}
    						
        					if(CollectionUtils.isNotEmpty(data) ){
        						
        						if(StringUtils.isNotBlank(code) && !type.gettName().contains("风向")){
        							//按传感器数据类型排序
        							Collections.sort(data, new Comparator<AirStationData>() {
            							@Override
            							public int compare(AirStationData o1, AirStationData o2) {
            								Double num1=Convert.toDouble(SensorTypeEnum.findDataBySensorType(code, o1));
            								Double num2 = Convert.toDouble(SensorTypeEnum.findDataBySensorType(code, o2));
            								if(num1==null || num2==null){
            									return 0;
            								}
            								return NumberUtil.compare(num1, num2);
            							}
            						});
        						}
        						
        					    minData = data.get(0);//最大数值
        						maxData = data.get(data.size()-1);//最小数值
        					}
        					
        						AirSensorDataDto dto =new AirSensorDataDto();
            					dto.setId(StringUtil.generatorShort());
            					dto.setCode(sensor.getCode());
            					dto.settName(sensor.gettName());
            					dto.setTypeName(type.gettName());
            					dto.setUnit(sensor.getUnit());
            					dto.setIcon(typeEnum.getIcon());
            					if(maxData!=null){
            						
            						if(dto.getTypeName().contains("风向")){
            							dto.setMaxNumerical(WindDirection.findWindDirectionByMark(String.valueOf(SensorTypeEnum.findDataBySensorType(code, maxData))).getMsg());
            						}else{
            							dto.setMaxNumerical(String.valueOf(SensorTypeEnum.findDataBySensorType(code, maxData)));
            						}
            						dto.setMaxTime(maxData.getHeartbeatTime());
            					}
            					if(minData!=null){
            						if(dto.getTypeName().contains("风向")){
            							dto.setMinNumerical(WindDirection.findWindDirectionByMark(String.valueOf(SensorTypeEnum.findDataBySensorType(code, minData))).getMsg());
            						}else{
            							dto.setMinNumerical(String.valueOf(SensorTypeEnum.findDataBySensorType(code, minData)));
            						}
            						dto.setMinTime(minData.getHeartbeatTime());
            					}
            					if(nowData!=null){
            						if(dto.getTypeName().contains("风向")){
            							dto.setNowNumerical(WindDirection.findWindDirectionByMark(String.valueOf(SensorTypeEnum.findDataBySensorType(code, nowData))).getMsg());
            						}else{
            							dto.setNowNumerical(String.valueOf(SensorTypeEnum.findDataBySensorType(code, nowData)));
            						}
            					}
            					dtos.add(dto);
    				}
        			result.put("alarms", alarms);	
        			result.put("data", dtos);
    				if(CollectionUtils.isNotEmpty(allData)){
    					result.put("refreshTime", allData.get(0).getHeartbeatTime());
    				}
    				return result;
    				
    				
    			}
    			
    		}
    	}
    	
    	
    	return result;
    	
    	
    }
    
    

}
