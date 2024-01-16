package info.wes.school.core.paging.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang.StringUtils;

/**
 * 이전페이지로(<)시 이전 블록으로 이동 <br/>
 * 다음페이지로(>)시 다음 블록으로 이동
 * 
 * @author awj04
 *
 */
public class Paging extends TagSupport {

	private static final long serialVersionUID = 5337622614914279301L;

	private Long totalCount; // 총row수

	private Integer currentPage = 1; // 현재페이지

	// 페이지번호 엘리먼트 관련
	private String numberWrapElement = "ul";

	private String numberWrapCssClass = "pagination";

	private String numberElement = "li";

	private boolean useBlock = true; // 처음페이지로(<<), 마지막페이지로(>>) 사용여부

	private Integer rowScale = 10; // 한페이지에 몇row씩 보여줄지

	private Integer pageScale = 10; // 한블록에 몇페이지씩 보여줄지 (페이지번호)

	private String firstImage; // 처음페이지로(<<) 이미지 경로

	private String prevImage; // 이전페이지로(<) 이미지 경로

	private String nextImage; // 다음페이지로(>) 이미지 경로

	private String lastImage; // 마지막페이지로(>>) 이미지 경로

	private String firstCssClass = "fa fa-angle-double-left"; // 처음페이지로(<<) css 클래스명

	private String prevCssClass = "fa fa-angle-left"; // 이전페이지로(<) css 클래스명

	private String nextCssClass = "fa fa-angle-right"; // 다음페이지로(>) css 클래스명

	private String lastCssClass = "fa fa-angle-double-right"; // 마지막페이지로(>>) css 클래스명

	private String currentIndexCssClass = "active"; // 현재페이지 css 클래스명

	private HttpServletRequest request;

	private String javascript; // 자바스크립트 function 호출로 페이지 이동하는 경우

	@Override
	public int doStartTag() throws JspException {

		if (request.getParameter("currentPage") == null) {
			this.currentPage = 1;
		} else {
			this.currentPage = Integer.valueOf((String) request.getParameter("currentPage"));
		}
		Integer totalPage = calcTotalPage(totalCount);
		StringBuffer html = new StringBuffer();
		
		if (totalPage > 1) {
			Integer startPage = ((currentPage - 1) / this.pageScale) * this.pageScale + 1; // 현재 블록의 시작페이지
			Integer endPage = startPage + pageScale - 1; // 현재 블록의 종료페이지
			endPage = (endPage < totalPage) ? endPage : totalPage;

			if (!StringUtils.isBlank(this.numberWrapElement)) {
				html.append("<" + this.numberWrapElement + " class=\"" + this.numberWrapCssClass + "\">");
			}

			// 처음페이지로 (<<)
			if (this.useBlock) {
				if (!StringUtils.isBlank(this.numberElement)) {
					html.append("<" + this.numberElement + ">");
				}

				html.append("<a " + this.link(1) + " title=\"처음페이지로 이동하기\" ><i class=\"" + this.firstCssClass + "\"></i>");
				if (!StringUtils.isBlank(this.firstImage)) {
					html.append("<img src=\"" + this.request.getContextPath() + this.firstImage + "\" alt=\"\" />");
				}
				html.append("</a>");

				if (!StringUtils.isBlank(this.numberElement)) {
					html.append("</" + this.numberElement + ">");
				}
			}

			// 이전페이지로 (<)
			int previousPage = startPage - pageScale;
			if (previousPage < 1) previousPage = 1;
			boolean hasPrevBlock = (previousPage < startPage);
			String addCss = "";
			if (!hasPrevBlock) {
				addCss = " class=\"disabled\"";
			}

			if (!StringUtils.isBlank(this.numberElement)) {
				html.append("<" + this.numberElement + addCss + ">");
			}
			html.append("<a " + this.link(previousPage) + " title=\"" + previousPage + "페이지로 이동하기\" ><i class=\"" + this.prevCssClass + "\"></i>");
			if (!StringUtils.isBlank(this.prevImage)) {
				html.append("<img src=\"" + this.request.getContextPath() + this.prevImage + "\" alt=\"" + previousPage + "\" />");
			}
			html.append("</a>");
			if (!StringUtils.isBlank(this.numberElement)) {
				html.append("</" + this.numberElement + ">");
			}
			
			// 페이지번호 (123...10)
			for (Integer i = startPage; i <= endPage; i++) {
				if (!StringUtils.isBlank(this.numberElement)) {
					html.append("<" + this.numberElement);
					if (i.equals(currentPage)) {
						html.append(" class=\"" + this.currentIndexCssClass + "\"");
					}
					html.append(">");
				}

				if (i.equals(currentPage)) {
					html.append("<a href=\"#\" title=\"현재페이지\">"+ i +"</a>");
				} else {
					html.append("<a " + this.link(i) + " title=\"" + i + "페이지로 이동하기\">"+ i +"</a>");
				}

				if (!StringUtils.isBlank(this.numberElement)) {
					html.append("</" + this.numberElement + ">");
				}
			}

			// 다음페이지로 (>)
			int nextPage = startPage + pageScale;
			if (nextPage > totalPage) nextPage = totalPage;
			boolean hasNextBlock = (nextPage > startPage);
			addCss = "";
			if (!hasNextBlock) {
				addCss = " class=\"disabled\"";
			}
			
			if (!StringUtils.isBlank(this.numberElement)) {
				html.append("<" + this.numberElement + addCss + ">");
			}
			html.append("<a " + this.link(nextPage) + " title=\"" + nextPage + "페이지로 이동하기\"><i class=\"" + this.nextCssClass + "\"></i>");
			if (!StringUtils.isBlank(this.nextImage)) {
				html.append("<img src=\"" + this.request.getContextPath() + this.nextImage + "\" alt=\"" + nextPage + "\" />");
			}
			html.append("</a>");
			if (!StringUtils.isBlank(this.numberElement)) {
				html.append("</" + this.numberElement + ">");
			}

			// 마지막페이지로 (>>)
			if (this.useBlock) {
				if (!StringUtils.isBlank(this.numberElement)) {
					html.append("<" + this.numberElement + ">");
				}

				html.append("<a " + this.link(totalPage) + " title=\"마지막페이지로 이동하기\" ><i class=\"" + this.lastCssClass + "\"></i>");
				if (!StringUtils.isBlank(this.lastImage)) {
					html.append("<img src=\"" + this.request.getContextPath() + this.lastImage + "\" alt=\"\" />");
				}
				html.append("</a>");

				if (!StringUtils.isBlank(this.numberElement)) {
					html.append("</" + this.numberElement + ">");
				}
			}

			if (!StringUtils.isBlank(this.numberWrapElement)) {
				html.append("</" + this.numberWrapElement + ">");
			}
		} else {
			if (!StringUtils.isBlank(this.numberWrapElement)) {
				html.append("<" + this.numberWrapElement + " class=\"" + this.numberWrapCssClass + "\">");
			}

			// 처음페이지로 (<)
			if (this.useBlock) {
				if (!StringUtils.isBlank(this.numberElement)) {
					html.append("<" + this.numberElement + " class=\"disabled\">");
				}
				html.append("<a href=\"#\" title=\"처음페이지로 이동하기\" ><i class=\"" + this.firstCssClass + "\"></i>");
				if (!StringUtils.isBlank(this.firstImage)) {
					html.append("<img src=\"" + this.request.getContextPath() + this.firstImage + "\" alt=\"\" />");
				}
				html.append("</a>");
				if (!StringUtils.isBlank(this.numberElement)) {
					html.append("</" + this.numberElement + ">");
				}
			}

			// 이전 페이지로 (<<)
			if (!StringUtils.isBlank(this.numberElement)) {
				html.append("<" + this.numberElement + " class=\"disabled\">");
			}
			html.append("<a href=\"#\" title=\"이전블록으로 이동하기\" ><i class=\"" + this.prevCssClass + "\"></i>");
			if (!StringUtils.isBlank(this.prevImage)) {
				html.append("<img src=\"" + this.request.getContextPath() + this.prevImage + "\" alt=\"\" />");
			}
			html.append("</a>");
			if (!StringUtils.isBlank(this.numberElement)) {
				html.append("</" + this.numberElement + ">");
			}

			// 페이지번호
			if (!StringUtils.isBlank(this.numberElement)) {
				html.append("<" + this.numberElement + " class=\"" + this.currentIndexCssClass + "\">");
			}
			html.append("<a href=\"#\">1</a>");
			if (!StringUtils.isBlank(this.numberElement)) {
				html.append("</" + this.numberElement + ">");
			}

			// 다음페이지로 (>)
			if (!StringUtils.isBlank(this.numberElement)) {
				html.append("<" + this.numberElement + " class=\"disabled\">");
			}
			html.append("<a href=\"#\" title=\"다음블록으로 이동하기\" ><i class=\"" + this.nextCssClass + "\"></i>");
			if (!StringUtils.isBlank(this.prevImage)) {
				html.append("<img src=\"" + this.request.getContextPath() + this.nextImage + "\" alt=\"\" />");
			}
			html.append("</a>");
			if (!StringUtils.isBlank(this.numberElement)) {
				html.append("</" + this.numberElement + ">");
			}

			// 마지막페이지로 (>>)
			if (this.useBlock) {
				if (!StringUtils.isBlank(this.numberElement)) {
					html.append("<" + this.numberElement + " class=\"disabled\">");
				}
				html.append("<a href=\"#\" title=\"마지막 페이지로 이동하기\" ><i class=\"" + this.lastCssClass + "\"></i>");
				if (!StringUtils.isBlank(this.firstImage)) {
					html.append("<img src=\"" + this.request.getContextPath() + this.firstImage + "\" alt=\"\" />");
				}
				html.append("</a>");

				if (!StringUtils.isBlank(this.numberElement)) {
					html.append("</" + this.numberElement + ">");
				}
			}

			if(!StringUtils.isBlank(this.numberWrapElement)) {
				html.append("</" + this.numberWrapElement + ">");
			}
		}
		
		try {
			JspWriter jout = pageContext.getOut();
			jout.println(html.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}

		return super.doStartTag();
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
	 * @param currentPage : 이동할 페이지
	 * @return
	 */
	private String link(Integer currentPage) {
		int offset = (currentPage - 1) * rowScale;
		String str = "href=\"?currentPage=" + currentPage + "&offset=" + offset + "&limit=" + this.rowScale;
//		String str = "href=\"?currentPage=" + currentPage + "&rows=" + this.rowScale;

		if (!StringUtils.isBlank(this.getParameters())) {
			if(!this.getParameters().startsWith("&")) str += "&";
			str += this.getParameters();
		}
		str += "\"";
		if (!StringUtils.isBlank(this.javascript)) {
			str = "href=\"javascript:" + str + "\" onclick=\"" + this.javascript + "(" + currentPage + ", " + offset + ", " + this.rowScale + ");return false;\"";
//			str = "href=\"javascript:" + str + "\" onclick=\"" + this.javascript + "(" + currentPage + ", " + this.rowScale + ");return false;\"";
		}
		return str;
	}

	/**
	 * 추가 파라미터만 리턴 (currentPage, offset, limit 제외)
	 * @return
	 */
	private String getParameters() {
		String parameter = this.request.getQueryString();
		
		if (!StringUtils.isBlank(parameter)) {
			parameter = parameter.replaceAll("&currentPage=\\d*", "");
			parameter = parameter.replaceAll("currentPage=\\d*", "");
			parameter = parameter.replaceAll("&offset=\\d*", "");
			parameter = parameter.replaceAll("offset=\\d*", "");
			parameter = parameter.replaceAll("&limit=\\d*", "");
			parameter = parameter.replaceAll("limit=\\d*", "");
//			parameter = parameter.replaceAll("&index=\\d*", "");
//			parameter = parameter.replaceAll("index=\\d*", "");
//			parameter = parameter.replaceAll("&rows=\\d*", "");
//			parameter = parameter.replaceAll("rows=\\d*", "");
		}
		return parameter;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	public void setCurrentPage(Integer currentPage) {
		if (currentPage == null || currentPage < 1) return;
		this.currentPage = currentPage;
	}

	public void setNumberWrapElement(String numberWrapElement) {
		this.numberWrapElement = numberWrapElement;
	}

	public void setNumberWrapCssClass(String numberWrapCssClass) {
		this.numberWrapCssClass = numberWrapCssClass;
	}

	public void setNumberElement(String numberElement) {
		this.numberElement = numberElement;
	}

	public void setUseBlock(boolean useBlock) {
		this.useBlock = useBlock;
	}

	public void setRowScale(Integer rowScale) {
		this.rowScale = rowScale;
	}

	public void setPageScale(Integer pageScale) {
		this.pageScale = pageScale;
	}

	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}

	public void setPrevImage(String prevImage) {
		this.prevImage = prevImage;
	}

	public void setNextImage(String nextImage) {
		this.nextImage = nextImage;
	}

	public void setLastImage(String lastImage) {
		this.lastImage = lastImage;
	}

	public void setFirstCssClass(String firstCssClass) {
		this.firstCssClass = firstCssClass;
	}

	public void setPrevCssClass(String prevCssClass) {
		this.prevCssClass = prevCssClass;
	}

	public void setNextCssClass(String nextCssClass) {
		this.nextCssClass = nextCssClass;
	}

	public void setLastCssClass(String lastCssClass) {
		this.lastCssClass = lastCssClass;
	}

	public void setCurrentIndexCssClass(String currentIndexCssClass) {
		this.currentIndexCssClass = currentIndexCssClass;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

}