package com.stylefeng.guns.modular.air.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class MySchedulerListener {
      
    @Autowired  
    MyJobFactory myJobFactory;

      
    @Bean(name ="schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setJobFactory(myJobFactory);  
        return bean;  
    }  
  
}  