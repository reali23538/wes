<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<spring:url var="articleUrl" value="/articles" />
<spring:url var="articleAddUrl" value="/articles/add" />
<spring:url var="articleRemoveUrl" value="/articles/removes" />
<spring:url var="articleExportUrl" value="/articles/export" />
<spring:url var="articleImportUrl" value="/articles/import" />
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>게시판 목록</title>		
		<script type="text/javascript">
				
			$(function() {
				<%-- 검색 --%>
				$('#q').on('keypress', function(e) {
					if (e.keyCode == 13) {
						search();
					}
				});		
				$('button[name=btnSearch]').on('click', search);
				function search() {
					$('#currentIndex').val('1');
					$('#offset').val('0');			
					$('#frm').attr('action', '${articleUrl}');
					$('#frm').submit();			
				};
				
				<%-- 등록 --%>
				$('button[name=btnAdd]').on('click', function() {
					location.href = '${articleAddUrl}${queryString}';
				});
				
				<%-- 멀티 삭제 --%>
				$('input[name=checkboxAll]').on('change', function() {			
					$('input[id^=id]').attr('checked', $(this).is(':checked'));			
				});
				$('button[name=btnRemove]').on('click', function() {
					if($('input[id^=id]:checked').length == 0) {
						alert('삭제할 게시물을 선택해주세요.');
						return;
					}
					
					if(confirm('선택한 게시물을 삭제하시겠습니까?')) {				
						$('#frm').attr({
							'action' : '${articleRemoveUrl}',
							'method' : 'post'
						});
						
						$('#frm').submit();
					}
				});
				
				<%-- 엑셀 내보내기 --%>
				$('button[name=btnExport]').on('click', function() {
					$('#frm').attr('action', '${articleExportUrl}');
					$('#frm').submit();
				});
			});
		</script>			
	</head>
	<body>
		<h2 class="sub-header">게시판</h2>
		<div class="table-responsive">
			<form:form id="frm" modelAttribute="dto" method="get">
				<form:hidden path="condition.currentIndex" id="currentIndex" />
				<form:hidden path="condition.offset" id="offset" />
				<form:hidden path="condition.orderBy" />
				<form:hidden path="condition.sortType" />
				<p>
					<form:input path="condition.q" id="q" placeholder="제목, 내용, 작성자명, 아이디" />
					<form:button type="button" name="btnSearch" class="btn btn-default">검색</form:button>
				</p>
				<p>
					<form:button type="button" name="btnAdd" class="btn btn-primary">등록</form:button>
					<form:button type="button" name="btnRemove" class="btn btn-danger">삭제</form:button>
					<form:button type="button" name="btnExport" class="btn btn-default">엑셀 내보내기</form:button>
				</p>
			   
				<div class="table-responsive">    
					<table class="table table-bordered">
						<thead>
							<tr class="active">
								<th><input type="checkbox" name="checkboxAll" /></th>
								<th>No</th>
								<th>제목</th>
								<th>작성자</th>
								<th>등록일자</th>
								<th>조회수</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="article" items="${dto.articles}" varStatus="status">
							<tr>
								<td>
									<input type="checkbox" name="articles[${status.index}].id" id="id${status.index}" <c:if test='${WESUser.user.userSeq ne article.user.userSeq}'>disabled</c:if> value="${article.id}" />
								</td>
								<td>${article.id}</td>
								<td>
									<a href="${articleUrl}/${article.id}${queryString}">
										<c:out value="${article.title}" />
									</a>
								</td>
								<td>
									<c:out value="${article.user.name}" />(<c:out value="${article.user.id}" />)
								</td>
								<td>
									<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${article.createdDate}"/>
								</td>
								<td>${article.viewCount}</td>
							</tr>
							</c:forEach>
							<c:if test="${empty dto.articles}">
								<td colspan="6">게시글이 없습니다.</td>
							</c:if>
						</tbody>
					</table>
					<p>
						<font size="2">
						${paging}
						Total ${dto.totalCount}
						</font>
					</p>
				</div>
			</form:form>
			<form id="addFrm"  action="${articleImportUrl}" method="post" enctype="multipart/form-data">
				<input type="file" name="articleExcelFile" />
				<input type="submit" name="btnImport" value="엑셀 가져오기" class="btn btn-default" />
			</form>				
		</div>
	</body>
</html>