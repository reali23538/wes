package info.wes.school.core.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

public class FileUtils extends org.apache.commons.io.FileUtils {

	/**
	 * 해당 시스템이 갖고 있는 임시디렉토리를 반환한다.
	 * @return
	 */
	public static String getTempDir() {
		String tempUploadDir = System.getProperty("java.io.tmpdir");
		if(tempUploadDir.endsWith(File.separator)) {
			tempUploadDir = tempUploadDir.substring(0, tempUploadDir.lastIndexOf(File.separator));
		}
		return tempUploadDir;
	}
	
	/**
	 * 디렉토리 생성 (존재하지않을경우)
	 * @param dir
	 */
	public static void makeDir(String dir) {
		String[] temps = dir.split(Pattern.quote(File.separator));
		String tempDir = temps[0] + File.separator + temps[1]; // C:\\Users
		
		for (int i = 2; i < temps.length; i++) {
			tempDir = tempDir + File.separator + temps[i];
			
			File chkDir = new File(tempDir);
			if (!chkDir.exists()) {
				chkDir.mkdir();
			}
		}
	}
	
	/**
	 * 확장자 리턴
	 * @param file
	 * @return
	 */
	public static String fileType(String file) {
		if (file == null || file.trim().equals("") || file.length() < 1) return "";
		
		int index = file.lastIndexOf('.');
		String fileType = file.substring(index + 1);
		
		return fileType.toLowerCase();
	}

	/**
	 * 파일 이동
	 * @param strCpyDir : 복사할 디렉토리 경로
	 * @param strOriDir : 원본 디렉토리 경로
	 * @param fileName : 파일명
	 * @return
	 */
	public static boolean moveFile(String strCpyDir, String strOriDir, String fileName) {
		File in = new File(strOriDir, fileName);
		File out = new File(strCpyDir, fileName);
	
		InputStream is = null;
		OutputStream os = null;
	
		try {
			is = new FileInputStream(in);
			os = new FileOutputStream(out);
	
			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) is.close();
				if (os != null) os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		return in.delete();
	}

	/**
	 * 파일 복사
	 * @param strCpyDir : 복사할 디렉토리 경로
	 * @param strOriDir : 원본 디렉토리 경로
	 * @param fileName : 파일명
	 * @return
	 */
	public static boolean copyFile(String strCpyDir, String strOriDir, String fileName) {
		File in = new File(strOriDir, fileName);
		File out = new File(strCpyDir, fileName);
		
		InputStream is = null;
		OutputStream os = null;
	
		try {
			is = new FileInputStream(in);
			os = new FileOutputStream(out);
		
			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) is.close();
				if (os != null) os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}

}