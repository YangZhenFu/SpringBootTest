package com.stylefeng.guns.air;

import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.core.common.constant.AirUnit;
import com.stylefeng.guns.core.common.constant.WindDirection;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.other.StringUtil;
//import com.stylefeng.guns.modular.air.dao.AirSensorDataMapper;
import com.stylefeng.guns.modular.air.dao.AirStationDataMapper;
import com.stylefeng.guns.modular.air.model.AirSensorAlarmInfo;
//import com.stylefeng.guns.modular.air.model.AirSensorData;
import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.service.IAirSensorAlarmInfoService;



/**  
 * <p>Title: AirSensorTest</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月20日  
 */
public class AirSensorTest extends BaseJunit{

//	@Autowired
//	AirSensorDataMapper sensorDataMapper;
	
	@Autowired
	AirStationDataMapper stationDataMapper;
	
	@Autowired
	IAirSensorAlarmInfoService sensorAlarmInfoService;
	
//	@Test
//	public void test1(){
//		//Long[] sensorId=new Long[]{1L,2L,4L,5L,6L,7L,8L,9L};
//		Map<Long,String> e1=ImmutableMap.<Long, String>of(1L, AirUnit.WIND_SPEED.getUnit());
//		Map<Long,String> e2=ImmutableMap.<Long, String>of(2L, AirUnit.TEMPERATURE.getUnit());
//		Map<Long,String> e3=ImmutableMap.<Long, String>of(4L, WindDirection.NORTHEAST_BY_EAST.getMark());
//		Map<Long,String> e4=ImmutableMap.<Long, String>of(5L, AirUnit.TEMPERATURE.getUnit());
//		Map<Long,String> e5=ImmutableMap.<Long, String>of(6L, AirUnit.PM.getUnit());
//		Map<Long,String> e6=ImmutableMap.<Long, String>of(7L, AirUnit.NOISE.getUnit());
//		Map<Long,String> e7=ImmutableMap.<Long, String>of(8L, AirUnit.HUMIDITY.getUnit());
//		Map<Long,String> e8=ImmutableMap.<Long, String>of(9L, AirUnit.PM.getUnit());
//		Map<Long,String> e9=ImmutableMap.<Long, String>of(10L, AirUnit.TEMPERATURE.getUnit());
//		Map<Long,String> e10=ImmutableMap.<Long, String>of(11L, AirUnit.HUMIDITY.getUnit());
//		Map<Long,String> e11=ImmutableMap.<Long, String>of(12L, AirUnit.WIND_SPEED.getUnit());
//		List<Map<Long,String>> datas= ImmutableList.<Map<Long,String>>of(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10,e11);
//		int count=0;
//		long start = System.currentTimeMillis();
//		for(int i=0;i<1000;i++){
//			AirSensorData data=new AirSensorData();
//			data.setCode(StringUtil.generatorShort());
//			data.setSortCode(i);
//			Map<Long, String> map = datas.get((int)(Math.random()*11));
//			
//			data.setSensorId(map.keySet().iterator().next());
//			data.settName("传感器"+data.getSensorId()+" "+DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//			data.setSignalStrength(5);
//			data.setHeartbeatTime(new Date());
//			data.setCreateBy("张三");
//			data.setCreateTime(new Date());
//			if(data.getSensorId()==4){
//				data.setNumerical(map.get(data.getSensorId()));
//			}else{
//				data.setNumerical(new DecimalFormat("#.0").format((double)(Math.random()*100)+1));
//				//data.setUnit(AirUnit.values()[(int)(Math.random()*12)].getUnit());
//			}
//			count+=sensorDataMapper.insert(data);
//		}
//		System.out.println("共耗时:"+(System.currentTimeMillis()-start)+"ms");
//		assertTrue(count==1000);
//	}
	
	@Test
	public void test2(){
		int count=0;
		Long startTime=System.currentTimeMillis();
		Long[] ids=new Long[]{1L,3L,4L,7L};
		DecimalFormat format=new DecimalFormat("#.0");
		WindDirection[] directions = WindDirection.values();
		for(int i=0;i<1000;i++){
			AirStationData data=new AirStationData();
			data.setStationId(ids[(int)(Math.random()*4)]);
			data.settName("气象站"+data.getStationId()+"-"+DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			data.setHeartbeatTime(new Date());
			data.setAirTemperature(format.format((double)(Math.random()*100)+1));
			data.setAirHumidity(format.format((double)(Math.random()*100)+1));
			data.setWindSpeed(format.format((double)(Math.random()*100)+1));
			data.setWindDirection(directions[(int)(Math.random()*16)].getMark());
			data.setNoise(format.format((double)(Math.random()*100)+1));
			data.setPm1(format.format((double)(Math.random()*100)+1));
			data.setPm10(format.format((double)(Math.random()*100)+1));
			data.setPm25(format.format((double)(Math.random()*100)+1));
			count+=stationDataMapper.insert(data);
		}
		System.out.println("共耗时："+(System.currentTimeMillis()-startTime)+"ms");
		assertTrue(count==1000);
	}
	
	@Test
	public void test() {
	    //地址:http://echarts.baidu.com/doc/example/line5.html
//	    EnhancedOption option = new EnhancedOption();
		GsonOption option=new GsonOption();
	    option.legend("高度(km)与气温(°C)变化关系");

	    option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);

	    option.calculable(true);
	    option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");

	    ValueAxis valueAxis = new ValueAxis();
	    valueAxis.axisLabel().formatter("{value} °C");
	    option.xAxis(valueAxis);

	    CategoryAxis categoryAxis = new CategoryAxis();
	    categoryAxis.axisLine().onZero(false);
	    categoryAxis.axisLabel().formatter("{value} km");
	    categoryAxis.boundaryGap(false);
	    categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
	    option.yAxis(categoryAxis);

	    Line line = new Line();
	    line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
	    option.series(line);
	    option.exportToHtml("line5.html");
	    option.view();
	}
	
	@Test
	public void test3(){
		List<AirSensorAlarmInfo> alarms = sensorAlarmInfoService.selectList(new EntityWrapper<AirSensorAlarmInfo>().eq("sensor_id", 1L).eq("valid", "0").eq("handle_state", "0").eq("alarm_type", "0"));
		System.out.println("==================="+alarms.size());
	}
	
	
}
