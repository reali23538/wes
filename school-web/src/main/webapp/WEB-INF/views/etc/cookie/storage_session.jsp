<jsp:directive.page contentType="text/html;charset=UTF-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />

<script src="<spring:url value="/static/scripts/libs/jquery/jquery-2.0.2.min.js" />"></script>
<script type="text/javascript">

	$(function() {
		
		// 세션스토리지 보기
		$('#btnView').on('click', function() {
			$('#sessionStorageArea').html('');
			
			for (var i=0; i<sessionStorage.length; i++) {
				var key = sessionStorage.key(i);
				var value = sessionStorage[key];
				
				$('#sessionStorageArea').append(key + ' : ' + value + '<br/>');
			}
		});
		
		// 세션스토리지 저장
		$('#btnSave').on('click', function() {
			sessionStorage['value1'] = '값1';
			sessionStorage['value2'] = '값2';
//	 		sessionStorage.setItem('value1', '값1');
//	 		sessionStorage.setItem('value2', '값2');
		});

		// 세션스토리지 삭제
		$('#btnRemove').on('click', function() {
			delete sessionStorage['value1'];
// 			sessionStorage.removeItem('value1');
		});

		// 세션스토리지 모두 삭제
		$('#btnRemoveAll').on('click', function() {
			sessionStorage.clear();
		});
	});

</script>

<p>
	세션스토리지 : 
</p>
<p>
	<span id="sessionStorageArea"></span>
</p>
<p><input type="button" id="btnSave" value="세션스토리지 저장" /></p>
<p><input type="button" id="btnView" value="세션스토리지 보기" /></p>
<p><input type="button" id="btnRemove" value="세션스토리지 지우기" /></p>
<p><input type="button" id="btnRemoveAll" value="세션스토리지 모두 지우기" /></p>
<br/>

<p>※ 설명 :</p>
<p>클라이언트 PC에 데이터 저장(세션단위로 데이터 저장)</p>
<p>원도우마다 유효범위, 생존기간을 가짐</p>
<p>같은 도메인에 같은 페이지라도 탭 또는 팝업으로 각각 다른 원도우에 열릴 시 각각에 원도우마다 세션 스토리지가 생성</p>
<p>원도우가 닫히면 세션 스토리지도 삭제 됨</p>