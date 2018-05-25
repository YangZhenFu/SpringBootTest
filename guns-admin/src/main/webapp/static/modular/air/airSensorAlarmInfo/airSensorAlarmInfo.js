/**
 * 传感器告警信息管理初始化
 */
var AirSensorAlarmInfo = {
    id: "AirSensorAlarmInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AirSensorAlarmInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一标识', field: 'id', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '编号', field: 'code', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '告警名称', field: 'tName', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '传感器名称', field: 'sensorName', visible: true, align: 'center', valign: 'middle'},
            {title: '告警类型', field: 'alarmType', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '告警信息', field: 'alarmInfo', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '告警时间', field: 'alarmTime', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '处理状态', field: 'handleState', visible: true, align: 'center', valign: 'middle',sortable: false,
            	formatter:function(value,row,index){
            		var status;
            		switch(value){
            			case '已恢复':
            				status='label-info';
            				break;
            			case '未恢复':
            				status='label-warning';
            				break;
            			default : 
            				status='';
            				break;
            		}
            		return '<span class="label '+status+'">'+value+'</span>';
            	}
            }
    ];
};

/**
 * 检查是否选中
 */
AirSensorAlarmInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AirSensorAlarmInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加传感器告警信息
 */
AirSensorAlarmInfo.openAddAirSensorAlarmInfo = function () {
    var index = layer.open({
        type: 2,
        title: '告警信息添加',
        area: ['900px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/airSensorAlarmInfo/airSensorAlarmInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看传感器告警信息详情
 */
AirSensorAlarmInfo.openAirSensorAlarmInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '告警信息修改',
            area: ['900px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/airSensorAlarmInfo/airSensorAlarmInfo_update/' + AirSensorAlarmInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除传感器告警信息
 */
AirSensorAlarmInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/airSensorAlarmInfo/delete", function (data) {
            Feng.success("删除成功!");
            AirSensorAlarmInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("airSensorAlarmInfoId",this.seItem.id);
        ajax.start();
    }
};


AirSensorAlarmInfo.openAlarmHandle = function(){
	if(this.check()){
		var index = layer.open({
	        type: 2,
	        title: '异常处理',
	        area: ['500px', '420px'], //宽高
	        fix: false, //不固定
	        maxmin: true,
	        content: Feng.ctxPath + '/airSensorAlarmInfo/layerHandle/'+ AirSensorAlarmInfo.seItem.id
	    });
	    this.layerIndex = index;
	}
}


AirSensorAlarmInfo.layerDetail = function(){
	if(this.check()){
		var index = layer.open({
	        type: 2,
	        title: '告警信息详情',
	        area: ['900px', '520px'], //宽高
	        fix: false, //不固定
	        maxmin: true,
	        content: Feng.ctxPath + '/airSensorAlarmInfo/layerdetail/' + AirSensorAlarmInfo.seItem.id
	    });
	    this.layerIndex = index;
	}
}


/**
 * 查询传感器告警信息列表
 */
AirSensorAlarmInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AirSensorAlarmInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AirSensorAlarmInfo.initColumn();
    var table = new BSTable(AirSensorAlarmInfo.id, "/airSensorAlarmInfo/list", defaultColunms);
    table.setPaginationType("server");
    AirSensorAlarmInfo.table = table.init();
});
