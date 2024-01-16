package info.wes.school.core.file.upload.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import info.wes.school.core.file.FileUtils;
import info.wes.school.core.file.upload.FileUploader;
import info.wes.school.core.file.upload.domain.FileData;

public class DefaultFileUploader implements FileUploader {

//	private String rootUrl; // ?

	private String rootDir; // 파일이 저장될 최상위 디렉토리. C:\\Projects\\WES\\WORK\\uploaded

	private String[] antiFile; // 업로드 불가파일 지정. mime타입. ex:image/jpeg

//	public String getRootUrl() {
//		return rootUrl;
//	}

//	public void setRootUrl(String rootUrl) {
//		this.rootUrl = rootUrl;
//	}

	@Override
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	@Override
	public String getRootDir() {
		return StringUtils.isNotEmpty(rootDir) ? rootDir : "";
	}

	public void setAntiFile(String[] antiFile) {
		this.antiFile = antiFile;
	}

	@Override
	public List<FileData> upload(List<MultipartFile> files, String dir) {
		return this.upload(files, dir, null);
	}

	@Override
	public List<FileData> upload(List<MultipartFile> files, String dir, String[] antiFile) {
		if (CollectionUtils.isEmpty(files)) return null;

		List<FileData> fileDatas = null;
		try {
			String tempDir = FileUtils.getTempDir();
			boolean isTemp = StringUtils.equals(tempDir, this.rootDir);
			
			dir = makeDirs(dir, isTemp);

			for (MultipartFile file : files) {
				if (file.isEmpty()) continue;
				if (isAntiFile(file.getContentType())) continue;
				if (fileDatas == null) fileDatas = new ArrayList<FileData>();

				FileData fileData = new FileData(file);
				String name = this.getUniqueFileName() + "." + fileData.getExtension(); // 15526286873697089.jpg
				fileData.setName(name);
				fileData.setPhysicalPath(File.separator + dir); // \\test\\201903
				fileData.setWebPath("/" + dir.replaceAll("\\\\", "/") + "/" + fileData.getName()); // /test/201903/15526286873697089.jpg
				fileData.setIsTemporary(false);
				File savefile = new File(this.rootDir + File.separator + dir + File.separator + name); //  C:\\Projects\\WES\\WORK\\uploaded\\test\\201903\\15526286873697089.jpg
				file.transferTo(savefile);
				fileData.setFile(savefile);
				fileDatas.add(fileData);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		return fileDatas;
	}

	@Override
	public FileData upload(MultipartFile file, String dir) {
		List<MultipartFile> files = new ArrayList<MultipartFile>();
		files.add(file);
		List<FileData> fileDatas = this.upload(files, dir, null);
		return (CollectionUtils.isNotEmpty(fileDatas)) ? fileDatas.get(0) : null;
	}

	@Override
	public FileData upload(MultipartFile file, String dir, String[] antiFile) {
		List<MultipartFile> files = new ArrayList<MultipartFile>();
		files.add(file);
		List<FileData> fileDatas = this.upload(files, dir, antiFile);
		return (CollectionUtils.isNotEmpty(fileDatas)) ? fileDatas.get(0) : null;
	}

	@Override
	public List<FileData> copy(List<File> files, String dir) {
		if (CollectionUtils.isEmpty(files)) return null;

		List<FileData> fileDatas = null;
		try {
			String tempDir = FileUtils.getTempDir();
			boolean isTemp = StringUtils.equals(tempDir, this.rootDir);
			
			dir = makeDirs(dir, isTemp);

			for (File file : files) {
				if (!file.exists()) continue;
				if (fileDatas == null) fileDatas = new ArrayList<FileData>();

				FileData data = new FileData(file);
				String name = this.getUniqueFileName() + "." + data.getExtension(); // 15526286873697089.jpg
				data.setName(name);
				data.setPhysicalPath(File.separator + dir); // \\test\\201903
				data.setWebPath("/" + dir.replaceAll("\\\\", "/") + "/" + data.getName()); // /test/201903/15526286873697089.jpg
				File savefile = new File(this.rootDir + File.separator + dir + File.separator + name); // C:\\Projects\\WES\\WORK\\uploaded\\test\\201903\\15526286873697089.jpg
				FileUtils.copyFile(file, savefile);
				data.setFile(savefile);
				fileDatas.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileDatas;
	}
	
	@Override
	public FileData copy(File file, String dir) {
		List<File> files = new ArrayList<File>();
		files.add(file);
		List<FileData> fileDatas = copy(files, dir);
		return (CollectionUtils.isNotEmpty(fileDatas)) ? fileDatas.get(0) : null;
	}

	/**
	 * 디렉토리 생성(없을경우만) <br/>
	 * 임시o => dir <br/>
	 * 임시x => dir\\201903
	 *
	 * @param dir
	 * @param isTemporary 임시여부
	 * @return
	 */
	private String makeDirs(String dir, boolean isTemporary) {
		if (!isTemporary) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String month = StringUtils.leftPad(String.valueOf(cal.get(Calendar.MONTH) + 1), 2, "0");
			dir = dir + File.separator + (year + month); // test\\201903
		}

		File tmp = new File(this.getRootDir() + File.separator + dir);
		if (!tmp.exists()) {
			tmp.mkdirs();
		}
		tmp = null;
		return dir;
	}
	
	/**
	 * 업로드 불가파일 여부
	 * @param contentType : mime타입. ex:image/jpeg
	 * @return
	 */
	private boolean isAntiFile(String contentType) {
		if (this.antiFile == null) return false;
		if (this.antiFile.length < 1) return false;

		List<String> antiFiles = Arrays.asList(this.antiFile);
		return antiFiles.contains(contentType);
	}
	
	/**
	 * 유니크한 파일명 리턴
	 * @return
	 */
	private String getUniqueFileName() {
		int randomValue = this.getRandomValue(1000, 9999);
		return Long.toString(System.currentTimeMillis()) + randomValue;
	}
	
	/**
	 * from(포함) ~ to(포함) 사이의 랜덤수를 리턴 <br/>
	 * 100 ~ 999
	 * @param from
	 * @param to
	 * @return
	 */
	private int getRandomValue(int from, int to) {
		return (int)(Math.random() * (Math.abs(to - from) + 1)) + Math.min(from, to);
	}

}
