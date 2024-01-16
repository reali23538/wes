<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>etc</title>
	</head>
	<body>
		<p>
			<a href="<spring:url value="/cookieUtil" />" target="_blank">쿠키(자바)</a> | 
			<a href="<spring:url value="/cookies/script" />" target="_blank">쿠키(스크립트)</a>
		</p><br/>
		
		<p>
			<a href="<spring:url value="/storage/local" />" target="_blank">로컬스토리지</a> | 
			<a href="<spring:url value="/storage/session" />" target="_blank">세션스토리지</a>
		</p>
	</body>
</html>