package info.wes.school.biz.user.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class User extends Object {

	private Long userSeq;
	
	@NotEmpty
	@Length(min=3, max=8)
	private String id;
	
	@NotEmpty
	private String password;
	
	private String name;
	
	@Email
	private String email;
	
	private Boolean sex;
	
	private String etc;
	
	private Date createdDate;
	
	private Integer wrongPasswordCount;
	
	private Boolean isLocked;
	
	public User() {
	}
	
	public User(Long userSeq, String id, String password, String name, String email,
				Boolean sex, String etc, Date createdDate, Integer wrongPasswordCount, Boolean isLocked) {
		super();
		this.userSeq = userSeq;
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.sex = sex;
		this.etc = etc;
		this.createdDate = createdDate;
		this.wrongPasswordCount = wrongPasswordCount;
		this.isLocked = isLocked;
	}
	
	public Long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getSex() {
		return sex;
	}
	
	public String getSexDesc() {
		if (this.sex == null) return "-";
		
		if (this.sex) {
			return "남자";
		} else {
			return "여자";
		}
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	
	public String getFormattedCreatedDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String formattedCreatedDate = sdf.format(this.createdDate);
		return formattedCreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getWrongPasswordCount() {
		return wrongPasswordCount;
	}

	public void setWrongPasswordCount(Integer wrongPasswordCount) {
		this.wrongPasswordCount = wrongPasswordCount;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

}
