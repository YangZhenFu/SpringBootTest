package com.stylefeng.guns.modular.air.warpper;

import java.util.Map;


import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.beetl.function.AreaFunction;
import com.stylefeng.guns.core.common.constant.Constant;
import com.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.StringConvert;

/**  
 * <p>Title: AirStationWarpper</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月18日  
 */
public class AirStationWarpper extends BaseControllerWarpper{
	
	private AreaFunction areaFunction = SpringContextHolder.getBean(AreaFunction.class);
	
	public AirStationWarpper(Object obj) {
		super(obj);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {
		map.put("type",Constant.AIR_STATION_TYPE.get(map.get("type")));
		map.put("deptName", ConstantFactory.me().getDeptName(((Long)map.get("deptId")).intValue()));
		map.put("areaName", areaFunction.getAreaStrByAreaId(StringConvert.toLong(map.get("areaId"))));
	}

}
