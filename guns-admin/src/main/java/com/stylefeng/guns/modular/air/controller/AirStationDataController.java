package com.stylefeng.guns.modular.air.controller;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.stylefeng.guns.core.common.constant.AirPollutionIndex;
import com.stylefeng.guns.core.common.constant.AirQualityGrade;
import com.stylefeng.guns.core.common.constant.WindDirection;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.excel.ExcelUtils;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.modular.air.dto.AirStationDataDto;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationDataService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;
import com.stylefeng.guns.modular.air.warpper.AirStationDataWarpper;

import cn.hutool.core.util.NumberUtil;

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
			
			List<AirStationDataDto> dtos=Lists.newArrayList();
			for(AirStation station : stations){
				
				AirStationDataDto dto=new AirStationDataDto();
				//查询气象站近五天数据
				List<AirStationData> data=airStationDataService.selectFiveDaysData(station.getId());
				//计算平均数值
				calAvgAQIData(data,dto);
				
				//计算空气质量指数  int pm25_AQI=0,pm10_AQI=0,co_AQI=0,o3_AQI=0,so2_AQI=0,no2_AQI=0;
				Integer[] aqi={0,0,0,0,0,0};
				if(StringUtils.isNotBlank(dto.getPm25()))  aqi[0]=AirPollutionIndex.calAirPollutionIndex((int)Math.round(Double.valueOf(dto.getPm25())), "pm2.5");
				if(StringUtils.isNotBlank(dto.getPm10()))  aqi[1] = AirPollutionIndex.calAirPollutionIndex((int)Math.round(Double.valueOf(dto.getPm10())), "pm10");
				if(StringUtils.isNotBlank(dto.getCo()))  aqi[2] = AirPollutionIndex.calAirPollutionIndex((int)Math.round(Double.valueOf(dto.getCo())), "CO");
				if(StringUtils.isNotBlank(dto.getO3()))  aqi[3] = AirPollutionIndex.calAirPollutionIndex((int)Math.round(Double.valueOf(dto.getO3())), "O3");
				if(StringUtils.isNotBlank(dto.getSo2()))  aqi[4] = AirPollutionIndex.calAirPollutionIndex((int)Math.round(Double.valueOf(dto.getSo2())), "SO2");
				if(StringUtils.isNotBlank(dto.getNo2()))  aqi[5] = AirPollutionIndex.calAirPollutionIndex((int)Math.round(Double.valueOf(dto.getNo2())), "NO2");

				//计算首要污染物
				calPrimaryPollutant(aqi,dto);
				
				//计算空气质量等级
				dto.setAirGrade(AirQualityGrade.calculateGrade(Double.valueOf(dto.getAQI())).getLevel());
				
				//查询传感器总数
				int sensorCount = airSensorService.selectCount(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
				
				dto.setId(StringUtil.generatorShort());
				dto.setCode(station.getCode());
				dto.settName(station.gettName());
				dto.setSensorNum(sensorCount);
				
				dtos.add(dto);
			}
			result.put("data", dtos);
		}
		
		return result;
	}
	
    
    
	/**  
	 * <p>Title: calPrimaryPollutant</p>  
	 * <p>Description: 计算首要污染物</p>  
	 * @param aqi
	 * @param dto  
	 */ 
	private void calPrimaryPollutant(Integer[] aqi, AirStationDataDto dto) {
		if(aqi!=null && aqi.length>0){
			List<Integer> asList = Arrays.asList(aqi);
			Collections.sort(asList);
			dto.setAQI(asList.get(asList.size()-1));
		}
		
	}


	/**  
	 * <p>Title: calAvgAQIData</p>  
	 * <p>Description: 计算aqi平均数值</p>  
	 * @param data
	 * @return  
	 */ 
	private void calAvgAQIData(List<AirStationData> datas,AirStationDataDto avgData) {
		if(CollectionUtils.isNotEmpty(datas)){
			//监测数据总值
	    	double all_Airtemperature=0,all_Airhumidity=0,all_Soiltemperature=0,all_SoilAirhumidity=0,all_illuminance=0,all_rainfall=0,all_airpressure=0,
	    		   all_WindSpeed=0,all_noise=0,all_pm25=0,all_pm10=0,all_pm1=0,all_co=0,all_o3=0,all_so2=0,all_no2=0,all_radiation=0,all_oxygenion=0;
	    	DecimalFormat df=new DecimalFormat("#.0");
	    	int total=datas.size();
			for(AirStationData data : datas){
				all_Airtemperature+=Convert.toDouble(data.getAirTemperature(),0D);
				all_Airhumidity+=Convert.toDouble(data.getAirHumidity(),0D);
				all_Soiltemperature+=Convert.toDouble(data.getSoilTemperature(),0D);
				all_SoilAirhumidity+=Convert.toDouble(data.getSoilHumidity(),0D);
				all_illuminance+=Convert.toDouble(data.getIlluminance(),0D);
				all_rainfall+=Convert.toDouble(data.getRainfall(),0D);
				all_airpressure+=Convert.toDouble(data.getAirPressure(),0D);
				all_WindSpeed+=Convert.toDouble(data.getWindSpeed(),0D);
				all_noise+=Convert.toDouble(data.getNoise(),0D);
				all_pm25+=Convert.toDouble(data.getPm25(),0D);
				all_pm10+=Convert.toDouble(data.getPm10(),0D);
				all_pm1+=Convert.toDouble(data.getPm1(),0D);
				all_co+=Convert.toDouble(data.getCo(),0D);
				all_o3+=Convert.toDouble(data.getO3(),0D);
				all_so2+=Convert.toDouble(data.getSo2(),0D);
				all_no2+=Convert.toDouble(data.getNo2(),0D);
				all_radiation+=Convert.toDouble(data.getRadiation(),0D);
				all_oxygenion+=Convert.toDouble(data.getNegativeOxygenIon(),0D);
			}
			avgData.setAirTemperature(df.format(all_Airtemperature/total));
			avgData.setAirHumidity(df.format(all_Airhumidity/total));
			avgData.setSoilTemperature(df.format(all_Soiltemperature/total));
			avgData.setSoilHumidity(df.format(all_SoilAirhumidity/total));
			avgData.setIlluminance(df.format(all_illuminance/total));
			avgData.setRainfall(df.format(all_rainfall/total));
			avgData.setAirPressure(df.format(all_airpressure/total));
			avgData.setWindSpeed(df.format(all_WindSpeed/total));
			avgData.setNoise(df.format(all_noise/total));
			avgData.setPm25(df.format(all_pm25/total));
			avgData.setPm10(df.format(all_pm10/total));
			avgData.setPm1(df.format(all_pm1/total));
			avgData.setCo(df.format(all_co/total));
			avgData.setO3(df.format(all_o3/total));
			avgData.setSo2(df.format(all_so2/total));
			avgData.setNo2(df.format(all_no2/total));
			avgData.setRadiation(df.format(all_radiation/total));
			avgData.setNegativeOxygenIon(df.format(all_oxygenion/total));
		}
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
