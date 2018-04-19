/**
 * 传感器类型管理初始化
 */
var SensorType = {
    id: "SensorTypeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SensorType.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '唯一标识', field: 'id', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '名称', field: 'tName', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '排序', field: 'sortCode', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '创建者', field: 'createBy', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle',sortable: true},
          
    ];
};

/**
 * 检查是否选中
 */
SensorType.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SensorType.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加传感器类型
 */
SensorType.openAddSensorType = function () {
    var index = layer.open({
        type: 2,
        title: '添加传感器类型',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sensorType/sensorType_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看传感器类型详情
 */
SensorType.openSensorTypeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '传感器类型详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sensorType/sensorType_update/' + SensorType.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除传感器类型
 */
SensorType.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/sensorType/delete", function (data) {
            Feng.success("删除成功!");
            SensorType.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("sensorTypeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询传感器类型列表
 */
SensorType.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    SensorType.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = SensorType.initColumn();
    var table = new BSTable(SensorType.id, "/sensorType/list", defaultColunms);
    table.setPaginationType("server");
    SensorType.table = table.init();
});
