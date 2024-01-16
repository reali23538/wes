package info.wes.school.web.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import info.wes.school.biz.user.service.UserService;

@Component
public class WESAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private String defaultFailureUrl;
	
	public void setDefaultFailureUrl(String defaultFailureUrl){
		this.defaultFailureUrl = defaultFailureUrl;
	}
	
	@Autowired
	private UserService service;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,	HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		service.manageLoginFail(request.getParameter("j_username"));
		response.sendRedirect(request.getContextPath() + this.defaultFailureUrl + "?login_error=wes_message_login_incorrect_password");
//		response.sendRedirect(request.getContextPath() + this.defaultFailureUrl + "?login_error=wes_message_login_incorrect_password&lang=en");
	}

}
