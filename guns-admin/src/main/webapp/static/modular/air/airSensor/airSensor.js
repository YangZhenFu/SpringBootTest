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
        {field: 'selectItem', radio: true},
            {title: '唯一标识', field: 'id', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '编号', field: 'code', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '传感器名称', field: 'tName', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '排序', field: 'sortCode', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '所属气象站', field: 'stationName', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '传感器类型', field: 'type', visible: true, align: 'center', valign: 'middle',sortable: false},
            {title: '产品型号', field: 'sensorModel', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '寄存器地址', field: 'rtuId', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle',sortable: true},
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
