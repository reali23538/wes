package info.wes.school.biz.user.service;

import java.util.List;

import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.domain.UserCondition;
import info.wes.school.biz.user.domain.UserDto;
import info.wes.school.biz.user.domain.excel.UserExcel;

public interface UserService {
	
	public UserDto findPagination(UserDto dto);
	
	public List<User> findAll(UserCondition condition);
	
	public User findById(long userSeq);
	
	public int save(User user);
	
	public int remove(long userSeq);
	
	public int removes(List<User> users);
	
	public int addAll(List<UserExcel> userExcels);
	
	public User findByLoginId(String id);
	
	public int resetWrongPasswordCount(String id);
	
	public void manageLoginFail(String id);

}
