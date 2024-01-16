<jsp:directive.page contentType="text/html;charset=utf-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>사용자 등록/수정</title>
	<script type="text/javascript">
	
		$(function() {

			<%-- 네이버 스마트 에디터 --%>
			var oEditors = [];

			// 추가 글꼴 목록
			//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

			nhn.husky.EZCreator.createInIFrame({
				oAppRef: oEditors,
				elPlaceHolder: "etc",
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
			
			<%-- validation --%>
			/*
			$('#frm').validate({
				rules : {
					'id' : { required : true, minlength : 3, maxlength : 8 }
					,'password' : { required : true }
					,'passwordConfirm' : { equalTo : '#password' }
// 					,'name' : { required : true }
// 					,'email' : { required : true, email : true }
// 					,'sex' : { required : true }
// 					,'etc' : { required : true }
				},
				messages : {
					'id' : { required : '아이디를 입력해주세요.', minlength : '3자이상 입력해주세요.', maxlength : '8자이하로 입력해주세요.' }
					,'password' : { required : '패스워드를 입력해주세요.' }
					,'passwordConfirm' : { equalTo : '패스워드가 다릅니다. 확인해주세요.' }
// 					,'name' : { required : '이름을 입력해주세요.' }
// 					,'email' : { required : '이메일을 입력해주세요.', email : '이메일 형식에맞게 입력해주세요.' }
// 					,'sex' : { required : '성별을 선택해주세요.' }
// 					,'etc' : { required : '기타를 입력해주세요.' }
				}
			});
			*/
			
			$('#btnSave').on('click', function() {
				oEditors.getById["etc"].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.
				
				var etc = $('#etc').val();
				var blankRemovedEtc = $.trim(etc.replace(/<p>|<\/p>|<p style=\"margin-left: 40px;\">|&nbsp;/gi, '')); 
				etc = (blankRemovedEtc == '') ? '' : etc;
				$('#etc').val(etc);
				
				$('#frm').submit();
			});
			
			$('#btnList').on('click', function() {
				location.href = '<spring:url value="/users" />${queryString}';
			});
			
		});

	</script>
</head>
<body>
	<h2 class="sub-header">사용자</h2>
	<div class="table-responsive">
		<spring:url var="userUrl" value="/users/save" />
		<form:form id="frm" modelAttribute="user" action="${userUrl}" method="post">
			<form:hidden path="userSeq" />
		
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<th class="active">아이디(*)</th>
							<td>
								<p><form:input path="id" /></p>
								<p><form:errors path="id" /></p>
							</td>
						</tr>
						<tr>
							<th class="active">패스워드(*)</th>
							<td>
								<p><form:password path="password" /></p>
								<p><form:errors path="password" /></p>
							</td>
						</tr>
						<tr>
							<th class="active">패스워드 확인</th>
							<td>
								<input type="password" id="passwordConfirm" name="passwordConfirm" />
							</td>
						</tr>
						<tr>
							<th class="active">이름</th>
							<td>
								<form:input path="name" />
								<form:errors path="name" />
							</td>
						</tr>
						<tr>
							<th class="active">이메일</th>
							<td>
								<form:input path="email" />
								<form:errors path="email" />
							</td>
						</tr>
						<tr>
							<th class="active">성별</th>
							<td>
								<form:radiobutton path="sex" value="1" label="남" checked="checked" />
								<form:radiobutton path="sex" value="0" label="여" />
							</td>
						</tr>
						<tr>
							<th class="active">기타</th>
							<td>
								<form:textarea path="etc" rows="3" cols="100" style="width:766px; height:412px; display:none;" />
								<form:errors path="etc" />
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