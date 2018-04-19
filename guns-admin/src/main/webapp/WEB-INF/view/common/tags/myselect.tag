@var id = id!"chosen-select";
@var name = name!;
@var width = width!"100%";
@var class = class!;
@var underline = underline!'';//下划线
@var describe = describe!'';//描述
<div class="form-group">
    <label class="col-sm-3 control-label">${describe}</label>
    <div class="col-sm-9">
		<select class="chosen-select ${class}" name="${name}" id="${id}">
			${tagBody!}
		</select>
	</div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}
<script>
$(function(){
	$("#${id}").chosen({width: "${width}",search_contains: true}); 
});
</script>