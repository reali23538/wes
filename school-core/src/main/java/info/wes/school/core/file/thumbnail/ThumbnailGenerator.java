package info.wes.school.core.file.thumbnail;

import java.io.File;
import java.util.List;

public interface ThumbnailGenerator {

	/**
	 * 썸네일이미지 생성
	 * @param originalFile : 원본파일
	 * @param uploadDir : 경로 (ex: C:\\Projects\\WES\\WORK\\uploaded)
	 * @param targetDir : 경로 (ex: thumbnail)
	 * @param sizeProperties : 썸네일사이즈  (ex: 180X132,40X40,92X70,138X101)
	 * @param compressQuality : 압축배율 (ex: 0.3F)
	 * @return
	 * @throws Exception
	 */
	public List<File> generate(File originalFile, String uploadDir, String targetDir, String sizeProperties, Float compressQuality) throws Exception;

//	String getSizeProperties(Integer width, Integer height);
	
}