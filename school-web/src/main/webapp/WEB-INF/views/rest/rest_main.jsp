<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>rest</title>
	</head>
	<body>
		<p>
			<a href="<spring:url value="/rest-template/get/data" />" target="_blank">restTemplate(get)쓰기</a> | 
			<a href="<spring:url value="/rest-template/get" />" target="_blank">restTemplate(get) 읽기</a>
		</p>
		<p>
			<a href="<spring:url value="/rest-template/post" />" target="_blank">restTemplate(post)읽기</a>
		</p><br/>
		
		<p>
			<a href="<spring:url value="/json/array/write" />" target="_blank">jsonArray쓰기</a> | 
			<a href="<spring:url value="/json/array/read" />" target="_blank">jsonArray읽기</a>
		</p>
		<p>
			<a href="<spring:url value="/json/object/write" />" target="_blank">jsonObject쓰기</a> | 
			<a href="<spring:url value="/json/object/read" />" target="_blank">jsonObject읽기</a>
		</p><br/>
		
		<p>
			<a href="<spring:url value="/ajax" />" target="_blank">ajax</a>
		</p>
	</body>
</html>