package com.stylefeng.guns.modular.air.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.air.dao.AirSensorWarnParamMapper;
import com.stylefeng.guns.modular.air.model.AirSensorWarnParam;
import com.stylefeng.guns.modular.air.service.IAirSensorWarnParamService;

/**
 * <p>
 * 传感器预警参数表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-24
 */
@Service
public class AirSensorWarnParamServiceImpl extends ServiceImpl<AirSensorWarnParamMapper, AirSensorWarnParam> implements IAirSensorWarnParamService {

	@Override
	public int saveSensorWarnParam(AirSensorWarnParam param) {
		int count=0;
		if(param!=null){
			
			if(param.getId()==null){
				param.setCode(StringUtil.generatorShort());
				param.setCreateBy(ShiroKit.getUser().getName());
				param.setCreateTime(new Date());
				count=baseMapper.insert(param);
			}else{
				param.setUpdateBy(ShiroKit.getUser().getName());
				param.setUpdateTime(new Date());
				count=baseMapper.updateById(param);
			}
		}
		return count;
	}

	@Override
	public int deleteWarnParamById(Integer paramId) {
		int count=0;
		if(paramId!=null){
			AirSensorWarnParam param = baseMapper.selectById(paramId);
			if(param!=null){
				param.setValid("1");
				param.setUpdateBy(ShiroKit.getUser().getName());
				param.setUpdateTime(new Date());
				count=baseMapper.updateById(param);
			}
		}
		return count;
	}

	

}
