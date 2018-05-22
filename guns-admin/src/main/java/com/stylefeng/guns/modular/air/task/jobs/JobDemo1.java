package com.stylefeng.guns.modular.air.task.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.modular.system.model.User;
import com.stylefeng.guns.modular.system.service.IUserService;

import cn.hutool.core.date.DateUtil;

/**
 * @author zhuxiaomeng
 * @date 2018/1/7.
 * @email 154040976@qq.com
 *
 * 定时
 */
@Component
public class JobDemo1 implements Job{

	private Logger logger = LoggerFactory.getLogger(JobDemo1.class);
  @Autowired
  IUserService userService;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
	  System.out.println("JobDemo1：启动任务================="+DateUtil.formatDateTime(new Date()));
    JobDataMap map = context.getJobDetail().getJobDataMap();
    System.out.println("code --> "+map.getString("code"));
    
    
    run();
    System.out.println("JobDemo1：下次执行时间====="+
        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            .format(context.getNextFireTime())+"==============");
  }

  public void run(){
    //可以 获取
    //SysUserService sys=SpringUtil.getBean(SysUserServiceImpl.class);
    List<User> userList = userService.selectList(new EntityWrapper<User>());
    System.out.println(userList.get(0).getName());;
    System.out.println("JobDemo1：执行完毕=======================");

  }
}
