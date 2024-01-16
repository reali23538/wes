<jsp:directive.page contentType="text/html;charset=UTF-8" />

<style>
	#map {
		width:500px;
		height:500px;
		margin:0px;
		padding:0px
	}
</style>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBuOVc7MT_t9ZZdHQLqPoEhZOXOsBwbFzg&callback=initMap" async defer></script>
<script type="text/javascript">

	// api 키 생성 필요

	function initMap() {
		var latitude = 37.6022482;
		var longitude = 127.0916452;
		var locationName = '우리집';
		
		var map = new google.maps.Map(
			document.getElementById('map'), {
			zoom : 17,
			center : {
				lat : latitude,
				lng : longitude
			}
		});
	
		var marker = new google.maps.Marker({
			map : map,
		  	// Define the place with a location, and a query string.
		  	place : {
		    	location : {lat : latitude, lng : longitude}
		    	// ,query: 'Google, Sydney, Australia'
		 		,query : locationName
		 	}
			// Attributions help users find your site again.
			,attribution : {
				source : 'Google Maps JavaScript API',
				webUrl : 'https://developers.google.com/maps/'
			}
		});
	
		// Construct a new InfoWindow.
		var infoWindow = new google.maps.InfoWindow({
			content : locationName
		});
		
		// Opens the InfoWindow when marker is clicked.
		marker.addListener('click', function() {
			infoWindow.open(map, marker);
		});
	}

</script>

<div id="map"></div>