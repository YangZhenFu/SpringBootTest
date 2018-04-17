package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.system.model.Area;

/**
 * <p>
 * 区域表 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-08
 */
public interface AreaMapper extends BaseMapper<Area> {

	/**
	 * <p>Title: getAreaListByParams</p>  
	 * <p>Description: 根据条件分页查询区域列表</p>  
	 * @param page
	 * @param name
	 * @param code
	 * @param areaId
	 * @param orderByField
	 * @param isAsc
	 * @return
	 */
	public List<Map<String,Object>> getAreaListByParams(@Param("page") Page<Area> page,@Param("name") String name,@Param("code") String code,@Param("areaId")Long areaId,@Param("orderByField") String orderByField,@Param("isAsc") boolean isAsc);

	/**  
	 * <p>Title: getZtree</p>  
	 * <p>Description: 获取区域树</p>  
	 * @return  
	 */ 
	public List<ZTreeNode> getZtree();
	
	public Integer deleteAreaByRootId(Long id);
}
