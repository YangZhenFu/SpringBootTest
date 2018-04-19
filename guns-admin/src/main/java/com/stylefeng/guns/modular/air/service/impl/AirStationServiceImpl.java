package com.stylefeng.guns.modular.air.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.air.dao.AirStationMapper;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.service.IAirStationService;

/**
 * <p>
 * 气象站表 服务实现类
 * </p>
 *
 * @author YangZhenfu123
 * @since 2018-04-17
 */
@Service
public class AirStationServiceImpl extends ServiceImpl<AirStationMapper, AirStation> implements IAirStationService {

	
	@Override
	public List<Map<String, Object>> findListByParams(Page<AirStation> page, String condition, String orderByField,
			boolean asc) {
		return baseMapper.selectByParams(page,condition,orderByField,asc);
	}

	@Override
	public Integer saveAirStation(AirStation station,String installTimes) {
		int count=0;
		if(station!=null){
			
			if(StringUtils.isNotBlank(installTimes)){
				station.setInstallTime(DateUtil.parse(installTimes, "yyyy-MM-DD"));
			}
			
			if(station.getId()==null){
				station.setCode(StringUtil.generatorShort());
				station.setCreateBy(ShiroKit.getUser().getName());
				station.setCreateTime(new Date());
				count=baseMapper.insert(station);
			}else{
				station.setUpdateBy(ShiroKit.getUser().getName());
				station.setUpdateTime(new Date());
				count=baseMapper.updateById(station);
			}
		}
		return count;
	}

	
	@Override
	public int deleteAirStationById(Integer stationId) {
		int count=0;
		if(stationId!=null){
			AirStation station = baseMapper.selectById(stationId);
			if(station!=null){
				station.setValid("1");
				station.setUpdateBy(ShiroKit.getUser().getName());
				station.setUpdateTime(new Date());
				count=baseMapper.updateById(station);
			}
		}
		return count;
	}

}
