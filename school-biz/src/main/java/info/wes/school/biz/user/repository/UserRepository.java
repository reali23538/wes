package info.wes.school.biz.user.repository;

import java.util.List;

import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.domain.UserCondition;

public interface UserRepository {
	
	public Long count(UserCondition condition);
	
	public List<User> findPagination(UserCondition condition);
	
	public List<User> findAll(UserCondition condition);
	
	public User findById(Long userSeq);
	
	public int insert(User user);
	
	public int update(User user);
	
	public int delete(Long userSeq);
	
	public int deletes(List<User> users);
	
	public User findByLoginId(String id);
	
	public int resetWrongPasswordCount(String id);
	
	public int updateWrongPasswordCount(String id);
	
	public int updateIsLocked(String id);

}