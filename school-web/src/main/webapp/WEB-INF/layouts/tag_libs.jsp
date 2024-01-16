<%-- jstl --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- sitemesh --%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%-- security --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%-- custom tag --%>
<%@ taglib prefix="wes" uri="/WEB-INF/tag/webElementarySchoolTags.tld" %>
<sec:authentication property="principal" var="WESUser" />
<%-- query string --%>
<c:set var="queryString" value="?${pageContext.request.queryString}" />
<c:if test="${queryString eq '?'}">
	<c:set var="queryString" value="" />
</c:if>
<%-- requestURI --%>
<c:set var="requestURI" value="${pageContext.request.requestURI}" />