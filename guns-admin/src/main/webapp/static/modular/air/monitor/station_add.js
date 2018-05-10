/**
 * 初始化气象站详情对话框
 */
var StationInfoDlg = {
    stationInfoData : {},
    zTreeInstance : null,
    validateFields: {
	    tName: {
	        validators: {
	            notEmpty: {
	                message: '名称不能为空'
	            }
//	            ,remote: {
//                    type: 'POST',
//                    //以get方式调用接口根据接口返回的valid,true为通过false为未通过
//                    url: Feng.ctxPath+'/air/station/checkName',
//                    message: '此名称太抢手了，请换一个!',
//                    delay: 500
//                }
	        }
	    },
	    sortCode: {
	        validators: {
	            notEmpty: {
	                message: '排序不能为空'
	            },
	            regexp:{
	            	regexp:/^\d+$/,
	            	message:'只能输入整数'
	            }
	        }
	    },
	    connMethod: {
	    	validators: {
	            notEmpty: {
	                message: '通讯方式不能为空'
	            }
	        }
	    },
	    ipAddr: {
	    	validators: {
	            notEmpty: {
	                message: 'IP地址不能为空'
	            },
	            ip:{
	            	message:'请输入正确的IP地址'
	            }
	         
	        }
	    },
	    port: {
	    	validators: {
	            notEmpty: {
	                message: '端口号不能为空'
	            },
	            regexp:{
	            	regexp:/^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,
	            	message:'请输入正确的端口号'
	            }
	        }
	    },
	    installer: {
	    	validators: {
	            notEmpty: {
	                message: '安装者不能为空'
	            }
	        }
	    },
	    installTimes: {
	    	validators: {
	            notEmpty: {
	                message: '安装时间不能为空'
	            },
	            date:{
	            	format:'YYYY-MM-DD',
	            	message:'日期格式不正确'
	            }
	        }
	    },
	    remark: {
	    	validators: {
	            notEmpty: {
	                message: '备注不能为空'
	            }
	        }
	    },
	    detailAddress: {
	    	validators: {
	            notEmpty: {
	                message: '详细地址不能为空'
	            }
	        }
	    },
	    offx: {
	    	validators: {
	            notEmpty: {
	                message: '坐标x不能为空'
	            },
	            numeric:{
	            	message: '只能填写数字'
	            }
	            
	            
	        }
	    },
	    offy: {
	    	validators: {
	            notEmpty: {
	                message: '坐标y不能为空'
	            },
	            numeric:{
	            	message: '只能填写数字'
	            }
	        }
	    },
	    longitude: {
	    	validators: {
	            notEmpty: {
	                message: '经度不能为空'
	            },
	            regexp:{
	            	regexp:/^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/,
	            	message:'只能填写数字'
	            }
	        }
	    },
	    latitude: {
	    	validators: {
	            notEmpty: {
	                message: '纬度不能为空'
	            },
	            regexp:{
	            	regexp:/^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/,
	            	message:'只能填写数字'
	            }
	        }
	    },
	    areaId: {
	    	validators: {
	            notEmpty: {
	                message: '归属区域不能为空'
	            }
	        }
	    },
	    deptName: {
	    	validators: {
	            notEmpty: {
	                message: '归属部门不能为空'
	            }
	        }
	    },
	    location: {
	    	validators: {
	            notEmpty: {
	                message: '位置分布不能为空'
	            }
	        }
	    },
	    dataUploadInterval: {
	    	validators: {
	            notEmpty: {
	                message: '数据上传间隔不能为空'
	            }
	        }
	    },
	    dataUploadStatus: {
	    	validators: {
	            notEmpty: {
	                message: '数据上传状态不能为空'
	            }
	        }
	    }
	    
	    
	    
	    
	    
    }
};

/**
 * 清除数据
 */
StationInfoDlg.clearData = function() {
    this.stationInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StationInfoDlg.set = function(key, val) {
    this.stationInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StationInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StationInfoDlg.close = function() {
    parent.layer.closeAll();
}

/**
 * 收集数据
 */
StationInfoDlg.collectData = function() {
    this
    .set('id')
    .set('code')
    .set('tName')
    .set('sortCode')
    .set('valid')
    .set('areaId')
    .set('deptId')
    .set('detailAddress')
    .set('offx')
    .set('offy')
    .set('longitude')
    .set('latitude')
    .set('type')
    .set('connMethod')
    .set('status')
    .set('ipAddr')
    .set('port')
    .set('installer')
    .set('installTimes')
    .set('createBy')
    .set('createTime')
    .set('updateBy')
    .set('updateTime')
    .set('location')
    .set('dataUploadInterval')
    .set('dataUploadStatus')
    .set('remark');
}


/**
 * 验证数据是否为空
 */
StationInfoDlg.validate = function () {
    $('#airStationForm').data("bootstrapValidator").resetForm();
    $('#airStationForm').bootstrapValidator('validate');
    return $("#airStationForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
StationInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    
    if(!this.validate()){
    	return;
    }
    
    var areaId = $("#pullDownTreeCurIdone").val();
    
    this.stationInfoData['areaId']=areaId;
    this.stationInfoData['type']=$('#type-choose option:selected').val();
	
	if($.trim(areaId)=="" || areaId == 0){
		layer.tips('请选择归属区域', $('#gsarea'), {
			  tips: [1, '#3595CC'],
			  time: 2000
		});
		return;
	}
	
	var url=Feng.ctxPath+'/area/detail/'+parseInt($.trim(areaId));
	$.get(url,function(result){
		if(result){
			if(result.type!='5'){
				layer.tips('请精确到街道!', $('#gsarea'), {
					  tips: [1, '#3595CC'],
					  time: 2000
				});
				return;
			}else{
				
				//提交信息
			    var ajax = new $ax(Feng.ctxPath + "/air/station/add", function(data){
			    	parent.layer.msg(data.message, {icon: 1},function(){
			    		parent.location.reload();
			    	});
			        
			    },function(data){
			        Feng.error("添加失败!" + data.responseJSON.message + "!");
			    });
			    ajax.set(StationInfoDlg.stationInfoData);
			    ajax.start();
				
			}
			
		}
	});
    
    
}



/**
 * 点击部门input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
StationInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#deptName").attr("value", instance.getSelectedVal());
    $("#deptId").attr("value", treeNode.id);
};

/**
 * 显示部门选择的树
 *
 * @returns
 */
StationInfoDlg.showDeptSelectTree = function () {
    var cityObj = $("#deptName");
    var cityOffset = $("#deptName").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};

/**
 * 显示用户详情部门选择的树
 *
 * @returns
 */
StationInfoDlg.showInfoDeptSelectTree = function () {
    var cityObj = $("#deptName");
    var cityPosition = $("#deptName").position();
    $("#menuContent").css({
        left: cityPosition.left + "px",
        top: cityPosition.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};

/**
 * 隐藏部门选择的树
 */
StationInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};





function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
    	StationInfoDlg.hideDeptSelectTree();
    }
}

$(function() {
	Feng.initValidator("airStationForm", StationInfoDlg.validateFields);
	
	var ztree = new $ZTree("treeDemo", "/dept/tree");
    ztree.bindOnClick(StationInfoDlg.onClickDept);
    ztree.init();
    instance = ztree;
	
});
