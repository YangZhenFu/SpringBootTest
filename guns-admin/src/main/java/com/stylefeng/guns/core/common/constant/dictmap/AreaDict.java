package com.stylefeng.guns.core.common.constant.dictmap;

import com.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

/**  
 * <p>Title: AreaDict</p>  
 * <p>Description: 区域字典</p>  
 * @author YangZhenfu  
 * @date 2018年4月13日  
 */
public class AreaDict extends AbstractDictMap{

	@Override
	public void init() {
		this.put("areaId", "区域Id");
		this.put("code", "区域编码");
		this.put("name", "区域名称");
		this.put("type", "区域类型");
		this.put("parentId", "上级区域");
		this.put("icon", "区域图标");
		this.put("remarks", "备注");
		
	}

	@Override
	protected void initBeWrapped() {
		
	}

}
