<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<spring:url var="articleUrl" value="/articles" />
<spring:url var="articleEditUrl" value="/articles/${article.id}/edit" />
<spring:url var="articleRemoveUrl" value="/articles/${article.id}/remove" />
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>게시판 상세</title>
	<script type="text/javascript">
		
		$(function() {
			$('button[name=btnList]').on('click', function() {
				location.href = '${articleUrl}${queryString}';
			});
			$('button[name=btnEdit]').on('click', function() {
				location.href = '${articleEditUrl}${queryString}';
			});
			$('button[name=btnRemove]').on('click', function() {
				if(confirm('삭제하시겠습니까?')) {
					$('#frm').submit();
				}
			});
		});
		
	</script>
</head>
<body>
	<h2 class="sub-header">게시판</h2>
	<div class="table-responsive">
		<form:form id="frm" modelAttribute="article" action="${articleRemoveUrl}" method="post">
		<p>		
			<form:button type="button" name="btnList" class="btn btn-default">목록</form:button>
			<c:if test="${WESUser.user.userSeq eq article.user.userSeq}">
			<form:button type="button" name="btnEdit" class="btn btn-primary">수정</form:button>
			<form:button type="button" name="btnRemove" class="btn btn-danger">삭제</form:button>
			</c:if>
		</p>
		</form:form>
		
		<div class="table-responsive">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<th class="active">번호</th>
						<td>${article.id}</td>
						<th class="active">조회수</th>
						<td>${article.viewCount}</td>
					</tr>
					<tr>
						<th class="active">작성자</th>
						<td><c:out value="${article.user.name}" />(<c:out value="${article.user.id}" />)</td>
						<th class="active">작성일시</th>
						<td>
							<fmt:formatDate value="${article.createdDate}" pattern="yyyy-MM-dd" />
							<c:if test="${not empty article.updatedDate}">
							(<fmt:formatDate value="${article.updatedDate}" pattern="yyyy-MM-dd" />)
							</c:if>
						</td>
					</tr>
					<tr>
						<th class="active">제목</th>
						<td colspan="3"><c:out value="${article.title}" /></td>
					</tr>
					<tr>
						<th class="active">내용</th>
						<td colspan="3">${article.contents}</td>
					</tr>
				</tbody>						
			</table>
		</div>
	</div>
</body>
</html>