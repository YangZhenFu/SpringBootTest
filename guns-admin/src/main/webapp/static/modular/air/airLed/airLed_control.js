/**
 * LED终端管理初始化
 */
var AirLed = {
    id: "AirLedTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AirLed.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '唯一标识', field: 'id', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '编号', field: 'code', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '名称', field: 'tName', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '排序', field: 'sortCode', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '类型', field: 'type', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '所属气象站', field: 'stationId', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle',sortable: true,
            	formatter:function(value,row,index){
            		var status;
            		switch(value){
            			case '正常':
            				status='label-info';
            				break;
            			case '响应':
            				status='label-success';
            				break;
            			case '通讯故障':
            				status='label-warning';
            				break;
            			case '设备故障':
            				status='label-important';
            				break;
            			default : 
            				status='';
            				break;
            		}
            		return '<span class="label '+status+'">'+value+'</span>';
            	}	
            },
            {title: 'IP地址', field: 'ipAddr', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '端口号', field: 'port', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '屏幕高度', field: 'screenHeight', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '屏幕宽度', field: 'screenWidth', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '屏幕状态', field: 'screenStatus', visible: true, align: 'center', valign: 'middle',sortable: true,
            	formatter:function(value,row,index){
            		var status;
            		switch(value){
            			case '关机':
            				status='label-info';
            				break;
            			case '开机':
            				status='label-success';
            				break;
            			default : 
            				status='';
            				break;
            		}
            		return '<span class="label '+status+'">'+value+'</span>';
            	}
            },
            {title: '屏幕亮度', field: 'brightness', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '实时发布', field: 'controlMode', visible: true, align: 'center', valign: 'middle',sortable: true,
            	formatter:function(value,row,index){
            		return '<a href="javascript:void(0);" title="点击切换状态" onclick="changeItemStatus(this,'+row.id+');">'+value+'</a>';
            	}
            },
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle',sortable: true,
            	formatter:function(value,row,index){
            		if(value){
            			return value.substring(0,3)+'...';
            		}
            	}	
            }
    ];
};


function changeItemStatus(obj,id){
	var controlMode=$(obj).html();
	if(controlMode=='开启'){
		controlMode='1';
	}else{
		controlMode='0';
	}
	var url='/airLed/changeMode';
	
	layer.msg('指令发送成功,请稍后.', {
  	  icon: 16,
  	  time: 1000000, //2秒关闭（如果不配置，默认是3秒）
  	  shade: [0.1,'#fff'] //0.1透明度的白色背景
  });
	
	$.post(url,{id:id,controlMode:controlMode},function(result){
		if(result.code=='000000'){
			 var index=layer.msg('操作成功！',{icon:1},function(){
				 	layer.close(index);
				 	AirLed.table.refresh();
   			});
		 }else{
			var index=layer.msg(result.msg,{icon:5},function(){
				layer.close(index);
				AirLed.table.refresh();
			});
		 }
	});
	
}

AirLed.checkOnline = function(){
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请至少选择一条记录！");
        return false;
    }else{
    	 var ids=[];
        for(var i=0;i<selected.length;i++){
        	ids.push(selected[i].id);
        }
        
        var url=Feng.ctxPath+'/airLed/screen/checkOnline';
        layer.msg('指令发送成功,请稍后.', {
        	  icon: 16,
        	  time: 1000000, //2秒关闭（如果不配置，默认是3秒）
        	  shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(url,{ids : ids.join(';')},function(result){
        	
        	if(result.code=='000000'){
    			 var index=layer.msg(result.msg,{icon:1},function(){
    				 	layer.close(index);
    				 	AirLed.table.refresh();
        			});
    		 }else{
    			var index=layer.msg(result.msg,{icon:5},function(){
    				layer.close(index);
    				AirLed.table.refresh();
    			});
    		 }
        });
    }
}

AirLed.changeLinghting = function(){
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请至少选择一条记录！");
        return false;
    }else{
    	 var ids=[];
        for(var i=0;i<selected.length;i++){
        	ids.push(selected[i].id);
        }
        
        var value=slider.getValue();//当前屏幕亮度
        
        layer.confirm('你确定要把屏幕亮度调整为'+value+'吗？',function(){
        	 var url=Feng.ctxPath+'/airLed/screen/setLighting';
             layer.msg('指令发送成功,请稍后.', {
             	  icon: 16,
             	  time: 1000000, //2秒关闭（如果不配置，默认是3秒）
             	  shade: [0.1,'#fff'] //0.1透明度的白色背景
             });
             $.post(url,{ids : ids.join(';'),brightness:value},function(result){
             	
             	if(result.code=='000000'){
         			 var index=layer.msg('操作成功!',{icon:1},function(){
         				 	layer.close(index);
             				AirLed.table.refresh();
             			});
         		 }else{
         			var index=layer.msg(result.msg,{icon:5},function(){
         				layer.close(index);
         				AirLed.table.refresh();
         			});
         		 }
             });
        });
       
    }
}

AirLed.syncTime = function(){
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请至少选择一条记录！");
        return false;
    }else{
    	 var ids=[];
        for(var i=0;i<selected.length;i++){
        	ids.push(selected[i].id);
        }
        
        var url=Feng.ctxPath+'/airLed/screen/syncTime';
        layer.msg('指令发送成功,请稍后.', {
        	  icon: 16,
        	  time: 1000000, //2秒关闭（如果不配置，默认是3秒）
        	  shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(url,{ids : ids.join(';')},function(result){
        	
        	if(result.code=='000000'){
    			 var index=layer.msg('操作成功!',{icon:1},function(){
    				 	layer.close(index);
        				AirLed.table.refresh();
        			});
    		 }else{
    			var index=layer.msg(result.msg,{icon:5},function(){
    				layer.close(index);
    				AirLed.table.refresh();
    			});
    		 }
        });
    }
}


AirLed.turnOn = function(){
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请至少选择一条记录！");
        return false;
    }else{
    	 var ids=[];
        for(var i=0;i<selected.length;i++){
        	ids.push(selected[i].id);
        }
        
        var url=Feng.ctxPath+'/airLed/screen/turnOn';
        layer.msg('指令发送成功,请稍后.', {
        	  icon: 16,
        	  time: 1000000, //2秒关闭（如果不配置，默认是3秒）
        	  shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(url,{ids : ids.join(';')},function(result){
        	
        	if(result.code=='000000'){
    			 var index=layer.msg('操作成功!',{icon:1},function(){
    				 	layer.close(index);
        				AirLed.table.refresh();
        			});
    		 }else{
    			var index=layer.msg(result.msg,{icon:5},function(){
    				layer.close(index);
    				AirLed.table.refresh();
    			});
    		 }
        });
    }
}


AirLed.turnOff = function(){
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请至少选择一条记录！");
        return false;
    }else{
    	 var ids=[];
        for(var i=0;i<selected.length;i++){
        	ids.push(selected[i].id);
        }
        
        var url=Feng.ctxPath+'/airLed/screen/turnOff';
        layer.msg('指令发送成功,请稍后.', {
        	  icon: 16,
        	  time: 1000000, //2秒关闭（如果不配置，默认是3秒）
        	  shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(url,{ids : ids.join(';')},function(result){
        	
        	if(result.code=='000000'){
    			 var index=layer.msg('操作成功!',{icon:1},function(){
    				 	layer.close(index);
        				AirLed.table.refresh();
        			});
    		 }else{
    			var index=layer.msg(result.msg,{icon:5},function(){
    				layer.close(index);
    				AirLed.table.refresh();
    			});
    		 }
        });
    }
}



/**
 * 检查是否选中
 */
AirLed.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AirLed.seItem = selected[0];
        return true;
    }
};


/**
 * 查询LED终端列表
 */
AirLed.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AirLed.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AirLed.initColumn();
    var table = new BSTable(AirLed.id, "/airLed/list", defaultColunms);
    table.setPaginationType("server");
    AirLed.table = table.init();
});
