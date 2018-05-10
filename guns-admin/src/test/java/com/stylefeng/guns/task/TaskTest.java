package com.stylefeng.guns.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.core.other.StringUtil;
import com.stylefeng.guns.modular.air.model.AirTask;
import com.stylefeng.guns.modular.air.task.JobTask;

/**  
 * <p>Title: TaskTest</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年5月7日  
 */
public class TaskTest extends BaseJunit{

	
	@Autowired
	JobTask jobTask;
	
	@Test
	public void test1() throws InterruptedException{
		AirTask job = new AirTask();
		job.setId(StringUtil.getUUID());
		job.setJobName("测试定时demo1");
		job.setJobDesc("测试定时任务demo1");
		job.setCron("0/5 * *  * * ? ");
		job.setClazzPath("com.stylefeng.guns.modular.air.task.jobs.JobDemo1");
		boolean flag = jobTask.startJob(job);
		Thread.sleep(60000);
		jobTask.remove(job);
		assertTrue(flag);
	}
}
