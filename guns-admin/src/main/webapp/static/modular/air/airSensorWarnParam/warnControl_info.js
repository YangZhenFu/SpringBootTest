/**
 * 初始化设备预警参数详情对话框
 */
var AirSensorWarnParamInfoDlg = {
    airSensorWarnParamInfoData : {},
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
	                message: '顺序位不能为空'
	            },
	            regexp:{
	            	regexp:/^\d+$/,
	            	message:'只能输入整数'
	            }
	        }
	    },
	    startTime: {
	    	validators: {
	            notEmpty: {
	                message: '开始时间不能为空'
	            },
	        }
	    },
	    endTime: {
	    	validators: {
	            notEmpty: {
	                message: '结束时间不能为空'
	            },
	            callback: {
                    message: '开始时间必须早于结束时间',
                    callback:function(value, validator,$field,options){
                        var  startTime = $('#warnParamForm').find("#startTime").val().trim();
                        var endTime = $('#warnParamForm').find("#endTime").val().trim();
                        
                        //时间对比函数，如果a>b返回1，如果a<b返回-1，相等返回0   
                		function comptime(a,b)   
                		{   
                		var dateA = new Date("1900/1/1 " + a);   
                		var dateB = new Date("1900/1/1 " + b);   
                		if(isNaN(dateA) || isNaN(dateB)) return null;   
                		if(dateA > dateB) return 1;   
                		if(dateA < dateB) return -1;   
                		return 0;   
                		}  
                        if(startTime && endTime){
                        	if(comptime(startTime,endTime)==-1){
                        		return true;
                        	}
                        }
                        return false;
                   }

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
	    expression: {
	        validators: {
	            notEmpty: {
	                message: '表达式类型不能为空'
	            }
	        }
	    },
	    threshold: {
	        validators: {
	            notEmpty: {
	                message: '阈值不能为空'
	            },
	            numeric:{
	            	message: '只能填写数字'
	            }
	        }
	    },
	    controlMode: {
	        validators: {
	            notEmpty: {
	                message: '控制方式不能为空'
	            }
	            
	        }
	    },
	    warnInterval: {
	        validators: {
	            notEmpty: {
	                message: '预警间隔不能为空'
	            }
	        }
	    },
	    alarmTime: {
	        validators: {
	            notEmpty: {
	                message: '报警时间不能为空'
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
AirSensorWarnParamInfoDlg.clearData = function() {
    this.airSensorWarnParamInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AirSensorWarnParamInfoDlg.set = function(key, val) {
    this.airSensorWarnParamInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AirSensorWarnParamInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AirSensorWarnParamInfoDlg.close = function() {
    parent.layer.closeAll();
}

/**
 * 收集数据
 */
AirSensorWarnParamInfoDlg.collectData = function() {
    this
    .set('id')
    .set('code')
    .set('tName')
    .set('sortCode')
    .set('valid')
    .set('sensorId')
    .set('startTime')
    .set('endTime')
    .set('expression')
    .set('threshold')
    .set('controlMode')
    .set('warnInterval')
    .set('alarmTime')
    .set('createBy')
    .set('createTime')
    .set('updateBy')
    .set('updateTime')
    .set('remark');
}


/**
 * 验证数据是否为空
 */
AirSensorWarnParamInfoDlg.validate = function () {
    $('#warnParamForm').data("bootstrapValidator").resetForm();
    $('#warnParamForm').bootstrapValidator('validate');
    return $("#warnParamForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
AirSensorWarnParamInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if(!this.validate()){
    	return;
    }
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/airSensorWarnParam/add", function(data){
    	 layer.msg(data.message,{icon:1},function(){
    		 var treeObj = parent.$.fn.zTree.getZTreeObj("areaTree");
    	      	var nodes = treeObj.getSelectedNodes();
    	    	var tid=nodes[0].tId;
    	    	parent.$('#'+tid+'_span').trigger('click');
    	    	window.parent.layer.closeAll();
		});
       
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.airSensorWarnParamInfoData);
    ajax.start();
}



$(function() {

	Feng.initValidator("warnParamForm", AirSensorWarnParamInfoDlg.validateFields);
});
