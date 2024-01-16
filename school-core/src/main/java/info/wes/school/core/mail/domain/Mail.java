package info.wes.school.core.mail.domain;

import java.util.HashMap;
import java.util.Map;

import info.wes.school.core.domain.CoreDomain;

public class Mail implements CoreDomain<Long>  {

	private static final long serialVersionUID = 1131924866029636374L;

	private String subject;

	private String fromEmail;

	private String toEmail;

	private String contents;

	private String template;

	private Map<String, String> bindMap = new HashMap<String, String>();

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, String> getBindMap() {
		return bindMap;
	}

	public void setBindMap(Map<String, String> bindMap) {
		this.bindMap = bindMap;
	}

	@Override
	public Long getGenericKey() {
		return null;
	}

	@Override
	public void setId(Long id) {
		
	}
	
}
