<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>external</title>
	</head>
	<body>
		<p>
			<a href="<spring:url value="/maps/google" />" target="_blank">지도(구글)</a> |
			<a href="<spring:url value="/maps/google/complex" />" target="_blank">지도(구글)-복잡한</a> |  
			<a href="<spring:url value="/maps/naver" />" target="_blank">지도(네이버)</a>
		</p><br/>
	</body>
</html>