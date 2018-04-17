/**
 * 区域管理初始化
 */
var Area = {
    id: "AreaTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    areaId: 0
};

/**
 * 初始化表格的列
 */
Area.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '编号', field: 'id', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '区域编码', field: 'code', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '区域名称', field: 'name', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '区域类型', field: 'type', visible: true, align: 'center', valign: 'middle', sortable: true,
            	formatter:function(value,row,index){
            		switch(value){
            			case '1':
            				return '国家';
            			case '2':
            				return '省份、直辖市';
            			case '3':
            				return '地市';
            			case '4':
            				return '区县';
            			case '5':
            				return '街道';
            		}
            	}
            },
            {title: '备注信息', field: 'remarks', visible: true, align: 'center', valign: 'middle', sortable: true,
            	formatter:function(value,row,index){
            		if(value){
            			return value.substring(0,5)+'...';
            		}
            	}
            },
            {title: '图标', field: 'icon', visible: true, align: 'center', valign: 'middle', sortable: true,
            	formatter:function(value,row,index){
            		return '<i class="ace-icon fa '+value+' bigger-130" style="font-size:130% !important"></i>';
            	}
            }
    ];
};

/**
 * 检查是否选中
 */
Area.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Area.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加区域
 */
Area.openAddArea = function () {
    var index = layer.open({
        type: 2,
        title: '添加区域',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/area/area_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看区域详情
 */
Area.openAreaDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '区域详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/area/area_update/' + Area.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除区域
 */
Area.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/area/delete", function (data) {
//            Feng.success("删除成功!");
//            Area.table.refresh();
        	layer.msg('删除成功!', {icon: 1},function(){
        		location.reload();
        	});
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("areaId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Area.formParams = function() {
    var queryData = {};
    queryData['areaId'] = Area.areaId;
    queryData['name'] = $("#name").val();

    return queryData;
}

/**
 * 重置查询条件
 */
Area.resetSearch = function() {
	$("#name").val('');
	Area.search();
}

/**
 * 查询区域列表
 */
Area.search = function () {
//    var queryData = {};
//    queryData['condition'] = $("#condition").val();
    Area.table.refresh({query: Area.formParams()});
};

Area.OnClickArea = function(e, treeId, treeNode){
	Area.areaId=treeNode.id;
	Area.search();
}

$(function () {
    var defaultColunms = Area.initColumn();
    var table = new BSTable(Area.id, "/area/list", defaultColunms);
    table.setPaginationType("server");
    Area.table = table.init();
    
    var zTree = new $ZTree('areaTree',Feng.ctxPath+'/area/ztree');
    zTree.bindOnClick(Area.OnClickArea);
    zTree.init();
});
