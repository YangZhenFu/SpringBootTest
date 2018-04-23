package com.stylefeng.guns.modular.air.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.warpper.AirSensorWarpper;

/**
 * 传感器管理控制器
 *
 * @author fengshuonan
 * @Date 2018-04-19 14:55:20
 */
@Controller
@RequestMapping("/airSensor")
public class AirSensorController extends BaseController {

    private String PREFIX = "/air/airSensor/";

    @Autowired
    private IAirSensorService airSensorService;

    /**
     * 跳转到传感器管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "airSensor.html";
    }

    /**
     * 跳转到添加传感器管理
     */
    @RequestMapping("/airSensor_add")
    public String airSensorAdd() {
        return PREFIX + "airSensor_add.html";
    }

    /**
     * 跳转到修改传感器管理
     */
    @RequestMapping("/airSensor_update/{airSensorId}")
    public String airSensorUpdate(@PathVariable Integer airSensorId, Model model) {
        AirSensor airSensor = airSensorService.selectById(airSensorId);
        model.addAttribute("item",airSensor);
        LogObjectHolder.me().set(airSensor);
        return PREFIX + "airSensor_edit.html";
    }

    /**
     * 获取传感器管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required=false) String condition) {
    	Page<AirSensor> page = new PageFactory<AirSensor>().defaultPage();
    	//List<Map<String, Object>> list = airSensorService.findListByParams(page, condition, page.getOrderByField(), page.isAsc());
    	//page.setRecords((List<AirSensor>) new AirSensorWarpper(list).warp());
    	Wrapper<AirSensor> sensor = Condition.create();
    	sensor.like("t_name", condition).or().like("sort_code", condition).and().eq("valid", "0").orderBy("create_time desc");
    	Page<Map<String, Object>> pageList = airSensorService.selectMapsPage(page, sensor);
    	pageList.setRecords((List<Map<String,Object>>) new AirSensorWarpper(pageList.getRecords()).warp());
        return packForBT(pageList);
        
    }

    /**
     * 新增传感器管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@ModelAttribute AirSensor airSensor,String installTimes) {
    	if(airSensorService.saveAirSensor(airSensor,installTimes)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 删除传感器管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer airSensorId) {
    	if(airSensorService.deleteAirSensor(airSensorId)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 修改传感器管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AirSensor airSensor,String installTimes) {
    	if(airSensorService.saveAirSensor(airSensor,installTimes)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 传感器管理详情
     */
    @RequestMapping(value = "/detail/{airSensorId}")
    @ResponseBody
    public Object detail(@PathVariable("airSensorId") Integer airSensorId) {
        return airSensorService.selectById(airSensorId);
    }
}
