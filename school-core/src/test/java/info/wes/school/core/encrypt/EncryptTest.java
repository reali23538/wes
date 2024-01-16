package info.wes.school.core.encrypt;

import org.junit.Test;

import info.wes.school.core.encrypt.Encrypter.EncryptType;
import info.wes.school.core.test.TestSupport;

public class EncryptTest extends TestSupport {
	
	@Test
	public void aesTest() {
		/**
		 * AES.java (암복호화 가능)
		 */
		String message = "awj0415@gmail.com";
		String encryptMessage = AES.encrypt(message);
		System.out.println("encryptMessage : " + encryptMessage);
		
		String decryptMessage = AES.decrypt(encryptMessage);
		System.out.println("decryptMessage : " + decryptMessage);
	}
	
	@Test
	public void encrypterTest() {
		
		/**
		 * Encrypter.java (암호화만 가능. 암호화된 문자열길이 동일)
		 */
//		String message = "awj0415";
		String message = "awj0415@gmail.com";
		String encryptMessage = Encrypter.encrypt(EncryptType.SHA256, message);
		System.out.println("encryptMessage : " + encryptMessage);
	}
	
}
