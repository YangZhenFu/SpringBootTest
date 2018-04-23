package com.stylefeng.guns.core.beetl.function;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.StringConvert;
import com.stylefeng.guns.modular.system.model.Area;
import com.stylefeng.guns.modular.system.service.IAreaService;

/**  
 * <p>Title: AreaFunction</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年4月17日  
 */
@Component
@DependsOn("springContextHolder")
public class AreaFunction {

	private IAreaService areaService = SpringContextHolder.getBean(IAreaService.class);
	
	public String findAreaList(){
		Area area=new Area();
		area.setDelFlag("0");
		List<Area> list = areaService.selectList(new EntityWrapper<Area>(area));
    	return JSON.toJSONString(list);
	}
	
	
	/**
	 * 全部区域 key:区域id  value:区域对象
	* @return
	 */
	public Map<Long, Area> getAllAreaMap(){
		Map<Long, Area> allAreaMap=Maps.newHashMap();
		List<Area> list = areaService.selectList(new EntityWrapper<Area>().eq("del_flag", "0"));
		if(CollectionUtils.isNotEmpty(list)){
			for(Area area : list){
				allAreaMap.put(area.getId(), area);
			}
		}
		return allAreaMap;
	}
	
	/**
	 * 拼接所有区域 XX-XX-XX
	 * @return
	 */
	public Map<String,Long> getAllAreaStr(){
		Map<Long, Area> allAreaMap = getAllAreaMap();
		Map<String,Long> result=Maps.newHashMap();
		if(allAreaMap!=null){
			Area Area=new Area();
			Area.setType("5");
			List<Area> list = areaService.selectList(new EntityWrapper<Area>(Area).eq("del_flag", "0").orderBy("id"));
			if(CollectionUtils.isNotEmpty(list)){
				for(Area area : list){
					StringBuilder builder=new StringBuilder();
					String[] pids = ((Area)allAreaMap.get(area.getId())).getParentIds().split(",");
					for(String pid : pids){
						Area sa = (Area)allAreaMap.get(StringConvert.toLong(pid));
						if(sa!=null) builder.append(sa.getName()).append("-");
					}
					Area area2 = (Area)allAreaMap.get(area.getId());
					builder.append(area2.getName());
					result.put(builder.toString(), area2.getId());
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据区域id拼接所有区域层级字符
	 * @param id
	 * @return
	 */
	public String getAreaStrByAreaId(Long id){
		Map<Long, Area> areaMap = getAllAreaMap();
		StringBuilder builder=new StringBuilder();
		if(areaMap!=null){
			Area Area = areaMap.get(id);
			String[] pids = Area.getParentIds().split(",");
			if(pids!=null){
				for(String pid : pids){
					Area sa = areaMap.get(StringConvert.toLong(pid));
					if(sa!=null) builder.append(sa.getName()).append("-");
				}
				builder.append(Area.getName());
			}
		}
		return builder.toString();
	}
	
}
