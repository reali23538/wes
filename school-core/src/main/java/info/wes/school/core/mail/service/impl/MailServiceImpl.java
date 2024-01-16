package info.wes.school.core.mail.service.impl;

import javax.annotation.Resource;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.wes.school.core.mail.MailMatcher;
import info.wes.school.core.mail.domain.Mail;
import info.wes.school.core.mail.service.MailService;

@Transactional
@Service
public class MailServiceImpl implements MailService {

	@Resource(name = "googleMailSender")
	private JavaMailSender mailSender;
	
	@Value("${config.mail.from.email}")
	private String fromEmail;

	public Boolean sendMail(Mail mail) {
		System.out.println("start sendMail");
		MailMatcher mailMatcher = new MailMatcher();

		boolean result = true;
		try {
			mailMatcher.addPattern(mail.getBindMap());
			
			String chunk = null;
			if (StringUtils.isNotEmpty(mail.getTemplate())) { // 템플릿 사용시
				chunk = mailMatcher.getMailBody(mail.getTemplate());
			} else { // 내용 직접 작성시
				chunk = mail.getContents();
			}
			System.out.println("template : " + mail.getTemplate());
			System.out.println("contents : " + mail.getContents());
			System.out.println("chunk : " + chunk);
			if (chunk == null) return false;

			// 메일 발송
			System.out.println("from : " + fromEmail);
			System.out.println("to : " + mail.getToEmail());
			System.out.println("subject : " + mail.getSubject());
			System.out.println("contents : " + mailMatcher.getBoundBody(chunk));
			MimeMessage message = mailSender.createMimeMessage();
			message.setFrom(new InternetAddress(fromEmail)); // from
			message.addRecipient(RecipientType.TO, new InternetAddress(mail.getToEmail())); // to
			message.setSubject(mail.getSubject()); // subject
			message.setText(mailMatcher.getBoundBody(chunk), "utf-8", "html"); // contents
			mailSender.send(message);

			result = true;
		} catch (Exception e) {
			System.out.println("sendMail exception");
			e.printStackTrace();
			result = false;
		}
		
		System.out.println("result : " + result);
		return result;
	}
}
