package info.wes.school.web.external;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MapController {
	
	/**
	 * 지도(구글)
	 * 지도(구글)-복잡한
	 * 지도(네이버)
	 */
	
	// DESC 지도(구글)
	@RequestMapping(value="/maps/google", method=RequestMethod.GET)
	public String googleMap() {
		return "external/map/google_map";
	}
	
	// DESC 지도(구글)-복잡한
	@RequestMapping(value="/maps/google/complex", method=RequestMethod.GET)
	public String googleMapComplex() {
		return "external/map/google_map_complex";
	}
	
	// DESC 지도(네이버)
	@RequestMapping(value="/maps/naver", method=RequestMethod.GET)
	public String naverMap() {
		return "external/map/naver_map";
	}

}
