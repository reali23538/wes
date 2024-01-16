package info.wes.school.web;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.CookieGenerator;

import info.wes.school.biz.user.domain.UserMessage;
import info.wes.school.web.security.WESUser;
import net.sf.jxls.transformer.XLSTransformer;

public class DefaultController {
	
	@Value("${webapp.path}") 
	protected String WEB_APP_PATH;
	
	@Value("${file.upload.dir}")
	protected String FILE_UPLOAD_DIR;
	
	protected static final Logger log = LoggerFactory.getLogger(DefaultController.class);
	
	protected WESUser getPrincipal() {
		return (WESUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	protected String getRefererQueryString(HttpServletRequest request) {
		try {
			URL url = new URL(request.getHeader("referer"));
			return url.getQuery() != null ? "?" + url.getQuery() : "";
		} catch (MalformedURLException e) {
			log.error("Referer URL 획득 실패", e);
		}
		return "";
	}
	
	protected void setResultMessage(UserMessage userMessage, HttpServletResponse response) {
		CookieGenerator cg = new CookieGenerator();
		cg.setCookieName("resultMessage");
		cg.addCookie(response, userMessage.toString());
	}
	
	/**
	 * 엑셀 export
	 * @param fileName 파일명
	 * @param templateFilePath 엑셀 템플릿 파일 경로
	 * @param data 바인딩할 데이터
	 * @param response
	 * @throws Exception
	 */
	protected void jxls(String fileName, String templateFilePath, Map<String, Object> data, HttpServletResponse response) throws Exception {
		XLSTransformer transformer = new XLSTransformer();
		String destFilePath = fileName + "_" + Long.toString(System.currentTimeMillis());
		HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(new FileInputStream(templateFilePath), data);
		response.setHeader("Content-disposition", "attachment;filename=" + destFilePath + ".xls");
		response.setContentType("application/x-msexcel");
		workbook.write(response.getOutputStream());
	}

}
