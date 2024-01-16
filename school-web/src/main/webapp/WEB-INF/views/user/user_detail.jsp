<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>사용자 상세</title>
	<script type="text/javascript">
		
		$(function() {
			$('[name=btnList]').on('click', function() {
				location.href = '<spring:url value="/users${queryString}" />';
			});
			$('[name=btnEdit]').on('click', function() {
				location.href = '<spring:url value="/users/${user.userSeq}/edit${queryString}" />';
			});
			$('[name=btnRemove]').on('click', function() {
				if(confirm('삭제하시겠습니까?')) {
					$('#frm').submit();
				}
			});
		});
		
	</script>
</head>
<body>
	<h2 class="sub-header">사용자</h2>
	<div class="table-responsive">
		<spring:url var="userRemoveUrl" value="/users/${user.userSeq}/remove" />
		<form:form id="frm" modelAttribute="user" action="${userRemoveUrl}" method="post">
			<p>
				<form:button type="button" name="btnList" class="btn btn-default">목록</form:button>
				<form:button type="button" name="btnEdit" class="btn btn-primary">수정</form:button>
				<form:button type="button" name="btnRemove" class="btn btn-danger">삭제</form:button>
			</p>
		</form:form>
		
		<div class="table-responsive">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<th class="active">아이디</th>
						<td>
							<c:out value="${user.id}" />
						</td>
					</tr>
					<tr>
						<th class="active">이름</th>
						<td>
							<c:out value="${user.name}" />
							<c:if test="${empty user.name}">-</c:if>
						</td>
					</tr>
					<tr>
						<th class="active">이메일</th>
						<td>
							<c:out value="${user.email}" />
							<c:if test="${empty user.email}">-</c:if>
						</td>
					</tr>
					<tr>
						<th class="active">성별</th>
						<td>
							${user.sexDesc}
						</td>
					</tr>
					<tr>
						<th class="active">기타</th>
						<td>
							${user.etc}
							<c:if test="${empty user.etc}">-</c:if>
						</td>
					</tr>
					<tr>
						<th class="active">등록일시</th>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${user.createdDate}" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>