package info.wes.school.core.string;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.junit.Test;

import info.wes.school.core.test.TestSupport;

public class StringUtilTest extends TestSupport {
	
	@Test
	public void randomStringTest() {
		System.out.println(StringUtil.getRandomString(20));
	}
	
	@Test
	public void formatDateStringTest() {
		System.out.println(StringUtil.getFormatDateString(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void encodeDecodeTest() throws UnsupportedEncodingException {
		String encData = StringUtil.encode("http://www.naver.com", "UTF-8");
		System.out.println("encData : " + encData);
		
		String decData = StringUtil.decode(encData, "UTF-8");
		System.out.println("decData : " + decData);
	}
	
	@Test
	public void base64EncodeDecodeTest() throws IOException {
		String encData = StringUtil.base64Encode("http://www.naver.com");
		System.out.println("encData : " + encData);
		
		String decData = StringUtil.base64Decode(encData);
		System.out.println("decData : " + decData);
	}

}
