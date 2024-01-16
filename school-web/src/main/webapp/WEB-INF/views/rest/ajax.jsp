<jsp:directive.page contentType="text/html;charset=UTF-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
 
<script type="text/javascript" src="<spring:url value="/static/scripts/libs/jquery/jquery-2.0.2.min.js" />"></script>
<script type="text/javascript">

	$(function() {
		
		$('#btnSave').on('click', function() {
			$.ajax({
				'url' : '<spring:url value="/ajax" />',
				'type' : 'post',
				'dataType' : 'json', // json, text, xml
				'data' : {
					'id' : 'user1',
					'password' : '1111'
				},
				'success' : function(result) {
					result = $.trim(result);
					
					if (result == '1') {
						alert('저장하였습니다.');
					} else {
						alert(result);
					}
				}
			});
		});
	});

</script>

<button id="btnSave">사용자 저장하기</button>