package com.stylefeng.guns.modular.air.service.impl;

import com.stylefeng.guns.modular.air.model.AirTask;
import com.stylefeng.guns.modular.air.dao.AirTaskMapper;
import com.stylefeng.guns.modular.air.service.IAirTaskService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 气象站任务执行表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-07
 */
@Service
public class AirTaskServiceImpl extends ServiceImpl<AirTaskMapper, AirTask> implements IAirTaskService {

}
