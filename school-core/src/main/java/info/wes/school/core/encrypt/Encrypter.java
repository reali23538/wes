package info.wes.school.core.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

/**
 * 암호화만 가능(MD5, SHA1, SHA-256(추천!!)) <br/>
 * 암호화된 문자열길이 동일함. 해쉬값
 * 
 * @author awj04
 *
 */
public class Encrypter {

	public enum EncryptType {
		MD5("MD5"),
		SHA1("SHA1"),
		SHA256("SHA-256");

		private String name;

		EncryptType(String str) {
			this.name = str;
		}

		public String getName() {
			return name;
		}
	}

	public static String encrypt(EncryptType encryptType, String message) {
		if (StringUtils.isEmpty(message)) return null;

		String encrypted = "";
		try {
			MessageDigest md = MessageDigest.getInstance(encryptType.getName());
			byte[] byteData = message.getBytes();
			md.update(byteData);
			byte[] digest = md.digest();

			encrypted = convertToHex(digest);
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encrypted;
		
	}

	private static String convertToHex(byte[] data) {
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < data.length; i++) {
			int halfByte = (data[i] >>> 4) & 0x0F;
			int twoHalfs = 0;
			
			do {
				if ((0 <= halfByte) && (halfByte <= 9)) {
					sb.append((char) ('0' + halfByte));
				} else {
					sb.append((char) ('a' + (halfByte - 10)));
				}
				halfByte = data[i] & 0x0F;
			} while (twoHalfs++ < 1);
		}
		return sb.toString();
	}

}
