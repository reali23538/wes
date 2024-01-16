package info.wes.school.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class StringUtil {
	/**
	 * 기본값
	 */
	public final static String EMPTY_STR = "";

	/**
	 * trans from String to int..
	 * 
	 * @param string
	 *            value..
	 * @return int value..
	 */
	public static int str2int(String val) {
		int ret = 0;
		try {
			if (val != null) {
				val = val.trim();
				ret = Integer.parseInt(val);
			}
		} catch (NumberFormatException e) {
		}

		return ret;
	}

	/**
	 * trans from String to int..
	 * 
	 * @param string
	 *            value..
	 * @return int value..
	 */
	public static long str2long(String val) {
		long ret = 0;
		try {
			val = val.trim();
			ret = Long.parseLong(val);
		} catch (NumberFormatException e) {
		}

		return ret;
	}

	/**
	 * <br>
	 * String to jumin number or ship_no style..
	 * 
	 * @param str
	 *            14 length string like 1234561234567.
	 * @return jumin number style(123456-1234567).
	 */
	public static String str2jumin(String str) {
		if (str == null)
			return null;

		String ret = str;
		try {
			ret = str.substring(0, 6) + "-" + str.substring(6, 13);
		} catch (Exception e) {
		}

		return ret;
	}

	/**
	 * Exception.printStackTrace내용을 String으로 변화시킴.
	 * 
	 * @param : 예외.
	 * @return 메세지.
	 */
	public static String getStackTraceString(Throwable e) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(bytes, true);
		e.printStackTrace(writer);

		return bytes.toString();
	}

	/**
	 * MD5
	 * 
	 * @param input
	 * @return
	 * @throws SunCCException
	 */
	public static byte[] getMd5(String input) throws Exception {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			return messageDigest.digest(input.getBytes());
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new Exception(noSuchAlgorithmException.getMessage());
		}
	}

	/**
	 * SHA256
	 * 
	 * @param input
	 * @return
	 * @throws SunCCException
	 */
	public static String getSha256(String password, String salt) throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			password = password + salt;
			byte[] digest2 = digest.digest(password.getBytes("UTF-8"));
			for (int i = 0; i < digest2.length; i++) {
				sb.append(Integer.toString((digest2[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
		} catch (NoSuchAlgorithmException e1) {
			throw new Exception(e1.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e.getMessage());
		}
		return sb.toString();
	}

	/**
	 * 패스워드 입력받은 원본을 MD5 하고 다시 SHA256 하는 로직
	 * 
	 * @param password
	 * @return
	 * @throws SunCCException
	 */
//	public static String getPasswordHash(String password) throws Exception {
//		if (password == null || password.equals(StringUtil.EMPTY_STR))
//			return StringUtil.EMPTY_STR;
//		String md5Hash = ByteUtils.toHexString(StringUtil.getMd5(password));
//		if (md5Hash == null || md5Hash.equals(StringUtil.EMPTY_STR))
//			return StringUtil.EMPTY_STR;
//		return StringUtil.getSha256(md5Hash, "mx1%d0t2");
//	}

	/**
	 * SHA256
	 * @param input
	 * @return
	 * @throws GmsException
	 */
	public static String getSha256EncodingBase64(String password, String salt) throws Exception{
		String resultDigest = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			password = password + salt;
			byte[] digest2 = digest.digest(password.getBytes("UTF-8"));
			resultDigest = StringUtil.encodeBase64(digest2);
		} catch (NoSuchAlgorithmException e1) {
			throw new Exception( e1.getMessage() );
		} catch (UnsupportedEncodingException e) {
			throw new Exception( e.getMessage() );
		}
		return resultDigest;
	}

	public static String encodeBase64(byte[] binaryData) throws UnsupportedEncodingException{
		byte[] buf = null;
		buf = (byte[])Base64.encodeBase64(binaryData);
		return new String(buf, "utf-8");
	}
		
	/**
	 * 자바스크립트 문법 리턴
	 * @param alertMessage
	 * @param replaceLocation
	 * @return
	 */
	public static String getJsAlertRedirectLocation( String alertMessage, String replaceLocation ) {
		return String.format( "<script type='text/javascript'>\r\n//<![CDATA[\r\nalert('%s');\r\ndocument.location.href='%s';\r\n//]]>\r\n</script>",alertMessage,replaceLocation);
	}
	
	/**
	 * 자바스크립트 문법 리턴
	 * @param alertMessage
	 * @param replaceLocation
	 * @return
	 */
	public static String getJsAlertReplaceLocation( String alertMessage, String replaceLocation ) {
		return String.format( "<script type='text/javascript'>\r\n//<![CDATA[\r\nalert('%s');\r\ndocument.location.replace('%s');\r\n//]]>\r\n</script>",alertMessage,replaceLocation);
	}
	
	/**
	 * 자바스크립트 문법 리턴
	 * @param alertMessage
	 * @param replaceLocation
	 * @return
	 */
	public static String getJsAlertHistoryBack( String alertMessage, int goDeep ) {
		return String.format( "<script type='text/javascript'>\r\n//<![CDATA[\r\nalert('%s');\r\nhistory.go(%s);\r\n//]]>\r\n</script>",alertMessage,goDeep);
	}
	
	/**
	 * 자바스크립트 문법 리턴
	 * @param alertMessage
	 * @param replaceLocation
	 * @return
	 */
	public static String getJsAlertClose( String alertMessage, String script ) {
		return String.format( "<script type='text/javascript'>\r\n//<![CDATA[\r\nalert('%s');\r\n%s;\r\n//]]>\r\n</script>",alertMessage,script);
	}

	/**
	 * param 포멧으로 시간 정보 리턴
	 * @return
	 */
	public static String getTimeFormat(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

    /**
     * 인증번호 생성
     * @param len
     * @return
     */
    public static String shuffleAuthNumber(int len) {
  	  char[] charSet = new char[]{
  	    '0','1','2','3','4','5','6','7','8','9'
  	  };
  	  int idx = 0;
  	  StringBuffer sb = new StringBuffer();
  	  for(int i=0; i<len; i++){
  	   idx = (int)(charSet.length*Math.random());
  	   sb.append(charSet[idx]);
  	  }
  	  return sb.toString();
  	}
   
	/**
	 * 페이징 시에 페이지 정보
	 * @param totalcnt
	 * @param pagesize
	 * @param blocksize
	 * @param cur_page
	 * @return
	 */
	public static int[] getPaingInfo(int totalcnt, int pagesize, int blocksize, int cur_page) {
        int[] ret = { 1, 1, 1 }; // 시작번호, 끝번호, 전체페이지수
        if (totalcnt > 0) {
            int pagecnt = (int) (Math.ceil((double) totalcnt / (double) pagesize)); // 전체 페이지수를 구한다.
            ret[0] = (int) (((int) Math.floor((double) (cur_page-1)  / (double) blocksize))*blocksize + 1);
            ret[1] = ret[0] + (blocksize - 1) > pagecnt ? pagecnt : ret[0] + (blocksize - 1);
            ret[2] = pagecnt;
        }
        return ret;
    }
	
	/**
	 * <br>
	 * String(20010522) to date style(2001.05.22). <br>
	 * When exception ocurr, just return parameter
	 * 
	 * @param str
	 *            8 length string like 20010522.
	 * @return date style(2001.05.22).
	 */
	public static String str2date(String str) {
		if (str == null)
			return null;

		String ret = str;
		try {
			str = str.trim();
			int slen = str.length();

			if (slen == 4) {
				ret = str.substring(0, 2) + "." + str.substring(2, 4);
			} else if (slen == 8) {
				ret = str.substring(0, 4) + "." + str.substring(4, 6) + "." + str.substring(6, 8);
			} else if (slen == 23) { // datetime(timestamp)
				ret = str.substring(0, 19);
			} else {

			}
		} catch (Exception e) {
			ret = str;
		}

		return ret;
	} // the end of str2date()..
	
	/**
	 * 월과 일을 무조건 두자리로 만들어 준다.
	 * 
	 * @param sDate
	 *            day or month int.
	 * @return seems like 01, 12, 05....
	 */
	public static String getTwo(int iDate) {
		return getTwo(Integer.toString(iDate));
	}

	/**
	 * 월과 일을 무조건 두자리로 만들어 준다.
	 * 
	 * @param : day or month.
	 * @return seems like 01, 12, 05....
	 */
	public static String getTwo(String sDate) {
		if (sDate == null || sDate.equals(""))
			return "";

		if (sDate.length() == 2)
			return sDate;

		sDate = "00" + sDate;
		return sDate.substring(1, 3);
	}
	
	/**
	 * 현재의 날짜를 8자리 character로 변환해서 구함.
	 * 
	 * @return 8자리의 character.
	 */
	public static String getNow() {
		Calendar now = Calendar.getInstance();
		String year = Integer.toString(now.get(Calendar.YEAR));
		String month = getTwo(now.get(Calendar.MONTH) + 1);
		String day = getTwo(now.get(Calendar.DATE));

		return year + month + day;
	}
	
	/**
	 * trans from String to double..
	 * 
	 * @param string
	 *            value..
	 * @return double value..
	 */
	public static double str2double(String val) {
		double ret = 0;
		try {
			val = val.trim();
			ret = Double.parseDouble(val);
		} catch (NumberFormatException e) {
		}

		return ret;
	}
	
	public static String str2time(String str) {
		if (str == null)
			return null;

		String ret = str;
		try {
			str = str.trim();
			ret = str.substring(0, 2) + ":" + str.substring(2, 4);
		} catch (Exception e) {
			ret = str;
		}

		return ret;
	}
	/**
	 * <br>
	 * String 의 null여부 체크
	 * 
	 * @param str
	 *            String value
	 * @return true : null, false : not null
	 */
	public static boolean isNull(String str) {
		return StringUtils.isEmpty(str);
	}
	
	/**
	 * <br>
	 * stiring을 년월 구분(-)으로 표시
	 * 
	 * @param str
	 *            string
	 * @return 20030501 ==> 2003-05-01
	 */
	public static String str2ym3(String str) {
		String ret = str;
		try {
			str = str.trim();
			if (str.length() == 8) {
				ret = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
			}
		} catch (Exception e) {
			ret = str;
		}

		return ret;
	}
	
	public static String EnCode(String param)

	{

	    StringBuffer    sb  = new StringBuffer();




	    if(param == null)

	    {

	        sb.append("");

	    }

	    else

	    {

	        if(param.length()>0)

	        {

	            for(int i=0; i<param.length(); i++)

	            {

	                String  len = ""+((int)param.charAt(i));

	                sb.append(len.length());

	                sb.append(((int)param.charAt(i)));

	            }

	        }

	    }




	    return sb.toString();

	}




	public static String DeCode(String param)

	{

	    StringBuffer    sb  = new StringBuffer();

	    int             pos = 0;

	    boolean         flg = true;




	    if(param!=null)

	    {

	        if(param.length()>1)

	        {

	            while(flg)

	            {

	                String  sLen    = param.substring(pos,++pos);

	                int     nLen    = 0;




	                try

	                {

	                    nLen    = Integer.parseInt(sLen);

	                }

	                catch(Exception e)

	                {

	                    nLen   = 0;

	                }




	                String  code    = "";

	                if((pos+nLen)>param.length())

	                    code    = param.substring(pos);

	                else

	                    code    = param.substring(pos,(pos+nLen));




	                pos += nLen;




	                sb.append(((char) Integer.parseInt(code)));




	                if(pos >= param.length())

	                    flg = false;

	            }

	        }

	    }

	    else

	    {

	        param = "";

	    }




	    return sb.toString();

	}
}
