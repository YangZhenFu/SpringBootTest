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
import com.stylefeng.guns.modular.air.dao.AirSensorMapper;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.service.IAirSensorService;

/**
 * <p>
 * 传感器表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-19
 */
@Service
public class AirSensorServiceImpl extends ServiceImpl<AirSensorMapper, AirSensor> implements IAirSensorService {

	@Override
	public int saveAirSensor(AirSensor airSensor, String installTimes) {
		int count=0;
		if(airSensor!=null){
			
			if(StringUtils.isNotBlank(installTimes)){
				airSensor.setInstallTime(DateUtil.parse(installTimes, "yyyy-MM-DD"));
			}
			
			if(airSensor.getId()==null){
				airSensor.setCode(StringUtil.generatorShort());
				airSensor.setCreateBy(ShiroKit.getUser().getName());
				airSensor.setCreateTime(new Date());
				count=baseMapper.insert(airSensor);
			}else{
				airSensor.setUpdateBy(ShiroKit.getUser().getName());
				airSensor.setUpdateTime(new Date());
				count=baseMapper.updateById(airSensor);
			}
		}
		return count;
	}

	@Override
	public int deleteAirSensor(Integer airSensorId) {
		int count=0;
		if(airSensorId!=null){
			AirSensor airSensor = baseMapper.selectById(airSensorId);
			if(airSensor!=null){
				airSensor.setValid("1");
				airSensor.setUpdateBy(ShiroKit.getUser().getName());
				airSensor.setUpdateTime(new Date());
				count=baseMapper.updateById(airSensor);
			}
		}
		return count;
	}

	@Override
	public List<Map<String, Object>> findListByParams(Page<AirSensor> page, String condition, String orderByField,
			boolean isAsc) {
		return baseMapper.findListByParams(page,condition,orderByField,isAsc);
	}

}
