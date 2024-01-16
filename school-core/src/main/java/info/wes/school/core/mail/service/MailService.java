package info.wes.school.core.mail.service;

import info.wes.school.core.mail.domain.Mail;

public interface MailService {

	public Boolean sendMail(Mail mail);
	
}
