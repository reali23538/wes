<jsp:directive.page contentType="text/html;charset=UTF-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />

<link type="text/css" rel="stylesheet" href="<spring:url value="/static/scripts/libs/jquery-ui/jquery-ui.css" />">
<script type="text/javascript" src="<spring:url value="/static/scripts/libs/jquery/jquery-2.0.2.min.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/scripts/libs/jquery-ui/jquery-ui.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/scripts/libs/jquery/jquery.fileupload.js" />"></script>
<script type="text/javascript">

$(function() {
	// 참고
	// http://noritersand.tistory.com/230
    $('#uploadFiles').fileupload({
        url : '<spring:url value="/files/upload/script" />', 
        dataType: 'json',
//         replaceFileInput: false,
        add: function(e, data){
            var uploadFile = data.files[0];
            if (!(/png|jpe?g|gif/i).test(uploadFile.name)) {
                alert('png, jpg, gif 만 가능합니다.');
                return false;
            } else if (uploadFile.size > 5000000) {
                alert('파일용량은 5MB를 초과할 수 없습니다.');
                return false;
            }
             data.submit();
        },
        progressall: function(e,data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            console.log(progress);
            
            $('#progress .bar').css(
                'width',
                progress + '%'
            );
        },
        done: function (e, data) {
        	console.log(data);
        	$('#resultArea').append(data.result[0].path)
        },
        fail: function(){
            alert("upload fail");
        }
    });
}); 
</script> 
<input type="file" id="uploadFiles" name="uploadFiles" multiple>
<div id="progress">
	<div class="bar" style="width: 0%;"></div>
</div>
<div id="resultArea">
</div>