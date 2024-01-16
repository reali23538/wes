package info.wes.school.biz.user.domain.excel;

import info.wes.school.core.parser.annotation.Parse;

public class UserExcel {

	@Parse(index=0)
	private String id;
	
	@Parse(index=1)
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
