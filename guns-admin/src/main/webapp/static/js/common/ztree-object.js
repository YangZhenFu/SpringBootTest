/**
 * ztree插件的封装
 */
(function() {

	var $ZTree = function(id, url) {
		this.id = id;
		this.url = url;
		this.onClick = null;
		this.settings = null;
		this.ondblclick=null;
		this.onNodeCreated=function(e, treeId, treeNode){
			if(!treeNode.icon){
				$('#'+treeNode.tId+'_ico').attr({'background-image':'none!important',
												 'font-family':'FontAwesome',
												 'height':'auto','font-size':'130%',
												 'text-align':'center','vertical-align':'middle'});
			}
		};
		this.beforeClick=null;
	};

	$ZTree.prototype = {
		/**
		 * 初始化ztree的设置
		 */
		initSetting : function() {
			var settings = {
				view : {
					dblClickExpand : true,
					selectedMulti : false,
					fontCss:function(treeId, treeNode) {
						return (!!treeNode.highlight) ? {"font-weight":"bold","color":"red"} : {"font-weight":"normal","color":"#333"};
					}
				},
				data : {simpleData : {enable : true}},
				callback : {
					onClick : this.onClick,
					onDblClick:this.ondblclick,
					onNodeCreated:this.onNodeCreated,
					beforeClick: this.beforeClick,
					//beforeExpand: beforeExpand,
					//onExpand: onExpand
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
		
		bindOnBeforeClick: function(func){
			this.beforeClick = func;
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
		},
		
		/**
		 * 节点搜索
		 */
		searchNodes : function(id){
			var pullDownTreeCurTree = $.fn.zTree.getZTreeObj(this.id);
		    
		    var pullDownTreeList = [];
			$("#"+id).bind("change keydown cut input propertychange", searchNode);
			
			function searchNode() {
				// 取得输入的关键字的值
				var value = $.trim($("#"+id).get(0).value);
				
				// 按名字查询
				var keyType = "name";
				
				
				// 如果要查空字串，就退出不查了。
				if (value === "") {
					for(var i=0, l=pullDownTreeList.length; i<l; i++) {
						pullDownTreeList[i].highlight = false;				
						pullDownTreeCurTree.updateNode(pullDownTreeList[i]);
					}
					return;
				}
				updateNodes(false);
				pullDownTreeList = pullDownTreeCurTree.getNodesByParamFuzzy(keyType, value);
				updateNodes(true);
			};
			function updateNodes(highlight) {
				for(var i=0, l=pullDownTreeList.length; i<l; i++) {
					pullDownTreeList[i].highlight = highlight;				
					pullDownTreeCurTree.updateNode(pullDownTreeList[i]);
					pullDownTreeCurTree.expandNode(pullDownTreeList[i].getParentNode(), true, false, false);
				}
			};   
			
		}
		
	};
	
	
	

	var curExpandNode = null;
	function beforeExpand(treeId, treeNode) {
		var pNode = curExpandNode ? curExpandNode.getParentNode():null;
		var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
		var zTree = $.fn.zTree.getZTreeObj("areaTree");
		for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
			if (treeNode !== treeNodeP.children[i]) {
				zTree.expandNode(treeNodeP.children[i], false);
			}
		}
		while (pNode) {
			if (pNode === treeNode) {
				break;
			}
			pNode = pNode.getParentNode();
		}
		if (!pNode) {
			singlePath(treeNode);
		}

	}
	
	function singlePath(newNode) {
		if (newNode === curExpandNode) return;

        var zTree = $.fn.zTree.getZTreeObj("areaTree"),
                rootNodes, tmpRoot, tmpTId, i, j, n;

        if (!curExpandNode) {
            tmpRoot = newNode;
            while (tmpRoot) {
                tmpTId = tmpRoot.tId;
                tmpRoot = tmpRoot.getParentNode();
            }
            rootNodes = zTree.getNodes();
            for (i=0, j=rootNodes.length; i<j; i++) {
                n = rootNodes[i];
                if (n.tId != tmpTId) {
                    zTree.expandNode(n, false);
                }
            }
        } else if (curExpandNode && curExpandNode.open) {
			if (newNode.parentTId === curExpandNode.parentTId) {
				zTree.expandNode(curExpandNode, false);
			} else {
				var newParents = [];
				while (newNode) {
					newNode = newNode.getParentNode();
					if (newNode === curExpandNode) {
						newParents = null;
						break;
					} else if (newNode) {
						newParents.push(newNode);
					}
				}
				if (newParents!=null) {
					var oldNode = curExpandNode;
					var oldParents = [];
					while (oldNode) {
						oldNode = oldNode.getParentNode();
						if (oldNode) {
							oldParents.push(oldNode);
						}
					}
					if (newParents.length>0) {
						zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
					} else {
						zTree.expandNode(oldParents[oldParents.length-1], false);
					}
				}
			}
		}
		curExpandNode = newNode;
	}

	function onExpand(event, treeId, treeNode) {
		curExpandNode = treeNode;
	}
	
	
	
	

	window.$ZTree = $ZTree;

}());