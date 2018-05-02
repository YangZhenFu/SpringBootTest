package com.stylefeng.guns.modular.air.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.air.model.AirSensorWarnParam;
import com.stylefeng.guns.modular.air.service.IAirSensorWarnParamService;
import com.stylefeng.guns.modular.air.warpper.AirSensorWarnParamWarpper;

/**
 * 设备预警参数控制器
 *
 * @author fengshuonan
 * @Date 2018-04-24 10:18:34
 */
@Controller
@RequestMapping("/airSensorWarnParam")
public class AirSensorWarnParamController extends BaseController {

    private String PREFIX = "/air/airSensorWarnParam/";

    @Autowired
    private IAirSensorWarnParamService airSensorWarnParamService;

    /**
     * 跳转到设备预警参数首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "airSensorWarnParam.html";
    }

    /**
     * 跳转到添加设备预警参数
     */
    @RequestMapping("/airSensorWarnParam_add")
    public String airSensorWarnParamAdd() {
        return PREFIX + "airSensorWarnParam_add.html";
    }

    /**
     * 跳转到修改设备预警参数
     */
    @RequestMapping("/airSensorWarnParam_update/{airSensorWarnParamId}")
    public String airSensorWarnParamUpdate(@PathVariable Integer airSensorWarnParamId, Model model) {
        AirSensorWarnParam airSensorWarnParam = airSensorWarnParamService.selectById(airSensorWarnParamId);
        model.addAttribute("item",airSensorWarnParam);
        LogObjectHolder.me().set(airSensorWarnParam);
        return PREFIX + "airSensorWarnParam_edit.html";
    }

    /**
     * 获取设备预警参数列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required=false) String condition) {
    	Page<AirSensorWarnParam> page =new PageFactory<AirSensorWarnParam>().defaultPage();
    	Condition wrapper = Condition.create();
    	wrapper.like("t_name", condition).or().like("sort_code", condition);
    	Page<Map<String,Object>> mapsPage = airSensorWarnParamService.selectMapsPage(page, wrapper);
    	mapsPage.setRecords((List<Map<String,Object>>)new AirSensorWarnParamWarpper(mapsPage.getRecords()).warp());
        return packForBT(mapsPage);
    }

    /**
     * 新增设备预警参数
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AirSensorWarnParam airSensorWarnParam) {
        if(airSensorWarnParamService.saveSensorWarnParam(airSensorWarnParam)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 删除设备预警参数
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer airSensorWarnParamId) {
        if(airSensorWarnParamService.deleteWarnParamById(airSensorWarnParamId)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 修改设备预警参数
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AirSensorWarnParam airSensorWarnParam) {
    	if(airSensorWarnParamService.saveSensorWarnParam(airSensorWarnParam)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 设备预警参数详情
     */
    @RequestMapping(value = "/detail/{airSensorWarnParamId}")
    @ResponseBody
    public Object detail(@PathVariable("airSensorWarnParamId") Integer airSensorWarnParamId) {
        return airSensorWarnParamService.selectById(airSensorWarnParamId);
    }
}
