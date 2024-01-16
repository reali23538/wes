package info.wes.school.core.domain.impl;

import info.wes.school.core.domain.CoreDto;

public class DefaultDto implements CoreDto {

	private static final long serialVersionUID = 5487051746228788384L;

	private Long	totalCount;

	private String	uploadDir;

	@Override
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public Long getTotalCount() {
		return this.totalCount;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

}