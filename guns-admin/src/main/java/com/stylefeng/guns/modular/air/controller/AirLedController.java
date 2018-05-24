package com.stylefeng.guns.modular.air.controller;

import java.util.List;
import java.util.Map;

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
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.air.model.AirLed;
import com.stylefeng.guns.modular.air.service.IAirLedService;
import com.stylefeng.guns.modular.air.warpper.AirLedWarpper;

/**
 * LED终端控制器
 *
 * @author fengshuonan
 * @Date 2018-05-18 10:16:27
 */
@Controller
@RequestMapping("/airLed")
public class AirLedController extends BaseController {

    private String PREFIX = "/air/airLed/";

    @Autowired
    private IAirLedService airLedService;

    /**
     * 跳转到LED终端首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "airLed.html";
    }

    /**
     * 跳转到添加LED终端
     */
    @RequestMapping("/airLed_add")
    public String airLedAdd() {
        return PREFIX + "airLed_add.html";
    }

    /**
     * 跳转到修改LED终端
     */
    @RequestMapping("/airLed_update/{airLedId}")
    public String airLedUpdate(@PathVariable Integer airLedId, Model model) {
        AirLed airLed = airLedService.selectById(airLedId);
        model.addAttribute("item",airLed);
        LogObjectHolder.me().set(airLed);
        return PREFIX + "airLed_edit.html";
    }

    /**
     * 获取LED终端列表
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required=false) String condition) {
    	Page<AirLed> page=new PageFactory<AirLed>().defaultPage();
    	Wrapper<AirLed> wrapper = Condition.create();
    	wrapper.like("t_name", condition).or().like("sort_code", condition).and().eq("valid", "0");
    	Page<Map<String,Object>> mapsPage = airLedService.selectMapsPage(page, wrapper);
    	mapsPage.setRecords((List<Map<String,Object>>)new AirLedWarpper(mapsPage.getRecords()).warp());
        return super.packForBT(mapsPage);
    }

    /**
     * 新增LED终端
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AirLed airLed) {
    	if(airLedService.saveAirLed(airLed)==1){
    		return SUCCESS_TIP;
    	}
    	return ERROR_TIP;
    }

    /**
     * 删除LED终端
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer airLedId) {
    	if(airLedService.deleteAirLed(airLedId)==1){
    		return SUCCESS_TIP;
    	}
    	return ERROR_TIP;
    }

    /**
     * 修改LED终端
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AirLed airLed) {
    	if(airLedService.saveAirLed(airLed)==1){
    		return SUCCESS_TIP;
    	}
    	return ERROR_TIP;
    }

    /**
     * LED终端详情
     */
    @RequestMapping(value = "/detail/{airLedId}")
    @ResponseBody
    public Object detail(@PathVariable("airLedId") Integer airLedId) {
        return airLedService.selectById(airLedId);
    }
    
    
    @RequestMapping(value="/layerDetail/{id}")
    public String layerDetail(@PathVariable("id")Integer id,Model model){
    	if(id!=null){
    		Map<String, Object> map = airLedService.selectMap(new EntityWrapper<AirLed>().eq("id", id));
    		model.addAttribute("item", new AirLedWarpper(map).warp());
    	}
    	return PREFIX+"airLed_detail.html";
    }
    
    /**
     * <p>Title: toLedControlPage</p>  
     * <p>Description: 跳转到LED控制页面</p>  
     * @return
     */
    @RequestMapping(value="control")
    public String toLedControlPage(){
    	return PREFIX+"led_control.html";
    }
    
    /**
     * <p>Title: switchScreenStatus</p>  
     * <p>Description: 更改屏幕状态</p>  
     * @param ids
     * @param brightness
     * @param mode
     * @return
     */
    @RequestMapping(value="screen/{mode}",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> switchScreenStatus(@RequestParam(required=true)String ids,@RequestParam(required=false)Integer brightness,@PathVariable("mode")String mode){
    	return airLedService.setScreenStatus(ids,brightness,mode);
    }
    
    /**
     * <p>Title: changeControlMode</p>  
     * <p>Description: 更改实时发布状态</p>  
     * @param led
     * @return
     */
    @RequestMapping(value="changeMode",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> changeControlMode(@ModelAttribute AirLed led){
    	return airLedService.changeLedControlMode(led);
    }
    
   
}
