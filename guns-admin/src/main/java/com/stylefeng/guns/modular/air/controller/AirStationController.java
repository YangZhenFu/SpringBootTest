package com.stylefeng.guns.modular.air.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.beetl.function.AreaFunction;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.air.model.AirStation;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.warpper.AirStationWarpper;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.service.IAreaService;
import com.stylefeng.guns.modular.system.service.IDeptService;

/**
 * 气象站控制器
 *
 * @author fengshuonan
 * @Date 2018-04-17 11:38:31
 */
@Controller
@RequestMapping("/air/station")
public class AirStationController extends BaseController {

    private String PREFIX = "/air/station/";

    @Autowired
    private IAirStationService airStationService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private AreaFunction areaFunction;
    @Autowired
    private IAreaService areaService;
    /**
     * 跳转到气象站首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "station.html";
    }

    /**
     * 跳转到添加气象站
     */
    @RequestMapping("/station_add")
    public String stationAdd() {
        return PREFIX + "station_add.html";
    }

    
    @RequestMapping(value="ztree",method=RequestMethod.POST)
    @ResponseBody
    public List<ZTreeNode> getStationZTree(){
    	List<ZTreeNode> ztree = areaService.getZtreeNode();
    	return createStationZTree(ztree);
    }
    
    /**  
	 * <p>Title: createStationZTree</p>  
	 * <p>Description: </p>  
	 * @param ztree
	 * @return  
	 */ 
	private List<ZTreeNode> createStationZTree(List<ZTreeNode> ztree) {
		List<ZTreeNode> stationZTree=Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(ztree)){
			for(ZTreeNode node : ztree){
				List<AirStation> list = airStationService.selectList(new EntityWrapper<AirStation>().eq("area_id", node.getId()).eq("valid", "0"));
				if(CollectionUtils.isNotEmpty(list)){
					for(AirStation station : list){
						ZTreeNode zTreeNode = new ZTreeNode();
						zTreeNode.setChecked(false);
						zTreeNode.setId(Long.valueOf(station.getCode()));
						zTreeNode.setName(station.gettName());
						zTreeNode.setOpen(false);
						zTreeNode.setpId(node.getId());
						stationZTree.add(zTreeNode);
					}
				}
			
			}
			ztree.addAll(stationZTree);
			ztree.add(ZTreeNode.createParent());
		}
		return ztree;
	}

	/**
     * 跳转到修改气象站
     */
    @RequestMapping("/station_update/{stationId}")
    public String stationUpdate(@PathVariable Integer stationId, Model model) {
    	AirStation station = airStationService.selectById(stationId);
    	if(station!=null){
    			Dept dept = deptService.selectById(station.getDeptId());
        		if(dept!=null){
        			model.addAttribute("deptName", dept.getSimplename());
        		}
    	}
    	
        model.addAttribute("item",station);
        LogObjectHolder.me().set(station);
        return PREFIX + "station_edit.html";
    }

    /**
     * 获取气象站列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required=false) String condition) {
    	Page<AirStation> page = new PageFactory<AirStation>().defaultPage();
		List<Map<String, Object>> list = airStationService.findListByParams(page, condition, page.getOrderByField(), page.isAsc());
    	page.setRecords((List<AirStation>) new AirStationWarpper(list).warp());
        return packForBT(page);
        
    }

    /**
     * 新增气象站
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@ModelAttribute AirStation station,String installTimes) {
    	if(airStationService.saveAirStation(station,installTimes)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 删除气象站
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer stationId) {
    	if(airStationService.deleteAirStationById(stationId)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 修改气象站
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AirStation station,String installTimes) {
    	if(airStationService.saveAirStation(station,installTimes)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 气象站详情
     */
    @RequestMapping(value = "/detail/{stationId}")
    @ResponseBody
    public Object detail(@PathVariable("stationId") Integer stationId) {
        return airStationService.selectById(stationId);
    }
    
    /**
     * <p>Title: checkName</p>  
     * <p>Description: 检验名称不能重复</p>  
     * @param tName
     * @return
     */
    @RequestMapping(value="/checkName",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkName(@RequestParam String tName){
    	Map<String,Object> result =Maps.newHashMap();
    	if(StringUtils.isNotBlank(tName)){
    		AirStation station = new AirStation();
    		station.settName(tName);
    		int count = airStationService.selectCount(new EntityWrapper<AirStation>(station));
    		if(count>0){
    			result.put("valid", false);
    		}else{
    			result.put("valid", true);
    		}
    	}else{
    		result.put("valid", false);
    	}
    	return result;
    }
    
    
    @RequestMapping(value="/layerMap",method=RequestMethod.GET)
    public String layerMap(String address,Model model){
    	model.addAttribute("address", address);
    	return PREFIX + "map_select.html";
    }
    
    /**
     * <p>Title: layerStationDetail</p>  
     * <p>Description: 查看详情</p>  
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/layerdetail/{id}")
    public String layerStationDetail(@PathVariable("id") Integer id,Model model){
    	AirStation station = airStationService.selectById(id);
    	if(station!=null){
			Dept dept = deptService.selectById(station.getDeptId());
    		if(dept!=null){
    			model.addAttribute("deptName", dept.getSimplename());
    		}
    		String areaName = areaFunction.getAreaStrByAreaId(station.getAreaId());
    		model.addAttribute("areaName", areaName);
    	}
	
    	model.addAttribute("item",station);
    	return PREFIX + "station_detail.html";
    }
    
}
