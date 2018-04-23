package com.stylefeng.guns.modular.air.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.service.IAirSensorDataService;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationService;

/**  
 * <p>Title: AirMonitorController</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月20日  
 */
@RequestMapping("air/monitor")
@Controller
public class AirMonitorController extends BaseController{

	private String PREFIX = "/air/monitor/";
	
	@Autowired
	private IAirStationService airStationService;
	@Autowired
	private IAirSensorService airSensorService;
	@Autowired
	private IAirSensorDataService airSensorDataService;
	
	 /**
     * 跳转到监控首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "monitor.html";
    }
    
    @RequestMapping(value="list",method=RequestMethod.POST)
    @ResponseBody
    public Object list(@RequestParam(required=false)String condition){
    	
    	Page<AirStation> page=new PageFactory<AirStation>().defaultPage();
    	page=airStationService.selectPage(page, new EntityWrapper<AirStation>().like("t_name", condition).eq("valid", "0"));
    	return packForBT(page);
    }

}
