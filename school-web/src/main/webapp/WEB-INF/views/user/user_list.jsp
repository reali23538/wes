<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>사용자 목록</title>
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
					$('#frm').attr('action', '<spring:url value="/users" />');
					$('#frm').submit();
				};
				
				<%-- 등록 --%>
				$('button[name=btnAdd]').on('click', function() {
					location.href = '<spring:url value="/users/add${queryString}" />';
				});
				
				<%-- 멀티 삭제 --%>
				$('[name=checkboxAll]').on('change', function() {
					var isChecked = $(this).is(':checked');
					$('[id^=userSeq]').prop('checked', isChecked);
				});
				$('[id^=userSeq]').on('change', function() {
					var count = parseInt('${fn:length(dto.users)}');
					var checkedCount = $('[id^=userSeq]:checked').length;
					
					$('[name=checkboxAll]').prop('checked', false);
					if (count == checkedCount) {
						$('[name=checkboxAll]').prop('checked', true);
					}
				});
				$('button[name=btnRemove]').on('click', function() {
					if ($('[id^=userSeq]:checked').length == 0) {
						alert('삭제대상을 선택해주세요.');
						return;
					}
					
					if (confirm('선택한 대상을 삭제하시겠습니까?')) {
						$('#frm').attr({
							'action' : '<spring:url value="/users/removes" />',
							'method' : 'post'
						});
						
						$('#frm').submit();
					}
				});
				
				<%-- 엑셀 가져오기 --%>
				$('#userImportModal').modal({
					show : false,
					keyboard : false
				});
				
				$('[name=btnSave]').on('click', function() {
					var isAttach = $.trim($('[name=userExcelFile]').val()) == '' ? false : true ;
					
					if (!isAttach) {
						alert('파일을 선택해주세요.');
						return;
					}
					$('#addFrm').submit();
				});
				
				<%-- 엑셀 내보내기 --%>
				$('[name=btnExport]').on('click', function() {
					$('#frm').attr('action', '<spring:url value="/users/export" />');
					$('#frm').submit();
				});
			});
		</script>
	</head>
	<body class="modal-open">

		<!-- Modal -->
		<div class="modal fade" id="userImportModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">엑셀 가져오기</h4>
					</div>
					<div class="modal-body">
						<form id="addFrm"  action="<spring:url value="/users/import" />" method="post" enctype="multipart/form-data">
							<input type="file" name="userExcelFile" />
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
						<button type="button" name="btnSave" class="btn btn-primary">저장</button>
					</div>
				</div>
			</div>
		</div>
	
		<h2 class="sub-header">사용자</h2>
		<div class="table-responsive">
			<form:form id="frm" modelAttribute="dto" method="get">
				<form:hidden path="condition.currentIndex" id="currentIndex" />
				<form:hidden path="condition.offset" id="offset" />
				<form:hidden path="condition.orderBy" />
				<form:hidden path="condition.sortType" />
				
				<p>
					<form:input path="condition.q" id="q" placeholder="아이디, 이름, 이메일" />
					<form:button type="button" name="btnSearch" class="btn btn-default">검색</form:button>
				</p>
				<p>
					<form:button type="button" name="btnAdd" class="btn btn-primary">등록</form:button>
					<form:button type="button" name="btnRemove" class="btn btn-danger">삭제</form:button>
					<form:button type="button" name="btnImport" data-toggle="modal" data-target="#userImportModal" class="btn btn-default">엑셀 가져오기</form:button>
					<form:button type="button" name="btnExport" class="btn btn-default">엑셀 내보내기</form:button>
				</p><br/><br/>

				<p>Total : ${dto.totalCount}</p>
				<div class="table-responsive">
					<table class="table table-bordered">
						<thead>
							<tr class="active">
								<th><input type="checkbox" name="checkboxAll" /></th>
								<th>아이디</th>
								<th>이름</th>
								<th>이메일</th>
								<th>성별</th>
								<th>등록일자</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${dto.users}" varStatus="status">
								<tr>
									<td>
										<input type="checkbox" name="users[${status.index}].userSeq" id="userSeq${status.index}" value="${user.userSeq}" />
									</td>
									<td>
										<a href="<spring:url value="/users/${user.userSeq}${queryString}" />">
											<c:out value="${user.id}" />
										</a>
									</td>
									<td>
										<c:out value="${user.name}" />
										<c:if test="${empty user.name}">-</c:if>
									</td>
									<td>
										<c:out value="${user.email}" />
										<c:if test="${empty user.email}">-</c:if>
									</td>
									<td>
										${user.sexDesc}
									</td>
									<td>
										<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${user.createdDate}"/>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty dto.users}">
								<td colspan="6" style="text-align:center;">사용자가 없습니다.</td>
							</c:if>
						</tbody>
					</table>
					<p style="text-align:center;">
						<font size="2">
							<%-- 기본 페이징
								- core에 paging.basic 패키지
								- applicationContext-web.xml에 페이징 관련 빈 선언
								- 컨트롤러에서 사용 (UserController)
								- jsp에 출력
							 --%>
<%-- 							${paging} --%>

							<%-- 페이징태그
								- core에 paging.tag.Paging.java
								- webElementarySchoolTags.tld
								- tag_libs.jsp에 선언
								- jsp에 출력
							 --%>
							<wes:paging request="${pageContext.request}" totalCount="${dto.totalCount}" />
						</font>
					</p>
				</div>
			</form:form>
		</div>
	</body>
</html>