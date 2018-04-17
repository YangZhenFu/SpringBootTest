package com.stylefeng.guns.modular.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.AreaMapper;
import com.stylefeng.guns.modular.system.model.Area;
import com.stylefeng.guns.modular.system.service.IAreaService;

/**
 * <p>
 * 区域表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-04-08
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

	/* (non-Javadoc)  
	 * <p>Title: findListByParams</p>  
	 * <p>Description: 分页查询区域列表</p>  
	 * @param page
	 * @param name
	 * @param code
	 * @param orderByField
	 * @param isAsc
	 * @return  
	 * @see com.stylefeng.guns.modular.system.service.IAreaService#findListByParams(com.baomidou.mybatisplus.plugins.Page, java.lang.String, java.lang.String, java.lang.String, boolean)  
	 */
	@Override
	public List<Map<String, Object>> findListByParams(Page<Area> page, String name, String code,Long areaId, String orderByField,
			boolean isAsc) {
		return this.baseMapper.getAreaListByParams(page, name, code,areaId, orderByField, isAsc);
	}

	/* (non-Javadoc)  
	 * <p>Title: getZtreeNode</p>  
	 * <p>Description: 获取区域Ztree</p>  
	 * @return  
	 * @see com.stylefeng.guns.modular.system.service.IAreaService#getZtreeNode()  
	 */
	@Override
	public List<ZTreeNode> getZtreeNode() {
		return this.baseMapper.getZtree();
	}

	/* (non-Javadoc)  
	 * <p>Title: saveSysArea</p>  
	 * <p>Description: 新增或修改区域</p>  
	 * @param area
	 * @return  
	 * @see com.stylefeng.guns.modular.system.service.IAreaService#saveSysArea(com.stylefeng.guns.modular.system.model.Area)  
	 */
	@Override
	public Integer saveSysArea(Area area) {
		areaSetPids(area);
		if(area.getId()==null){
			area.setCreateBy(ShiroKit.getUser().getName());
			area.setCreateDate(new Date());
			return baseMapper.insert(area);
		}else{
			area.setUpdateBy(ShiroKit.getUser().getName());
			area.setUpdateDate(new Date());
			return baseMapper.updateById(area);
		}
	}
	
	/**
	 * <p>Title: areaSetPids</p>  
	 * <p>Description: 设置区域Pids</p>  
	 * @param area
	 */
	private void areaSetPids(Area area) {
        if (ToolUtil.isEmpty(area.getParentId()) || area.getParentId()==0) {
            area.setParentId(0L);
            area.setParentIds("0,");
        } else {
            Long pid = area.getParentId();
            Area pArea = baseMapper.selectById(pid);
            String pids=pArea.getParentIds();
            area.setParentIds(pids+pid+",");
        }
    }

	/* (non-Javadoc)  
	 * <p>Title: deleteAreaByRootId</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return  
	 * @see com.stylefeng.guns.modular.system.service.IAreaService#deleteAreaByRootId(java.lang.Long)  
	 */
	@Override
	public Integer deleteAreaByRootId(Long id) {
		return baseMapper.deleteAreaByRootId(id);
	}


}
