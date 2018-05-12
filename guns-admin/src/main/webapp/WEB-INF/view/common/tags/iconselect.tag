@var class = class!'';
@var name = name!'icon';
@var value = value!'';
@var title = title!'图标';
@var underline = underline!'';
@var search = search!'true';
<div class="form-group">
    <label class="col-sm-3 control-label">${title!}</label>
    <div class="col-sm-9">
        <div class="width-100 clearfix ${class!}">
			<input type="hidden" value="${value}" name="${name}" id="${name}-input"/>
			<i class="ace-icon ${value} bigger-200 pink" style="vertical-align: middle;padding-right: 10px;"></i>
			@if(search=='true'){
				<span class="btn btn-sm btn-primary" id="${name}-icon-btn">
					<i class="ace-icon fa fa-search bigger-110"></i>查找
				</span>
			@}
		</div>
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}
<script>
$(function(){
	$("#${name}-icon-btn").click(function(){
		/* layer.open({
		    type: 2,
		    title: '请单击选择图标',
		    maxmin: true,
		    iframe: {src : Feng.ctxPath+'/tag/fontawesome?id=${name}-input'},
		    area: ['1000px' , '550px']
		});  */
		//iframe层
		parent.layer.open({
		  type: 2,
		  title: '请点击选择图标',
		  shadeClose: true,
		  //shade: 0.8,
		  area: ['1000px', '550px'],
		  content: Feng.ctxPath+'/tag/fontawesome?id=${name}-input'
		}); 
	});
});
</script>
<style>
.bigger-200 {
    font-size: 200% !important;
}
.pink {
    color: #c6699f !important;
}
.ace-icon {
    text-align: center;
}
.bigger-110 {
    font-size: 110% !important;
}
</style>