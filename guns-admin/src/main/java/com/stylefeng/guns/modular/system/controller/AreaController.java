package com.stylefeng.guns.modular.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.common.annotion.BussinessLog;
import com.stylefeng.guns.core.common.annotion.Permission;
import com.stylefeng.guns.core.common.constant.Const;
import com.stylefeng.guns.core.common.constant.dictmap.AreaDict;
import com.stylefeng.guns.core.common.constant.factory.PageFactory;
import com.stylefeng.guns.core.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.system.model.Area;
import com.stylefeng.guns.modular.system.model.Dept;
import com.stylefeng.guns.modular.system.service.IAreaService;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.warpper.AreaWarpper;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 区域控制器
 *
 * @author fengshuonan
 * @Date 2018-04-08 11:12:50
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController {

    private String PREFIX = "/system/area/";

    @Autowired
    private IAreaService areaService;

    
    /**
     * 跳转到区域首页
     */
    @RequestMapping("")
    public String index(Model model) {
        return PREFIX + "area.html";
    }

    /**
     * 跳转到添加区域
     */
    @RequestMapping("/area_add")
    public String areaAdd() {
        return PREFIX + "area_add.html";
    }

    /**
     * <p>Title: getAreaZtree</p>  
     * <p>Description: 获取区域树</p>  
     * @return
     */
    @RequestMapping(value="ztree",method=RequestMethod.POST)
    @ResponseBody
    public List<ZTreeNode> getAreaZtree(){
    	List<ZTreeNode> ztree=areaService.getZtreeNode();
    	ztree.add(ZTreeNode.createParent());
    	return ztree;
    }
    
    
    /**
     * 跳转到修改区域
     */
    @RequestMapping("/area_update/{areaId}")
    public String areaUpdate(@PathVariable Integer areaId, Model model) {
        Area area = areaService.selectById(areaId);
        model.addAttribute("item",area);
        LogObjectHolder.me().set(area);
        Area pArea = areaService.selectById(area.getParentId());
        model.addAttribute("pName", (pArea!=null ? pArea.getName() : "顶级"));
        return PREFIX + "area_edit.html";
    }

    /**
     * 获取区域列表
     */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/list")
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    public Object list(@RequestParam(required=false) String name,@RequestParam(required=false) String code,@RequestParam(required=false) Long areaId) {
    	Page<Area> page = new PageFactory<Area>().defaultPage();
		List<Map<String, Object>> list = areaService.findListByParams(page, name, code, areaId, page.getOrderByField(), page.isAsc());
    	page.setRecords((List<Area>) new AreaWarpper(list).warp());
//    	page = areaService.selectPage(page, new EntityWrapper<Area>(new Area()).like("name", name).like("code", code));
    	return this.packForBT(page);
    }

    /**
     * 新增区域
     */
	@ApiOperation(value="新增区域", notes="根据Area对象添加区域")
    @ApiImplicitParam(dataType = "Area", name = "area", value = "区域信息", required = true )
    @RequestMapping(value = "/add",method=RequestMethod.POST)
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "新增区域", key = "name", dict = AreaDict.class)
    public Object add(Area area) {
    	if(StringUtils.isBlank(area.getName())){
    		throw new GunsException(BizExceptionEnum.REQUEST_NULL);
    	}
    	if(areaService.saveSysArea(area)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 删除区域
     */
	@ApiOperation(value="删除区域", notes="根据id删除区域")
    @ApiImplicitParam(dataType = "java.lang.Long", name = "areaId", value = "区域id", required = true )
    @RequestMapping(value = "/delete",method=RequestMethod.POST)
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "删除区域", key = "areaId", dict = AreaDict.class)
    public Object delete(@RequestParam Long areaId) {
        areaService.deleteAreaByRootId(areaId);
        return SUCCESS_TIP;
    }

    /**
     * 修改区域
     */
	@ApiOperation(value="修改区域", notes="根据Area对象修改区域")
    @ApiImplicitParam(dataType = "Area", name = "area", value = "区域信息", required = true )
    @RequestMapping(value = "/update",method=RequestMethod.POST)
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "修改区域", key = "name", dict = AreaDict.class)
    public Object update(Area area) {
    	if(StringUtils.isBlank(area.getName())){
    		throw new GunsException(BizExceptionEnum.REQUEST_NULL);
    	}
    	if(areaService.saveSysArea(area)==1){
    		return SUCCESS_TIP;
    	}else{
    		return ERROR_TIP;
    	}
    }

    /**
     * 区域详情
     */
    @RequestMapping(value = "/detail/{areaId}")
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    public Object detail(@PathVariable("areaId") Integer areaId) {
        return areaService.selectById(areaId);
    }
}
