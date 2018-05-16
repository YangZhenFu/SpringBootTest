//package com.stylefeng.guns.modular.air.controller;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.baomidou.mybatisplus.plugins.Page;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.stylefeng.guns.core.base.controller.BaseController;
//import com.stylefeng.guns.core.common.constant.factory.PageFactory;
//import com.stylefeng.guns.core.excel.ExcelUtils;
//import com.stylefeng.guns.modular.air.model.AirSensorData;
//import com.stylefeng.guns.modular.air.service.IAirSensorDataService;
//import com.stylefeng.guns.modular.air.warpper.AirSensorDataWarpper;
//
///**  
// * <p>Title: AirSensorDataController</p>  
// * <p>Description: </p>  
// * @author YangZhenfu  
// * @date 2018年4月25日  
// */
//@RequestMapping("/air/data")
//@Controller
//public class AirSensorDataController extends BaseController{
//
//	private String PREFIX="/air/data/";
//	
//	private static Logger logger = org.slf4j.LoggerFactory.getLogger(AirSensorDataController.class);
//	
//	@Autowired
//	private IAirSensorDataService airSensorDataService;
//	
//	
//	@RequestMapping
//	public String main(Model model){
//		return PREFIX+"air_data.html";
//	}
//	
//	@RequestMapping(value="list",method=RequestMethod.POST)
//	@ResponseBody
//	public Object querySensorData(@RequestParam(required=false) String condition,@RequestParam(required=false) String areaId,@RequestParam(required=false) String beginTime,@RequestParam(required=false) String endTime){
//		Page<AirSensorData> page =new PageFactory<AirSensorData>().defaultPage();
////    	Condition wrapper = Condition.create();
////    	wrapper.like("t_name", condition).or().like("sort_code", condition);
////    	Page<Map<String,Object>> mapsPage = airSensorDataService.selectMapsPage(page, wrapper);
////    	mapsPage.setRecords((List<Map<String,Object>>)new AirSensorDataWarpper(mapsPage.getRecords()).warp());
//    	List<Map<String, Object>> list = airSensorDataService.findDataByParams(page, condition, areaId, beginTime, endTime, page.getOrderByField(), page.isAsc());
//    	page.setRecords((List<AirSensorData>) new AirSensorDataWarpper(list).warp());
//        return packForBT(page);
//	}
//	
//	
//	/**
//	 * 导出用户
//	 * @param params
//	 * @param response
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="export", method=RequestMethod.POST )
//	public void exportFile(@RequestParam(required=false) String condition,@RequestParam(required=false) String areaId,@RequestParam(required=false) String beginTime,@RequestParam(required=false) String endTime, HttpServletResponse response) {
//		List<AirSensorData> datas=Lists.newArrayList();
//		List<Map<String,Object>> list = airSensorDataService.queryDataByParams(condition, areaId, beginTime, endTime);
//		datas.addAll((List<AirSensorData>) new AirSensorDataWarpper(list).warp());
//		Map<String, String> titleMap=Maps.newLinkedHashMap();
//		titleMap.put("唯一标识", "id");
//		titleMap.put("编号", "code");
//		titleMap.put("排序", "sortCode");
//		titleMap.put("传感器名称", "sensorName");
//		titleMap.put("检测数值", "numerical");
//		titleMap.put("检测单位", "unit");
//		titleMap.put("接收时间", "heartbeatTime");
//		titleMap.put("信号强度", "signalStrength");
//		titleMap.put("创建者", "createBy");
//		titleMap.put("创建时间", "createTime");
//		
//		try {
//			//流的方式直接下载
//			ExcelUtils.exportExcel(response, "历史数据.xls", datas, titleMap);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//	}
//	
//	
//}
