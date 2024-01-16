package info.wes.school.web.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import info.wes.school.biz.user.domain.User;
import info.wes.school.core.file.thumbnail.ThumbnailGenerator;
import info.wes.school.core.file.upload.FileUploader;
import info.wes.school.core.file.upload.domain.FileData;
import net.sf.jxls.transformer.XLSTransformer;

@Controller
public class FileController {
	
	/**
	 * 멀티파일업로더
	 * 파일다운로드(뷰구현)
	 * 파일다운로드(뷰확장)
	 * 썸네일이미지 만들기
	 * 
	 * 파일업로드
	 * 멀티파일업로드 (submit)
	 * 멀티파일업로드 (미리보기)
	 * 
	 * 파일다운로드-엑셀
	 * 
	 * 파일업로드(old)
	 * 파일다운로드(old)
	 * 
	 * 파일쓰기
	 * 파일읽기
	 * 파일삭제
	 */
	
	/**
	 * C:\\Projects\\WES\\WORK\\uploaded
	 */
	@Value("${file.upload.dir}") 
	private String FILE_UPLOAD_DIR;
	
	@Value("${file.upload.temp.dir}") 
	private String FILE_UPLOAD_TEMP_DIR;
	
	@Value("${webapp.path}") 
	private String WEB_APP_PATH;
	
	@Value("${thumbnail.size}")
	private String thumbnailSize;
	
	@Autowired
	private FileUploader fileUploader;
	
	@Autowired
	private ThumbnailGenerator thumbnailGenerator;
	
	/**
	 * 멀티파일업로더
	 * 
	 * file.upload 패키지
	 * applicationContext-web.xml에 빈 생성
	 * school-web.properties에 file.upload.dir 작성
	 * FileUploader 사용
	 * 
	 * @return
	 */
	@RequestMapping(value="/fileUploader", method=RequestMethod.GET)
	public String fileUploader() {
		return "file/multi_file_upload";
	}
	@RequestMapping(value="/fileUploader", method=RequestMethod.POST)
	public String fileUploader(
		@RequestParam(value="multipleFile") List<MultipartFile> files
		, ModelMap modelMap
		) throws Exception {
		List<FileData> fileDatas = fileUploader.upload(files, "test");
		
		StringBuffer result = new StringBuffer();
		for (FileData fileData : fileDatas) {
			result.append(fileData.getWebPath()).append("<br/>");
		}
		modelMap.addAttribute("result", result);
		return "common/result";
	}
	
	/**
	 * 파일다운로드(뷰구현)
	 * 
	 * FileDownloadViewImpl.java
	 * applicationContext-web.xml에 빈선언
	 * ModelAndView로 리턴
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/fileDownloadView/impl", method=RequestMethod.GET)
	public ModelAndView fileDownloadViewImpl(ModelMap modelMap) {
		File file = new File(this.FILE_UPLOAD_DIR + File.separator + "test" + File.separator + "201903" + File.separator + "15525311320988982.jpg");
		
		modelMap.addAttribute("file", file)
				.addAttribute("fileName", "test.jpg");
		return new ModelAndView("fileDownloadViewImpl", modelMap);
	}
	
	/**
	 * 파일다운로드(뷰확장)
	 * 
	 * FileDownloadView.java
	 * applicationContext-web.xml에  beanNameViewResolver 빈선언
	 * String으로 리턴
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/fileDownloadView/extends", method=RequestMethod.GET)
	public String fileDownloadViewExtends(ModelMap modelMap) {
		File file = new File(this.FILE_UPLOAD_DIR + File.separator + "test" + File.separator + "201903" + File.separator + "15525311320988982.jpg");
		
		modelMap.addAttribute("file", file);
		return "fileDownloadView";
	}
	
	/**
	 * 썸네일이미지 만들기
	 * 
	 * file.thumbnail패키지
	 * school-web.properties에 thumbnailSize프로퍼티값 추가
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/thumbnails", method=RequestMethod.GET)
	public String makeThumbnails(ModelMap modelMap) {
		File file = new File(this.FILE_UPLOAD_DIR + File.separator + "test" + File.separator + "201903" + File.separator + "15525311320988982.jpg");
		
		try {
			List<File> thumbnails = thumbnailGenerator.generate(file, this.FILE_UPLOAD_DIR, "thumbnail", thumbnailSize, 0.3F);
			modelMap.addAttribute("result", thumbnails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "common/result";
	}
	
	// DESC 파일업로드
	// pom.xml 에 commons-fileupload, commons-io dependancy를 작성
	// applicationContext-web.xml에 파일업로드 관련 설정 작성
	@RequestMapping(value="/files/upload", method=RequestMethod.GET)
	public String upload() {
		return "file/file_upload";
	}
	@RequestMapping(value="/files/upload", method=RequestMethod.POST)
	public String upload(MultipartFile file, ModelMap modelMap) throws Exception {
		String dir = this.FILE_UPLOAD_DIR + File.separator + "test";
		String orginalFilename = file.getOriginalFilename(); // 원본파일명
		String fileName = this.getUniqueFileName(orginalFilename); // 유니크파일명
		String path = dir + File.separator + fileName;
		
		makeDir(dir);
		File savefile = new File(path); // 파일생성
		file.transferTo(savefile); // 파일에쓰기
		
		modelMap.addAttribute("result", path);
		return "common/result";
	}
	
	// DESC 멀티파일업로드 (submit)
	@RequestMapping(value="/files/multi-upload", method=RequestMethod.GET)
	public String multiUpload() {
		return "file/multi_file_upload";
	}
	@RequestMapping(value="/files/multi-upload", method=RequestMethod.POST)
	public String multiUpload(
		@RequestParam(value="multipleFile") List<MultipartFile> files
//		@RequestParam(value="multipleFile") MultipartFile[] arrFiles
//		@ModelAttribute("multipleFile") MultipleFileUpload multipleFileUpload
//		MultipartRequest multipartRequest
//		MultipartHttpServletRequest multipartHttpServletRequest
		, ModelMap modelMap
		) throws Exception {
//		List<MultipartFile> files = multipleFileUpload.getFiles();
//		List<MultipartFile> files = multipartRequest.getFiles("multipleFile");
//		List<MultipartFile> files = multipartHttpServletRequest.getFiles("multipleFile");
		String dir = this.FILE_UPLOAD_DIR + File.separator + "test";
		makeDir(dir);
		
		String result = "";
		for(MultipartFile file : files) {
			String orginalFilename = file.getOriginalFilename();
			File savefile = new File(dir + File.separator + orginalFilename); // 파일생성
			file.transferTo(savefile); // 파일에쓰기
			
			result += savefile.getPath() + "<br/>";
		}
		
		modelMap.addAttribute("result", result);
		return "common/result";
	}
	
	// DESC 멀티파일업로드 (미리보기)
	@RequestMapping(value="/files/upload/script", method=RequestMethod.GET)
	public String jqueryFileupload() {
		return "/file/file_upload_script";
	}	
	@ResponseBody
	@RequestMapping(value="/files/upload/script", produces="application/json", method=RequestMethod.POST)
	public List<HashMap<String, String>> uploadProcess(MultipartHttpServletRequest req) throws IOException {
	    List<MultipartFile> multipartFiles = req.getFiles("uploadFiles");
	    List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

	    for(MultipartFile file : multipartFiles) {
		    String orginalFilename = file.getOriginalFilename();
		    String fileName = getUniqueFileName(orginalFilename);   
		    
		    makeDir(FILE_UPLOAD_TEMP_DIR); // 디렉토리생성
		    File savefile = new File(FILE_UPLOAD_TEMP_DIR + File.separator + fileName); // 파일생성
		    file.transferTo(savefile); // 파일에쓰기

		    HashMap<String, String> map = new HashMap<String, String>();
		    map.put("path", savefile.getPath() + "<br/>");
		    result.add(map);
	    }
	    return result;
	}
	
	// DESC 파일다운로드-엑셀
	@RequestMapping(value="/files/excel-export", method=RequestMethod.GET)
	public void exportExcel(HttpServletResponse response) throws Exception {
		File templateFile = new File(this.WEB_APP_PATH + File.separator + "jxls" + File.separator + "article_list.xls");
		
		User user = new User();
		user.setId("user1");
		user.setName("사용자1");
		user.setEmail("user1@wes.com");
		user.setSex(true);
		user.setCreatedDate(new Date());
		ArrayList<User> users = new ArrayList<User>();
		users.add(user);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("users", users);
		
		jxls("user_list", templateFile.getPath(), data, response);
	}
	private void jxls(String fileName, String templateFilePath, Map<String, Object> data, HttpServletResponse response) throws Exception {
		XLSTransformer transformer = new XLSTransformer();
		String destFilePath = fileName + "_" + Long.toString(System.currentTimeMillis());
		HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(new FileInputStream(templateFilePath), data);
		response.setHeader("Content-disposition", "attachment;filename=" + destFilePath + ".xls");
		response.setContentType("application/x-msexcel");
		workbook.write(response.getOutputStream());
	}
	
	// DESC 파일업로드(old)
	// pom.xml 에 commons-fileupload, commons-io dependancy를 작성
	// applicationContext-web.xml에 파일업로드 태그 주석처리
	@RequestMapping(value="/files/upload/old", method=RequestMethod.GET)
	public String uploadOld() {
		return "file/file_upload_old";
	}	
	@RequestMapping(value="/files/upload/old", method=RequestMethod.POST)
	public String uploadOld(HttpServletRequest request, ModelMap modelMap) throws Exception {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);
		
		String result = "";
		for (int i=0; i<items.size(); i++) {
			FileItem item = items.get(i);
			String field = "";
			
			if (item.isFormField()) { // 일반
				field = item.getString("UTF-8");
				result += "일반 : " + field + "<br/>";
			} else { // 파일
				field = item.getName();
				String path = this.FILE_UPLOAD_DIR + File.separator + "test" + File.separator + field;
				
				File file = new File(path); // 파일생성
				item.write(file); // 파일에쓰기
				result += "파일 : " + path;
			}
		}

		modelMap.addAttribute("result", result);
		return "common/result";
	}
	
	// DESC 파일다운로드(old)
	@RequestMapping(value="/files/download/old", method=RequestMethod.GET)
	public void downloadOld(HttpServletResponse response) throws Exception {
		String fileName = "test.jpg";
		String fileType = fileName.substring(fileName.lastIndexOf('.')+1);
		File file = new File(this.FILE_UPLOAD_DIR + File.separator + "test" + File.separator + fileName);

		response.reset(); // contentType 등을 동적으로 바꾸기 위해 response를 날림
		if ("jpg".equals(fileType) || "jpeg".equals(fileType)){
			response.setContentType("image/jpeg");
		} else if ("bmp".equals(fileType)) {
			response.setContentType("image/bmp");
		} else if ("gif".equals(fileType)) {
			response.setContentType("image/gif");
		} else if ("tif".equals(fileType)) {
			response.setContentType("image/tiff");
		} else if ("xls".equals(fileType) || "xlsx".equals(fileType) || "cvs".equals(fileType)) {
			response.setContentType("application/vnd.ms-excel");
		} else if ("doc".equals(fileType) || "docx".equals(fileType) | "rtf".equals(fileType)) {
			response.setContentType("application/msword");
		} else if ("ppt".equals(fileType) || "pptx".equals(fileType)) {
			response.setContentType("application/vnd.ms-powerpoint");
		} else {
			response.setContentType("application/octet-stream");
		}
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + "");
		response.setHeader("Content-Transper-Encoding", "binary");
		response.setContentLength((int)file.length());
		response.setHeader("Pargma", "no-cache");
		response.setHeader("Expires", "-1");

		byte[] data = new byte[(int)file.length()];
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream fos = new BufferedOutputStream(response.getOutputStream());

		while((fis.read(data)) != -1){
			fos.write(data);
		}

		if(fis != null) fis.close();
		if(fos != null) fos.close();
	}
	
	// DESC 파일쓰기
	@RequestMapping(value="/files/write", method=RequestMethod.GET)
	public String write(ModelMap modelMap) throws IOException {
		String path = FILE_UPLOAD_DIR + File.separator + "test" + File.separator + "test1.txt";
		
		FileWriter fw = new FileWriter(path);
		fw.write("12345 abcde 가나다라마 !@#$%");
		fw.close();

		modelMap.addAttribute("result", path);
		return "common/result";
	}
	// 파일읽기
	@RequestMapping(value="/files/read", method=RequestMethod.GET)
	public String read(ModelMap modelMap) throws IOException {
		String filePath = FILE_UPLOAD_DIR + File.separator + "test" + File.separator + "test1.txt";
		
		String result = "";
		int data = 0;
		FileReader fr = new FileReader(filePath);
		
		while ((data = fr.read()) != -1) {
			char c = (char)data;
			result += c;
		}
		
		fr.close();
		modelMap.addAttribute("result", result);
		return "common/result";
	}
	// 파일삭제
	@RequestMapping(value="/files/remove", method=RequestMethod.GET)
	public String remove(ModelMap modelMap) {
		String path = FILE_UPLOAD_DIR + File.separator + "test" + File.separator + "test1.txt";
		
		File file = new File(path);
		
		String result = "삭제할 파일 없음";
		if(file.exists()) {
			file.delete();
			result = path;
		}
		
		modelMap.addAttribute("result", result);
		return "common/result";
	}
	
	/**
	 * 디렉토리 생성
	 * @param path
	 */
	private void makeDir(String path) {
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdirs();
		}
	}
	/**
	 * 유니크한 파일명 생성
	 * @param originalFilename
	 * @return
	 */
	private String getUniqueFileName(String originalFilename) {
		String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		int randomValue = this.getRandomValue(100, 999);
		
		return Long.toString(System.currentTimeMillis()) + randomValue + "." + extension;
	}
	/**
	 * from(포함) ~ to(포함) 사이의 랜덤수를 리턴
	 * 100 ~ 999
	 * @param from
	 * @param to
	 * @return
	 */
	private int getRandomValue(int from, int to) {
		return (int)(Math.random() * (Math.abs(to - from) + 1)) + Math.min(from, to) ;
	}

}
