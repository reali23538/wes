package info.wes.school.core.file.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.View;

public class FileDownloadViewImpl implements View {

	@Override
	public String getContentType() {
		return "application/octet-stream";
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void render(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		File file = (File) model.get("file");
//		String fileName = (String) model.get("fileName");

		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;
		String fileName = null;
		
		if (ie) {
			fileName = URLEncoder.encode(file.getName(), "utf-8");
		} else {
			fileName = new String(file.getName().getBytes("utf-8"), "iso-8859-1");
		}
		response.setContentType(this.getContentType());
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = response.getOutputStream();
		FileInputStream in = null;

		try {
			in = new FileInputStream(file);
			FileCopyUtils.copy(in, out);
		} catch (Exception exception) {
			PrintWriter writer = response.getWriter();
			writer.println("IO_ERR");
			writer.println("File downlad failed.");
		} finally {
			if (in != null) {
				in.close();
			}
			out.flush();
			out.close();
		}
	}

}
