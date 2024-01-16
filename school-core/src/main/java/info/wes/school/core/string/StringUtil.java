package info.wes.school.core.string;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class StringUtil {

	/**
	 * 자릿수만큼의 대소문자 + 숫자가 섞인 랜덤 문자열을 리턴
	 * @param num : 자릿수
	 * @return
	 */
	public static String getRandomString(int num) {
		
		int[] asciis = new int[num];
		for (int j = 0; j < asciis.length; j++) {
			asciis[j] = randomAsciiCode();
		}
		
		StringBuffer sb = new StringBuffer();
		for (int n : asciis) {
			sb.append((char) n);
		}
		return sb.toString();
	}

	/**
	 * 대소문자 + 숫자에 해당하는 아스키코드값을 리턴
	 * @return
	 */
	private static int randomAsciiCode() {
		boolean isNotAlphaOrNum = true;
		
		int out = 0;
		do {
			out = (int) ((Math.random() * 74)) + 48; // 0~73 + 48
			
			// 48~57 : 0~9
			// 65~90 : A~Z
			// 97~122 : a~z
			if ((out >= 48 && out <= 57) 
				|| (out >= 65 && out <= 90)
				|| (out >= 97 && out <= 122)) {
				isNotAlphaOrNum = false;
			}
		} while (isNotAlphaOrNum);
		return out;
	}
	
	/**
	 * 날짜를 지정한 포맷으로 리턴
	 * @param date
	 * @param format : 날짜 포맷 (ex: yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String getFormatDateString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 인코딩
	 * @param data
	 * @param encodeType : UTF-8, EUC-KR
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String data, String encodeType) throws UnsupportedEncodingException {
		return URLEncoder.encode(data, encodeType);
	}
	
	/**
	 * 디코딩
	 * @param data
	 * @param decodeType : UTF-8, EUC-KR
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String data, String decodeType) throws UnsupportedEncodingException {
		return URLDecoder.decode(data, decodeType);
	}
	
	/**
	 * base64인코딩
	 * @param data
	 * @return
	 */
	public static String base64Encode(String data) {
		return new BASE64Encoder().encode(data.getBytes());
	}
	
	/**
	 * base64디코딩
	 * @param encData
	 * @return
	 * @throws IOException
	 */
	public static String base64Decode(String encData) throws IOException {
		return new String(new BASE64Decoder().decodeBuffer(encData));
	}

}
