@/*
    时间查询条件标签的参数说明:

    name : 查询条件的名称
    id : 查询内容的input框id
    isTime : 日期是否带有小时和分钟(true/false)
    pattern : 日期的正则表达式(例如:"YYYY-MM-DD")
@*/
@var type = type!'';//类型
@var underline = underline!'';//下划线
@if(isEmpty(type)){
<div class="input-group">
    <div class="input-group-btn">
        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle"
                type="button">${name}
        </button>
    </div>
    <input type="text" class="form-control layer-date"
           onclick="laydate({istime: ${isTime}, format: '${pattern}'})" id="${id}"/>
</div>
@}
@else if(type=='date'){
		<div class="form-group">
	    <label class="col-sm-3 control-label">${name}</label>
	    <div class="col-sm-9">
	        <input type="text" class="form-control layer-date" 
           onclick="laydate({istime: ${isTime}, format: '${pattern}'})" id="${id}"/>
	    </div>
	</div>
	@if(isNotEmpty(underline) && underline == 'true'){
	    <div class="hr-line-dashed"></div>
	@}
	
@}