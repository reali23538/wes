package info.wes.school.web.etc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import info.wes.school.core.cookie.CookieUtil;

@Controller
public class CookieController {
	
	/**
	 * 쿠키읽기(쿠키유틸)
	 * 쿠키생성(쿠키유틸)
	 * 쿠키삭제(쿠키유틸)
	 * 
	 * 쿠키읽기
	 * 쿠키등록
	 * 쿠키수정
	 * 쿠키삭제
	 * 
	 * 쿠키읽기/등록/수정/삭제(스크립트)
	 * 
	 * 로컬스토리지
	 * 세션스토리지
	 */
	
	/**
	 * 쿠키읽기(쿠키유틸)
	 * 
	 * CookieUtil.java
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/cookieUtil", method=RequestMethod.GET)
	public String readCookie(HttpServletRequest request, ModelMap modelMap) {
		String cookieValue = CookieUtil.getCookieValue(request, "key");
		modelMap.addAttribute("cookieValue", cookieValue);
		return "etc/cookie/cookie";
	}
	
	/**
	 * 쿠키생성(쿠키유틸)
	 * 
	 * CookieUtil.java
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cookieUtil/add", method=RequestMethod.GET)
	public String addCookie(HttpServletResponse response) {
		CookieUtil.setCookie(response, "key", "add", 60*60*24*365);
		return "redirect:/cookieUtil";
	}
	
	/**
	 * 쿠키삭제(쿠키유틸)
	 * 
	 * CookieUtil.java
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/cookieUtil/remove", method=RequestMethod.GET)
	public String removeCookie(HttpServletResponse response) {
		CookieUtil.removeCookie(response, "key");
		return "redirect:/cookieUtil";
	}
	
	// DESC 쿠키읽기
	@RequestMapping(value="/cookies", method=RequestMethod.GET)
	public String read(HttpServletRequest request, ModelMap modelMap) {
		Cookie[] cookies = request.getCookies();
		String cookieValue = getCookieValue(cookies, "key");
		
		modelMap.addAttribute("cookieValue", cookieValue);
		return "etc/cookie/cookie";
	}
	private String getCookieValue(Cookie[] cookies, String name) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	// DESC 쿠키등록
	@RequestMapping(value="/cookies/add", method=RequestMethod.GET)
	public String add(HttpServletResponse response) {
		Cookie cookie = new Cookie("key", "add"); // 생성
//		cookie.setMaxAge(60*60*24*365); // 유지기간 - 1년
//		cookie.setPath("/"); // 경로지정
//		cookie.setDomain(".naver.com"); // 특정 도메인에서 유효한 쿠키 생성을 위해 셋팅
		response.addCookie(cookie);
		
		return "redirect:/cookies";
	}
	// DESC 쿠키수정
	@RequestMapping(value="/cookies/edit", method=RequestMethod.GET)
	public String edit(HttpServletResponse response) {
		Cookie cookie = new Cookie("key", "edit"); // 수정
		response.addCookie(cookie);
		
		return "redirect:/cookies";
	}
	// DESC 쿠키삭제
	@RequestMapping(value="/cookies/remove", method=RequestMethod.GET)
	public String remove(HttpServletResponse response) {
		Cookie cookie = new Cookie("key", "");
		cookie.setMaxAge(0); // 소멸시간 / 0은 즉시삭제 // 초단위
		response.addCookie(cookie);
		
		return "redirect:/cookies";
	}
	
	// DESC 쿠키읽기,등록,수정,삭제(스크립트)
	@RequestMapping(value="/cookies/script", method=RequestMethod.GET)
	public String cookieScript() {
		return "etc/cookie/cookie_script";
	}
	
	// DESC 로컬스토리지
	@RequestMapping(value="/storage/local", method=RequestMethod.GET)
	public String localStorage() {
		return "etc/cookie/storage_local";
	}
	// DESC 세션스토리지
	@RequestMapping(value="/storage/session", method=RequestMethod.GET)
	public String sessionStorage() {
		return "etc/cookie/storage_session";
	}

}
