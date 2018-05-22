package com.stylefeng.guns.modular.air.task;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import java.util.Date;
import java.util.HashSet;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.stylefeng.guns.modular.air.model.AirTask;

/**
 * @author zhuxiaomeng
 * @date 2018/1/5.
 * @email 154040976@qq.com
 *
 * 定时任务类 增删改 可参考api：http://www.quartz-scheduler.org/api/2.2.1/
 *
 * 任务名称 默认为 SysJob 类 id
 */
@Service
public class JobTask {

  @Autowired
  SchedulerFactoryBean schedulerFactoryBean;

  private Logger log = LoggerFactory.getLogger(JobTask.class);
  
  /**
   * true 存在 false 不存在
   * @param
   * @return
   */
  public boolean checkJob(AirTask job){
    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
    try {
      if(scheduler.checkExists(triggerKey)){
        return true;
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 开启
   */
  public boolean startJob(AirTask job) {
    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    try {
      Class clazz = Class.forName(job.getClazzPath());
      JobDetail jobDetail = JobBuilder.newJob(clazz).build();
      
      jobDetail.getJobDataMap().put("deviceCode", job.getDeviceCode());
      
      // 触发器
      TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
      CronTrigger trigger = TriggerBuilder.newTrigger()
          .withIdentity(triggerKey)
          .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron())).build();
      scheduler.scheduleJob(jobDetail, trigger);
      // 启动
      if (!scheduler.isShutdown()) {
        scheduler.start();
        log.info("---任务[" + triggerKey.getName() + "]启动成功-------");
        return true;
      }else{
        log.info("---任务[" + triggerKey.getName() + "]已经运行，请勿再次启动-------");
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return false;
  }
  
  
  /**
   * <p>Title: startJobNow</p>  
   * <p>Description: 启动任务 并立即执行一次</p>  
   * @param job
   * @param intervalMinutes
   * @return
   */
  public boolean startJobNow(AirTask job,int intervalMinutes){
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
	    try {
	      Class clazz = Class.forName(job.getClazzPath());
	      JobDetail jobDetail = JobBuilder.newJob(clazz).build();
	      
	      jobDetail.getJobDataMap().put("deviceCode", job.getDeviceCode());
	      
	      // 触发器
	      TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
	      SimpleTrigger trigger = TriggerBuilder.newTrigger()
	          .withIdentity(triggerKey)
	          .startNow()
	          .withSchedule(simpleSchedule().withIntervalInMinutes(intervalMinutes).repeatForever()).build();
	      scheduler.scheduleJob(jobDetail, trigger);
	      // 启动
	      if (!scheduler.isShutdown()) {
	        scheduler.start();
	        log.info("---任务[" + triggerKey.getName() + "]启动成功-------");
	        return true;
	      }else{
	        log.info("---任务[" + triggerKey.getName() + "]已经运行，请勿再次启动-------");
	      }
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	    return false;
	}
  
  

  /**
   * 更新
   */
  public boolean updateJob(AirTask job) {
    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    String createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

    TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
    try {
      if (scheduler.checkExists(triggerKey)) {
        return false;
      }

      JobKey jobKey = JobKey.jobKey(job.getId(), Scheduler.DEFAULT_GROUP);

      CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(job.getCron())
          .withMisfireHandlingInstructionDoNothing();
      CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
          .withDescription(createTime).withSchedule(schedBuilder).build();
      Class clazz = null;
      JobDetail jobDetail = scheduler.getJobDetail(jobKey);
      
      jobDetail.getJobDataMap().put("deviceCode", job.getDeviceCode());
      
      HashSet<Trigger> triggerSet = new HashSet<>();
      triggerSet.add(trigger);
      scheduler.scheduleJob(jobDetail, triggerSet, true);
      log.info("---任务["+triggerKey.getName()+"]更新成功-------");
      return true;
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 删除
   */
  public boolean remove(AirTask job) {
    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
    try {
      if (checkJob(job)) {
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(JobKey.jobKey(job.getId(), Scheduler.DEFAULT_GROUP));
        log.info("---任务[" + triggerKey.getName() + "]删除成功-------");
        return true;
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return false;
  }
}
