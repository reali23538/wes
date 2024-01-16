<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>file</title>
	</head>
	<body>
		<p><a href="<spring:url value="/fileUploader" />" target="_blank">*멀티파일업로더</a></p>
		<p><a href="<spring:url value="/fileDownloadView/impl" />" target="_blank">*파일다운로드(뷰구현)</a></p>
		<p><a href="<spring:url value="/fileDownloadView/extends" />" target="_blank">*파일다운로드(뷰확장)</a></p>
		<p><a href="<spring:url value="/thumbnails" />" target="_blank">*썸네일이미지 만들기</a></p>
		<br/>
	
		<p>
			<a href="<spring:url value="/files/upload" />" target="_blank">파일업로드</a> | 
			<a href="<spring:url value="/files/multi-upload" />" target="_blank">멀티파일업로드 (submit)</a> | 
			<a href="<spring:url value="/files/upload/script" />" target="_blank">멀티파일업로드 (미리보기)</a>
		</p><br/>
		
		<p>
			<a href="<spring:url value="/files/excel-export" />" target="_blank">파일다운로드-엑셀</a>
		</p><br/>
		
		<p>
			<a href="<spring:url value="/files/upload/old" />" target="_blank">파일업로드(old)</a> | 
			<a href="<spring:url value="/files/download/old" />" target="_blank">파일다운로드(old)</a>
		</p>
		<p>
			<a href="<spring:url value="/files/write" />" target="_blank">파일쓰기</a> | 
			<a href="<spring:url value="/files/read" />" target="_blank">파일읽기</a> | 
			<a href="<spring:url value="/files/remove" />" target="_blank">파일삭제</a>
		</p>
	</body>
</html>