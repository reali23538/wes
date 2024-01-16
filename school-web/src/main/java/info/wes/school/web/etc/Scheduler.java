package info.wes.school.web.etc;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class Scheduler {

	/*
	 * - applicationContext-web.xml 설정
	 * xmlns:task="http://www.springframework.org/schema/task"
	 * http://www.springframework.org/schema/task
	 * http://www.springframework.org/schema/task/spring-task-3.1.xsd
	 * <task:scheduler id="jobScheduler" pool-size="10" />
	 * <task:annotation-driven scheduler="jobScheduler" />
	 */
	
	// 초 분 시 일 월 주(년)
//	@Scheduled(cron = "0 10 19 * * *")
	public void scheduleTest1(){
		System.out.println("19시 10분에 호출");
	}
	
	// 1000 = 1초
//	@Scheduled(fixedDelay = 60000)
	public void scheduleTest2(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		String dateTime = sdf.format(calendar.getTime());
		
		System.out.println("60초에 한번 호출 (" + dateTime + ")");
	}
}