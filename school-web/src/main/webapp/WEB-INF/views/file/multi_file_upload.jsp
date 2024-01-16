<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />

<script type="text/javascript" src="<spring:url value="/static/scripts/libs/jquery/jquery-2.0.2.min.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/scripts/libs/jquery/jquery.MultiFile.js" />"></script>
<script type="text/javascript">
	$(function() {
		// accept : 허용할 확장자
		// list : 업로드할 파일리스트 영역
		// STRING : 문자열 셋팅
		//		- remove : 삭제버튼
		//		- denied : 확장자 제한문구
		//		- duplicate : 중복문구
		$('[name=multipleFile]').MultiFile({  
             accept: 'jpg|jpeg|bmp|gif|png',  
             list: '#fileArea',  
             STRING: {  
                 remove: '삭제',  
                 selected: 'Selecionado: $file',  
                 denied: '$ext 형식은 업로드 할 수 없습니다.\n이미지 파일만 업로드 해주세요.\n(jpg, jpeg, bmp, gif, png)', 
                 duplicate: '$file 은(는) 이미 추가 되었습니다.'  
             }
         });
	});
</script>

<form method="POST" enctype="multipart/form-data">
	<input type="file" name="multipleFile" multiple />
	<div id="fileArea" style="border:#999 solid 1px; padding:10px;"></div>
	<input type="submit" id="btnSubmit" value="전송"/>
</form>