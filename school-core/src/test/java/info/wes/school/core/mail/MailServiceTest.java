package info.wes.school.core.mail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import info.wes.school.core.mail.domain.Mail;
import info.wes.school.core.mail.service.MailService;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath*:/META-INF/spring/applicationContext*.xml"
})
public class MailServiceTest {
	
	@Value("${webapp.path}")
	private String WEB_APP_PATH;
	
	@Autowired
	private MailService mailService;
	
	@Test
	public void diTest() {
		Assert.assertNotNull(mailService);
	}
	
	@Test
	public void sendMailTest() {
		/*
		- pom.xml javax.mail dependency 추가
		- applicationContext-core.xml에 이메일 설정 추가
		- core.properties에 접속정보값 추가
		- 자바소스는 mail package
		- 템플릿 파일은 reset_password.html
		- gmail : 1) imap 설정
				  2) 보안 수준이 낮은 앱의 액세스 허용하기
		*/
		String subject = "임시비밀번호 발급";
		
		String mailTemplate = WEB_APP_PATH + "/mailForm/reset_password.html";

		Map<String, String> bindMap = new HashMap<String, String>();
		bindMap.put("#NAME#", "ahn");
		bindMap.put("#PASSWORD#", "qwer1234");

		Mail mail = new Mail();
		mail.setSubject(subject);
		mail.setToEmail("awj0415@gmail.com");
		mail.setTemplate(mailTemplate); // 템플릿 발송시
//		mail.setContents("#NAME#님, 안녕하세요.<br/> 임시비밀번호 : #PASSWORD#"); // 직접 내용 발송시
		mail.setBindMap(bindMap);
		mailService.sendMail(mail);
	}
	
}
