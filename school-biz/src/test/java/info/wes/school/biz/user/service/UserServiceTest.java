package info.wes.school.biz.user.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.domain.UserCondition;
import info.wes.school.biz.user.domain.UserDto;
import info.wes.school.biz.user.domain.excel.UserExcel;
import info.wes.school.biz.user.service.exception.CustomizeException;
import info.wes.school.core.test.TestSupport;

public class UserServiceTest extends TestSupport {

	@Autowired
	private UserService service;
	
	@Test
	public void di() {
		assertThat(service, notNullValue());
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
	
	/**
	 * 등록
	 * @return
	 */
	private User add() {
		User user = getUser();
		
		int addRow = service.save(user);
		print("addRow", addRow);
		assertTrue(addRow == 1);
		return user;
	}
	
	/**
	 * 2명 등록
	 * @return
	 */
	private List<User> adds() {
		User user1 = getUser();
		User user2 = getUser2();
		
		int addRow = service.save(user1);
		addRow += service.save(user2);
		print("addRow", addRow);
		assertTrue(addRow == 2);
		
		List<User> users = Arrays.asList(user1, user2);
		return users;
	}
	
	/**
	 * 출력
	 * @param title
	 * @param oContents
	 */
	private void print(String title, Object oContents) {
		String contents = "";
		
		if (oContents instanceof String) {
			contents = (String) oContents;
		} else if (oContents instanceof Integer) {
			Integer iContents = (Integer) oContents;
			contents = String.valueOf(iContents);
		} else if (oContents instanceof Long) {
			Long lContents = (Long) oContents;
			contents = String.valueOf(lContents);
		} else if (oContents instanceof Boolean) {
			Boolean bContents = (Boolean) oContents;
			contents = String.valueOf(bContents);
		} else if (oContents instanceof Date) {
			Date dContents = (Date) oContents;
			contents = String.valueOf(dContents);
		} else {
			System.out.println(title + " : " + oContents);
			return;
		}
		
		System.out.println(title + " : " + contents);
	}
	
	@Test
	public void findPagination() {
		// 등록
		adds();
		
		// 조회
		UserCondition condition = getCondition();
		UserDto userDto = new UserDto();
		userDto.setCondition(condition);
		UserDto resultUserDto = service.findPagination(userDto);
		
		List<User> users = resultUserDto.getUsers();
		Long totalCount = resultUserDto.getTotalCount();
		for (User user : users) {
			print("userSeq", user.getUserSeq());
			print("id", user.getId());
			print("name", user.getName());
			print("email", user.getEmail());
			print("sex", user.getSex());
			print("createdDate", user.getCreatedDate());
		}
		print("totalCount", totalCount);
		
		assertTrue(totalCount == 1);
	}

	@Test
	public void findAll() {
		// 등록
		adds();
		
		// 조회
		UserCondition condition = getCondition();
		List<User> users = service.findAll(condition);
		print("user size", users.size());
		assertTrue(users.size() == 1);
	}
	
	@Test
	public void findById() {
		User user = add();
		
		User result = service.findById(user.getUserSeq());
		print("user id", user.getId());
		print("result", result.getId());
		assertThat(user.getId(), is(result.getId()));
	}
	
	@Test
	public void save() {
		// 등록
		User addUser = add();
		
		// 수정
		User editUser = getUser2();
		editUser.setUserSeq(addUser.getUserSeq());
		int editRow = service.save(editUser);
		print("editRow", editRow);
		assertTrue(editRow == 1);

		// 수정확인
		User resultUser = service.findById(addUser.getUserSeq());
		print("addUser name", addUser.getName());
		print("addUser email", addUser.getEmail());
		print("addUser sex", addUser.getSex());
		print("addUser etc", addUser.getEtc());
		print("editUser name", editUser.getName());
		print("editUser email", editUser.getEmail());
		print("editUser sex", editUser.getSex());
		print("editUser etc", editUser.getEtc());
		assertThat(addUser.getName(), not(resultUser.getName()));
		assertThat(addUser.getEmail(), not(resultUser.getEmail()));
		assertThat(addUser.getSex(), not(resultUser.getSex()));
		assertThat(addUser.getEtc(), not(resultUser.getEtc()));
	}
	
	@Test
	public void remove() {
		// 등록
		User user = add();
		Long userSeq = user.getUserSeq();
		
		// 조회
		User savedUser = service.findById(userSeq);
		assertThat(savedUser, is(notNullValue()));
		print("savedUser id", savedUser.getId());
		
		// 삭제
		int removeRow = service.remove(userSeq);
		print("removeRow", removeRow);
		
		// 조회
		savedUser = null;
		savedUser = service.findById(userSeq);
		print("savedUser", savedUser);
		assertThat(savedUser, is(nullValue()));
	}
	
	@Test
	public void removes() {
		// 등록
		List<User> users = adds();
		
		// 삭제
		int removeRow = service.removes(users);
		print("removeRow", removeRow);
		assertTrue(removeRow == 2);
	}
	
	@Test
	public void addAll() {
		UserExcel userExcel1 = new UserExcel();
		userExcel1.setId("awj0603");
		userExcel1.setPassword("p@ssw@rd");
		UserExcel userExcel2 = new UserExcel();
		userExcel2.setId("awj0415");
		userExcel2.setPassword("p@ssw@rd");
		List<UserExcel> userExcels = Arrays.asList(userExcel1, userExcel2);
		
		int addRow = service.addAll(userExcels);
		print("addRow", addRow);
		assertTrue(addRow == 2);
	}
	
	@Test(expected = CustomizeException.class)
	public void addAllFail() {
		// 5000개 이상을 넘겨서 일부러 실패하기
		List<UserExcel> userExcels = new ArrayList<UserExcel>();
		
		for (int i=0; i<5001; i++) {
			UserExcel userExcel = new UserExcel();
			userExcel.setId("awj" + i);
			userExcel.setPassword("p@ssw@rd");
			userExcels.add(userExcel);
		}
		
		int addRow = service.addAll(userExcels);
		print("addRow", addRow);
	}
	
	@Test
	public void findByLoginId() {
		// 등록
		User user = add();
		
		// 조회
		User result = service.findByLoginId(user.getId());
		print("user userSeq", user.getUserSeq());
		print("result userSeq", result.getUserSeq());
		assertThat(user.getUserSeq(), is(result.getUserSeq()));
	}
	
	@Test
	public void resetWrongPasswordCount() {
		// 등록
		User user = add();
		String id = user.getId();
		
		// 로그인 실패 => 카운트 증가
		service.manageLoginFail(id);
		
		// 실패 카운트 조회
		User savedUser = service.findByLoginId(id);
		print("savedUser wrongPasswordCount", savedUser.getWrongPasswordCount());
		
		// 실패 카운트 리셋
		int editRow = service.resetWrongPasswordCount(id);
		print("editRow", editRow);
		
		// 실패 카운트 조회
		savedUser = service.findByLoginId(id);
		print("savedUser wrongPasswordCount", savedUser.getWrongPasswordCount());
		assertTrue(savedUser.getWrongPasswordCount() == 0);
	}
	
	@Test
	public void manageLoginFail() {
		// 사용자 등록
		User user = add();
		String id = user.getId();
		
		boolean isLocked = false;
		for (int i=0; i<5; i++) {
			// 로그인 실패 호출
			service.manageLoginFail(id);
	
			// 실패 카운트, 잠김여부 조회
			User savedUser = service.findByLoginId(id);
			isLocked = savedUser.getIsLocked();
			print("wrongPasswordCount", savedUser.getWrongPasswordCount());
			print("isLocked", isLocked);
		}
		
		assertTrue(isLocked);
	}
	
}
