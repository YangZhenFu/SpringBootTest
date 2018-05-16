package com.stylefeng.guns.modular.air.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.abel533.echarts.AxisPointer;
import com.github.abel533.echarts.DataZoom;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.DataZoomType;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.PointerType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.series.Line;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.WindDirection;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorData;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.service.IAirSensorDataService;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;
import com.stylefeng.guns.modular.air.warpper.AirSensorDataWarpper;

/**  
 * <p>Title: AirDataAnalyzeController</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月26日  
 */
@RequestMapping("air/curve")
@Controller
public class AirDataAnalyzeController extends BaseController{

	private String PREFIX = "/air/curve/";
	
	@Autowired
	private IAirSensorDataService airSensorDataService;
	@Autowired
	private IAirStationService airStationService;
	@Autowired
	private IAirSensorService airSensorService;
	@Autowired
	private ISensorTypeService sensorTypeService;
	
	@RequestMapping
	public String main(){
		return PREFIX+"air_curve.html";
	}
	
	
//	@RequestMapping(value="list",method=RequestMethod.POST)
//	@ResponseBody
//	public Object querySensorData(@RequestParam(required=false) String condition,@RequestParam(required=false) String areaId,@RequestParam(required=false) String beginTime,@RequestParam(required=false) String endTime){
//		Page<AirSensorData> page =new PageFactory<AirSensorData>().defaultPage();
//    	List<Map<String, Object>> list = airSensorDataService.findDataByParams(page, condition, areaId, beginTime, endTime, page.getOrderByField(), page.isAsc());
//    	page.setRecords((List<AirSensorData>) new AirSensorDataWarpper(list).warp());
//        return packForBT(page);
//	}
//	
//	/**
//	 * <p>Title: querySensorData</p>  
//	 * <p>Description: 根据条件查询数据</p>  
//	 * @param code
//	 * @return
//	 */
//	@RequestMapping(value="query",method=RequestMethod.POST)
//	@ResponseBody
//	public Map<String,Object> querySensorData(@RequestParam(required=false) String code){
//		Map<String,Object> result=Maps.newHashMap();
//		if(code!=null){
//			AirSensor sensor = airSensorService.selectOne(new EntityWrapper<AirSensor>().eq("code", code).eq("valid", "0"));
//			AirStation station = airStationService.selectOne(new EntityWrapper<AirStation>().eq("code", code).eq("valid", "0"));
//			//传感器类型
//			if(sensor!=null){
//				//查询传感器类型名称
//				sensor.setTypeName(sensorTypeService.selectById(sensor.getTypeId()).gettName());
//				sensor.setLegend(sensor.getTypeName()+"("+sensor.getUnit()+")");
//				
//				List<List<AirSensorData>> datas=Lists.newArrayList();
//				
//				//查询历史数据
//				List<AirSensorData> sensorData = airSensorDataService.selectList(new EntityWrapper<AirSensorData>().eq("sensor_id", sensor.getId()));
//				if(CollectionUtils.isNotEmpty(sensorData)){
//					
//					//如果传感器是风向类型的
//					if(sensor.getTypeName().contains("风向")){
//						for(AirSensorData data : sensorData){
//							data.setRemark(WindDirection.findWindDirectionByMark(data.getNumerical()).getMsg());;
//						}
//					}
//					datas.add(sensorData);
//					
//					result.put("data", datas);
//					result.put("device",Lists.newArrayList(sensor));
//					return result;
//				}
//				
//			}
//			//气象站类型
//			else if(station!=null){
//				//查询所有的传感器
//				List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
//				if(CollectionUtils.isNotEmpty(sensors)){
//					List<List<AirSensorData>> datas=Lists.newArrayList();
//					for(AirSensor _sensor : sensors){
//						//查询传感器类型名称
//						_sensor.setTypeName(sensorTypeService.selectById(_sensor.getTypeId()).gettName());
//						_sensor.setLegend(_sensor.getTypeName()+"("+_sensor.getUnit()+")");
//						
//						//查询历史数据
//						List<AirSensorData> sensorData = airSensorDataService.selectList(new EntityWrapper<AirSensorData>().eq("sensor_id", _sensor.getId()));
//						if(CollectionUtils.isNotEmpty(sensorData)){
//							
//							//如果传感器是风向类型的
//							if(_sensor.getTypeName().contains("风向")){
//								for(AirSensorData data : sensorData){
//									data.setRemark(WindDirection.findWindDirectionByMark(data.getNumerical()).getMsg());
//								}
//							}
//							datas.add(sensorData);
//						}
//						
//					}
//					result.put("data", datas);
//					result.put("device", sensors);
//					return result;
//					
//				}
//			}
//			
//		}
//		return null;
//	}
//	
//	
//	
//	@RequestMapping(value="query1",method=RequestMethod.POST)
//	@ResponseBody
//	public Option generateAirDataCurve(@RequestParam(required=false) String code){
//		Map<String,Object> result=Maps.newHashMap();
//		Option option=new Option();
////		option.title("数据统计表")
////			  .tooltip(new Tooltip().trigger(Trigger.axis).show(true).axisPointer(new AxisPointer().type(PointerType.cross)).backgroundColor("#283b56"))
////			  .dataZoom(Lists.newArrayList(new DataZoom().type(DataZoomType.slider).start(0).end(100),new DataZoom().type(DataZoomType.inside).start(0).end(100)));
//		option.legend("高度(km)与气温(°C)变化关系");
//
//	    option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);
//
//	    option.calculable(true);
//	    option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");
//
//	    ValueAxis valueAxis = new ValueAxis();
//	    valueAxis.axisLabel().formatter("{value} °C");
//	    option.xAxis(valueAxis);
//
//	    CategoryAxis categoryAxis = new CategoryAxis();
//	    categoryAxis.axisLine().onZero(false);
//	    categoryAxis.axisLabel().formatter("{value} km");
//	    categoryAxis.boundaryGap(false);
//	    categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
//	    option.yAxis(categoryAxis);
//
//	    Line line = new Line();
//	    line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
//	    option.series(line);
//		return option;
//		
//	}
	
	
}
