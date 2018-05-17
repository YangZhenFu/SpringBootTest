package com.stylefeng.guns.modular.air.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stylefeng.guns.core.other.StringUtil;


/**
 * cron配置
 	"0/10 * *  * * ? " 每10秒执行一次
 	"0 0 0/1 * * ? " 每1小时执行一次
	"0 0 12 * * ?"    每天中午十二点触发 
	"0 15 10 ? * *"    每天早上10：15触发 
	"0 15 10 * * ?"    每天早上10：15触发 
	"0 15 10 * * ? *"    每天早上10：15触发 
	"0 15 10 * * ? 2005"    2005年的每天早上10：15触发 
	"0 * 14 * * ?"    每天从下午2点开始到2点59分每分钟一次触发 
	"0 0/5 14 * * ?"    每天从下午2点开始到2：55分结束每5分钟一次触发 
	"0 0/5 14,18 * * ?"    每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
	"0 0-5 14 * * ?"    每天14:00至14:05每分钟一次触发 
	"0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发 
	"0 15 10 ? * MON-FRI"    每个周一、周二、周三、周四、周五的10：15触发 
*
*/

//@Component
public class TaskJob {

	private Logger logger=LoggerFactory.getLogger(TaskJob.class);
	
	
//	@Scheduled(cron="0 0/5 * * * ?")//每5分钟执行一次
	public void schedule(){
		String syncKey = StringUtil.getUUID();
		long beginTime = System.currentTimeMillis();
		logger.info("定时[{}]服务启动成功.",syncKey);
		
		logger.info("定时[{}]服务结束.耗时:{}ms",new Object[]{syncKey,(System.currentTimeMillis()-beginTime)});
	}
	
	
}
