package com.stylefeng.guns.modular.air.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.google.common.collect.Lists;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.warpper.AirSensorWarpper;
import com.stylefeng.guns.modular.system.service.IAreaService;

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
    @Autowired
    private IAreaService areaService;
    @Autowired
    private IAirStationService airStationService;

    /**
     * 跳转到传感器管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "airSensor.html";
    }
    
    /**
     * <p>Title: getStationZTree</p>  
     * <p>Description: 传感器ZTree</p>  
     * @return
     */
    @RequestMapping(value="ztree",method=RequestMethod.POST)
    @ResponseBody
    public List<ZTreeNode> getStationZTree(){
    	List<ZTreeNode> ztree = areaService.getZtreeNode();
    	return createSensorZTree(ztree);
    }
    
    /**  
	 * <p>Title: createStationZTree</p>  
	 * <p>Description: </p>  
	 * @param ztree
	 * @return  
	 */ 
	private List<ZTreeNode> createSensorZTree(List<ZTreeNode> ztree) {
		List<ZTreeNode> stationZTree=Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(ztree)){
			for(ZTreeNode node : ztree){
				List<AirStation> list = airStationService.selectList(new EntityWrapper<AirStation>().eq("area_id", node.getId()).eq("valid", "0"));
				if(CollectionUtils.isNotEmpty(list)){
					for(AirStation station : list){
						//新增气象站节点
						ZTreeNode zTreeNode = new ZTreeNode();
						zTreeNode.setChecked(false);
						zTreeNode.setId(Long.valueOf(station.getCode()));
						zTreeNode.setName(station.gettName());
						zTreeNode.setOpen(false);
						zTreeNode.setpId(node.getId());
						stationZTree.add(zTreeNode);
						
						//查询气象站下的所有传感器
						List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", station.getId()).eq("valid", "0"));
						if(CollectionUtils.isNotEmpty(sensors)){
							for(AirSensor sensor: sensors){
								//新增传感器节点
								ZTreeNode sensorNode = new ZTreeNode();
								sensorNode.setChecked(false);
								sensorNode.setId(Long.valueOf(sensor.getCode()));
								sensorNode.setName(sensor.gettName());
								sensorNode.setOpen(false);
								sensorNode.setpId(Long.valueOf(station.getCode()));
								stationZTree.add(sensorNode);
							}
						}
						
					}
				}
			
			}
			ztree.addAll(stationZTree);
			ztree.add(ZTreeNode.createParent());
		}
		return ztree;
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
    	sensor.like("t_name", condition).or().like("sort_code", condition).and().eq("valid", "0");
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
    
    
    
    /**
     * <p>Title: layerDetail</p>  
     * <p>Description: 弹出传感器详情页面</p>  
     * @param id
     * @return
     */
    @RequestMapping(value="/layerdetail/{id}")
    public String layerDetail(@PathVariable("id") Integer id,Model model){
    	if(id!=null){
    		AirSensor sensor = airSensorService.selectById(id);
    		model.addAttribute("item", sensor);
    	}
    	return PREFIX + "airSensor_detail.html";
    }
    
    
    @RequestMapping(value="checkOnline",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkOnline(String ids){
    	return airSensorService.checkOnline(ids);
    }
    
    
}
