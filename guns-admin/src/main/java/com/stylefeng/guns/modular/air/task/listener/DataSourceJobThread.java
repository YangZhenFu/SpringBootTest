package com.stylefeng.guns.modular.air.task.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.air.model.AirTask;
import com.stylefeng.guns.modular.air.service.IAirTaskService;
import com.stylefeng.guns.modular.air.task.JobTask;

/**
 * @author zhuxiaomeng
 * @date 2018/1/6.
 * @email 154040976@qq.com
 * <p>
 * 启动数据库中已经设定为 启动状态(status:true)的任务 项目启动时init
 */
@Configuration
public class DataSourceJobThread extends Thread {

	private Logger log = LoggerFactory.getLogger(DataSourceJobThread.class);

    @Autowired
    IAirTaskService airTaskService;
    
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            log.info("---------线程启动---------");
            JobTask jobTask = SpringContextHolder.getBean(JobTask.class);
            
            List<AirTask> jobList = airTaskService.selectList(new EntityWrapper<AirTask>().eq("status", 0));
            
            //开启任务
            jobList.forEach(jobs -> {
                        log.info("---任务[" + jobs.getId() + "]系统 init--开始启动---------");
                        jobTask.startJob(jobs);
                    }
            );
            if (jobList.size() == 0) {
                log.info("---数据库暂无启动的任务---------");
            } else
                log.info("---任务启动完毕---------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
