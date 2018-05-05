package com.stylefeng.guns.modular.air.warpper;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.common.constant.SensorTypeEnum;
import com.stylefeng.guns.core.common.constant.WindDirection;
import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.core.util.StringConvert;
import com.stylefeng.guns.modular.air.model.AirSensor;
import com.stylefeng.guns.modular.air.model.SensorType;
import com.stylefeng.guns.modular.air.service.IAirSensorService;
import com.stylefeng.guns.modular.air.service.IAirStationService;
import com.stylefeng.guns.modular.air.service.ISensorTypeService;

/**  
 * <p>Title: AirStationDataWarpper</p>  
 * <p>Description: </p>  
 * @author YangZhenfu  
 * @date 2018年5月2日  
 */
public class AirStationDataWarpper extends BaseControllerWarpper{

	
	private IAirStationService airStationService = SpringContextHolder.getBean(IAirStationService.class);
	private IAirSensorService airSensorService=SpringContextHolder.getBean(IAirSensorService.class);
	private ISensorTypeService sensorTypeService=SpringContextHolder.getBean(ISensorTypeService.class);
	
	/**  
	 * <p>Title: </p>  
	 * <p>Description: </p>  
	 * @param obj  
	 */  
	public AirStationDataWarpper(Object obj) {
		super(obj);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {
		map.put("stationName", airStationService.selectById((Long)map.get("stationId")).gettName());
		StringBuilder dataInfo=new StringBuilder();
		StringBuilder dataType=new StringBuilder();
		
		List<AirSensor> sensors = airSensorService.selectList(new EntityWrapper<AirSensor>().eq("station_id", map.get("stationId")).eq("valid", "0"));
		if(CollectionUtils.isNotEmpty(sensors)){
			for(int i=0;i<sensors.size();i++){
				AirSensor sensor = sensors.get(i);
				SensorType type = sensorTypeService.selectById(sensor.getTypeId());
				
				String typeCode = SensorTypeEnum.findSensorTypeByName(type.gettName()).getCode();
				if(i==sensors.size()-1){
					dataType.append(type.gettName());
					if(type.gettName().contains("风向")){
						dataInfo.append(WindDirection.findWindDirectionByMark((String)map.get(StringConvert.underlineToCamelhump(typeCode))).getMsg());
					}else{
						dataInfo.append(map.get(StringConvert.underlineToCamelhump(typeCode))).append(sensor.getUnit());
					}
				}else{
					dataType.append(type.gettName()).append(" / ");
					if(type.gettName().contains("风向")){
						dataInfo.append(WindDirection.findWindDirectionByMark((String)map.get(StringConvert.underlineToCamelhump(typeCode))).getMsg()).append(" | ");
					}else{
						dataInfo.append(map.get(StringConvert.underlineToCamelhump(typeCode))).append(sensor.getUnit()).append(" | ");
					}
					
				}
			}
		}
		map.put("dataInfo", dataInfo);
		map.put("dataType", dataType);
		
	}

}
