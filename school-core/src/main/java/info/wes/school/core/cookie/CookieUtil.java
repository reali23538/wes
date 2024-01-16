package info.wes.school.core.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class CookieUtil {
	
	/**
	 * 쿠키 읽기
	 * @param request
	 * @param key : 쿠키키
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String key) {
		Cookie cookies[] = request.getCookies();
		
		Cookie cookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equalsIgnoreCase(key)) {
					cookie = cookies[i];
					break;
				}
			}
		}
		return cookie;
	}

	/**
	 * 쿠키값 리턴
	 * @param request
	 * @param key : 쿠키키
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie cookie = getCookie(request, key);
		
		String cookieValue = null;
		if (cookie != null) {
			cookieValue = cookie.getValue();
//			cookieValue = HttpUtil.decode(cookie.getValue());
		}
		return cookieValue;
	}

	/**
	 * 쿠키 생성
	 * @param response
	 * @param key : 쿠키키
	 * @param val : 쿠키값
	 */
	public static void setCookie(HttpServletResponse response, String key, String val) {
		setCookie(response, key, val, ((String) (null)));
	}

	/**
	 * 쿠키 생성
	 * @param response
	 * @param key : 쿠키키
	 * @param val : 쿠키값
	 * @param age : 만료시간(초)
	 */
	public static void setCookie(HttpServletResponse response, String key, String val, int age) {
		setCookie(response, key, val, age, null, null);
	}

	/**
	 * 쿠키 생성
	 * @param response
	 * @param key : 쿠키키
	 * @param val : 쿠키값
	 * @param domain : 특정 도메인에서 유효한 쿠키 생성을 위해 셋팅 (선택). ex:".naver.com"
	 */
	public static void setCookie(HttpServletResponse response, String key, String val, String domain) {
		setCookie(response, key, val, -1, null, domain);
	}

	/**
	 * 쿠키 생성
	 * @param response
	 * @param key : 쿠키키
	 * @param val : 쿠키값
	 * @param age : 만료시간(초)
	 * @param path : 경로지정 (선택) default:/
	 * @param domain : 특정 도메인에서 유효한 쿠키 생성을 위해 셋팅 (선택). ex:".naver.com"
	 */
	public static void setCookie(HttpServletResponse response, String key, String val, int age, String path, String domain) {
//		Cookie cookie = new Cookie(key, HttpUtil.encode(val));
		Cookie cookie = new Cookie(key, val);
		
		if (StringUtils.isBlank(path)) {
			path = "/";
		}
		cookie.setPath(path);
		cookie.setMaxAge(age);
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	/**
	 * 쿠키 생성(직접)
	 * @param response
	 * @param cookie : 쿠키
	 */
	public static void setCookie(HttpServletResponse response, Cookie cookie) {
		response.addCookie(cookie);
	}

	/**
	 * 쿠키 제거
	 * @param response
	 * @param key : 쿠키키
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		removeCookie(response, key, null);
	}

	/**
	 * 쿠키 제거
	 * @param response
	 * @param key : 쿠키키
	 * @param domain : 특정 도메인에서 유효한 쿠키 생성을 위해 셋팅 (선택). ex:".naver.com"
	 */
	public static void removeCookie(HttpServletResponse response, String key, String domain) {
		Cookie cookie = new Cookie(key, "");
		
		cookie.setMaxAge(0);
		cookie.setPath("/");
		if (domain != null) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}
}