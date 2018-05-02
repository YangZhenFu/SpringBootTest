var AirCurve = {
//  /  id: "AirDataTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    areaId: 0,
    color :  function(){
    	  return (function(m,s,c){
    		    return (c ? arguments.callee(m,s,c-1) : '#') +
    		      s[m.floor(m.random() * 16)]
    	})(Math,'0123456789abcdef',5)
    },
    symbol : function(){
    	var symbols=['circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow'];
    	return symbols[parseInt(Math.random()*7)];
    }
};

//基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

// 指定图表的配置项和数据
var option = {};

var directionMap = {};
echarts.util.each(
    ['E', 'ENE', 'NE', 'NNE', 'N', 'NNW', 'NW', 'WNW', 'W', 'WSW', 'SW', 'SSW', 'S', 'SSE', 'SE', 'ESE'],
    function (name, index) {
        directionMap[name] = Math.PI / 8 * index;
    }
);


var dims = {
        time: 0
//        pm25: 1,
//        humidity: 2,
//        temperature: 3,
//        windSpeed: 4,
//        noise: 5,
//        windDirection:6,
//        windDirectionMsg:7,
//        pm10:8
    };
  var arrowSize = 18;

  function renderArrow(param, api) {
      var point = api.coord([
          api.value(dims.time),
          api.value(dims.windSpeed)
      ]);

      return {
          type: 'path',
          shape: {
              pathData: 'M31 16l-15-15v9h-26v12h26v9z',
              x: -arrowSize / 2,
              y: -arrowSize / 2,
              width: arrowSize,
              height: arrowSize
          },
          rotation: directionMap[api.value(dims.windDirection)],
          position: point,
          style: api.style({
              stroke: '#555',
              lineWidth: 1
          })
      };
  }





/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
AirCurve.formParams = function() {
    var queryData = {};
    queryData['areaId'] = AirCurve.areaId;
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    return queryData;
}

/**
 * 重置查询条件
 */
AirCurve.resetSearch = function() {
	$("#condition").val('');$("#beginTime").val('');$("#endTime").val('');
	this.areaId=0;
	var treeObj = $.fn.zTree.getZTreeObj("areaTree");
	var nodes = treeObj.getNodes();
	if (nodes.length>0) {
		treeObj.selectNode(nodes[0]);
	}
	AirCurve.search();
}

/**
 * 查询区域列表
 */
AirCurve.search = function () {
	console.log(this.formParams());
	//AirCurve.table.refresh({query: AirCurve.formParams()});
};




AirCurve.OnClickArea = function(e, treeId, treeNode){
//	var zTree = $.fn.zTree.getZTreeObj("areaTree");
//	zTree.expandNode(treeNode, null, null, null, true);
	
	AirCurve.areaId=treeNode.id;
	if((treeNode.id+'').length==17){
		//AirCurve.search();
		
		myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
		 
		 
		var nums=[];        //数值数组（存放服务器返回的所有温度值）
		var dates=[];		//时间数组
		var types=[]; 		//类型数组
		var units=[];        //单位数组
		var legends=[];		//图表类型
		var colors=[];		//颜色
		
		var series=[];      //系列列表
		var yAxises=[];		//Y轴
		
		$.ajax({    //使用JQuery内置的Ajax方法
		type : "post",        //post请求方式
		async : true,        //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
		url : "/air/curve/query",    //请求发送到ShowInfoIndexServlet处
		data : {code:treeNode.id},        //请求内包含一个key为name，value为A0001的参数；服务器接收到客户端请求时通过request.getParameter方法获取该参数值
		dataType : "json",        //返回数据形式为json
		success : function(result) {
		    //请求成功时执行该函数内容，result即为服务器返回的json对象
		    if (result  && result.data.length > 0) {
		   	 //console.log(result);
		    	var data=result.data;
		    	var device=result.device;
		    	
		    	for(var i=0;i<device.length;i++){
		    		types.push(device[i].typeName);
		    		units.push(device[i].unit);
		    		legends.push(device[i].legend);
		    		
		    		dims[device[i].typeName]=i+1;
		    	}
		    	
		    	
//		    	console.log(dims);
		    	
		    	for(var i=0;i<data.length;i++){
		    		var sensorData=data[i];
		    		var sensorNum = [];//传感器数值数组
		    		var sensorDates = []; //传感器时间数组
		    		if(sensorData.length>0){
		    			for(var j=0;j<sensorData.length;j++){
		    				sensorNum.push(sensorData[j].numerical);
		    				sensorDates.push(sensorData[j].heartbeatTime);
		    			}
		    			nums.push(sensorNum);
		    			dates.push(sensorDates);
		    		}
		    		
		    		colors.push(AirCurve.color());
		    		
		    		var serie={name : legends[i], data : sensorNum,type : 'line',symbol:AirCurve.symbol()};
		    		
		    		series.push(serie);
		    		
		    	}
		    	
		    	console.log(nums);
		    	console.log(dates);
		    	console.log(types);
		    	console.log(units);
		    	console.log(legends);
		    	console.log(colors);
		    	console.log(series);
		    	
		           myChart.hideLoading();    //隐藏加载动画
		           
		           myChart.setOption({        //载入数据
		        	   title: {    //图表标题
		        	        text: '数据统计表'
		        	    },
		        	    tooltip: {
		        	    	trigger: 'axis',
		        	        axisPointer: {
		        	            type: 'cross',
		        	            label: {
		        	                backgroundColor: '#283b56'
		        	            }
		        	        },
		        	    	show:true
		        	    },
		        	    dataZoom: [
		        	         {
		        	             type: 'slider',    //支持鼠标滚轮缩放
		        	             start: 0,            //默认数据初始缩放范围为10%到90%
		        	             end: 100
		        	         },
		        	         {
		        	             type: 'inside',    //支持单独的滑动条缩放
		        	             start: 0,            //默认数据初始缩放范围为10%到90%
		        	             end: 100
		        	         }
		        	    ],
		        	    toolbox: {    //工具栏显示             
		        	        show: true,
		        	        feature: {                
		        	           // saveAsImage: {show:true} //显示“另存为图片”工具
		        	        mark : {show: true},  
		        	        dataView : {show: true, readOnly: false},  
		        	        magicType : {show: true, type: ['line', 'bar']},  
		        	        restore : {show: true},  
		        	        saveAsImage : {show: true} 
		        	        }
		        	    },
		        	   
		        	   legend: {    //图表上方的类别显示               
		        	        show:true,
		        	        data:legends
		        	    },
		        	   color : colors,
		               xAxis: {
		                   data: dates[0]    //填入X轴数据
		               },
		               
		               yAxis : [    //Y轴（这里我设置了两个Y轴，左右各一个）
		                            {
		                                 name : types[0],
		                                 type : 'value',
		                                 scale : true,
		                                 axisLabel : {
		                                     formatter: '{value}'+units[0]    //控制输出格式
		                                 }
		                             }
		                        
		               ],
		               
		               series: series
		           },true);
		           
		    }
		    else {
		        //返回的数据为空时显示提示信息
		        	layer.msg("图表请求数据为空，可能服务器暂未录入观测数据，您可以稍后再试！");
		          myChart.hideLoading();
		          myChart.clear();
		    }

		},
		error : function(errorMsg) {
		    //请求失败时执行该函数
		    layer.msg("图表请求数据失败，可能是服务器开小差了");
		    myChart.hideLoading();  
		    myChart.clear();
		}
		});


		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option); 
		console.log(myChart.getOption());
		
	}
	

}



$(function(){
	 var myZTree = new $ZTree('areaTree',Feng.ctxPath+'/airSensor/ztree');
	    myZTree.bindOnClick(AirCurve.OnClickArea);
	    myZTree.init();
	    myZTree.searchNodes('search_input');
	     
	 
});