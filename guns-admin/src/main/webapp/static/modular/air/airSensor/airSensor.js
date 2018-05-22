/**
 * 传感器管理管理初始化
 */
var AirSensor = {
    id: "AirSensorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AirSensor.initColumn = function () {
    return [
        {field: 'selectItem', checkbox:true},
            {title: '唯一标识', field: 'id', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '编号', field: 'code', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '传感器名称', field: 'tName', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '排序', field: 'sortCode', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '所属气象站', field: 'stationName', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '传感器类型', field: 'type', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '产品型号', field: 'sensorModel', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '寄存器地址', field: 'rtuId', visible: true, align: 'center', valign: 'middle',sortable: true},
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
            {title: '检测单位', field: 'unit', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '传感器图标', field: 'icon', visible: true, align: 'center', valign: 'middle',sortable: true,
            	formatter:function(value,row,index){
            		return '<i class="ace-icon fa '+value+' bigger-130" style="font-size:130% !important"></i>';
            	}	
            },
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle',sortable: true,
            	formatter:function(value,row,index){
            		if(value){
            			return value.substring(0,5)+'...';
            		}
            	}	
            }
    ];
};

/**
 * 检查是否选中
 */
AirSensor.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else if(selected.length>1){
    	Feng.info('只能选择一条记录!');
    	return false;
    }else{
    	 AirSensor.seItem = selected[0];
         return true;
    }
};

/**
 * 点击添加传感器管理
 */
AirSensor.openAddAirSensor = function () {
    var index = layer.open({
        type: 2,
        title: '添加传感器',
        area: ['950px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/airSensor/airSensor_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看传感器管理详情
 */
AirSensor.openAirSensorDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改传感器',
            area: ['950px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/airSensor/airSensor_update/' + AirSensor.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除传感器管理
 */
AirSensor.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/airSensor/delete", function (data) {
            Feng.success("删除成功!");
            AirSensor.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("airSensorId",this.seItem.id);
        ajax.start();
    }
};

AirSensor.layerDetail = function(){
	if(this.check()){
		var index = layer.open({
	        type: 2,
	        title: '传感器详情',
	        area: ['950px', '520px'], //宽高
	        fix: false, //不固定
	        maxmin: true,
	        content: Feng.ctxPath + '/airSensor/layerdetail/' + AirSensor.seItem.id
	    });
	    this.layerIndex = index;
	}
}

AirSensor.checkOnline = function(){
	var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请至少选择一条记录！");
        return false;
    }else{
    	 var ids=[];
        for(var i=0;i<selected.length;i++){
        	console.log(selected[i].id);
        	ids.push(selected[i].id);
        }
//        console.log(ids);
        
        var url=Feng.ctxPath+'/airSensor/checkOnline';
        layer.msg('指令发送成功,请稍后.', {
        	  icon: 16,
        	  time: 1000000, //2秒关闭（如果不配置，默认是3秒）
        	  shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(url,{ids : ids.join(';')},function(result){
        	
        	if(result.code=='000000'){
    			 var index=layer.alert('选中的设备都在线!',{icon:1},function(){
    				 	layer.close(index);
        				AirSensor.table.refresh();
        			});
    		 }else{
    			var index=layer.alert(result.msg,{icon:5},function(){
    				layer.close(index);
    				AirSensor.table.refresh();
    			});
    		 }
        });
    }
}


/**
 * 查询传感器管理列表
 */
AirSensor.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AirSensor.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AirSensor.initColumn();
    var table = new BSTable(AirSensor.id, "/airSensor/list", defaultColunms);
    table.setPaginationType("server");
    AirSensor.table = table.init();
});
