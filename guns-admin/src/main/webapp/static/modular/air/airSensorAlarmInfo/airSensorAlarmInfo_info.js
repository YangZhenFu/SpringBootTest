/**
 * 初始化传感器告警信息详情对话框
 */
var AirSensorAlarmInfoInfoDlg = {
    airSensorAlarmInfoInfoData : {},
    validateFields : {
		tName: {
	        validators: {
	            notEmpty: {
	                message: '告警名称不能为空'
	            }
	        }
	    },
	    sortCode: {
	        validators: {
	            notEmpty: {
	                message: '序号不能为空'
	            },
	            regexp:{
	            	regexp:/^\d+$/,
	            	message:'只能输入整数'
	            }
	        }
	    },
	    sensorId: {
	        validators: {
	            notEmpty: {
	                message: '所属传感器不能为空'
	            }
	        }
	    },
	    alarmType: {
	        validators: {
	            notEmpty: {
	                message: '告警类型不能为空'
	            }
	        }
	    },
	    alarmInfo: {
	        validators: {
	            notEmpty: {
	                message: '告警信息不能为空'
	            }
	        }
	    },
	    alarmTimes: {
	        validators: {
	            notEmpty: {
	                message: '告警时间不能为空'
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
	    handleContent: {
	        validators: {
	            notEmpty: {
	                message: '处理内容不能为空'
	            }
	            
	        }
	    }
	 
	    
	}
};

/**
 * 清除数据
 */
AirSensorAlarmInfoInfoDlg.clearData = function() {
    this.airSensorAlarmInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AirSensorAlarmInfoInfoDlg.set = function(key, val) {
    this.airSensorAlarmInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AirSensorAlarmInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AirSensorAlarmInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.AirSensorAlarmInfo.layerIndex);
}

/**
 * 验证数据是否为空
 */
AirSensorAlarmInfoInfoDlg.validate = function () {
    $('#alarmInfoForm').data("bootstrapValidator").resetForm();
    $('#alarmInfoForm').bootstrapValidator('validate');
    return $("#alarmInfoForm").data('bootstrapValidator').isValid();
};


/**
 * 收集数据
 */
AirSensorAlarmInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('code')
    .set('tName')
    .set('sortCode')
    .set('valid')
    .set('createId')
    .set('createTime')
    .set('updateId')
    .set('updateTime')
    .set('sensorId')
    .set('alarmType')
    .set('alarmInfo')
    .set('alarmTimes')
    .set('handleState')
    .set('handleName')
    .set('handleContent')
    .set('handleTime')
    .set('remark');
}

/**
 * 提交添加
 */
AirSensorAlarmInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if(!this.validate()){
    	return;
    }
    
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/airSensorAlarmInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.AirSensorAlarmInfo.table.refresh();
        AirSensorAlarmInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.airSensorAlarmInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AirSensorAlarmInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if(!this.validate()){
    	return;
    }
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/airSensorAlarmInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.AirSensorAlarmInfo.table.refresh();
        AirSensorAlarmInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.airSensorAlarmInfoInfoData);
    ajax.start();
}



/**
 * 提交异常处理结果
 */
AirSensorAlarmInfoInfoDlg.handleSubmit = function() {

    this.clearData();
    this.collectData();

    if(!this.validate()){
    	return;
    }
    
    this.airSensorAlarmInfoInfoData['handleState']=$('input[name="handleState"]:checked').val();
    
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/airSensorAlarmInfo/handle", function(data){
        Feng.success("操作成功!");
        window.parent.AirSensorAlarmInfo.table.refresh();
        AirSensorAlarmInfoInfoDlg.close();
    },function(data){
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.airSensorAlarmInfoInfoData);
    ajax.start();
}


$(function() {
	Feng.initValidator("alarmInfoForm", AirSensorAlarmInfoInfoDlg.validateFields);
});
