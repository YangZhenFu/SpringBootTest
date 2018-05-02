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
															'<img alt="image" class="img-circle m-t-xs img-responsive" src="img/a4.jpg">'+
														'</div>'+
													'</div>'+
													'<div class="col-sm-8">'+
														'<h3><font color="white">'+sensor.typeName+'</font></h3>'+
														'<h3><strong><font color="white">'+sensor.nowNumerical+sensor.unit+'</font></strong></h3>'+
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