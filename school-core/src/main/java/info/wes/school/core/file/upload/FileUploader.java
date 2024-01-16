package info.wes.school.core.file.upload;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import info.wes.school.core.file.upload.domain.FileData;

/**
 * 파일 업로드시에 사용하는 File Uploader
 * Spring configuration 에 FileUploader 의 구현체를 bean 으로 등록하고
 * 파일이 저장될 root directory 를 설정하여 사용한다.
 * @author MZ
 *
 */
public interface FileUploader {

	/**
	 * 파일이 저장될 최상위 디렉토리
	 * @param dir : C:\\Projects\\WES\\WORK\\uploaded
	 */
	public void setRootDir(String dir);

	/**
	 * 파일이 저장될 최상위 디렉토리
	 * @return
	 */
	public String getRootDir();
	
	/**
	 * 여러개의 파일을 dir에 업로드 한다.
	 * @param files
	 * @param dir
	 * @return
	 */
	public List<FileData> upload(List<MultipartFile> files, String dir);

	/**
	 * 여러개의 파일을 dir에 업로드 한다. (업로드 불가파일은 제외하고 업로드)
	 * @param files
	 * @param dir
	 * @param antiFile : 업로드 불가파일. mime타입. ex:image/jpeg
	 * @return
	 */
	public List<FileData> upload(List<MultipartFile> files, String dir, String[] antiFile);

	/**
	 * 파일을 dir에 업로드 한다.
	 * @param file
	 * @param dir
	 * @return
	 */
	public FileData upload(MultipartFile file, String dir);

	/**
	 * 파일을 dir에 업로드 한다. (업로드 불가파일은 제외하고 업로드)
	 * @param file
	 * @param dir
	 * @param antiFile : 업로드 불가파일. mime타입. ex:image/jpeg
	 * @return
	 */
	public FileData upload(MultipartFile file, String dir, String antiFile[]);

	/**
	 * 파일들을 dir로 복사한다.
	 * @param files
	 * @param dir
	 * @return
	 */
	public List<FileData> copy(List<File> files, String dir);
	
	/**
	 * 파일을 dir로 복사한다.
	 * @param files
	 * @param dir
	 * @return
	 */
	public FileData copy(File files, String dir);

}