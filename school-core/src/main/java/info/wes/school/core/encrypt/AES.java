package info.wes.school.core.encrypt;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES암복호화
 * @author awj04
 *
 */
public class AES {

	public static String key = "7yAaxSEXpnQkTbhr"; // 키값은 16비트만 가능

	/**
	 * 암호화
	 * @param message
	 * @return
	 */
	public static String encrypt(String message) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			
			byte[] encrypted = cipher.doFinal(message.getBytes(Charset.forName("UTF-8")));
			message = byteArrayToHex(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 복호화
	 * @param encrypted
	 * @return
	 */
	public static String decrypt(String encrypted) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			
			byte[] byteArray = hexToByteArray(encrypted);
			if (byteArray == null) return encrypted;
	
			byte[] original = cipher.doFinal(byteArray);
			encrypted = new String(original, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encrypted;
	}
	
	/**
	 * byte[] to hex : 바이트 배열을 16진수 문자열로 변환
	 * 
	 * @param byte[]
	 * @return
	 */
	public static String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}
	
	/**
	 * hex to byte[] : 16진수 문자열을 바이트 배열로 변환
	 * 
	 * @param hex string
	 * @return
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

}
