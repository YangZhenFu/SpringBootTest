package com.stylefeng.guns.modular.air.service;

import com.stylefeng.guns.modular.air.model.AirStationData;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 气象站检测数据表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-05-02
 */
public interface IAirStationDataService extends IService<AirStationData> {

	/**  
	 * <p>Title: findDataByParams</p>  
	 * <p>Description: </p>  
	 * @param page
	 * @param condition
	 * @param areaId
	 * @param beginTime
	 * @param endTime
	 * @param orderByField
	 * @param asc
	 * @return  
	 */ 
	public List<Map<String, Object>> findPageDataByParams(Page<AirStationData> page, String condition, String areaId,
			String beginTime, String endTime, String orderByField, boolean isAsc);

	/**  
	 * <p>Title: selectDataByParams</p>  
	 * <p>Description: </p>  
	 * @param code
	 * @param beginTime
	 * @param endTime
	 * @return  
	 */ 
	public List<AirStationData> selectDataByParams(String condition,String code, String beginTime, String endTime);

	/**  
	 * <p>Title: selectMapDataByParams</p>  
	 * <p>Description: </p>  
	 * @param condition
	 * @param areaId
	 * @param beginTime
	 * @param endTime
	 * @return  
	 */ 
	public List<Map<String, Object>> selectMapDataByParams(String condition, String areaId, String beginTime,
			String endTime);

	/**  
	 * <p>Title: findOneDayData</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return  
	 */ 
	public List<AirStationData> findOneDayData(Long id);

	/**  
	 * <p>Title: selectFiveDaysData</p>  
	 * <p>Description: </p>  
	 * @param id
	 * @return  
	 */ 
	public List<AirStationData> selectFiveDaysData(Long id);

}
