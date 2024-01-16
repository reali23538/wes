package info.wes.school.biz.user.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.domain.UserCondition;
import info.wes.school.biz.user.repository.UserRepository;

public class MockUserRepositoryImpl implements UserRepository {

	@Override
	public Long count(UserCondition condition) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<User> findPagination(UserCondition condition) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<User> findAll(UserCondition condition) {
		List<User> users = Arrays.asList(
			new User(1L, "awj0603", "dksdnwls!", "안우진", "awj0603@wes.com", true, "수영을 좋아합니다.", new Date(), 0, false),
			new User(2L, "bumjin", "qkrqjawls1!", "박범진", "bumjin@wes.com", true, "농구를 좋아합니다.", new Date(), 0, false),
			new User(3L, "joytouch", "rkdaudtjd!", "강명성", "joytouch@wes.com", true, "축구를 좋아합니다.", new Date(), 1, false),
			new User(4L, "erwins", "tlstmdgks!", "신승한", "erwins@wes.com", true, "테니스 좋아합니다.", new Date(), 0, false),
			new User(5L, "madnite1", "dltkdgh!", "이상호", "madnite1@wes.com", true, "노래를 좋아합니다.", new Date(), 0, false),
			new User(6L, "green", "dhalsrb!", "오민규", "green@wes.com", true, "풋살을 좋아합니다.", new Date(), 0, false)
		);
		List<User> resultUsers = new ArrayList<User>(); // 결과값

		// 검색
		String q = condition.getQ();
		if (StringUtils.isNotEmpty(q)) {
			for (User user : users) {
				String id = user.getId();
				String name = user.getName();
				String email = user.getEmail();
				
				if (id.indexOf(q) != -1 || 
					name.indexOf(q) != -1 ||
					email.indexOf(q) != -1) {
					resultUsers.add(user);
				}
			}
		}
		
		return resultUsers;
	}

	@Override
	public User findById(Long userSeq) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int insert(User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int update(User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int delete(Long userSeq) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int deletes(List<User> users) {
		throw new UnsupportedOperationException();
	}

	@Override
	public User findByLoginId(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int resetWrongPasswordCount(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int updateWrongPasswordCount(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int updateIsLocked(String id) {
		throw new UnsupportedOperationException();
	}

}
