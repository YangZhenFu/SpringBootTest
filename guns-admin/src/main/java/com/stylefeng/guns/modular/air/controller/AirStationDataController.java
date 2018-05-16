package com.stylefeng.guns.modular.air.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.stylefeng.guns.core.excel.ExcelUtils;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationDataService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;
import com.stylefeng.guns.modular.air.warpper.AirStationDataWarpper;

/**
 * 气象站检测数据控制器
 *
 * @author fengshuonan
 * @Date 2018-05-02 17:11:06
 */
@Controller
@RequestMapping("/airStationData")
public class AirStationDataController extends BaseController {

    private String PREFIX = "/air/airStationData/";

    @Autowired
    private IAirStationDataService airStationDataService;
    @Autowired
	private IAirStationService airStationService;
	@Autowired
	private IAirSensorService airSensorService;
	@Autowired
	private ISensorTypeService sensorTypeService;
	
	private static Logger logger = LoggerFactory.getLogger(AirStationDataController.class);
    
    /**
     * 跳转到气象站检测数据首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "airStationData.html";
    }


    /**
     * 获取气象站检测数据列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required=false) String condition,@RequestParam(required=false) String areaId,@RequestParam(required=false) String beginTime,@RequestParam(required=false) String endTime) {
    	Page<AirStationData> page=new PageFactory<AirStationData>().defaultPage();
    	List<Map<String,Object>> list=airStationDataService.findPageDataByParams(page,condition,areaId,beginTime,endTime,page.getOrderByField(),page.isAsc());
    	page.setRecords((List<AirStationData>)new AirStationDataWarpper(list).warp());
    	return packForBT(page);
    }
    
    
    
    /**
	 * <p>Title: querySensorData</p>  
	 * <p>Description: 根据条件查询数据</p>  
	 * @param code
	 * @return
	 */
	@RequestMapping(value="query",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> querySensorData(@RequestParam(required=false) String areaId,@RequestParam(required=false)String beginTime,@RequestParam(required=false)String endTime){
		Map<String,Object> result=Maps.newHashMap();
		if(areaId!=null){
			AirSensor sensor = airSensorService.selectOne(new EntityWrapper<AirSensor>().eq("code", areaId).eq("valid", "0"));
			AirStation station = airStationService.selectOne(new EntityWrapper<AirStation>().eq("code", areaId).eq("valid", "0"));
			//传感器类型
			if(sensor!=null){
				//查询传感器类型名称
				sensor.setTypeName(sensorTypeService.selectById(sensor.getTypeId()).gettName());
				sensor.setLegend(sensor.getTypeName()+"("+sensor.getUnit()+")");

				//查询传感器所属气象站
				AirStation airStation = airStationService.selectById(sensor.getStationId());
				if(airStation!=null){
					//查询气象站历史数据
					List<AirStationData> datas=airStationDataService.selectDataByParams(null,airStation.getCode(),beginTime, endTime);
					if(CollectionUtils.isNotEmpty(datas)){
						for(AirStationData data : datas){
							if(StringUtils.isNotBlank(data.getWindDirection())){
								data.setWindDirectionMsg(WindDirection.findWindDirectionByMark(data.getWindDirection()).getMsg());
							}
						}
					}
					
					result.put("data", datas);
					result.put("device",Lists.newArrayList(sensor));
					return result;
					
				}
			}
			//气象站类型
			else if(station!=null){
				//查询所有的传感器
				List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
				if(CollectionUtils.isNotEmpty(sensors)){
					for(AirSensor _sensor : sensors){
						//查询传感器类型名称
						_sensor.setTypeName(sensorTypeService.selectById(_sensor.getTypeId()).gettName());
						_sensor.setLegend(_sensor.getTypeName()+"("+_sensor.getUnit()+")");
						
					}
					
					//查询气象站历史数据
					List<AirStationData> datas=airStationDataService.selectDataByParams(null,areaId,beginTime, endTime);
					if(CollectionUtils.isNotEmpty(datas)){
						for(AirStationData data : datas){
							if(StringUtils.isNotBlank(data.getWindDirection())){
								data.setWindDirectionMsg(WindDirection.findWindDirectionByMark(data.getWindDirection()).getMsg());
							}
						}
					}
					result.put("data", datas);
					result.put("device", sensors);
					return result;
					
				}
			}
			
		}
		return null;
	}
    
	/**
	 * <p>Title: analyzeFiveDaysAQI</p>  
	 * <p>Description: 查询气象站近五天AQI数值</p>  
	 * @return
	 */
	@RequestMapping(value="showFiveDaysAQI",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> analyzeFiveDaysAQI(){
		Map<String,Object> result=Maps.newHashMap();
		List<AirStation> stations = airStationService.selectList(new EntityWrapper<AirStation>().eq("valid", "0"));
		if(CollectionUtils.isNotEmpty(stations)){
			for(AirStation station : stations){
				//查询气象站近五天数据
				List<AirStationData> data=airStationDataService.selectFiveDaysData(station.getId());
				//计算AQI平均数值
				String aqi=calAvgAQIData(data);
			}
		}
		
		return result;
	}
	
    
    
	/**  
	 * <p>Title: calAvgAQIData</p>  
	 * <p>Description: 计算aqi平均数值</p>  
	 * @param data
	 * @return  
	 */ 
	private String calAvgAQIData(List<AirStationData> data) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * 导出用户
	 * @param params
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="export", method=RequestMethod.POST )
	public void exportFile(@RequestParam(required=false) String condition,@RequestParam(required=false) String areaId,@RequestParam(required=false) String beginTime,@RequestParam(required=false) String endTime, HttpServletResponse response) {
		List<AirStationData> datas=Lists.newArrayList();
		
		//根据条件查询数据
		List<Map<String,Object>> list = airStationDataService.selectMapDataByParams(condition, areaId, beginTime, endTime);
		datas.addAll((List<AirStationData>) new AirStationDataWarpper(list).warp());
		
		Map<String, String> titleMap=Maps.newLinkedHashMap();
		titleMap.put("唯一标识", "id");
		titleMap.put("气象站名称", "stationName");
		titleMap.put("接收时间", "heartbeatTime");
		titleMap.put("检测数据", "dataInfo");
		titleMap.put("检测类型", "dataType");
		
		try {
			//流的方式直接下载
			ExcelUtils.exportExcel(response, "历史数据.xls", datas, titleMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	

}
