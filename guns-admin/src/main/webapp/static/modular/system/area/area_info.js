/**
 * 初始化区域详情对话框
 */
var AreaInfoDlg = {
    areaInfoData : {},
    zTreeInstance:null,
	validateFields: {
		    code: {
		        validators: {
		            notEmpty: {
		                message: '区域编码不能为空'
		            },
		            numeric:{
		            	message: '只能填写数字'
		            }
		        }
		    },
		    name: {
		        validators: {
		            notEmpty: {
		                message: '区域名称不能为空'
		            }
		        }
		    },
		    type: {
		        validators: {
		            notEmpty: {
		                message: '区域类型不能为空'
		            }
		        }
		    },
		    pName: {
		        validators: {
		            notEmpty: {
		                message: '上级区域不能为空'
		            }
		        }
		    },
		    remarks: {
		        validators: {
		            notEmpty: {
		                message: '区域描述不能为空'
		            }
		        }
		    }
		    
   
    
    
	}

};

/**
 * 清除数据
 */
AreaInfoDlg.clearData = function() {
    this.areaInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AreaInfoDlg.set = function(key, val) {
    this.areaInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AreaInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AreaInfoDlg.close = function() {
    parent.layer.close(window.parent.Area.layerIndex);
}

/**
 * 显示部门选择的树
 *
 * @returns
 */
AreaInfoDlg.showAreaSelectTree = function() {
    var pName = $("#pName");
    var pNameOffset = $("#pName").offset();
    $("#parentAreaMenu").css({
        left : pNameOffset.left + "px",
        top : pNameOffset.top + pName.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

/**
 * 隐藏部门选择的树
 */
AreaInfoDlg.hideAreaSelectTree = function() {
    $("#parentAreaMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentAreaMenu" || $(
            event.target).parents("#parentAreaMenu").length > 0)) {
    	AreaInfoDlg.hideAreaSelectTree();
    }
}


/**
 * 点击区域ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
AreaInfoDlg.onClickArea = function(e, treeId, treeNode) {
    $("#pName").attr("value", AreaInfoDlg.zTreeInstance.getSelectedVal());
    $("#parentId").attr("value", treeNode.id);
}

/**
 * 收集数据
 */
AreaInfoDlg.collectData = function() {
    this
    .set('id')
    .set('parentId')
    .set('parentIds')
    .set('code')
    .set('name')
    .set('type')
    .set('createBy')
    .set('createDate')
    .set('updateBy')
    .set('updateDate')
    .set('remarks')
    .set('delFlag')
    .set('icon');
}

/**
 * 验证数据是否为空
 */
AreaInfoDlg.validate = function () {
    $('#areaInfoForm').data("bootstrapValidator").resetForm();
    $('#areaInfoForm').bootstrapValidator('validate');
    return $("#areaInfoForm").data('bootstrapValidator').isValid();
};


/**
 * 提交添加
 */
AreaInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    
    if(!this.validate()){
    	return;
    }
    this.areaInfoData['icon']=$('#icon-input').val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/area/add", function(data){
//        Feng.success("添加成功!");parent.location.reload();
//        window.parent.Area.table.refresh();
//        AreaInfoDlg.close();
    	parent.layer.msg(data.message, {icon: 1},function(){
    		parent.location.reload();
    	});
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.areaInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AreaInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if(!this.validate()){
    	return;
    }
    this.areaInfoData['icon']=$('#icon-input').val();
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/area/update", function(data){
      //  Feng.success("修改成功!");
    	parent.layer.msg(data.message, {icon: 1},function(){
    		parent.location.reload();
    	});
        
//        window.parent.Area.table.refresh();
//        AreaInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.areaInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("areaInfoForm", AreaInfoDlg.validateFields);
	
	var ztree = new $ZTree("parentAreaMenuTree", "/area/ztree");
    ztree.bindOnClick(AreaInfoDlg.onClickArea);
    ztree.init();
    AreaInfoDlg.zTreeInstance = ztree;
    
    $('#type').val($('#typeValue').val());
});
