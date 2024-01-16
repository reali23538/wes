<jsp:directive.page contentType="text/html;charset=UTF-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<p>
	쿠키값(자바) : ${cookieValue}
	<c:if test="${empty cookieValue}">없음</c:if>
</p>
<p>
	쿠키값(jstl) : ${cookie.key.value}
	<c:if test="${empty cookie.key.value}">없음</c:if>
</p>
<p><a href="/cookieUtil">*쿠키읽기(쿠키유틸)</a></p>
<p><a href="/cookieUtil/add">*쿠키생성(쿠키유틸)</a></p>
<p><a href="/cookieUtil/remove">*쿠키삭제(쿠키유틸)</a></p>
<br/>

<p><a href="/cookies">쿠키보기</a></p>
<p><a href="/cookies/add">쿠키생성</a></p>
<p><a href="/cookies/edit">쿠키수정</a></p>
<p><a href="/cookies/remove">쿠키삭제</a></p>