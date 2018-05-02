/**
 * 设备预警参数管理初始化
 */
var AirSensorWarnParam = {
    id: "AirSensorWarnParamTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AirSensorWarnParam.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一标识', field: 'id', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '编号', field: 'code', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '预警名称', field: 'tName', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '顺序位', field: 'sortCode', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '设备名称', field: 'sensorName', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '表达式', field: 'expression', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '阈值', field: 'threshold', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '控制方式', field: 'controlMode', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '预警间隔（单位：分钟）', field: 'warnInterval', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '异常报警时间（单位：分钟）', field: 'alarmTime', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '状态', field: 'valid', visible: true, align: 'center', valign: 'middle',sortable: true,
            	formatter:function(value,row,index){
            		return '<a href="javascript:void(0);" onclick="changeItemStatus(this,'+row.id+');">'+value+'</a>';
            	}
            }
            
           
    ];
};

/**
 * 更改状态
 * @param obj
 * @param id
 */
function changeItemStatus(obj,id){
	var valid=$(obj).html();
	if(valid=='启用'){
		valid='1';
	}else{
		valid='0';
	}
	
	$.post(Feng.ctxPath + "/airSensorWarnParam/update",{id:id,valid:valid},function(data){
		AirSensorWarnParam.table.refresh();
	});
}


/**
 * 检查是否选中
 */
AirSensorWarnParam.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AirSensorWarnParam.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加设备预警参数
 */
AirSensorWarnParam.openAddAirSensorWarnParam = function () {
    var index = layer.open({
        type: 2,
        title: '添加设备预警',
        area: ['900px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/airSensorWarnParam/airSensorWarnParam_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看设备预警参数详情
 */
AirSensorWarnParam.openAirSensorWarnParamDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改设备预警',
            area: ['900px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/airSensorWarnParam/airSensorWarnParam_update/' + AirSensorWarnParam.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除设备预警参数
 */
AirSensorWarnParam.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/airSensorWarnParam/delete", function (data) {
            Feng.success("删除成功!");
            AirSensorWarnParam.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("airSensorWarnParamId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询设备预警参数列表
 */
AirSensorWarnParam.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AirSensorWarnParam.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AirSensorWarnParam.initColumn();
    var table = new BSTable(AirSensorWarnParam.id, "/airSensorWarnParam/list", defaultColunms);
    table.setPaginationType("server");
    AirSensorWarnParam.table = table.init();
});
