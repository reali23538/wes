package info.wes.school.web.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.service.UserService;
import info.wes.school.web.security.WESUser;

@Component
public class WESAuthenticationSuccessHandler extends	SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private UserService service;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {		
		WESUser wesUser = (WESUser)authentication.getPrincipal(); // 로그인 세션정보 가져옴
		
		User user = service.findByLoginId(wesUser.getUsername());
		if (user.getIsLocked()) {
			response.sendRedirect(request.getContextPath() + "/static/j_spring_security_logout?spring-security-redirect=/login?login_error=wes_message_is_locked");
			return;
		}

		service.resetWrongPasswordCount(wesUser.getUsername());
		super.onAuthenticationSuccess(request, response, authentication);		
	}

}
