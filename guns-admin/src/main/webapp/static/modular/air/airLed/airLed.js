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
        {field: 'selectItem', radio: true},
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
            {title: '实时发布', field: 'controlMode', visible: true, align: 'center', valign: 'middle',sortable: true},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle',sortable: true,
            	formatter:function(value,row,index){
            		if(value){
            			return value.substring(0,3)+'...';
            		}
            	}	
            }
    ];
};

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
 * 点击添加LED终端
 */
AirLed.openAddAirLed = function () {
    var index = layer.open({
        type: 2,
        title: '添加LED终端',
        area: ['900px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/airLed/airLed_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看LED终端详情
 */
AirLed.openAirLedDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改LED终端',
            area: ['900px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/airLed/airLed_update/' + AirLed.seItem.id
        });
        this.layerIndex = index;
    }
};


AirLed.layerLedDetail = function(){
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'LED终端详情',
            area: ['900px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            shadeClose:true,
            content: Feng.ctxPath + '/airLed/layerDetail/' + AirLed.seItem.id
        });
        this.layerIndex = index;
    }
}


/**
 * 删除LED终端
 */
AirLed.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/airLed/delete", function (data) {
            Feng.success("删除成功!");
            AirLed.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("airLedId",this.seItem.id);
        ajax.start();
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
