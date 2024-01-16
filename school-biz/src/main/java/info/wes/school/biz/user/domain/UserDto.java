package info.wes.school.biz.user.domain;

import java.util.List;

public class UserDto {

	private User user;

	private List<User> users;
	
	private Long totalCount;
	
	private UserCondition condition;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public UserCondition getCondition() {
		if (condition == null) {
			this.condition = new UserCondition(); 
		}
		return condition;
	}

	public void setCondition(UserCondition condition) {
		this.condition = condition;
	}

}
