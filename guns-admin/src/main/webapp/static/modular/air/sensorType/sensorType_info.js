/**
 * 初始化传感器类型详情对话框
 */
var SensorTypeInfoDlg = {
    sensorTypeInfoData : {},
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
	    }
    }
};

/**
 * 清除数据
 */
SensorTypeInfoDlg.clearData = function() {
    this.sensorTypeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SensorTypeInfoDlg.set = function(key, val) {
    this.sensorTypeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SensorTypeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SensorTypeInfoDlg.close = function() {
    parent.layer.close(window.parent.SensorType.layerIndex);
}

/**
 * 收集数据
 */
SensorTypeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('tName')
    .set('sortCode')
    .set('valid')
    .set('createBy')
    .set('createTime')
    .set('updateBy')
    .set('updateTime');
}

/**
 * 验证数据是否为空
 */
SensorTypeInfoDlg.validate = function () {
    $('#sensorTypeForm').data("bootstrapValidator").resetForm();
    $('#sensorTypeForm').bootstrapValidator('validate');
    return $("#sensorTypeForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
SensorTypeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    
    if(!this.validate()){
    	return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sensorType/add", function(data){
        Feng.success("添加成功!");
        window.parent.SensorType.table.refresh();
        SensorTypeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.sensorTypeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SensorTypeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    
    if(!this.validate()){
    	return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sensorType/update", function(data){
        Feng.success("修改成功!");
        window.parent.SensorType.table.refresh();
        SensorTypeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.sensorTypeInfoData);
    ajax.start();
}

$(function() {

	Feng.initValidator("sensorTypeForm", SensorTypeInfoDlg.validateFields);
});
