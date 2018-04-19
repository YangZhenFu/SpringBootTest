package com.stylefeng.guns.modular.air.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.air.model.SensorType;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;

/**
 * 传感器类型控制器
 *
 * @author fengshuonan
 * @Date 2018-04-19 11:34:21
 */
@Controller
@RequestMapping("/sensorType")
public class SensorTypeController extends BaseController {

    private String PREFIX = "/air/sensorType/";

    @Autowired
    private ISensorTypeService sensorTypeService;

    /**
     * 跳转到传感器类型首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "sensorType.html";
    }

    /**
     * 跳转到添加传感器类型
     */
    @RequestMapping("/sensorType_add")
    public String sensorTypeAdd() {
        return PREFIX + "sensorType_add.html";
    }

    /**
     * 跳转到修改传感器类型
     */
    @RequestMapping("/sensorType_update/{sensorTypeId}")
    public String sensorTypeUpdate(@PathVariable Integer sensorTypeId, Model model) {
        SensorType sensorType = sensorTypeService.selectById(sensorTypeId);
        model.addAttribute("item",sensorType);
        LogObjectHolder.me().set(sensorType);
        return PREFIX + "sensorType_edit.html";
    }

    /**
     * 获取传感器类型列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required=false) String condition) {
    	Page<SensorType> page = new PageFactory<SensorType>().defaultPage();
    	page = sensorTypeService.selectPage(page, new EntityWrapper<SensorType>().like("t_name", condition).or().like("sort_code", condition).eq("valid", "0"));
        return packForBT(page);
    }

    /**
     * 新增传感器类型
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(SensorType sensorType) {
    	if(sensorTypeService.saveSensorType(sensorType)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 删除传感器类型
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer sensorTypeId) {
    	if(sensorTypeService.deleteSensorTypeById(sensorTypeId)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 修改传感器类型
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(SensorType sensorType) {
    	if(sensorTypeService.saveSensorType(sensorType)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 传感器类型详情
     */
    @RequestMapping(value = "/detail/{sensorTypeId}")
    @ResponseBody
    public Object detail(@PathVariable("sensorTypeId") Integer sensorTypeId) {
        return sensorTypeService.selectById(sensorTypeId);
    }
}
