package com.stylefeng.guns.modular.air.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.other.CronUtil;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.air.dao.AirStationMapper;
import com.stylefeng.guns.modular.air.dao.AirTaskMapper;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.model.AirTask;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.task.JobTask;

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

	@Autowired
	private AirTaskMapper airTaskMapper;
	@Autowired
	private JobTask jobTask;
	
	
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
			//新增
			if(station.getId()==null){
				station.setCode(StringUtil.generatorShort());
				station.setCreateBy(ShiroKit.getUser().getName());
				station.setCreateTime(new Date());
				count=baseMapper.insert(station);
				
				//新增气象站数据上传任务
				AirTask task = new AirTask();
				task.setId(StringUtil.generator());
				task.setJobName("气象站 -->"+station.gettName()+" 数据上传定时");
				task.setJobDesc("气象站 -->"+station.gettName()+" 数据上传定时任务, 每隔"+station.getDataUploadInterval()+"分钟执行一次");
				task.setDeviceCode(station.getCode());
				task.setCron(CronUtil.generateMinuteCron(station.getDataUploadInterval()));
				task.setClazzPath("com.stylefeng.guns.modular.air.task.jobs.AirDataUploadJob");
				task.setStatus(station.getDataUploadStatus());
				task.setCreateBy(ShiroKit.getUser().getName());
				task.setCreateTime(new Date());
				airTaskMapper.insert(task);
				//如果为启动状态
				if(task.getStatus()==0){
					//启动任务
					jobTask.startJob(task);
				}
				
			}
			//修改
			else{
				station.setUpdateBy(ShiroKit.getUser().getName());
				station.setUpdateTime(new Date());
				count=baseMapper.updateById(station);
				
				//查询气象站
				AirStation airStation = baseMapper.selectById(station.getId());
				
				//查询气象站数据上传任务
				AirTask taskQuery=new AirTask();
				taskQuery.setDeviceCode(airStation.getCode());
				AirTask task = airTaskMapper.selectOne(taskQuery);
				if(task!=null){
					task.setJobName("气象站 -->"+airStation.gettName()+" 数据上传定时");
					task.setJobDesc("气象站 -->"+airStation.gettName()+" 数据上传定时任务, 每隔"+airStation.getDataUploadInterval()+"分钟执行一次");
					task.setCron(CronUtil.generateMinuteCron(airStation.getDataUploadInterval()));
					task.setUpdateBy(ShiroKit.getUser().getName());
					task.setUpdateTime(new Date());
					task.setStatus(airStation.getDataUploadStatus());
					airTaskMapper.updateById(task);
					
					//先将任务移除
					jobTask.remove(task);
					//启动
					if(airStation.getDataUploadStatus()==0){
						jobTask.startJob(task);
					}
				}
				
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
				
				//查询气象站数据上传任务
				AirTask taskQuery=new AirTask();
				taskQuery.setDeviceCode(station.getCode());
				AirTask task = airTaskMapper.selectOne(taskQuery);
				if(task!=null){
					airTaskMapper.deleteById(task.getId());
					//将任务移除
					jobTask.remove(task);
					
				}
			}
		}
		return count;
	}

}
