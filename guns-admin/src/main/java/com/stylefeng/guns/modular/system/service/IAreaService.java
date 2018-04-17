package com.stylefeng.guns.modular.system.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.system.model.Area;

/**
 * <p>
 * 区域表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-08
 */
public interface IAreaService extends IService<Area> {

	
	public List<Map<String,Object>> findListByParams(Page<Area> page,String name,String code,Long areaId,String orderByField,boolean isAsc);

	/**  
	 * <p>Title: getZtreeNode</p>  
	 * <p>Description: </p>  
	 * @return  
	 */ 
	public List<ZTreeNode> getZtreeNode();

	/**  
	 * <p>Title: saveSysArea</p>  
	 * <p>Description: 添加或修改区域</p>  
	 * @param area
	 * @return  
	 */ 
	public Integer saveSysArea(Area area);
	
	/**
	 * <p>Title: deleteAreaByRootId</p>  
	 * <p>Description: 根据id删除所有子孙区域</p>  
	 * @param id
	 * @return
	 */
	public Integer deleteAreaByRootId(Long id);
	
}
