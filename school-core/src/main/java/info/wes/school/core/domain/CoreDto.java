package info.wes.school.core.domain;

import java.io.Serializable;

public interface CoreDto extends Serializable {

	public void setTotalCount(Long totalCount);

	public Long getTotalCount();

}