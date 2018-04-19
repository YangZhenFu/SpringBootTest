package com.stylefeng.guns.modular.air.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.air.dao.SensorTypeMapper;
import com.stylefeng.guns.modular.air.model.SensorType;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;

/**
 * <p>
 * 传感器类型表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-19
 */
@Service
public class SensorTypeServiceImpl extends ServiceImpl<SensorTypeMapper, SensorType> implements ISensorTypeService {

	@Override
	public Integer saveSensorType(SensorType sensorType) {
		int count=0;
		if(sensorType!=null){
			if(sensorType.getId()==null){
				sensorType.setCreateBy(ShiroKit.getUser().getName());
				sensorType.setCreateTime(new Date());
				count=baseMapper.insert(sensorType);
			}else{
				sensorType.setUpdateBy(ShiroKit.getUser().getName());
				sensorType.setUpdateTime(new Date());
				count=baseMapper.updateById(sensorType);
			}
		}
		return count;
	}

	@Override
	public Integer deleteSensorTypeById(Integer sensorTypeId) {
		int count=0;
		if(sensorTypeId!=null){
			SensorType sensorType = baseMapper.selectById(sensorTypeId);
			if(sensorType!=null){
				sensorType.setValid("1");
				sensorType.setUpdateBy(ShiroKit.getUser().getName());
				sensorType.setUpdateTime(new Date());
				count=baseMapper.updateById(sensorType);
			}
		}
		return count;
	}

}
