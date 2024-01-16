<jsp:directive.page contentType="text/html;charset=UTF-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />

<script src="<spring:url value="/static/scripts/libs/jquery/jquery-2.0.2.min.js" />"></script>
<script type="text/javascript">

	$(function() {
		// 로컬스토리지 보기
		$('#btnView').on('click', function() {
			$('#localStorageArea').html('');
			
			for (var i=0; i<localStorage.length; i++) {
				var key = localStorage.key(i);
				var value = localStorage[key];
				
				$('#localStorageArea').append(key + ' : ' + value + '<br/>');
			}
		});

		// 로컬스토리지 저장
		$('#btnSave').on('click', function() {
			localStorage['value1'] = '값1';
			localStorage['value2'] = '값2';
//	 		localStorage.setItem('value1', '값1');
//	 		localStorage.setItem('value2', '값2');
		});
		
		// 로컬스토리지 삭제
		$('#btnRemove').on('click', function() {
			delete localStorage['value1'];
//	 		localStorage.removeItem('value1');
		});
		
		// 로컬스토리지 모두 삭제
		$('#btnRemoveAll').on('click', function() {
			localStorage.clear();
		});
	});

</script>

<p>
	로컬스토리지 : 
</p>
<p>
	<span id="localStorageArea"></span>
</p>
<p><input type="button" id="btnView" value="로컬스토리지 보기" /></p>
<p><input type="button" id="btnSave" value="로컬스토리지 저장" /></p>
<p><input type="button" id="btnRemove" value="로컬스토리지 지우기" /></p>
<p><input type="button" id="btnRemoveAll" value="로컬스토리지 모두 지우기" /></p>
<br/>

<p>※ 설명 :</p>
<p>클라이언트 PC에 데이터 저장(만료기간 없음)</p>