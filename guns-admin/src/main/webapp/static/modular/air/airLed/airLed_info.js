/**
 * 初始化LED终端详情对话框
 */
var AirLedInfoDlg = {
    airLedInfoData : {},
    validateFields : {
		tName: {
	        validators: {
	            notEmpty: {
	                message: '名称不能为空'
	            }
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
	    stationId: {
	        validators: {
	            notEmpty: {
	                message: '所属气象站不能为空'
	            }
	        }
	    },
	    type: {
	        validators: {
	            notEmpty: {
	                message: 'LED类型不能为空'
	            }
	        }
	    },
	    unitCode: {
	        validators: {
	            notEmpty: {
	                message: '控制卡编号不能为空'
	            }
	        }
	    },
	    unitType: {
	        validators: {
	            notEmpty: {
	                message: '控制卡型号不能为空'
	            }
	        }
	    },
	    
	    screenWidth: {
	        validators: {
	            notEmpty: {
	                message: '屏幕宽度不能为空'
	            },
	            regexp:{
	            	regexp:/^\d+$/,
	            	message:'只能输入整数'
	            }
	        }
	    },
	    screenHeight: {
	        validators: {
	            notEmpty: {
	                message: '屏幕高度不能为空'
	            },
	            regexp:{
	            	regexp:/^\d+$/,
	            	message:'只能输入整数'
	            }
	        }
	    },
	    controlMode: {
	        validators: {
	            notEmpty: {
	                message: '实时发布不能为空'
	            }
	        }
	    },
	    remark: {
	        validators: {
	            notEmpty: {
	                message: '备注不能为空'
	            }
	        }
	    }	   
	    
	}
};

/**
 * 清除数据
 */
AirLedInfoDlg.clearData = function() {
    this.airLedInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AirLedInfoDlg.set = function(key, val) {
    this.airLedInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AirLedInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AirLedInfoDlg.close = function() {
    parent.layer.close(window.parent.AirLed.layerIndex);
}

/**
 * 收集数据
 */
AirLedInfoDlg.collectData = function() {
    this
    .set('id')
    .set('code')
    .set('tName')
    .set('sortCode')
    .set('valid')
    .set('type')
    .set('stationId')
    .set('status')
    .set('ipAddr')
    .set('port')
    .set('unitCode')
    .set('unitType')
    .set('screenHeight')
    .set('screenWidth')
    .set('screenStatus')
    .set('brightness')
    .set('controlMode')
    .set('createBy')
    .set('createTime')
    .set('updateBy')
    .set('updateTime')
    .set('remark');
}

/**
 * 验证数据是否为空
 */
AirLedInfoDlg.validate = function () {
    $('#airLedForm').data("bootstrapValidator").resetForm();
    $('#airLedForm').bootstrapValidator('validate');
    return $("#airLedForm").data('bootstrapValidator').isValid();
};


/**
 * 提交添加
 */
AirLedInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    
    if(!this.validate()){
    	return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/airLed/add", function(data){
        Feng.success("添加成功!");
        window.parent.AirLed.table.refresh();
        AirLedInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.airLedInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AirLedInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if(!this.validate()){
    	return;
    }
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/airLed/update", function(data){
        Feng.success("修改成功!");
        window.parent.AirLed.table.refresh();
        AirLedInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.airLedInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("airLedForm", AirLedInfoDlg.validateFields);
});
