package info.wes.school.core.file.upload.domain;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileData implements Serializable {

	private static final long serialVersionUID = 8214451650541662130L;

	private String originalFilename; // 원본 파일명

	private String name; // 15526286873697089.jpg

	private Long size;

	private String physicalPath; // \\test\\201903

	private String webPath; // /test/201903/15526286873697089.jpg

	private String contentType; // mime타입 or 확장자. ex:image/jpeg or jpg

	private Boolean isTemporary;

	private File file;

	public FileData() {
	}
	
	public FileData(MultipartFile file) {
		if (file == null) return;

		this.originalFilename = file.getOriginalFilename();
		this.contentType = file.getContentType();
		this.size = file.getSize();
	}

	public FileData(File file) {
		if (!file.exists()) return;

		this.originalFilename = file.getName();
		this.contentType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		this.size = file.length();
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getPhysicalPath() {
		return physicalPath;
	}

	public void setPhysicalPath(String physicalPath) {
		this.physicalPath = physicalPath;
	}

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * 확장자 리턴
	 * @return
	 */
	public String getExtension() {
		if (StringUtils.isEmpty(this.getOriginalFilename())) return null;
		return this.originalFilename.substring(this.originalFilename.lastIndexOf(".") + 1);
	}

	public Boolean getIsTemporary() {
		return isTemporary;
	}

	public void setIsTemporary(Boolean isTemporary) {
		this.isTemporary = isTemporary;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

}