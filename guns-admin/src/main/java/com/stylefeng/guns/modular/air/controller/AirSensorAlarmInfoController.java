package com.stylefeng.guns.modular.air.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.Constant;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.air.model.AirSensorAlarmInfo;
import com.stylefeng.guns.modular.air.service.IAirSensorAlarmInfoService;
import com.stylefeng.guns.modular.air.warpper.AirSensorAlarmInfoWarpper;

/**
 * 传感器告警信息控制器
 *
 * @author fengshuonan
 * @Date 2018-05-10 12:02:22
 */
@Controller
@RequestMapping("/airSensorAlarmInfo")
public class AirSensorAlarmInfoController extends BaseController {

    private String PREFIX = "/air/airSensorAlarmInfo/";

    @Autowired
    private IAirSensorAlarmInfoService airSensorAlarmInfoService;

    /**
     * 跳转到传感器告警信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "airSensorAlarmInfo.html";
    }

    /**
     * 跳转到添加传感器告警信息
     */
    @RequestMapping("/airSensorAlarmInfo_add")
    public String airSensorAlarmInfoAdd() {
        return PREFIX + "airSensorAlarmInfo_add.html";
    }

    /**
     * 跳转到修改传感器告警信息
     */
    @RequestMapping("/airSensorAlarmInfo_update/{airSensorAlarmInfoId}")
    public String airSensorAlarmInfoUpdate(@PathVariable Integer airSensorAlarmInfoId, Model model) {
        AirSensorAlarmInfo airSensorAlarmInfo = airSensorAlarmInfoService.selectById(airSensorAlarmInfoId);
        model.addAttribute("item",airSensorAlarmInfo);
        LogObjectHolder.me().set(airSensorAlarmInfo);
        return PREFIX + "airSensorAlarmInfo_edit.html";
    }
    
    /**
     * <p>Title: openAlarmInfoHandle</p>  
     * <p>Description: 弹出异常处理页面</p>  
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/layerHandle/{id}")
    public String openAlarmInfoHandle(@PathVariable("id")Integer id,Model model){
    	if(id!=null){
    		AirSensorAlarmInfo info = airSensorAlarmInfoService.selectById(id);
    		model.addAttribute("item", info);
    		LogObjectHolder.me().set(info);
    		return PREFIX + "alarm_handle.html";
    	}
    	return null;
    }
    
    

    /**
     * 获取传感器告警信息列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required=false)String condition) {
        Page<AirSensorAlarmInfo> page=new PageFactory<AirSensorAlarmInfo>().defaultPage();
        Wrapper<AirSensorAlarmInfo> wrapper=Condition.create();
        wrapper.like("t_name", condition).or().like("sort_code", condition).and().eq("valid", "0").orderBy("handle_state,alarm_time desc");
        Page<Map<String, Object>> pageList = airSensorAlarmInfoService.selectMapsPage(page, wrapper);
        pageList.setRecords((List<Map<String,Object>>)new AirSensorAlarmInfoWarpper(pageList.getRecords()).warp());
        return super.packForBT(pageList);
    }

    /**
     * 新增传感器告警信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AirSensorAlarmInfo airSensorAlarmInfo,String alarmTimes) {
        if(airSensorAlarmInfoService.saveSensorAlarmInfo(airSensorAlarmInfo,alarmTimes)==1){
        	return SUCCESS_TIP;
        }
        return ERROR_TIP;
    }

    /**
     * 删除传感器告警信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer airSensorAlarmInfoId) {
    	if(airSensorAlarmInfoService.deleteSensorAlarmInfo(airSensorAlarmInfoId)==1){
        	return SUCCESS_TIP;
        }
        return ERROR_TIP;
    }

    /**
     * 修改传感器告警信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AirSensorAlarmInfo airSensorAlarmInfo,String alarmTimes) {
    	if(airSensorAlarmInfoService.saveSensorAlarmInfo(airSensorAlarmInfo,alarmTimes)==1){
        	return SUCCESS_TIP;
        }
        return ERROR_TIP;
    }

    /**
     * 传感器告警信息详情
     */
    @RequestMapping(value = "/detail/{airSensorAlarmInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("airSensorAlarmInfoId") Integer airSensorAlarmInfoId) {
        return airSensorAlarmInfoService.selectById(airSensorAlarmInfoId);
    }
    
    /**
     * <p>Title: layerDetail</p>  
     * <p>Description: 弹出详情页面</p>  
     * @param id
     * @return
     */
    @RequestMapping(value="/layerdetail/{id}")
    public String layerDetail(@PathVariable("id")Integer id,Model model){
    	if(id!=null){
    		AirSensorAlarmInfo alarmInfo = airSensorAlarmInfoService.selectById(id);
    		model.addAttribute("item", alarmInfo);
    	}
    	return PREFIX + "airSensorAlarmInfo_detail.html";
    }
    
    /**
     * <p>Title: handleAlarmInfo</p>  
     * <p>Description: 异常处理</p>  
     * @param alarm
     * @return
     */
    @RequestMapping(value="handle",method=RequestMethod.POST)
    @ResponseBody
    public Object handleAlarmInfo(@ModelAttribute AirSensorAlarmInfo alarm){
    	if(airSensorAlarmInfoService.handleAlarmInfo(alarm)==1){
    		return SUCCESS_TIP;
    	}
    	return ERROR_TIP;
    }
    
    
    
    /**
     * <p>Title: getAlarmInfoCount</p>  
     * <p>Description: 查询告警信息总数</p>  
     * @return
     */
    @RequestMapping(value="getCount",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getAlarmInfoCount(){
    	Map<String,Object> result=Maps.newHashMap();
    	int total = airSensorAlarmInfoService.selectCount(new EntityWrapper<AirSensorAlarmInfo>().eq("valid", "0").eq("handle_state", "0"));
    	if(total>0){
    		Map<String,Object> typeCount=Maps.newHashMap();
    		//查询传感器异常类型
    		Set<String> types = Constant.sensor_exception_type.keySet();
    		for(String type : types){
    			int count = airSensorAlarmInfoService.selectCount(new EntityWrapper<AirSensorAlarmInfo>().eq("valid", "0").eq("alarm_type", type).eq("handle_state", "0"));
    			typeCount.put(Constant.sensor_exception_type.get(type), count);
    		}
    		result.put("typeCount", typeCount);
    	}
    	result.put("total", total);
    	return result;
    }
    
     
}
