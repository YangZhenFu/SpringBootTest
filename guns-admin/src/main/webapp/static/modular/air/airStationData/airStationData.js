var AirData = {
    id: "AirDataTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    areaId: 0
};


/**
 * 初始化表格的列
 */
AirData.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '编号', field: 'id', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '气象站名称', field: 'stationName', visible: true, align: 'center', valign: 'middle', sortable: false},
            {title: '接收时间', field: 'heartbeatTime', visible: true, align: 'center', valign: 'middle', sortable: true},
            {title: '检测数据', field: 'dataInfo', visible: true, align: 'center', valign: 'middle', sortable: false},
            {title: '检测类型', field: 'dataType', visible: true, align: 'center', valign: 'middle', sortable: false},
    ];
};

/**
 * 检查是否选中
 */
AirData.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	AirData.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
AirData.formParams = function() {
    var queryData = {};
    queryData['areaId'] = AirData.areaId;
    queryData['condition'] = $("#condition").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    return queryData;
}

/**
 * 重置查询条件
 */
AirData.resetSearch = function() {
	$("#condition").val('');$("#beginTime").val('');$("#endTime").val('');
	this.areaId=0;
	var treeObj = $.fn.zTree.getZTreeObj("areaTree");
	var nodes = treeObj.getNodes();
	if (nodes.length>0) {
		treeObj.selectNode(nodes[0]);
	}
	AirData.search();
}

/**
 * 查询区域列表
 */
AirData.search = function () {
	console.log(this.formParams());
	AirData.table.refresh({query: AirData.formParams()});
};


/**
 * 导出Excel
 */
AirData.exportExcel = function() {
	//先根据查询条件刷新表格
	AirData.table.refresh({query: this.formParams()});
	var url=Feng.ctxPath+'/airStationData/export';
	//调用表格导出插件
	Angel.downloadFile(this.formParams(),url)
}


AirData.OnClickArea = function(e, treeId, treeNode){
	var zTree = $.fn.zTree.getZTreeObj("areaTree");
	zTree.expandNode(treeNode, null, null, null, true);
	AirData.areaId=treeNode.id;
	if((treeNode.id+'').length==17){
		AirData.search();
	}
	

}



$(function(){
	 var myZTree = new $ZTree('areaTree',Feng.ctxPath+'/air/station/ztree');
	    myZTree.bindOnClick(AirData.OnClickArea);
	    myZTree.init();
	    myZTree.searchNodes('search_input');
	     
	    var defaultColunms = AirData.initColumn();
	    var table = new BSTable(AirData.id, "/airStationData/list", defaultColunms);
	    table.setPaginationType("server");
	    AirData.table = table.init();
	 
});