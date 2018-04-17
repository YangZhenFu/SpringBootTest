package com.stylefeng.guns.modular.system.warpper;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;

/**  
 * <p>Title: AreaWarpper</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月8日  
 */
public class AreaWarpper extends BaseControllerWarpper{

	public AreaWarpper(List<Map<String,Object>> list) {
		super(list);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {
		map.put("areaType", ConstantFactory.me().getAreaType(Integer.parseInt(String.valueOf(map.get("type")))));
	}

}
