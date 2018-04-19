package com.stylefeng.guns.core.beetl.function;

import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.service.IDeptService;

/**  
 * <p>Title: DeptFunction</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月17日  
 */
@Component
@DependsOn("springContextHolder")
public class DeptFunction {

	private IDeptService deptService = SpringContextHolder.getBean(IDeptService.class);
	
	public String findAllDeptInfo(){
		List<Dept> list = deptService.selectList(new EntityWrapper<Dept>());
		return JSON.toJSONString(list);
	}
}
