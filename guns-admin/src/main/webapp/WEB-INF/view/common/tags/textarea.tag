@/*
    表单中input框标签中各个参数的说明:

    hidden : input hidden框的id
    id : input框id
    name : input框名称
    readonly : readonly属性
    clickFun : 点击事件的方法名
    style : 附加的css属性
@*/
@var placeholder = placeholder!'';
@var class = class!'';
@var value = value!'';
@var style = style!'';
@var autosize = autosize!'';//是否自动扩张高度
@var readonly = readonly!'';
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
    	<textarea  class="form-control ${class}" id="${id}" name="${id}" placeholder="${placeholder}" style="${style}"
    		@if(isNotEmpty(readonly)){
    			readonly="${readonly}"
    		@}
    	>${value}</textarea>
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}
<script type="text/javascript">
@if(isNotEmpty(autosize) && autosize == 'true'){
	$('#${id}').autosize({append: "\n"});
@}
</script>




