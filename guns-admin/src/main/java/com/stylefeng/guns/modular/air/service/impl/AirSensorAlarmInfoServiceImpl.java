package com.stylefeng.guns.modular.air.service.impl;

import com.stylefeng.guns.modular.air.model.AirSensorAlarmInfo;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.air.dao.AirSensorAlarmInfoMapper;
import com.stylefeng.guns.modular.air.service.IAirSensorAlarmInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 传感器告警信息表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-10
 */
@Service
public class AirSensorAlarmInfoServiceImpl extends ServiceImpl<AirSensorAlarmInfoMapper, AirSensorAlarmInfo> implements IAirSensorAlarmInfoService {

	@Override
	public int saveSensorAlarmInfo(AirSensorAlarmInfo airSensorAlarmInfo,String alarmTimes) {
		int count=0;
		if(airSensorAlarmInfo!=null){
			
			if(StringUtils.isNotBlank(alarmTimes)){
				airSensorAlarmInfo.setAlarmTime(DateUtil.parse(alarmTimes, "yyyy-MM-dd HH:mm:ss"));
			}
			
			if(airSensorAlarmInfo.getId()==null){
				airSensorAlarmInfo.setCode(StringUtil.generatorShort());
				airSensorAlarmInfo.setCreateId(ShiroKit.getUser().getName());
				airSensorAlarmInfo.setCreateTime(new Date());
				count=baseMapper.insert(airSensorAlarmInfo);
			}else{
				airSensorAlarmInfo.setUpdateId(ShiroKit.getUser().getName());
				airSensorAlarmInfo.setUpdateTime(new Date());
				count=baseMapper.updateById(airSensorAlarmInfo);
			}
		}
		return count;
	}

	@Override
	public int deleteSensorAlarmInfo(Integer airSensorAlarmInfoId) {
		int count=0;
		if(airSensorAlarmInfoId!=null){
			AirSensorAlarmInfo alarmInfo = baseMapper.selectById(airSensorAlarmInfoId);
			if(alarmInfo!=null){
				alarmInfo.setValid("1");
				alarmInfo.setUpdateId(ShiroKit.getUser().getName());
				alarmInfo.setUpdateTime(new Date());
				count=baseMapper.updateById(alarmInfo);
			}
		}
		return count;
	}

	@Override
	public int handleAlarmInfo(AirSensorAlarmInfo alarm) {
		int count=0;
		if(alarm!=null){
			if(alarm.getId()!=null){
				alarm.setUpdateId(ShiroKit.getUser().getName());
				alarm.setUpdateTime(new Date());
				alarm.setHandleName(ShiroKit.getUser().getName());//处理人
				alarm.setHandleTime(new Date());//处理时间
				count=baseMapper.updateById(alarm);
			}
		}
		return count;
	}
	
	

}
