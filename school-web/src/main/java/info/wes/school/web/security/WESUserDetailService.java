package info.wes.school.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.service.UserService;
import info.wes.school.web.rest.StringUtil;

@Component("WESUserDetailService")
public class WESUserDetailService implements UserDetailsService {

	@Autowired
	private UserService service;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		List<GrantedAuthority> autorities = new ArrayList<GrantedAuthority>();
		
		WESUser WESUser = null;
		try {
			User user = service.findByLoginId(StringUtil.DeCode(id));
			
			if(user == null) {
				throw new UsernameNotFoundException("User Not Founded");
			}
			autorities.add(new SimpleGrantedAuthority("ROLE_MEMBER")); // 권한부여
			
			WESUser = new WESUser(user, autorities);
		} catch(Exception e) {
			throw new UsernameNotFoundException("User Not Founded");
		}
		return WESUser;
	}

}
