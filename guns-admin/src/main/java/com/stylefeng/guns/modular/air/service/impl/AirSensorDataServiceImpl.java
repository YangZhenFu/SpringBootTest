package com.stylefeng.guns.modular.air.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.modular.air.dao.AirSensorDataMapper;
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

}
