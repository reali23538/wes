package info.wes.school.biz.user.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.domain.UserCondition;
import info.wes.school.biz.user.repository.impl.MockUserRepositoryImpl;
import info.wes.school.core.test.TestSupport;

public class UserRepositoryTest extends TestSupport {
	
	@Autowired
	private UserRepository repository;
	
	@Test
	public void di() {
//		Assert.assertNotNull(repository);
		assertThat(repository, is(notNullValue()));
	}
	
	private User getUser() {
		User user = new User();
		user.setId("awj0603");
		user.setPassword("p@ssw@rd");
		user.setName("안우진");
		user.setEmail("awj0603@gmail.com");
		user.setSex(true);
		user.setEtc("프리랜서 개발자 입니다.");
		
		return user;
	}
	
	private User getUser2() {
		User user = new User();
		user.setId("awj0415");
		user.setPassword("p@ssw@rd");
		user.setName("안우지");
		user.setEmail("awj0415@gmail.com");
		user.setSex(false);
		user.setEtc("프리랜서 디자이너 입니다.");
		
		return user;
	}
	
	private UserCondition getCondition() {
		UserCondition condition = new UserCondition();
		condition.setQ("awj0603");
//		condition.setQ("awj0415");
		
		return condition;
	}
	
	@Test
	public void count() {
		insert();

		UserCondition condition = getCondition();
		
		Long count = repository.count(condition);
		System.out.println("count : " + count);
		assertTrue(count > 0);
	}
	
	@Test
	public void findPagination() {
		insert();
		
		UserCondition condition = getCondition();
		
		List<User> users = repository.findPagination(condition);
		System.out.println("user size : " + users.size());
		assertTrue(users.size() > 0);
	}
	
	@Test
	public void findAll() {
		insert();
		
		UserCondition condition = getCondition();
		
		List<User> users = repository.findAll(condition);
		System.out.println("user size : " + users.size());
		assertTrue(users.size() > 0);
	}
	
	@Test
	public void mockFindAll() {
		UserCondition condition = getCondition();
		
		MockUserRepositoryImpl mockUserRepository = new MockUserRepositoryImpl();
		List<User> users = mockUserRepository.findAll(condition);
		System.out.println("user size : " + users.size());
		assertTrue(users.size() > 0);
	}
	
	@Test
	public void findById() {
		User user = getUser();
		repository.insert(user);
		
		User resultUser = repository.findById(user.getUserSeq());
		
		System.out.println("userSeq : " + user.getUserSeq());
		System.out.println("id : " + user.getId());
		System.out.println("result userSeq : " + resultUser.getUserSeq());
		System.out.println("result id : " + resultUser.getId());
		assertThat(user.getUserSeq(), is(resultUser.getUserSeq()));
		assertThat(user.getId(), is(resultUser.getId()));
	}
	
	@Test
	public void insert() {
		User user = getUser();

		int insertedRow = repository.insert(user);
		System.out.println("insertedRow : " + insertedRow);
		assertTrue(insertedRow == 1);
	}
	
	@Test
	public void update() {
		User user = getUser();
		repository.insert(user);
		System.out.println("user name : " + user.getName());
		System.out.println("user email : " + user.getEmail());
		System.out.println("user sex : " + (user.getSex() ? "남자" : "여자"));
		System.out.println("user etc : " + user.getEtc());

		user.setName("안우지");
		user.setEmail("awj0415@gmail.com");
		user.setSex(false);
		user.setEtc("프리랜서 디자이너 입니다.");
		int updatedRow = repository.update(user);
		System.out.println("updatedRow : " + updatedRow);
		
		User resultUser = repository.findById(user.getUserSeq());
		System.out.println("resultUser name : " + resultUser.getName());
		System.out.println("resultUser email : " + resultUser.getEmail());
		System.out.println("resultUser sex : " + (resultUser.getSex() ? "남자" : "여자"));
		System.out.println("resultUser etc : " + resultUser.getEtc());
		
		assertTrue(updatedRow > 0);
	}
	
	@Test
	public void delete() {
		User user = getUser();
		repository.insert(user);
		
		int deletedRow = repository.delete(user.getUserSeq());
		System.out.println("deletedRow : " + deletedRow);
		assertTrue(deletedRow == 1);
	}
	
	@Test
	public void deletes() {
		// 등록
		User user1 = getUser();
		User user2 = getUser2();
		repository.insert(user1);
		repository.insert(user2);
		
		// 삭제
		List<User> users = Arrays.asList(user1, user2);
		int deletedRow = repository.deletes(users);
		System.out.println("deletedRow : " + deletedRow);
		assertTrue(deletedRow == 2);
	}
	
	@Test
	public void findByLoginId() {
		String id = "user1";
		User user = repository.findByLoginId(id); 
		
		assertThat(user, notNullValue());
		System.out.println("id : " + user.getId());
	}
	
	@Test
	public void resetWrongPasswordCountTest() {
		String id = "user1";
		int resetRow = repository.resetWrongPasswordCount(id);
		
		System.out.println("resetRow : " + resetRow);
		assertTrue(resetRow == 1);
	}
	
	@Test
	public void updateWrongPasswordCount() {
		String id = "user1";
		int updatedRow = repository.updateWrongPasswordCount(id);
		
		System.out.println("updatedRow : " + updatedRow);
		assertTrue(updatedRow == 1);
	}
	
	@Test
	public void updateIsLocked() {
		String id = "user1";
		
		for (int i=0; i<5; i++) {
			repository.updateWrongPasswordCount(id);
		}
		int updatedRow = repository.updateIsLocked(id);
		
		System.out.println("updatedRow : " + updatedRow);
		assertTrue(updatedRow == 1);
	}

}