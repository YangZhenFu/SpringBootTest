package com.stylefeng.guns.modular.air.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.SensorTypeEnum;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirSensorWarnParam;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.model.SensorType;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirSensorWarnParamService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;
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
    @Autowired
    private IAirStationService airStationService;
    @Autowired
    private IAirSensorService airSensorService;
    @Autowired
    private ISensorTypeService sensorTypeService;
    
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
    
    /**
     * <p>Title: toWarnControl</p>  
     * <p>Description: 跳转到预警控制页面</p>  
     * @return
     */
    @RequestMapping("warnControl")
    public String toWarnControl(){
    	return PREFIX+"warn_control.html";
    }
    
    /**
     * <p>Title: querySensorWarnParam</p>  
     * <p>Description: 查询传感器预警参数</p>  
     * @param stationCode
     * @return
     */
    @RequestMapping(value="queryData",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> querySensorWarnParam(@RequestParam(required=true) String stationCode){
    	Map<String,Object> result=Maps.newHashMap();
    	if(StringUtils.isNotBlank(stationCode)){
    		//查询气象站
    		AirStation station = airStationService.selectOne(new EntityWrapper<AirStation>().eq("code", stationCode).eq("valid", "0"));
    		if(station!=null){
    			//查询所有的传感器
    			List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
    			if(CollectionUtils.isNotEmpty(sensors)){
    				for(AirSensor sensor : sensors){
    					//查询传感器设备预警参数
    					List<AirSensorWarnParam> params = airSensorWarnParamService.selectList(new EntityWrapper<AirSensorWarnParam>().eq("valid", "0").eq("sensor_id", sensor.getId()).orderBy("sort_code"));
    					sensor.setWarnParams(params);
    					
    					//查询传感器类型
    					SensorType type = sensorTypeService.selectById(sensor.getTypeId());
    					
    					//传感器数据类型
    					SensorTypeEnum typeEnum = SensorTypeEnum.findSensorTypeByName(type.gettName());
    					sensor.setImg(typeEnum.getIcon());
    				}
    				
        			result.put("sensors", sensors);
    				return result;
    			}
    			
    		}
    	}
    	return result;
    	
    }
    
    /**
     * <p>Title: queryWarnParamBySensorId</p>  
     * <p>Description: </p>  
     * @param sensorId
     * @return
     */
    @RequestMapping(value="/queryBySensorId/{sensorId}",method=RequestMethod.POST)
    @ResponseBody
    public Object queryWarnParamBySensorId(@PathVariable("sensorId") Long sensorId){
    	if(sensorId!=null){
			List<Map<String,Object>> maps = airSensorWarnParamService.selectMaps(new EntityWrapper<AirSensorWarnParam>().eq("sensor_id", sensorId).orderBy("sort_code"));
			return new AirSensorWarnParamWarpper(maps).warp();
    	}
    	return null;
    }
     
    
    @RequestMapping(value="/updateBySensorId/{sensorId}",method=RequestMethod.POST)
    @ResponseBody
    public Object queryWarnParamBySensorId(@PathVariable("sensorId") Long sensorId, @RequestParam(required=true)String state){
    	if(sensorId!=null){
			List<AirSensorWarnParam> params = airSensorWarnParamService.selectList(new EntityWrapper<AirSensorWarnParam>().eq("valid", "0").eq("sensor_id", sensorId).orderBy("sort_code"));
			if(CollectionUtils.isNotEmpty(params)){
				for(AirSensorWarnParam param : params){
					param.setControlMode(state);
					param.setUpdateBy(ShiroKit.getUser().getName());
					param.setUpdateTime(new Date());
					airSensorWarnParamService.updateById(param);
				}
				return SUCCESS_TIP;
			}
			
    	}
    	return ERROR_TIP;
    }
    
    
    
    /**
     * 跳转到添加设备预警参数
     */
    @RequestMapping("/addWarnParam")
    public String layerWarnParamAdd() {
        return PREFIX + "warnParam_add.html";
    }
    
}
