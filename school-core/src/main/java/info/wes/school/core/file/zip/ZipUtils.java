package info.wes.school.core.file.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import info.wes.school.core.file.FileUtils;

public class ZipUtils {

	private static final int COMPRESSION_LEVEL = 8;

	private static final int BUFFER_SIZE = 1024 * 2;

	/**
	 * 지정된 폴더를 zip 파일로 압축한다.
	 * @param sourcePath : 압축 대상 디렉토리. <br/>ex: C:\\Projects\\WES\\WORK\\uploaded\\test\\201903
	 * @param output : 저장할 경로 및 zip파일명. <br/>ex: C:\\Projects\\WES\\WORK\\uploaded\\test\\aaa.zip
	 * @throws Exception
	 */
	public static void zip(String sourcePath, String output) throws Exception {
		File sourceFile = new File(sourcePath);
		
		if (!sourceFile.isFile() && !sourceFile.isDirectory()) {
			throw new Exception("압축대상 파일을 찾을 수가 없습니다.");
		}
		
		if (!FileUtils.fileType(output).equals("zip")) {
			throw new Exception("압축 후 저장 파일명의 확장자를 확인하세요.");
		}
		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ZipOutputStream zos = null;
		
		try {
			fos = new FileOutputStream(output);
			bos = new BufferedOutputStream(fos);
			zos = new ZipOutputStream(bos);
			zos.setLevel(COMPRESSION_LEVEL); // 압축 레벨 - 최대 압축률은 9, 디폴트 8
			zipEntry(sourceFile, sourcePath, zos); // zip 파일 생성
			zos.finish();
		
		} finally {
			if (zos != null) {
				zos.close();
			}
			if (bos != null) {
				bos.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	/**
	 * 압축
	 * @param sourceFile : 압축 대상 디렉토리 (파일타입)
	 * @param sourcePath : 압축 대상 디렉토리. <br/>ex: C:\\Projects\\WES\\WORK\\uploaded\\test\\201903
	 * @param zos : zip outputStream
	 * @throws Exception
	 */
	private static void zipEntry(File sourceFile, String sourcePath, ZipOutputStream zos) throws Exception {
		// sourceFile 이 디렉토리인 경우 하위 파일 리스트 가져와 재귀호출
		if (sourceFile.isDirectory()) {
			if (sourceFile.getName().equalsIgnoreCase(".metadata")) { // .metadata 디렉토리 return
				return;
			}
			
			File[] fileArray = sourceFile.listFiles(); // sourceFile 의 하위 파일 리스트
			for (int i = 0; i < fileArray.length; i++) {
				zipEntry(fileArray[i], sourcePath, zos); // 재귀 호출
			}
		} else {
			BufferedInputStream bis = null;
			try {
				String sFilePath = sourceFile.getPath(); // C:\\Projects\\WES\\WORK\\uploaded\\test\\201903\\15526286873697089.JPG
				
				String zipEntryName = sFilePath.substring(sourcePath.length(), sFilePath.length()); // \\15526286873697089.JPG
				
//				System.out.println("sourcePath : " + sourcePath);
//				System.out.println("sFilePath : " + sFilePath);
//				System.out.println("zipEntryName : " + zipEntryName);
				
				bis = new BufferedInputStream(new FileInputStream(sourceFile));
				ZipEntry zentry = new ZipEntry(zipEntryName);
				zentry.setTime(sourceFile.lastModified());
				zos.putNextEntry(zentry);
				
				byte[] buffer = new byte[BUFFER_SIZE];
				int cnt = 0;
				while ((cnt = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
					zos.write(buffer, 0, cnt);
				}
				zos.closeEntry();
			} finally {
				if (bis != null) {
					bis.close();
				}
			}
		}
	}

	/**
	 * zip파일의 압축을 푼다.
	 *
	 * @param zipFile - 압축 풀 zip파일
	 * @param targetDir - 압축 푼 파일이 들어갈 디렉토리
	 * @param fileNameToLowerCase - 파일명을 소문자로 바꿀지 여부
	 * @throws Exception
	 */
	public static void unzip(File zipFile, File targetDir, boolean fileNameToLowerCase) throws Exception {
		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zentry = null;
		
		try {
			fis = new FileInputStream(zipFile);
			zis = new ZipInputStream(fis);
			
			while ((zentry = zis.getNextEntry()) != null) {
				String fileNameToUnzip = zentry.getName(); // \\15525311320988982.JPG
				if (fileNameToLowerCase) { // fileName toLowerCase
					fileNameToUnzip = fileNameToUnzip.toLowerCase();
				}
//				System.out.println("fileNameToUnzip : " + fileNameToUnzip);
				
				File targetFile = new File(targetDir, fileNameToUnzip);
				
				if (zentry.isDirectory()) {
					FileUtils.makeDir(targetFile.getAbsolutePath()); // 디렉토리 생성
				} else { // File 인 경우
//					System.out.println("targetFile.getParent : " + targetFile.getParent());
					FileUtils.makeDir(targetFile.getParent()); // C:\\Users\\awj04\\Desktop\\result
					unzipEntry(zis, targetFile);
				}
			}
		} finally {
			if (zis != null) {
				zis.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}

	/**
	 * zip파일의 한개 엔트리의 압축을 푼다.
	 *
	 * @param zis : Zip Input Stream
	 * @param targetFile : 압축 풀린 파일
	 * @return
	 * @throws Exception
	 */
	public static File unzipEntry(ZipInputStream zis, File targetFile) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(targetFile);
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = zis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
		return targetFile;
	}
}

