package com.stylefeng.guns.modular.air.controller;

import java.util.Date;
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
import com.stylefeng.guns.core.common.constant.WindDirection;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.air.dto.AirSensorDataDto;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorData;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.service.IAirSensorDataService;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;

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
	private IAirSensorDataService airSensorDataService;
	@Autowired
	private ISensorTypeService sensorTypeService;
	
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
    				
    				for(AirSensor sensor : sensors){
    					//查询24小时之内最大值
    					AirSensorData maxData=airSensorDataService.selectOneDayMaxData(sensor.getId());
    					//查询24小时之内最小值
    					AirSensorData minData=airSensorDataService.selectOneDayMinData(sensor.getId());
    					//查询当前最新数据
    					List<AirSensorData> list = airSensorDataService.selectList(new EntityWrapper<AirSensorData>().eq("sensor_id", sensor.getId()).orderBy("heartbeat_time desc"));
    					
    					AirSensorDataDto dto =new AirSensorDataDto();
    					dto.setId(StringUtil.generatorShort());
    					dto.setCode(sensor.getCode());
    					dto.settName(sensor.gettName());
    					dto.setTypeName(sensorTypeService.selectById(sensor.getTypeId()).gettName());
    					dto.setUnit(sensor.getUnit());
    					if(maxData!=null){
    						
    						if(dto.getTypeName().contains("风向")){
    							dto.setMaxNumerical(WindDirection.findWindDirectionByMark(maxData.getNumerical()).getMsg());
    						}else{
    							dto.setMaxNumerical(maxData.getNumerical());
    						}
    						dto.setMaxTime(maxData.getHeartbeatTime());
    					}
    					if(minData!=null){
    						if(dto.getTypeName().contains("风向")){
    							dto.setMinNumerical(WindDirection.findWindDirectionByMark(minData.getNumerical()).getMsg());
    						}else{
    							dto.setMinNumerical(minData.getNumerical());
    						}
    						dto.setMinTime(minData.getHeartbeatTime());
    					}
    					if(CollectionUtils.isNotEmpty(list)){
    						AirSensorData nowData = list.get(0);
    						if(dto.getTypeName().contains("风向")){
    							dto.setNowNumerical(WindDirection.findWindDirectionByMark(nowData.getNumerical()).getMsg());
    						}else{
    							dto.setNowNumerical(nowData.getNumerical());
    						}
    					}
    					dtos.add(dto);
    					
    				}
    				
    				//查询数据更新时间
    				Date refreshTime=airSensorDataService.selectMaxHeatbeatTime(sensors);
    				result.put("refreshTime", refreshTime);
    				result.put("data", dtos);
    				return result;
    			}
    			
    		}
    	}
    	
    	
    	return result;
    	
    	
    }
    
    
    
    

}
