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


/**
 * 点击添加设备预警参数
 */
Monitor.openAddAirSensorWarnParam = function (obj) {
	var sensorId=$(obj)[0].id;
	console.log($(obj)[0].id);
    var index = layer.open({
        type: 2,
        title: '添加设备预警',
        area: ['900px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        shadeClose:true,
        content: Feng.ctxPath + '/airSensorWarnParam/addWarnParam',
        success:function(layero,index){
         	var iframe=layero.find("iframe")[0].contentWindow.document;
         	$('#sensorId',iframe).val(sensorId).attr('disabled','true');
         	
         }
    });
    this.layerIndex = index;
};


Monitor.OnClickArea = function(e, treeId, treeNode){
	Monitor.areaId=treeNode.id;
	var zTree = $.fn.zTree.getZTreeObj("areaTree");
	zTree.expandNode(treeNode, null, null, null, true);
	
	if((treeNode.id+'').length==17){
		$.post(Feng.ctxPath+'/airSensorWarnParam/queryData',{stationCode:treeNode.id},function(result){
			console.log(result);
			
			$('#real_time_status').empty();
			$('#sensor_status').empty();
			
			if(result){
				var sensors=result.sensors;
				for(var i=0;i<sensors.length;i++){
					var sensor=sensors[i];
					var params=sensor.warnParams;
					var div;
					if(params.length>0){
						div='<div class="col-md-2 col-sm-4 col-xs-4">'+
						   		'<div class="widget gray-bg orders">'+
								'<div class="row">'+
										'<div class="col-sm-4">'+
											'<div class="text-center">'+
												'<img alt="image" class="img-circle m-t-xs img-responsive" src="/static/img/sensor/'+sensor.img+'.png">'+
											'</div>'+
										'</div>'+
										'<div class="col-sm-8">'+
											'<h3 class="switch-title"><font>'+sensor.tName+'</font></h3>'+
												'<div class="switch" >'+
												    '<input type="checkbox"   name="my-checkbox" id="'+sensor.id+'" status="'+params[0].controlMode+'"/>'+
												'</div>'+
										'</div>'+
										'<div class="clearfix"></div>'+
								'</div>'+
							   '</div>'+
							'</div>';
					}else{
						div='<div class="col-md-2 col-sm-4 col-xs-4">'+
						   		'<div class="widget gray-bg orders">'+
								'<div class="row">'+
										'<div class="col-sm-4">'+
											'<div class="text-center">'+
												'<img alt="image" class="img-circle m-t-xs img-responsive" src="/static/img/sensor/'+sensor.img+'.png">'+
											'</div>'+
										'</div>'+
										'<div class="col-sm-8">'+
											'<h3 class="switch-title"><font>'+sensor.tName+'</font></h3>'+
												'<button type="button" class="btn btn-primary " onclick="Monitor.openAddAirSensorWarnParam(this);" id="'+sensor.id+'">'+
											    '<i class="fa fa-plus"></i>&nbsp;添加'+
											    '</button>'+
										'</div>'+
										'<div class="clearfix"></div>'+
								'</div>'+
							   '</div>'+
							'</div>';
					}
					$('#real_time_status').append(div);
				}
				
				
				$("input[name='my-checkbox']").each(function(){
					var switchBtn=$(this);
					var status=$(this).attr('status');
					var sensorId=$(this)[0].id;
					console.log($(this));
					$(this).bootstrapSwitch({
						 onText:'开',  
				         offText:'关',
				         onColor:"success",    
			            offColor:"info",    
			            size:"small", 
				        onSwitchChange:function(event,state){
				        	//打开
				        	if(state==true){
				        		state='0';
				        	}else{
				        		state='1';
				        	}
				        	var url=Feng.ctxPath+'/airSensorWarnParam/updateBySensorId/'+sensorId;
				        	$.post(url,{state:state},function(result){
				        		if(result.code=='200'){
				        			 layer.msg(result.message,{icon:1},function(){
				        				 switchBtn.parent().parent().parent().trigger('click');
				         			});
				        		}else{
				        			 layer.msg(result.message,{icon:5},function(){
				        				 switchBtn.parent().parent().parent().trigger('click');
					         			});
				        		}
				        		
				        	});
				        	
				        }
					});
					
					if(status=='0'){
						//打开
				        $(this).bootstrapSwitch('toggleState',true);
					}
				});
				
				$('#real_time_status .orders').click(function(){
					$('#sensor_status').empty();
					if($(this).hasClass('warn-control-selected')){
						$(this).removeClass('warn-control-selected');
					}else{
						$(this).addClass('warn-control-selected').parent().siblings().find('.orders').removeClass('warn-control-selected');
						
						var tagName=$(this).find('.switch-title').next().prop('tagName');
						if(tagName=='DIV'){
							var id=$(this).find("input[name='my-checkbox']")[0].id;
							var url=Feng.ctxPath+'/airSensorWarnParam/queryBySensorId/'+id;
							$.post(url,function(result){
								console.log(result);
								var params=result;
								if(params && params.length>0){
									for(var i=0;i<params.length;i++){
										var param=params[i];
										var tr='<tr><td class="center">'+param.sensorName+'</td>'+
													'<td class="center">'+(param.startTime+'-'+param.endTime)+'</td>'+
													'<td class="center">'+param.expression+'</td>'+
													'<td class="center">'+param.threshold+'</td>'+
													'<td class="center">'+param.controlMode+'</td>'+
													'<td class="center">'+param.warnInterval+'</td>'+
													'<td class="center">'+param.valid+'</td>'+
													'<td class="center">'+param.alarmTime+'</td>'+
													'<td class="center">'+param.sortCode+'</td>'+
												'</tr>';
										$('#sensor_status').append(tr);
									}
								}
								
								
							});
						}
						
					}
				});
				
			
				
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