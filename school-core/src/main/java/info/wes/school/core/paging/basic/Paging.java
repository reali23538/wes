package info.wes.school.core.paging.basic;

public interface Paging {
	
	/**
	 * 페이징 태그 리턴
	 * @param totalCount : 총row개수
	 * @param currentIndex : 현재페이지
	 */
	public String generateHtml(Long totalCount, Integer currentIndex);

	/**
	 * 페이징 태그 리턴
	 * @param totalCount : 총row개수
	 * @param currentIndex : 현재페이지
	 * @param parameters : 추가파라미터 (ex : f=name&q=john)
	 */
	public String generateHtml(Long totalCount, Integer currentIndex, String parameters);

	/**
	 * contextPath 설정
	 * @param contextPath
	 */
	public void setContextPath(String contextPath);
	
	/**
	 * 페이지번호들을 감싸는 wrap 태그명 설정
	 * 기본값 : null (null 이거나 공백일 경우 사용하지 않음)
	 * @param elementName : 태그명
	 */
	public void setNumberWrapElement(String elementName);

	/**
	 * 페이지번호들을 감싸는 wrap 태그에 사용될 css 클래스명 설정
	 * 기본값 : page
	 * @param className : css 클래스명
	 */
	public void setNumberWrapCssClass(String className);

	/**
	 * 페이지번호를 감쌀 태그명 설정
	 * 기본값 : null (null 이거나 공백일 경우 사용하지 않음)
	 * @param elementName : 태그명
	 */
	public void setNumberElement(String elementName);

	/**
	 * 처음페이지로 (<<) 사용여부
	 * 기본값 : true
	 * @param useFirst
	 */
	public void setUseFirst(boolean useFirst);

	/**
	 * 마지막페이지로(>>) 사용여부
	 * 기본값 : true
	 * @param useLast
	 */
	public void setUseLast(boolean useLast);

	/**
	 * 한페이지에 몇row씩 보여줄지 설정
	 * 기본값 : 10
	 * @param rowScale
	 */
	public void setRowScale(Integer rowScale);

	/**
	 * 한블록에 몇페이지씩 보여줄지 설정 (페이지번호)
	 * 기본값 : 10
	 * @param pageScale
	 */
	public void setPageScale(Integer pageScale);

	/**
	 * 처음페이지로(<<) 이미지 경로
	 * 기본값 : /static/images/common/btn/btn_first.gif
	 * @param firstImage
	 */
	public void setFirstImage(String firstImage);

	/**
	 * 이전페이지로(<) 이미지 경로
	 * 기본값 : /static/images/common/btn/btn_prev.gif
	 * @param prevImage
	 */
	public void setPrevImage(String prevImage);

	/**
	 * 다음페이지로(>) 이미지 경로
	 * 기본값 : /static/images/common/btn/btn_next.gif
	 * @param nextImage
	 */
	public void setNextImage(String nextImage);

	/**
	 * 마지막페이지로(>>) 이미지 경로
	 * 기본값 : /static/images/common/btn/btn_last.gif
	 * @param lastImage
	 */
	public void setLastImage(String lastImage);

	/**
	 * 처음페이지로(<<) css 클래스명
	 * 기본값 : first
	 * @param className
	 */
	public void setFirstCssClass(String className);

	/**
	 * 이전페이지로(<) css 클래스명
	 * 기본값 : prev
	 * @param className
	 */
	public void setPrevCssClass(String className);

	/**
	 * 다음페이지로(>) css 클래스명
	 * 기본값 : next
	 * @param className
	 */
	public void setNextCssClass(String className);

	/**
	 * 마지막페이지로(>>) css 클래스명
	 * 기본값 : last
	 * @param className
	 */
	public void setLastCssClass(String className);

	/**
	 * 현재페이지 css 클래스명
	 * 기본값 : current
	 * @param className
	 */
	public void setCurrentIndexCssClass(String className);

	/**
	 * 페이지 이동시, 추가로 넘겨야할 파라미터가 있는 경우
	 * @param queryString : (ex : f=name&q=john)
	 */
	public void setParameters(String queryString);

	/**
	 * 자바스크립트 function 호출로 페이지 이동하는 경우
	 * @param javascript
	 */
	public void setJavascript(String javascript);

}