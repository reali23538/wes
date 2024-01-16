package info.wes.school.biz.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.domain.UserCondition;
import info.wes.school.biz.user.domain.UserDto;
import info.wes.school.biz.user.domain.excel.UserExcel;
import info.wes.school.biz.user.repository.UserRepository;
import info.wes.school.biz.user.service.UserService;
import info.wes.school.biz.user.service.exception.CustomizeException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDto findPagination(UserDto dto) {
		dto.setUsers(repository.findPagination(dto.getCondition()));
		dto.setTotalCount(repository.count(dto.getCondition()));
		return dto;
	}

	@Override
	public List<User> findAll(UserCondition condition) {
		return repository.findAll(condition);
	}

	@Override
	public User findById(long userSeq) {
		return repository.findById(userSeq);
	}

	@Override
	public int save(User user) {
		user.setCreatedDate(new Date());
		
		if (user.getUserSeq() == null) {
			return repository.insert(user);
		}
		return repository.update(user);
	}

	@Override
	public int remove(long userSeq) {
		return repository.delete(userSeq);
	}
	
	@Override
	public int removes(List<User> users) {
		return repository.deletes(users);
	}

	@Override
	@Transactional
	public int addAll(List<UserExcel> userExcels) {
		Date createdDate = new Date();
		int addRow = 0;
		
		if (userExcels.size() > 5000) {
			throw new CustomizeException("5000개 이하만 등록해주세요(커스텀 익셉션 테스트)");
		}
		
		for (UserExcel userExcel : userExcels) {
			User user = new User();
			user.setId(userExcel.getId());
			user.setPassword(userExcel.getPassword());
			user.setName("");
			user.setEmail("");
			user.setSex(true);
			user.setEtc("");
			user.setCreatedDate(createdDate);
			addRow += repository.insert(user);
		}

		return addRow;
	}
	
	@Override
	public User findByLoginId(String id) {
		return repository.findByLoginId(id);
	}

	@Override
	public int resetWrongPasswordCount(String id) {
		return repository.resetWrongPasswordCount(id);
	}	
	
	@Override
	@Transactional
	public void manageLoginFail(String id) {
		repository.updateWrongPasswordCount(id);
		repository.updateIsLocked(id);
	}

}
