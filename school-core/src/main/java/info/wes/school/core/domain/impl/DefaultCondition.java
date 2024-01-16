package info.wes.school.core.domain.impl;

import java.io.Serializable;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

public abstract class DefaultCondition implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String q;
	
	private Integer currentIndex = 1;
	
	private Long offset = 0L;
	
	private Long limit = 10L;
	
	private String orderBy;
	
	private SortType sortType;
	
	public enum SortType {
		ASC, DESC
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Integer getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(Integer currentIndex) {
		this.currentIndex = currentIndex;
	}

	public Long getOffset() {
		return offset;
	}

	public void setOffset(Long offset) {
		this.offset = offset;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public SortType getSortType() {
		return sortType;
	}

	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}

	@Override
	public String toString() {
		String queryString = "";
		try {
			queryString = "condition.q=" + URLEncoder.encode(this.q, "utf-8")
						+ "&condition.currentIndex=" + this.currentIndex;

			if(StringUtils.isNotEmpty(this.orderBy)) queryString += "&condition.orderBy=" + this.orderBy;
			if(this.sortType != null) queryString += "&condition.sortType=" + this.sortType;
		} catch(Exception e) {}
		return queryString;
	}	
}
