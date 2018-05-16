/**
 * 气象站管理初始化
 */
var Station = {
    id: "StationTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Station.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '编号', field: 'code', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '名称', field: 'tName', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '排序', field: 'sortCode', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '归属区域', field: 'areaName', visible: true, align: 'center', valign: 'middle', sortable: false},
            {title: '归属部门', field: 'deptName', visible: true, align: 'center', valign: 'middle', sortable: false},
            {title: '类型', field: 'type', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '通讯方式', field: 'connMethod', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: 'ip地址', field: 'ipAddr', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '端口号', field: 'port', visible: true, align: 'center', valign: 'middle', sortable: true},
            
            {title: '数据上传间隔（分钟）', field: 'dataUploadInterval', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '数据上传状态', field: 'dataUploadStatus', visible: true, align: 'center', valign: 'middle', sortable: true,
            	formatter:function(value,row,index){
            		if(value){
            			return '<a href="javascript:void(0);" title="点击更改状态" onclick="changeItemStatus(this,'+row.id+');">停止</a>';
            		}else{
            			return '<a href="javascript:void(0);" title="点击更改状态" onclick="changeItemStatus(this,'+row.id+');">启动</a>';
            		}
            	}
            },
            
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle', sortable: true,
            	formatter:function(value,row,index){
            		if(value){
            			return value.substring(0,5)+'...';
            		}
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
	var status=$(obj).html();
	if(status=='启动'){
		status='1';
	}else{
		status='0';
	}
	
	$.post(Feng.ctxPath + "/air/station/update",{id:id,dataUploadStatus:status},function(data){
		Station.table.refresh();
	});
}


/**
 * 检查是否选中
 */
Station.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Station.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加气象站
 */
Station.openAddStation = function () {
    var index = layer.open({
        type: 2,
        title: '添加气象站',
        area: ['1000px', '750px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/air/station/station_add'
    });
    this.layerIndex = index;
};

/**
 * 修改气象站信息
 */
Station.openStationDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改气象站',
            area: ['1000px', '750px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/air/station/station_update/' + Station.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 查看详情
 */
Station.layerStationDetail = function(){
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '查看详情',
            area: ['920px', '750px'], //宽高
            fix: false, //不固定
            maxmin: true,
            shadeClose:true,
            content: Feng.ctxPath + '/air/station/layerdetail/' + Station.seItem.id
        });
        this.layerIndex = index;
    }
}



/**
 * 删除气象站
 */
Station.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/air/station/delete", function (data) {
            Feng.success("删除成功!");
            Station.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("stationId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询气象站列表
 */
Station.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Station.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Station.initColumn();
    var table = new BSTable(Station.id, "/air/station/list", defaultColunms);
    table.setPaginationType("server");
    Station.table = table.init();
});
