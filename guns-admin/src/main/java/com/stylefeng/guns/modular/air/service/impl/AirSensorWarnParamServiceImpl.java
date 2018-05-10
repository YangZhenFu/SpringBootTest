package com.stylefeng.guns.modular.air.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.other.CronUtil;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.modular.air.dao.AirSensorMapper;
import com.stylefeng.guns.modular.air.dao.AirSensorWarnParamMapper;
import com.stylefeng.guns.modular.air.dao.AirTaskMapper;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorWarnParam;
import com.stylefeng.guns.modular.air.model.AirTask;
import com.stylefeng.guns.modular.air.service.IAirSensorWarnParamService;
import com.stylefeng.guns.modular.air.task.JobTask;

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

	@Autowired
	private AirSensorMapper airSensorMapper;
	@Autowired
	private AirTaskMapper airTaskMapper;
	@Autowired
	private JobTask jobTask;
	
	@Override
	public int saveSensorWarnParam(AirSensorWarnParam param) {
		int count=0;
		if(param!=null){
			
			if(param.getId()==null){
				param.setCode(StringUtil.generatorShort());
				param.setCreateBy(ShiroKit.getUser().getName());
				param.setCreateTime(new Date());
				count=baseMapper.insert(param);
				
				//查询预警设备
				AirSensor sensor = airSensorMapper.selectById(param.getSensorId());
				if(sensor!=null){
					//添加设备预警检查任务
					AirTask task = new AirTask();
					task.setId(StringUtil.generator());
					task.setJobName("传感器 -->"+sensor.gettName()+" 预警检查定时");
					task.setJobDesc("传感器 -->"+sensor.gettName()+" 预警检查定时任务, 每隔"+param.getWarnInterval()+"分钟执行一次");
					task.setDeviceCode(sensor.getCode());
					task.setCron(CronUtil.generateMinuteCron(String.valueOf(param.getWarnInterval())));
					task.setClazzPath("com.stylefeng.guns.modular.air.task.jobs.AirSensorWarnJob");
					task.setStatus(Integer.parseInt(param.getControlMode()));
					task.setCreateBy(ShiroKit.getUser().getName());
					task.setCreateTime(new Date());
					airTaskMapper.insert(task);
					//如果为启动状态
					if(task.getStatus()==0){
						//启动任务
						jobTask.startJob(task);
					}
				}
				
				
				
			}else{
				param.setUpdateBy(ShiroKit.getUser().getName());
				param.setUpdateTime(new Date());
				count=baseMapper.updateById(param);
				
				//查询传感器
				AirSensor sensor = airSensorMapper.selectById(param.getSensorId());
				
				//查询传感器预警参数
				AirSensorWarnParam warnParam = baseMapper.selectById(param.getId());
				
				if(sensor!=null){
					//查询传感器预警检查任务
					AirTask taskQuery=new AirTask();
					taskQuery.setDeviceCode(sensor.getCode());
					AirTask task = airTaskMapper.selectOne(taskQuery);
					if(task!=null){
						task.setJobName("传感器 -->"+sensor.gettName()+" 预警检查定时");
						task.setJobDesc("传感器 -->"+sensor.gettName()+" 预警检查定时任务, 每隔"+warnParam.getWarnInterval()+"分钟执行一次");
						task.setCron(CronUtil.generateMinuteCron(String.valueOf(warnParam.getWarnInterval())));
						task.setUpdateBy(ShiroKit.getUser().getName());
						task.setUpdateTime(new Date());
						task.setStatus(Integer.parseInt(warnParam.getControlMode()));
						
						airTaskMapper.updateById(task);
						
						//先将任务移除
						jobTask.remove(task);
						//启动
						if(Integer.parseInt(warnParam.getControlMode())==0){
							jobTask.startJob(task);
						}
					}
				}
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
				count+=baseMapper.deleteById(paramId);
				//查询传感器
				AirSensor sensor = airSensorMapper.selectById(param.getSensorId());
				if(sensor!=null){
					//查询气象站数据上传任务
					AirTask taskQuery=new AirTask();
					taskQuery.setDeviceCode(sensor.getCode());
					AirTask task = airTaskMapper.selectOne(taskQuery);
					if(task!=null){
						airTaskMapper.deleteById(task.getId());
						//将任务移除
						jobTask.remove(task);
						
					}
				}
			}
		}
		return count;
	}

	

}
