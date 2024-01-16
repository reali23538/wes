package info.wes.school.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import info.wes.school.biz.user.domain.User;
import info.wes.school.web.rest.StringUtil;

public class WESUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	
	private User user;

	public WESUser(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getId(), StringUtil.EnCode(user.getPassword()), authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
}
