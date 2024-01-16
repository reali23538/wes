package info.wes.school.biz.user.repository.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.domain.UserCondition;
import info.wes.school.biz.user.repository.UserRepository;

@Repository
public class UserRepositoryImpl extends SqlSessionDaoSupport implements UserRepository {

	@Override
	public Long count(UserCondition condition) {
		return getSqlSession().selectOne("User.count", condition);
	}

	@Override
	public List<User> findPagination(UserCondition condition) {
		return getSqlSession().selectList("User.findPagination", condition);
	}

	@Override
	public List<User> findAll(UserCondition condition) {
		return getSqlSession().selectList("User.findAll", condition);
	}

	@Override
	public User findById(Long userSeq) {
		return getSqlSession().selectOne("User.findById", userSeq);
	}

	@Override
	public int insert(User user) {
		return getSqlSession().insert("User.insert", user);
	}

	@Override
	public int update(User user) {
		return getSqlSession().update("User.update", user);
	}

	@Override
	public int delete(Long userSeq) {
		return getSqlSession().delete("User.delete", userSeq);
	}

	@Override
	public int deletes(List<User> users) {
		return getSqlSession().delete("User.deletes", users);
	}
	
	@Override
	public User findByLoginId(String id) {
		return getSqlSession().selectOne("User.findByLoginId", id);
	}	

	@Override
	public int resetWrongPasswordCount(String id) {
		return getSqlSession().update("User.resetWrongPasswordCount", id);
	}

	@Override
	public int updateWrongPasswordCount(String id) {
		return getSqlSession().update("User.updateWrongPasswordCount", id);
	}

	@Override
	public int updateIsLocked(String id) {
		return getSqlSession().update("User.updateIsLocked", id);
	}
	
}
