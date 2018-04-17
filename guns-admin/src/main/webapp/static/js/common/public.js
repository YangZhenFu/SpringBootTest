/*function dyniframesize(down) { 
	var pTar = null; 
	if (document.getElementById){ 
		pTar = document.getElementById(down); 
	} 
	else{ 
		eval('pTar = ' + down + ';'); 
	} 
	if (pTar && !window.opera){ 
		//begin resizing iframe 
		pTar.style.display="block" 
		if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){ 
			//ns6 syntax 
			pTar.height = pTar.contentDocument.body.offsetHeight +20;  
			pTar.width = pTar.contentDocument.body.scrollWidth+20; 
		} 
		else if (pTar.Document && pTar.Document.body.scrollHeight){ 
			//ie5+ syntax 
			pTar.height = pTar.Document.body.scrollHeight; 
			pTar.width = pTar.Document.body.scrollWidth; 
		} 
	} 
} */
//document.getElementById("ifms").style.height=document.body.scrollHeight; 
// 声明一个全局对象Namespace，用来注册命名空间
var Namespace = new Object();
// 全局对象仅仅存在register函数，参数为名称空间全路径，如"Grandsoft.GEA"
Namespace.register = function(fullNS){
    // 将命名空间切成N部分, 比如Grandsoft、GEA等
    var nsArray = fullNS.split('.');
    var sEval = "";
    var sNS = "";
    for (var i = 0; i < nsArray.length; i++)
    {
        if (i != 0) sNS += ".";
        sNS += nsArray[i];
        // 依次创建构造命名空间对象（假如不存在的话）的语句
        // 比如先创建Grandsoft，然后创建Grandsoft.GEA，依次下去
        sEval += "if (typeof(" + sNS + ") == 'undefined') " + sNS + " = new Object();"
    }
    if (sEval != "") eval(sEval);
};
Namespace.register("Angel");
var isIe8 = !(typeof(ie8) == "undefined");

var $curmenu,lastIndex;//最后弹窗索引


function loadHtmlPage(path) {
    path = ctxPath + "/" + path;
    var result;
    $.ajax({
        url: path,
        dataType: "text",
        async: false,
        success: function(data) {
            result = data;
        }
    });
    return result;
};



;(function($){
	var cuslayer = function(params){
		var defaults = {
			mode:'normal',
			type:1, //0：信息框（默认），1：页面层，2：iframe层，3：加载层，4：tips层。
			title:false,
			shade: [0.5, '#000'], //[遮罩透明度, 遮罩颜色]
			border:[3, 0.5, '#666'],
			closeBtn:[0, true],
			url:undefined, //请求回来弹窗的url
			data:{}, //请求弹窗携带的参数
			maxmin:true, //是否输出窗口最小化/全屏/还原按钮。 
			width:'0',
			height:'0',
			btns:2,
			btn:['确 定','取 消'],
			msg:'',
			reloadurl:false //是否url刷新,默认false当前右侧刷新
		};
		
		params = $.extend(defaults, params);
		
		var mode = params.mode;
		if(undefined != params.closebtn){
			params.closeBtn = params.closebtn;
		}
		
		if(mode == 'del' || mode == 'delete' || mode == 'page'){
			if(undefined == params.url) {
				alert("请求url未填写");
				return false;
			}
		}
		if(mode == 'del' || mode == 'delete'){
			var loadi;
			layer.confirm(params.msg,function(index){
				$.ajax({
					url:params.url,
					data:params.data,
					type:'post',
					beforeSend:function(){
						loadi = layer.load(5,0);
					}
				}).done(function(data){
					layer.close(loadi);
        			if(data>0) {
        				if(params.success == undefined){
        					layer.msg('<span class="red bigger-120">删除成功</span>', 1, 1,function(){
            					if(params.reloadurl){
            						location.reload();
            					}else{
            						$curmenu.trigger('click');
            						if(params.callback != undefined) {
            							if(typeof params.callback === "string"){
            								eval(params.callback)
            							}else{
            								params.callback();
            							}
            						}
            					}
            				});
        				}else{
        					params.success();
        				}
        			}else if(data<0){
        				layer.alert('<span class="red bigger-120">删除失败，数据正在被使用！</span>', 8, !1);
        			}
        		}).fail(function(error){
        			layer.msg('<span class="red bigger-120">播放失败</span>', 2, 8);
        		});
			},params.title);
			return false;
		}
		
		
		if(mode == 'page' || mode == 'detail'){
			var loadi; //加载窗
			var oheight,owidth;
			$.ajax({
				url:params.url,
				data:params.data,
				type:'post',
				dataType:'html',
				beforeSend:function(){
					loadi = layer.load(5,0);
				}
			}).done(function(data){
				var layerObj; //弹窗
				var increment = 36,dheight = params.height,dwidth = params.width;
				var _scrollHeight = $(document).scrollTop();
				var _windowHeight = $(window).height();
				var _windowWidth = $(window).width();
				var _windowHeight1 = $(window).height();
				var _windowWidth1 = $(window).width();
				var borderWidth = params.borderwidth==undefined?4:params.borderwidth;
				layer.close(loadi); //关闭加载框
				lastIndex = layer.open({
				    type : 1,
				    title : params.title,
				    maxmin: params.maxmin,
				    closeBtn: params.closeBtn,
				    area : ['99%','100%'],
				    border:[borderWidth, 0.5, '#888'],
				    page : {html:data},
				    success:function(layero){
				    	layerObj = layero;
				    },
				    full:function(layero){
				    	layero.find('.xuboxPageHtml').css({'height':_windowHeight-increment-(borderWidth*2)});
				    },
				    restore: function(layero){
				    	layerObj.css({
				    		'width':owidth,
				    		'height':oheight
				    	});
				    	layerObj.find(".xubox_main").css({
				    		'width':owidth,
				    		'height':oheight
				    	});
				    	layero.find('.xuboxPageHtml').css({'height':oheight-increment});
				    	layerObj.find(".xubox_border").width(owidth+8).height(oheight+8);
				    },
				    close: function(index){
				    	layer.closeTips();
				    }
				});
				var saveTag = layerObj.find('div[tag-save-btn]');
				if(saveTag.length > 0) increment = 36*2;
				oheight = layerObj.find("div.layer").outerHeight()+increment;
				owidth = layerObj.find("div.layer").outerWidth();
				if(oheight>_windowHeight) oheight = _windowHeight;
				if(owidth>_windowWidth) owidth = _windowWidth;
				//默认设置
				if(dheight!=0 && dheight!="") { //显式的指定高度情况
					var bf = dheight.indexOf('%');
					if(bf != -1) { //百分比
						oheight = parseInt($.trim(dheight.substring (0,bf)))/100.0 * _windowHeight;
					}else{ //px
						var px = dheight.indexOf('px');
						oheight = parseInt($.trim(dheight.substring (0,px)));
					}
				}
				if(dwidth!=0 && dwidth!="") {//显式的指定宽度情况
					var bf = dwidth.indexOf('%');
					if(bf != -1){
						owidth = parseInt($.trim(dwidth.substring (0,bf)))/100.0 * _windowWidth;
					}else{
						var px = dwidth.indexOf('px');
						owidth = parseInt($.trim(dwidth.substring (0,px)));
					}
				}else{
					//owidth = 0.46 * _windowWidth;
				}
				var _posiTop = _posiLeft = 0;
				if(oheight != _windowHeight){
					_posiTop = (_windowHeight - oheight-8)/2;
				}else{
					oheight = _windowHeight-8;
				}
				if(owidth != _windowWidth){
					_posiLeft = (_windowWidth - owidth-8)/2;
				}else{
					owidth = _windowWidth-8;
				}
				//layer.area(lastIndex, {width:_windowWidth1,height:_windowHeight1,top:_posiTop,left:_posiLeft});
//				layer.area(lastIndex, {width:owidth,height:oheight,top:_posiTop,left:_posiLeft});
		    	
				var bottom = '0px';
				if(saveTag.length > 0) {
					bottom = '-36px';
				}
		    	layerObj.find('.xuboxPageHtml').css({
		    		'overflowY':'auto',
		    		'height':layerObj.height()-increment,
		    	});
		    	saveTag.css({'bottom':bottom});
		    	layerObj.find(".xubox_page").css({width:'100%'});
		    	
			}).fail(function(err){
				layer.msg('操作失败', 2, 8);
			});
		}
		
	};
	$.cuslayer = cuslayer;
})(jQuery);

$(function(){
	//数组操作
	Array.prototype.indexOf = function(val) {              
	    for (var i = 0; i < this.length; i++) {  
	        if (this[i] == val) return i;  
	    }  
	    return -1;  
	};
	Array.prototype.remove = function(val) {  
	    var index = this.indexOf(val);  
	    if (index > -1) {  
	        this.splice(index, 1);  
	    }  
	};
	
	//document.oncontextmenu=function(){return false;}//屏蔽右键 
	
	// 禁用Enter键表单自动提交
	document.onkeydown = function(event) {
		var target, code, tag;
		if (!event) {
			event = window.event; // 针对ie浏览器
			target = event.srcElement;
			code = event.keyCode;
			if (code == 13) {
				tag = target.tagName;
				if (tag == "TEXTAREA") {
					return true;
				} else {
					return false;
				}
			}
		} else {
			target = event.target; // 针对遵循w3c标准的浏览器，如Firefox
			code = event.keyCode;
			if (code == 13) {
				tag = target.tagName;
				if (tag == "INPUT") {
					return false;
				} else {
					return true;
				}
			}
		}
	};
	
	//属性模式
	$(document).on('click','[data-mode]',function(){
		var data = $(this).data();
		if(undefined != data['data'] && typeof data['data'] != "object") {
			data['data'] = eval("("+data.data+")");
		}
		$.cuslayer(data);
	});
	
});


//得到url的参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
};

//刷新url
function reloadUrl(){
	window.location.href = (window.location.href).split("?")[0]+"?menuid="+$curmenu.attr("id");
}

//提示tip
var tip={
	errorTip:function(msg,obj,style){
		style = style == undefined?['background-color:#F26C4F; color:#fff','#F26C4F' ]:style;
		layer.tips(msg, obj, {
			guide:0,
			time: 4,
			style : style
		});
	}
};

//Angel.downloadFile = function(formid,action){
//	   var curForm=$("#"+formid);
//	   var queryParams=curForm.serialize();
//	   queryParams = decodeURIComponent(queryParams,true);//将中文解码进行还原
//	   
//	   console.log(queryParams);
//	   var $tempForm=$("<form style='display:none;'></form>");
//	   var paramArr=queryParams.split("&");
//	   for(var i=0;i<paramArr.length;i++){
//		   var paramValue=paramArr[i].split("=");
//		   var paramName=paramValue[0];
//		   var paramValue=paramValue[1];
//		   var $input=$("<input name='"+paramName+"' value='"+paramValue+"'/>");
//			   $tempForm.append($input);
//	   }
//	   $("body").append($tempForm);
//	   $tempForm.attr("action",action);
//	   $tempForm.attr("method","post");
//	   $tempForm.submit();
//	   $tempForm.remove();
//	};


Angel.downloadFile = function(queryData,action){
   var $tempForm=$("<form style='display:none;'></form>");
   for(var k in queryData){
	   var paramName=k;
	   var paramValue=queryData[k];
	   var $input=$("<input name='"+paramName+"' value='"+paramValue+"'/>");
		   $tempForm.append($input);
   }
   $("body").append($tempForm);
   $tempForm.attr("action",action);
   $tempForm.attr("method","post");
   $tempForm.submit();
   $tempForm.remove();
};

Angel.uploadFile = {
	init:function(fileInput){
		if(!fileInput.parent().is("form")){
			var url = fileInput.data("url");
			fileInput.wrap("<form action='"+url+
			"' method='post' enctype='multipart/form-data'></form>"); 
		}
	},
	excel:function(target){
		var $this = $(target),url = $this.data("url"),
			progress = $($this.data("progressid")),oldTxt = $this.closest(".btn").find("span").text();
		this.init($this);
		var form = $this.parent();
		
		form.ajaxSubmit({ 
            dataType: 'html', 
            beforeSend: function() { //开始上传 
            	$this.css({"top":"-1000px"});
            	progress.attr("data-percent","0%");
                progress.children().eq(0).width("0%");
                $this.closest(".btn").find("span").text("处理中,请稍后…");
                if(!isIe8){
                	progress.show();
                }
            }, 
            uploadProgress: function(event, position, total, percentComplete) { 
                var percentVal = percentComplete + '%'; //获得进度 
                progress.attr("data-percent",percentVal);
                progress.children().eq(0).width(percentVal);
            }, 
            success: function(data) { //成功 
            	$this.css({"top":"0px"});
            	progress.hide();
            	$this.closest(".btn").find("span").text(oldTxt);
            	$this.replaceWith($this.clone());
            	layer.alert(data, 1,function(index){
            		layer.close(index);
            		location.reload();
            	});
            }, 
            error:function(xhr){ //上传失败 
            	$this.css({"top":"0px"});
            	progress.hide();
            	$this.closest(".btn").find("span").text(oldTxt);
            	$this.replaceWith($this.clone());
                //alert(xhr.responseText); //返回失败信息 
            } 
        }); 
	}
};

// Find the right method, call on correct element
function launchFullscreen(element) {

    if (!$("body").hasClass("full-screen")) {

        $("body").addClass("full-screen");

        if (element.requestFullscreen) {
            element.requestFullscreen();
        } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
        } else if (element.webkitRequestFullscreen) {
            element.webkitRequestFullscreen();
        } else if (element.msRequestFullscreen) {
            element.msRequestFullscreen();
        }

    } else {

        $("body").removeClass("full-screen");

        if (document.exitFullscreen) {
            document.exitFullscreen();
        } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
        } else if (document.webkitExitFullscreen) {
            document.webkitExitFullscreen();
        }

    }

}
