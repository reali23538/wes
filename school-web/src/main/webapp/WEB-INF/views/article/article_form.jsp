<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>게시판 등록/수정</title>
	<script type="text/javascript">
	
		$(function() {

			<%-- 네이버 스마트 에디터 --%>
			var oEditors = [];

			// 추가 글꼴 목록
			//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

			nhn.husky.EZCreator.createInIFrame({
				oAppRef: oEditors,
				elPlaceHolder: "contents",
				sSkinURI: "<spring:url value="/static/scripts/libs/SE2.8.2.O4259f59/SmartEditor2Skin.html" />",
				htParams : {
					bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
					bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
					//bSkipXssFilter : true,		// client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
					//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
					fOnBeforeUnload : function(){
						//alert("완료!");
					}
				}, //boolean
				fOnAppLoad : function(){
					//예제 코드
					//oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
				},
				fCreator: "createSEditor2"
			});
			
			$('#btnSave').on('click', function() {
				oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.
				$('#frm').submit();
			});
			
			$('#btnList').on('click', function() {
				location.href = '<spring:url value="/articles" />${queryString}';
			});
			
		});

	</script>
</head>
<body>
	<h2 class="sub-header">게시판</h2>
	<div class="table-responsive">
		<spring:url var="articleUrl" value="/articles/save" />
		<form:form id="frm" modelAttribute="article" action="${articleUrl}" method="post">
			<form:hidden path="id" />
		
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<th class="active">제목</th>
							<td>
								<form:input path="title" style="width:440px;"/>
								<form:errors path="title" />
							</td>
						</tr>
						<tr>
							<th class="active">내용</th>
							<td>
								<form:textarea path="contents" rows="10" cols="100" style="width:766px; height:412px; display:none;" />
								<form:errors path="contents" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<p>	 	
		 		<form:button type="button" id="btnSave" class="btn btn-primary">저장</form:button>
		 		<form:button type="button" id="btnList" class="btn btn-default">목록</form:button>
			</p>
		</form:form>
	</div>
</body>
</html>