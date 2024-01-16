<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<jsp:directive.include file="/WEB-INF/layouts/resources.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Web Elementary School</title>
<link href="<spring:url value="/static/scripts/libs/bootstrap-3.3.2-dist/css/signin.css" />" rel="stylesheet">
<decorator:head />
</head>
<body>
    <div class="container">
		<decorator:body />
    </div>
    
    <div style="position:absolute; bottom:15px; width:100%; text-align:center;">
		<font size="2">Copyright 2015 © WES. All rights reserved.</font>
    </div>
</body>
</html>