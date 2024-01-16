package info.wes.school.core.paging.basic.impl;

import org.apache.commons.lang.StringUtils;

import info.wes.school.core.paging.basic.Paging;

/**
 * 이전페이지로(<)시 1페이지 앞으로 이동 <br/>
 * 다음페이지로(>)시 1페이지 뒤로 이동
 * 
 * @author awj04
 *
 */
public class DefaultPaging implements Paging {

	private String contextPath;

	// 페이지번호 엘리먼트 관련
	private String numberWrapElement;

	private String numberWrapCssClass = "page";

	private String numberElement;

	private boolean useFirst = true; // 처음페이지로(<<) 사용여부

	private boolean useLast = true; // 마지막페이지로(>>) 사용여부
	
	private Integer rowScale = 10; // 한페이지에 몇row씩 보여줄지

	private Integer pageScale = 10; // 한블록에 몇페이지씩 보여줄지 (페이지번호)

	private String firstImage = "/static/images/common/btn/btn_first.gif"; // 처음페이지로(<<) 이미지 경로

	private String prevImage = "/static/images/common/btn/btn_prev.gif"; // 이전페이지로(<) 이미지 경로
	
	private String nextImage = "/static/images/common/btn/btn_next.gif"; // 다음페이지로(>) 이미지 경로
	
	private String lastImage = "/static/images/common/btn/btn_last.gif"; // 마지막페이지로(>>) 이미지 경로

	private String firstCssClass = "first"; // 처음페이지로(<<) css 클래스명

	private String prevCssClass = "prev"; // 이전페이지로(<) css 클래스명

	private String nextCssClass = "next"; // 다음페이지로(>) css 클래스명

	private String lastCssClass = "last"; // 마지막페이지로(>>) css 클래스명

	private String currentIndexCssClass = "current"; // 현재페이지 css 클래스명

	private StringBuffer html; // 페이징 출력 html

	private String parameters; // 페이지 이동시, 추가로 넘겨야할 파라미터가 있는 경우

	private String javascript; // 자바스크립트 function 호출로 페이지 이동하는 경우

	@Override
	public String generateHtml(Long totalCount, Integer currentIndex) {
		return this.generateHtml(totalCount, currentIndex, null);
	}

	@Override
	public String generateHtml(Long totalCount, Integer currentIndex, String parameters) {
		Integer totalPage = calcTotalPage(totalCount);
		html = new StringBuffer();
		
		if (totalPage <= 1) {
			
			if (this.useFirst) {
				html.append("<a href=\"#\" class=\"" + this.firstCssClass + "\"><img src=\"" + this.getContextPath() + this.firstImage + "\" alt=\"first\" /></a>\n");
			}
			html.append("<a href=\"#\" class=\"" + this.prevCssClass + "\"><img src=\"" + this.getContextPath() + this.prevImage + "\" alt=\"prev\" /></a>\n");
			html.append("<a href=\"#\" class=\"" + this.currentIndexCssClass + "\">1</a>");
			html.append("<a href=\"#\" class=\"" + this.prevCssClass + "\"><img src=\"" + this.getContextPath() + this.nextImage + "\" alt=\"next\" /></a>\n");
			if (this.useLast) {
				html.append("<a href=\"#\" class=\"" + this.lastCssClass + "\"><img src=\"" + this.getContextPath() + this.lastImage + "\" alt=\"last\" /></a>\n");
			}
			
			return html.toString();
		}

		if (StringUtils.isNotEmpty(parameters)) {
			this.setParameters(parameters);
		}

		// 처음페이지로 (<<)
		if (this.useFirst) {
			html.append("<a " + this.link(1, 0) + " class=\"" + this.firstCssClass + "\"><img src=\"" + this.getContextPath() + this.firstImage + "\" alt=\"first\" /></a>\n");
		}

		// 이전페이지로 (<)
		Integer previousPage = (currentIndex > 1) ? currentIndex - 1 : 1;
		html.append("<a " + this.link(previousPage, ((previousPage - 1) * rowScale)) + " class=\"" + this.prevCssClass + "\"><img src=\"" + this.getContextPath() + this.prevImage + "\" alt=\"first\" /></a>\n");

		// 페이지번호 (123...10)
		if (StringUtils.isNotEmpty(this.numberWrapElement)) {
			html.append("<" + this.numberWrapElement + " class=\"" + this.numberWrapCssClass + "\">\n");
		}

		Integer startPage = ((currentIndex-1) / this.pageScale) * this.pageScale + 1; // 현재 블록의 시작페이지
		Integer endPage = startPage + pageScale - 1; // 현재 블록의 종료페이지
		endPage = (endPage < totalPage) ? endPage : totalPage;

		for (Integer i = startPage; i <= endPage; i++) {
			if (StringUtils.isNotEmpty(this.numberElement)) {
				html.append("<" + this.numberElement + ">");
			}

			if (i.equals(currentIndex)) { // 현재페이지
				html.append("<a href=\"#\" class=\"" + this.currentIndexCssClass + "\">"+ i +"</a>");
			} else {
				html.append("<a " + this.link(i, ((i-1) * this.rowScale)) + ">"+ i +"</a>");
			}

			if (StringUtils.isNotEmpty(this.numberElement)) {
				html.append("</" + this.numberElement + ">");
			}
			html.append("\n");
		}

		if (StringUtils.isNotEmpty(this.numberWrapElement)) {
			html.append("</" + this.numberWrapElement + ">\n");
		}

		// 다음페이지로 (>)
		Integer nextPage = (currentIndex < totalPage) ? currentIndex + 1 : totalPage;
		html.append("<a " + this.link(nextPage, ((nextPage - 1) * rowScale)) + " class=\"" + this.nextCssClass + "\"><img src=\"" + this.getContextPath() + this.nextImage + "\" alt=\"next\" /></a>\n");
		
		// 마지막페이지로 (>>)
		if (this.useLast) {
			html.append("<a " + this.link(totalPage, ((totalPage - 1) * rowScale)) + " class=\"" + this.lastCssClass + "\"><img src=\"" + this.getContextPath() + this.lastImage + "\" alt=\"last\" /></a>\n");
		}
			
		return html.toString();
	}

	/**
	 * 총페이지 리턴
	 * @param totalCount : 총row수
	 * @return
	 */
	private Integer calcTotalPage(Long totalCount) {
		if (totalCount.intValue() % this.rowScale == 0) {
			return totalCount.intValue() / this.rowScale;
		} else {
			return totalCount.intValue() / this.rowScale + 1;
		}
	}
	
	/**
	 * 페이지이동 href 리턴
	 * @param index : 이동할 페이지
	 * @param offset : 이동할 게시물 시작위치
	 * @return
	 */
	private String link(Integer index, Integer offset) {
		String str = "href=\"?condition.currentIndex=" + index + "&condition.offset=" + offset + "&" + this.getParameters() + "\"";
		if (StringUtils.isNotEmpty(this.javascript)) {
			str = "href=\"javascript:void(0);\" onclick=\"" + this.javascript + "(" + index + ", " + offset + ");return false;\"";
		}
		return str;
	}

	@Override
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getContextPath() {
		return StringUtils.isNotEmpty(contextPath) ? this.contextPath : "";
	}

	@Override
	public void setNumberWrapElement(String elementName) {
		this.numberWrapElement = elementName;
	}
	
	@Override
	public void setNumberWrapCssClass(String className) {
		this.numberWrapCssClass = className;
	}

	@Override
	public void setNumberElement(String elementName) {
		this.numberElement = elementName;
	}

	@Override
	public void setUseFirst(boolean useFirst) {
		this.useFirst = useFirst;
	}

	@Override
	public void setUseLast(boolean useLast) {
		this.useLast = useLast;
	}

	@Override
	public void setRowScale(Integer rowScale) {
		this.rowScale = rowScale;
	}

	@Override
	public void setPageScale(Integer pageScale) {
		this.pageScale = pageScale;
	}

	@Override
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	@Override
	public void setPrevImage(String prevImage) {
		this.prevImage = prevImage;
	}

	@Override
	public void setNextImage(String nextImage) {
		this.nextImage = nextImage;
	}

	@Override
	public void setLastImage(String lastImage) {
		this.lastImage = lastImage;
	}

	@Override
	public void setFirstCssClass(String className) {
		this.firstCssClass = className;
	}
	
	@Override
	public void setNextCssClass(String className) {
		this.nextCssClass = className;
	}
	
	@Override
	public void setPrevCssClass(String className) {
		this.prevCssClass = className;
	}
	
	@Override
	public void setLastCssClass(String className) {
		this.lastCssClass = className;
	}

	@Override
	public void setCurrentIndexCssClass(String className) {
		this.currentIndexCssClass = className;
	}
	
	@Override
	public void setParameters(String queryString) {
		this.parameters = queryString;
	}

	public String getParameters() {
		return StringUtils.isNotEmpty(this.parameters) ? this.parameters : "";
	}

	@Override
	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

}