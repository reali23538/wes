<jsp:directive.page contentType="text/html;charset=UTF-8" />
<jsp:directive.include file="/WEB-INF/layouts/tag_libs.jsp" />
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>구글맵</title>
    <style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #map {
        height: 100%;
      }
    </style>
  </head>
  <body>
    <div id="map"></div>
    <script>
		function initMap() {
			var center = {lat : 36.4193641, lng : 140.3417433};
			var coordinates = {
				1 : {
					position : {lat: 36.4214526, lng: 140.3398911},
					line : [{lat: 36.4214526, lng: 140.3398911},{lat: 36.4212572, lng: 140.343745}],
					image : '<spring:url value="/static/images/common/map/1.png" />',
					description : '1번홀 입니다.'
				},
				2 : {
					position: {lat: 36.4212572, lng: 140.343745},
					line : [{lat: 36.4212572, lng: 140.343745},{lat: 36.4196578, lng: 140.3475913}],
					image : '<spring:url value="/static/images/common/map/2.png" />',
					description : '2번홀 입니다.'
				},
				3 : {
					position: {lat: 36.4196578, lng: 140.3475913},
					line : [{lat: 36.4196578, lng: 140.3475913},{lat: 36.4175944, lng: 140.3472954}],
					image : '<spring:url value="/static/images/common/map/3.png" />',
					description : '3번홀 입니다.'
				},
				4 : {
					position: {lat: 36.4175944, lng: 140.3472954},
					line : [{lat: 36.4175944, lng: 140.3472954},{lat: 36.4171862, lng: 140.3432072}],
					image : '<spring:url value="/static/images/common/map/4.png" />',
					description : '4번홀 입니다.'
				},
				5 : {
					position: {lat: 36.4171862, lng: 140.3432072},
					line : null,
					image : '<spring:url value="/static/images/common/map/5.png" />',
					description : '5번홀 입니다.'
				}
			};
			var population = 0.1;
			
			// 맵 생성
			var map = new google.maps.Map(document.getElementById('map'), {
				zoom : 17,
				center : center,
				mapTypeId : google.maps.MapTypeId.SATELLITE
			});
			
			for (var coordinate in coordinates) {
				// 마커
				var marker = new google.maps.Marker({
					position : coordinates[coordinate].position,
					icon : coordinates[coordinate].image,
					map : map
				});
				attachSecretMessage(marker, coordinates[coordinate].description);
				
				// 선
				var path = coordinates[coordinate].line;
				if (path != null) {
					var line = new google.maps.Polyline({
						path : path,
						geodesic : true,
						strokeColor : '#FF0000',
						strokeOpacity : 1.0,
						strokeWeight : 2
					});
					line.setMap(map);
				}
				
				// 원
				var circle = new google.maps.Circle({
					strokeColor : '#FF0000',
					strokeOpacity : 0.8,
					strokeWeight : 2,
					fillColor : '#FF0000',
					fillOpacity : 0.35,
					map : map,
					center : coordinates[coordinate].position,
					radius : Math.sqrt(population) * 100
				});
			}
		}
		
		function attachSecretMessage(marker, secretMessage) {
			var infowindow = new google.maps.InfoWindow({
				content : secretMessage
			});

			// 클릭이벤트
			marker.addListener('click', function() {
				infowindow.open(marker.get('map'), marker);
			});
		}
    </script>
    <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBuOVc7MT_t9ZZdHQLqPoEhZOXOsBwbFzg&signed_in=true&callback=initMap"></script>
  </body>
</html>