package com.stylefeng.guns.modular.air.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.air.dao.AirSensorDataMapper;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorData;
import com.stylefeng.guns.modular.air.service.IAirSensorDataService;

/**
 * <p>
 * 传感器检测数据表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-20
 */
@Service
public class AirSensorDataServiceImpl extends ServiceImpl<AirSensorDataMapper, AirSensorData> implements IAirSensorDataService {

	@Override
	public List<Map<String, Object>> findDataByParams(Page<AirSensorData> page, String condition, String areaId,
			String beginTime, String endTime, String orderByField, boolean isAsc) {
		return baseMapper.selectListByParams(page, condition, areaId, beginTime, endTime, orderByField, isAsc);
	}

	@Override
	public List<Map<String, Object>> queryDataByParams(String condition, String areaId, String beginTime,
			String endTime) {
		return baseMapper.queryListByParams(condition, areaId, beginTime, endTime);
	}

	@Override
	public AirSensorData selectOneDayMaxData(Long id) {
		return baseMapper.findOneDayMaxData(id);
	}

	@Override
	public AirSensorData selectOneDayMinData(Long id) {
		return baseMapper.findOneDayMinData(id);
	}

	@Override
	public Date selectMaxHeatbeatTime(List<AirSensor> sensors) {
		return baseMapper.findMaxHeatbeatTime(sensors);
	}

	

}
