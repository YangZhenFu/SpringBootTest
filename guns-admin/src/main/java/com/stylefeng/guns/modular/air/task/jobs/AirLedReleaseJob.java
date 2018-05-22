package com.stylefeng.guns.modular.air.task.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.other.DateUtil;
import com.stylefeng.guns.modular.air.model.AirLed;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.service.IAirLedService;
import com.stylefeng.guns.modular.air.service.IAirSensorAlarmInfoService;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationDataService;
import com.stylefeng.guns.modular.air.service.IAirStationService;

/**  
 * <p>Title: GetUdpDataTask</p>  
 * <p>Description: LED数据发布任务</p>  
 * @author YangZhenfu  
 * @date 2018年5月8日  
 */
@Component
public class AirLedReleaseJob implements Job{

	@Autowired
	private IAirStationService airStationService;
	@Autowired
	private IAirSensorService airSensorService;
	@Autowired
	private IAirStationDataService airStationDataService;
	@Autowired
	private IAirSensorAlarmInfoService sensorAlarmInfoService;
	@Autowired
	private IAirLedService airLedService;
	
	private Logger logger = LoggerFactory.getLogger(AirLedReleaseJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("LED数据发布：启动任务======================="+DateUtil.formatFullDateTime(new Date()));
	    JobDataMap map = context.getJobDetail().getJobDataMap();
	    String deviceCode = map.getString("deviceCode");
	    logger.info("code --> "+deviceCode);
	    run(deviceCode);
	    logger.info("LED数据发布：下次执行时间====="+
	        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
	            .format(context.getNextFireTime())+"==============");
	}
	
	public void run(String deviceCode){
		if(StringUtils.isNotBlank(deviceCode)){
			//查询LED终端
			AirLed led = airLedService.selectOne(new EntityWrapper<AirLed>().eq("code", deviceCode).eq("valid", "0"));
			if(led!=null){
				System.out.println("============="+led.gettName()+"============");
				//查询所属气象站
				AirStation station = airStationService.selectById(led.getStationId());
				if(station!=null){
					List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
					if(CollectionUtils.isNotEmpty(sensors)){
					}
				}
			}
			
		}
		
	}
	
	
	
}
