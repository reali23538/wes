<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<spring:url var="loginUrl" value="/static/j_spring_security_check" />
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Login</title>
		<script type="text/javascript">
		
			$(function() {
				$('#btnSubmit').on('click', function() {
					var username = fncEnCode($.trim($('#j_username').val()));
					var password = fncEnCode($.trim($('#j_password').val()));
					
					$('#j_username').val(username);
					$('#j_password').val(password);
					
					$('#frm').submit();
				});
			});
		
			function fncEnCode(param){
				var encode = '';
	
				for (i=0; i<param.length; i++) {
					var len = '' + param.charCodeAt(i);
					var token = '' + len.length;
	
					encode += token + param.charCodeAt(i);
				}
				return encode;
			}
		</script>
	</head>
	<body>
		<form id="frm" action="${loginUrl}" method="post" class="form-signin">
			<h2 class="form-signin-heading">Please sign in</h2>
			
			<label for="inputEmail" class="sr-only">Email address</label>
			<input type="text" id="j_username" name="j_username" value="awj0603" class="form-control" placeholder="ID" required autofocus>
			
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" id="j_password" name="j_password" value="dksdnwls1!" class="form-control" placeholder="Password" required>

			<button type="button" id="btnSubmit" class="btn btn-lg btn-primary btn-block">Sign in</button>
		</form>
		<c:if test="${not empty param.login_error}">
			<span style="color:red;">
				<spring:message code="${param.login_error}" />
			</span>
		</c:if>
	</body>
</html>