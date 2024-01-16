<jsp:directive.page contentType="text/html;charset=UTF-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp"/>

<script type="text/javascript" src="<spring:url value="/static/scripts/libs/jquery/jquery-2.0.2.min.js" />"></script>
<script type="text/javascript" src="http://openapi.map.naver.com/openapi/v2/maps.js?clientId=HLuitXjUYLYtBE1YM4m4"></script>
<script type="text/javascript">

	// 애플리케이션 등록 필요함 (https://developers.naver.com/openapi)
	// 웹:10만, 모바일:5천개 까지 가능
	// 그 이상할 경우 제휴신청해야함 

	$(function() {
		var id = 'map';
		var latitude = 37.6022482;
		var longitude = 127.0916452;
		var width = 500;
		var height = 500;

		var oPoint = new nhn.api.map.LatLng(latitude, longitude);
		nhn.api.map.setDefaultPoint('LatLng');
		oMap = new nhn.api.map.Map('map', {
					point : oPoint,
					zoom : 10, // - 초기 줌 레벨은 10으로 둔다.
					enableWheelZoom : true,
					enableDragPan : true,
					enableDblClickZoom : true,
					mapMode : 0,
					activateTrafficMap : true,
					activateBicycleMap : true,
					minMaxLevel : [ 1, 14 ],
					size : new nhn.api.map.Size(width, height)
				});
		oMap.setCenter(oPoint);
		
		var oSize = new nhn.api.map.Size(28, 37);
		var oOffset = new nhn.api.map.Size(14, 37);
		var oIcon = new nhn.api.map.Icon('http://static.naver.com/maps2/icons/pin_spot2.png', oSize, oOffset);
		var oMarker = new nhn.api.map.Marker(oIcon, { title : '마커 : ' + oPoint.toString() });
		oMarker.setPoint(oPoint);
		oMap.addOverlay(oMarker);
		
		<%-- 사이즈 조절 --%>
		var oSlider = new nhn.api.map.ZoomControl();
        oMap.addControl(oSlider);
        oSlider.setPosition({
                top : 10,
                left : 10
        });
	});

</script>

<div id="map"></div>