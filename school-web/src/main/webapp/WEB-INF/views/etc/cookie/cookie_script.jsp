<jsp:directive.page contentType="text/html;charset=UTF-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />

<script src="<spring:url value="/static/scripts/libs/jquery/jquery-2.0.2.min.js" />"></script>
<script src="<spring:url value="/static/scripts/libs/jquery/jquery.cookie.js" />"></script>
<script type="text/javascript">

	$(function() {
		$('#btnReadCookie').on('click', readCookie);
		function readCookie() {
			var cookieValue = $.cookie('key');
			if (cookieValue == undefined) cookieValue = '없음';
			
			$('#cookieArea').text(cookieValue);
		};
		
		$('#btnAddCookie').on('click', addCookie);
		function addCookie() {
 			$.cookie('key' , 'add'); // 세션 쿠키 : 브라우저가 열려 있는 동안만 유지
//  		$.cookie('key', 'add', { expires: 7, path: '/', domain: 'localhost', secure: false });
// 			 expires : 만료일. 위 예제는 7일.
// 			 path : 경로설정. /는 이 사이트의 모든페이지
// 			 domain : 쿠키가 적용될 도메인. 기본 설정은 쿠키가 만들어진 도메인.
// 			 secure : 기본 설정은 false. true/false 로 입력가능하며 true 일 경우 https 프로토콜만 적용.

			readCookie();
		};
		
		$('#btnEditCookie').on('click', editCookie);
		function editCookie() {
			$.cookie('key', 'edit');
			
			readCookie();
		};
		
		$('#btnRemoveCookie').on('click', removeCookie);
		function removeCookie() {
			$.cookie("key", null, { expires: -1 });
			
			readCookie();
		};
	});

</script>

<p>
	쿠키값 : <span id="cookieArea"></span>
</p>

<p><input type="button" id="btnReadCookie" value="쿠키보기" /></p>
<p><input type="button" id="btnAddCookie" value="쿠키생성" /></p>
<p><input type="button" id="btnEditCookie" value="쿠키수정" /></p>
<p><input type="button" id="btnRemoveCookie" value="쿠키삭제" /></p>