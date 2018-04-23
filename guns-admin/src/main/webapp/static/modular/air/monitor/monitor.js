var Monitor = {
    id: "monitorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    areaId: 0
};


Monitor.OnClickArea = function(e, treeId, treeNode){
	Monitor.areaId=treeNode.id;
	console.log(this.areaId);
	//Area.search();
}




$(function(){
	 var myZTree = new $MyZTree('areaTree',Feng.ctxPath+'/area/ztree');
	    myZTree.bindOnClick(Monitor.OnClickArea);
	    myZTree.init();
});