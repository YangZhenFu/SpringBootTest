package com.stylefeng.guns.air;

import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.core.common.constant.AirUnit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.modular.air.dao.AirSensorDataMapper;
import com.stylefeng.guns.modular.air.model.AirSensorData;



/**  
 * <p>Title: AirSensorTest</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月20日  
 */
public class AirSensorTest extends BaseJunit{

	@Autowired
	AirSensorDataMapper sensorDataMapper;
	
	@Test
	public void test1(){
		Long[] sensorId=new Long[]{1L,2L,4L,5L,6L,7L,8L,9L};
		int count=0;
		long start = System.currentTimeMillis();
		for(int i=0;i<1000;i++){
			AirSensorData data=new AirSensorData();
			data.setCode(StringUtil.generatorShort());
			data.setSortCode(i);
			data.setSensorId(sensorId[(int)(Math.random()*8)]);
			data.settName("传感器"+data.getSensorId()+" "+DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			data.setSignalStrength(5);
			data.setHeartbeatTime(new Date());
			data.setCreateBy("张三");
			data.setCreateTime(new Date());
			data.setNumerical(new DecimalFormat("#.0").format((double)(Math.random()*100)+1));
			data.setUnit(AirUnit.values()[(int)(Math.random()*12)].getUnit());
			count+=sensorDataMapper.insert(data);
		}
		System.out.println("共耗时:"+(System.currentTimeMillis()-start)+"ms");
		assertTrue(count==1000);
	}
	
}
