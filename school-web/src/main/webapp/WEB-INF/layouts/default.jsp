<jsp:directive.page contentType="text/html;charset=utf-8"/>
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp"/>
<jsp:directive.include file="/WEB-INF/layouts/resources.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Web Elementary School</title>
<link href="<spring:url value="/static/scripts/libs/bootstrap-3.3.2-dist/css/dashboard.css" />" rel="stylesheet">
<script type="text/javascript">
	
	<%-- resultMessage --%>
	if ($.cookie('resultMessage') != undefined) {
		var resultMessage = $.cookie('resultMessage');
		
		if (resultMessage == 'SUCCESS_SAVE') {
			alert('저장 되었습니다.');
		} else if (resultMessage == 'SUCCESS_REMOVE') {
			alert('삭제 되었습니다.');
		} else {
			alert(resultMessage);
		}
		$.removeCookie('resultMessage', { path: '/' });
	}
	
</script>
<decorator:head/>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="<spring:url value="/users" />">Web Elementary School</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">안녕하세요. ${WESUser.user.name}님</a></li>
					<li><a href="<spring:url value="/static/j_spring_security_logout" />">로그아웃</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li <c:if test="${fn:startsWith(requestURI, '/users')}">class="active"</c:if>><a href="<spring:url value="/users" />">사용자 <span class="sr-only">(current)</span></a></li>
					<li <c:if test="${requestURI eq '/files'}">class="active"</c:if>><a href="<spring:url value="/files" />">파일</a></li>
					<li <c:if test="${requestURI eq '/rest-template'}">class="active"</c:if>><a href="<spring:url value="/rest-template" />">통신</a></li>
					<li <c:if test="${requestURI eq '/etc'}">class="active"</c:if>><a href="<spring:url value="/etc" />">기타</a></li>
					<li <c:if test="${requestURI eq '/external'}">class="active"</c:if>><a href="<spring:url value="/external" />">외부 라이브러리</a></li>
				</ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<decorator:body />
			</div>
		</div>
	</div>
</body>
</html>