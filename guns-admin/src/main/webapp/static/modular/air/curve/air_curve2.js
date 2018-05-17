var AirCurve = {
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
        time: 0,
        '大气温度':1,
        '大气湿度':2,
        '土壤温度':3,
        '土壤湿度':4,
        '照度':5,
        '雨量':6,
        '大气压':7,
        '风速':8,
        '风向':9,
        windDirectionMsg:10,
        '噪声':11,
        'PM10':12,
        'PM2.5': 13,
        'PM1.0':14,
        'CO':15,
        'O3':16,
        'SO2':17,
        'NO2':18,
        '辐射':19,
        '负氧离子':20
    };
  var arrowSize = 18;

  function renderArrow(param, api) {
      var point = api.coord([
          api.value(dims.time),
          api.value(dims.风速)
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
          rotation: directionMap[api.value(dims.风向)],
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
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    return queryData;
}

/**
 * 重置查询条件
 */
AirCurve.resetSearch = function() {
	location.reload();
//	$("#beginTime").val('');$("#endTime").val('');
//	AirCurve.search();
}

/**
 * 查询区域列表
 */
AirCurve.search = function () {
//	console.log(this.formParams());
	
	var treeObj = $.fn.zTree.getZTreeObj("areaTree");
	var nodes = treeObj.getSelectedNodes();
	if(nodes.length>0 && nodes[0].id.length==17){
		
		myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
		 
		 
		var dates=[];		//时间数组
		var types=[]; 		//类型数组
		var units=[];        //单位数组
		var legends=[];		//图表类型
		var colors=[];		//颜色
		
		var data={};
		var series=[];      //系列列表
		var yAxises=[];		//Y轴
		
		$.ajax({    //使用JQuery内置的Ajax方法
		type : "post",        //post请求方式
		async : true,        //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
		url : "/airStationData/query",    //请求发送到ShowInfoIndexServlet处
		data : this.formParams(),        //请求内包含一个key为name，value为A0001的参数；服务器接收到客户端请求时通过request.getParameter方法获取该参数值
		dataType : "json",        //返回数据形式为json
		success : function(result) {
		    //请求成功时执行该函数内容，result即为服务器返回的json对象
		    if (result  && result.data.length > 0) {
		    	var device=result.device;
		    	
		    	
		    	for(var i=0;i<result.data.length;i++){
		    		var stationData=result.data[i];
		    		dates.push(stationData.heartbeatTime);
		    		
		    	}
		    	
		    	data = echarts.util.map(result.data, function (entry) {
                    return [entry.heartbeatTime, entry.airTemperature, entry.airHumidity, entry.soilTemperature, entry.soilHumidity, 
                            entry.illuminance, entry.rainfall, entry.airPressure, entry.windSpeed, entry.windDirection, entry.windDirectionMsg, entry.noise,
                            entry.pm10, entry.pm25, entry.pm1, entry.co, entry.o3, entry.so2, entry.no2, entry.radiation, entry.negativeOxygenIon];
                });
		    	
		    	
		    	for(var i=0;i<device.length;i++){
		    		var typeName=device[i].typeName;
		    		types.push(typeName);
		    		units.push(device[i].unit);
		    		legends.push(device[i].legend);
		    		
		    		colors.push(AirCurve.color());
		    		
		    		var serie;
		    		if(device[i].typeName=='风向'){
		    			serie={
                            	name:'风向(。)',
                                type: 'custom',
                                renderItem: renderArrow,
                                encode: {
                                    x: dims.time,
                                    y: dims.风速
                                },
                                data: data,
                                z: 10
                            };
		    		}else{
		    			serie={name : legends[i], data : data,type : 'line',symbol:AirCurve.symbol(), 
	                            encode: {
	                                x: dims.time,
	                                y: dims[typeName]
	                            }
		    				};
		    			
		    			
		    		}
		    		
		    		series.push(serie);
		    	}
		    	
		    	
		    	
		    	
//		    	console.log(data);
//		    	console.log(dates);
//		    	console.log(types);
//		    	console.log(units);
		    	console.log(legends);
//		    	console.log(colors);
//		    	console.log(series);
		    	
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
		        	    	show:true,
		        	    	formatter: function (params) {
		        	    		
		        	    		var formatter=[echarts.format.formatTime('yyyy-MM-dd hh:mm:ss', params[0].value[dims.time])];
		        	    		
//		        	    		for(var i=0;i<types.length;i++){
//		        	    			if(types[i]=='风向'){
//		        	    				formatter.push(types[i]+' : '+params[0].value[dims.windDirectionMsg]);
//		        	    			}else{
//		        	    				formatter.push(types[i]+' : '+params[0].value[dims[types[i]]]);
//		        	    			}
//		        	    			
//		        	    		}
		        	    		for(var i=0;i<legends.length;i++){
		        	    			var legend=legends[i];
		        	    			if(legend=='风向(。)'){
		        	    				formatter.push('风向  : '+params[0].value[dims.windDirectionMsg]);
		        	    			}else{
		        	    				formatter.push(legend+' : '+params[0].value[dims[legend.substring(0,legend.indexOf('('))]]);
		        	    			}
		        	    			
		        	    		}
		        	    		
//		        	    		console.log(formatter);
                                return formatter.join('<br>');
                            } 
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
		                   data: dates    //填入X轴数据
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
		        	layer.msg("图表请求数据为空，可能服务器暂未录入观测数据，您可以稍后再试！",{icon:5});
		          myChart.hideLoading();
		          myChart.clear();
		    }

		},
		error : function(errorMsg) {
		    //请求失败时执行该函数
		    layer.msg("图表请求数据失败，可能是服务器开小差了",{icon:5});
		    myChart.hideLoading();        
		    myChart.clear();
		}
		});


		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option); 
//		console.log(myChart.getOption());
		
		
	}else{
		layer.msg('请点击选择查询传感器树节点项!',{icon:5});
	}
};




AirCurve.OnClickArea = function(e, treeId, treeNode){
	AirCurve.areaId=treeNode.id;
	AirCurve.search();
}



$(function(){
	 var myZTree = new $ZTree('areaTree',Feng.ctxPath+'/airSensor/ztree');
	    myZTree.bindOnClick(AirCurve.OnClickArea);
	    myZTree.init();
	    myZTree.searchNodes('search_input');
	     
	 
});