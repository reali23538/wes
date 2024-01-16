package info.wes.school.core.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

// 자동으로 롤백 시킴
//@Transactional
// 테스트가 사용할 애플리케이션 컨텍스트를 관리하는 역할을 함
@RunWith(SpringJUnit4ClassRunner.class)
// 설정파일적용
@ContextConfiguration(locations = {
	"classpath*:/META-INF/spring/applicationContext*.xml"
})
public class TestSupport {

}