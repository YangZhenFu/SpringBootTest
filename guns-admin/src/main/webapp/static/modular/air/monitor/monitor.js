var Monitor = {
    id: "monitorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    areaId: 0,
    sensorClasses : ['lazur-bg','blue-bg','navy-bg','yellow-bg','red-bg']
};

/**
 * 日期格式化
 */
Date.prototype.format =function(format){var o = {"M+" : this.getMonth()+1, //month
		 "d+" : this.getDate(), //day
		 "h+" : this.getHours(), //hour
	     "m+" : this.getMinutes(), //minute
		 "s+" : this.getSeconds(), //second
		 "q+" : Math.floor((this.getMonth()+3)/3), //quarter
		 "S" : this.getMilliseconds() //millisecond
}
if(/(y+)/.test(format)) format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4- RegExp.$1.length));
for(var k in o)if(new RegExp("("+ k +")").test(format))format = format.replace(RegExp.$1,RegExp.$1.length==1? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
return format;
}

/**
 * JS获取n至m随机整数
 */
function rd(n,m){
    var c = m-n+1; 
    return Math.floor(Math.random() * c + n);
}


Monitor.OnClickArea = function(e, treeId, treeNode){
	Monitor.areaId=treeNode.id;
	var zTree = $.fn.zTree.getZTreeObj("areaTree");
	zTree.expandNode(treeNode, null, null, null, true);
	
	if((treeNode.id+'').length==17){
		$.post(Feng.ctxPath+'/air/monitor/queryData',{stationCode:treeNode.id},function(result){
//			console.log(result);
			$('#data_refresh_time').html(''); 
			$('#refresh_time').html('');
			$('#real_time_status').empty();
			$('#sensor_status').empty();
			
			if(result){
				var refreshTime=new Date().format('yyyy-MM-dd hh:mm:ss');
				$('#data_refresh_time').html(result.refreshTime); 
				$('#refresh_time').html(refreshTime.split(' ')[1]);
				
				
				toastr.clear();
				var alarms=result.alarms;
				if(alarms && alarms.length>0){
					
					 //参数设置，若用默认值可以省略以下面代
				    toastr.options = {
					     "closeButton": true, //是否显示关闭按钮
					     "debug": false, //是否使用debug模式
					     "progressBar": true,
					     "positionClass": "toast-bottom-right",//弹出窗的位置
					     "showDuration": "300",//显示的动画时间
					     "hideDuration": "1000",//消失的动画时间
					     "timeOut": "60000", //展现时间
					     "extendedTimeOut": "1000",//加长展示时间
					     "showEasing": "swing",//显示时的动画缓冲方式
					     "hideEasing": "linear",//消失时的动画缓冲方式
					     "showMethod": "fadeIn",//显示时的动画方式
					     "hideMethod": "fadeOut", //消失时的动画方式
				   };
				    
					for(var i=0;i<alarms.length;i++){
						var alarm=alarms[i];
						toastr.warning('<a class="J_menuItem" href="/airSensorAlarmInfo" data-index="89">'+alarm.alarmInfo+'</a>','告警信息');
					}
				}
				
				
				
				
				var data=result.data;
				if(data){
					for(var i=0;i<data.length;i++){
						var sensor=data[i];
						var tr='<tr><td class="center">'+sensor.code+'</td>'+
									'<td class="center">'+sensor.tName+'</td>'+
									'<td class="center">'+(sensor.minNumerical || '无')+'</td>'+
									'<td class="center">'+(sensor.minTime || '-')+'</td>'+
									'<td class="center">'+(sensor.maxNumerical || '无')+'</td>'+
									'<td class="center">'+(sensor.maxTime || '-')+'</td>'+
								'</tr>';
						
						var sensorClass=Monitor.sensorClasses[rd(0,(Monitor.sensorClasses.length-1))];
						var div='<div class="col-md-3 col-sm-6 col-xs-6">'+
									'<div class="widget '+sensorClass+' orders">'+
										'<div class="row">'+
											    '<a href="javascript:void(0);">'+
													'<div class="col-sm-4">'+
														'<div class="text-center">'+
															'<img alt="image" class="img-circle m-t-xs img-responsive" style="height: 65.66px;" src="/static/img/sensor/'+sensor.icon+'.png">'+
														'</div>'+
													'</div>'+
													'<div class="col-sm-8">'+
														'<h3><font color="white">'+sensor.typeName+'</font></h3>'+
														'<h3><strong><font color="white">'+(sensor.nowNumerical || '')+sensor.unit+'</font></strong></h3>'+
													'</div>'+
													'<div class="clearfix"></div>'+
											    '</a>'+
										'</div>'+
								   '</div>'+
								'</div>';
						
						$('#sensor_status').append(tr);
						$('#real_time_status').append(div);
					}
				}
				
			}
		});
	}
	
}



$(function(){

	   
	    var myZTree = new $ZTree('areaTree',Feng.ctxPath+'/air/station/ztree');
	    myZTree.bindOnClick(Monitor.OnClickArea);
	    myZTree.init();
	    myZTree.searchNodes('search_input');
	 
});