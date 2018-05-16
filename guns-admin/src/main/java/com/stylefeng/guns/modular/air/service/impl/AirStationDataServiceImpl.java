package com.stylefeng.guns.modular.air.service.impl;

import com.stylefeng.guns.modular.air.model.AirStationData;
import com.stylefeng.guns.modular.air.dao.AirStationDataMapper;
import com.stylefeng.guns.modular.air.service.IAirStationDataService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 气象站检测数据表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-02
 */
@Service
public class AirStationDataServiceImpl extends ServiceImpl<AirStationDataMapper, AirStationData> implements IAirStationDataService {

	@Override
	public List<Map<String, Object>> findPageDataByParams(Page<AirStationData> page, String condition, String areaId,
			String beginTime, String endTime, String orderByField, boolean isAsc) {
		return baseMapper.selectPageDataByParams(page,condition,areaId,beginTime,endTime,orderByField,isAsc);
	}

	@Override
	public List<AirStationData> selectDataByParams(String condition,String code, String beginTime, String endTime) {
		return baseMapper.selectDataByParams(condition,code,beginTime,endTime);
	}

	@Override
	public List<Map<String, Object>> selectMapDataByParams(String condition, String areaId, String beginTime,
			String endTime) {
		return baseMapper.selectMapDataByParams(condition,areaId,beginTime,endTime);
	}

	
	@Override
	public List<AirStationData> findOneDayData(Long id) {
		return baseMapper.findOneDayData(id);
	}

	@Override
	public List<AirStationData> selectFiveDaysData(Long id) {
		return baseMapper.findFiveDaysData(id);
	}

}
