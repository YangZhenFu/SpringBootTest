/**
 * ztree插件的封装
 */
(function() {

	var $MyZTree = function(id, url) {
		this.id = id;
		this.url = url;
		this.onClick = null;
		this.settings = null;
		this.ondblclick=null;
		this.beforeClick = function(treeId, treeNode){
				console.log($MyZTree.id);
				if (treeNode.level == 0 ) {
					var zTree = $.fn.zTree.getZTreeObj($("#" + $MyZTree.id));
					zTree.expandNode(treeNode);
					return false;
				}
				return true;
		}
	};

	$MyZTree.prototype = {
		/**
		 * 初始化ztree的设置
		 */
		initSetting : function() {
			var settings = {
				view : {
					selectedMulti : false,
					showLine: false,
					showIcon: false,
					dblClickExpand: false,
					addDiyDom: addDiyDom
				},
				data : {simpleData : {enable : true}},
				callback : {
					onClick : this.onClick,
					onDblClick:this.ondblclick,
					beforeClick: this.beforeClick
				}
			};
			return settings;
		},

		/**
		 * 手动设置ztree的设置
		 */
		setSettings : function(val) {
			this.settings = val;
		},

		/**
		 * 初始化ztree
		 */
		init : function() {
			var zNodeSeting = null;
			if(this.settings != null){
				zNodeSeting = this.settings;
			}else{
				zNodeSeting = this.initSetting();
			}
			var zNodes = this.loadNodes();
			$.fn.zTree.init($("#" + this.id), zNodeSeting, zNodes);
			
			var treeObj=$('#'+this.id);
			
//			var zTree_Menu = $.fn.zTree.getZTreeObj('#'+this.id);
//			curMenu = zTree_Menu.getNodes()[0].children[0].children[0];
//			zTree_Menu.selectNode(curMenu);
			
			
			//treeObj.hover(function () {
				//if (!treeObj.hasClass("showIcon")) {
					treeObj.addClass("showIcon");
				//}
			//}, function() {
				//treeObj.removeClass("showIcon");
			//});
		},

		/**
		 * 绑定onclick事件
		 */
		bindOnClick : function(func) {
			this.onClick = func;
		},
		/**
		 * 绑定双击事件
		 */
		bindOnDblClick : function(func) {
			this.ondblclick=func;
		},


		/**
		 * 加载节点
		 */
		loadNodes : function() {
			var zNodes = null;
			var ajax = new $ax(Feng.ctxPath + this.url, function(data) {
				zNodes = data;
			}, function(data) {
				Feng.error("加载ztree信息失败!");
			});
			ajax.start();
			return zNodes;
		},

		/**
		 * 获取选中的值
		 */
		getSelectedVal : function(){
			var zTree = $.fn.zTree.getZTreeObj(this.id);
			var nodes = zTree.getSelectedNodes();
			return nodes[0].name;
		}
		
		
	};
	
	
	function addDiyDom(treeId, treeNode) {
		
		console.log(treeId);
		var spaceWidth = 5;
		var switchObj = $("#" + treeNode.tId + "_switch"),
		icoObj = $("#" + treeNode.tId + "_ico");
		switchObj.remove();
		icoObj.before(switchObj);

		if (treeNode.level > 1) {
			var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
			switchObj.before(spaceStr);
		}
	}

	
	

	window.$MyZTree = $MyZTree;

}());