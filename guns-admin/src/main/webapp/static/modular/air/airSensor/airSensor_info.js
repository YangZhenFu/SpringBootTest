/**
 * 初始化传感器管理详情对话框
 */
var AirSensorInfoDlg = {
    airSensorInfoData : {},
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
	    
	    
	    stationId: {
	        validators: {
	            notEmpty: {
	                message: '所属气象站不能为空'
	            }
	        }
	    },
	    typeId: {
	        validators: {
	            notEmpty: {
	                message: '传感器类型不能为空'
	            }
	        }
	    },
	    sensorModel: {
	        validators: {
	            notEmpty: {
	                message: '型号不能为空'
	            }
	        }
	    },
	    rtuId: {
	        validators: {
	            notEmpty: {
	                message: '寄存器地址不能为空'
	            },
	            regexp:{
	            	regexp: /^[0-9a-fA-F]{2}$/,
	            	message:'寄存器地址格式不正确'
	            }
	            
	        }
	    },
	    voltage: {
	        validators: {
	            notEmpty: {
	                message: '电压不能为空'
	            },
	            numeric:{
	            	message: '只能填写数字'
	            }
	        }
	    },
	    electricity: {
	        validators: {
	            notEmpty: {
	                message: '电流不能为空'
	            },
	            numeric:{
	            	message: '只能填写数字'
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
	    remark: {
	        validators: {
	            notEmpty: {
	                message: '备注不能为空'
	            }
	        }
	    },
	    unit: {
	        validators: {
	            notEmpty: {
	                message: '检测单位不能为空'
	            }
	        }
	    },
	    command: {
	        validators: {
	            notEmpty: {
	                message: '查询指令不能为空'
	            },
	            regexp:{
	            	regexp: /^[0-9a-fA-F]{16}$/,
	            	message:'指令格式不正确'
	            }
	        }
	    
	    }
	    
	}

};

/**
 * 清除数据
 */
AirSensorInfoDlg.clearData = function() {
    this.airSensorInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AirSensorInfoDlg.set = function(key, val) {
    this.airSensorInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AirSensorInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AirSensorInfoDlg.close = function() {
    parent.layer.close(window.parent.AirSensor.layerIndex);
}

/**
 * 收集数据
 */
AirSensorInfoDlg.collectData = function() {
    this
    .set('id')
    .set('code')
    .set('tName')
    .set('sortCode')
    .set('valid')
    .set('stationId')
    .set('typeId')
    .set('sensorModel')
    .set('rtuId')
    .set('status')
    .set('voltage')
    .set('electricity')
    .set('installer')
    .set('installTimes')
    .set('createBy')
    .set('createTime')
    .set('updateBy')
    .set('updateTime')
    .set('icon')
    .set('unit')
    .set('command')
    .set('remark');
}

/**
 * 验证数据是否为空
 */
AirSensorInfoDlg.validate = function () {
    $('#airSensorForm').data("bootstrapValidator").resetForm();
    $('#airSensorForm').bootstrapValidator('validate');
    return $("#airSensorForm").data('bootstrapValidator').isValid();
};


/**
 * 提交添加
 */
AirSensorInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    
    if(!this.validate()){
    	return;
    }
    
    this.airSensorInfoData['icon']=$('#icon-input').val();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/airSensor/add", function(data){
        Feng.success("添加成功!");
        window.parent.AirSensor.table.refresh();
        AirSensorInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.airSensorInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AirSensorInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    
    if(!this.validate()){
    	return;
    }
    
    this.airSensorInfoData['icon']=$('#icon-input').val();
    

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/airSensor/update", function(data){
        Feng.success("修改成功!");
        window.parent.AirSensor.table.refresh();
        AirSensorInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.airSensorInfoData);
    ajax.start();
}

$(function() {

	Feng.initValidator("airSensorForm", AirSensorInfoDlg.validateFields);
});
