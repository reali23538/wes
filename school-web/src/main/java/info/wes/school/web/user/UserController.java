package info.wes.school.web.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import info.wes.school.biz.user.domain.Picture;
import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.domain.UserDto;
import info.wes.school.biz.user.domain.UserMessage;
import info.wes.school.biz.user.domain.excel.UserExcel;
//import info.wes.school.biz.user.domain.UserExcel;
import info.wes.school.biz.user.service.UserService;
import info.wes.school.core.paging.basic.Paging;
import info.wes.school.core.parser.xls.ExcelParser;
import info.wes.school.web.DefaultController;

@Controller
public class UserController extends DefaultController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private Paging paging;
	
	@Autowired
	private ExcelParser<UserExcel> parser;
	
	@Autowired
	private UserService service;
	
	@Value("${conf.env}")
	private String env;
	
//	@Autowired
//	private UserValidator validator;
	
	/**
	 * DESC 사용자 리스트
	 * @param dto
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String list(@ModelAttribute(value="dto") UserDto dto, ModelMap modelMap) {
		/**
		 * 로그백
		 * - pom.xml에 ch.qos.logback dependency 추가
		 * - logback.xml 설정파일
		 * - logger로 출력
		 */
		logger.trace("logback start");
		logger.debug("logback start");
		logger.info("logback start");
		logger.warn("logback start");
		logger.error("logback start");
		
		/**
		 * 환경별 프로퍼티 분리
		 * - properties.xml
		 * - applicationContext-web.xml에 properties.xml import
		 * - 환경별 properties 파일 작성 (*.properties)
		 * - -Dspring.profiles.active=local or web.xml에 spring.profiles.active param 작성
		 */
		logger.info("env : {}", env);
		
		modelMap.addAttribute("dto", service.findPagination(dto))
				.addAttribute("paging", paging.generateHtml(dto.getTotalCount(), dto.getCondition().getCurrentIndex(), dto.getCondition().toString()));
		return "user/user_list";
	}
	
	/**
	 * DESC 사용자 상세
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public String detail(@PathVariable(value="id") Long id, ModelMap modelMap) {
		User user = service.findById(id);
		
		modelMap.addAttribute("user", user);
		return "user/user_detail";
	}
	
	/**
	 * DESC 사용자 등록 폼
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/users/add", method=RequestMethod.GET)
	public String add(ModelMap modelMap) {
		modelMap.addAttribute("user", new User());
		return "user/user_form";
	}
	
	/**
	 * DESC 사용자 수정 폼
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/users/{id}/edit", method=RequestMethod.GET)
	public String edit(@PathVariable(value="id") Long id, ModelMap modelMap) {
		User user = service.findById(id);
		modelMap.addAttribute("user", user);
		return "user/user_form";
	}
	
	/**
	 * DESC 사용자 등록/수정 처리
	 * @param user
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/users/save", method=RequestMethod.POST)
	public String save(@Valid User user, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		// validation
//		if (StringUtils.isEmpty(user.getId())) {
//			bindingResult.rejectValue("id", "required.user.id");
//		}
//		validator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "user/user_form";
		}

		int saveRow = service.save(user);
		System.out.println("saveRow : " + saveRow);
		
		super.setResultMessage(UserMessage.FAIL, response);
		if (saveRow == 1) {
			super.setResultMessage(UserMessage.SUCCESS_SAVE, response);
		}
		return "redirect:/users" + getRefererQueryString(request); // TODO 여기부터!!!
	}
	
	/**
	 * DESC 에디터 사진 업로드
	 * @param request
	 * @param picture
	 * @return
	 */
	@RequestMapping(value="/users/upload/picture", method=RequestMethod.POST)
	public String uploadPicture(HttpServletRequest request, Picture picture) {
		// applicationContext-web.xml 에 CommonsMultipartResolver 추가 (파일업로드)
		
		String callback = picture.getCallback();
		String callbackFunc = picture.getCallback_func();
		MultipartFile filedata = picture.getFiledata();
		String result = "";

		try {
			if (filedata != null && !StringUtils.isEmpty(filedata.getOriginalFilename())) {

				String path = this.FILE_UPLOAD_DIR + File.separator + "test" + File.separator;
				File dir = new File(path);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				String originalFilename = filedata.getOriginalFilename();
				String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
				String fileName = UUID.randomUUID().toString() + "." + extension;
				File file = new File(path + fileName);
				
				filedata.transferTo(file);
				result += "&bNewLine=true&sFileName=" + originalFilename + "&sFileURL=/test/" + fileName;
			} else {
				result += "&errstr=error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + callback + "?callback_func=" + callbackFunc + result;
	}

	/**
	 * DESC 에디터 멀티 사진업로드
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/users/upload/pictures", method=RequestMethod.POST)
	public void uploadPictures(HttpServletRequest request, HttpServletResponse response){
		try {
			String result = "";
			String originalFileName = request.getHeader("file-name");
			System.out.println("originalFileName : " + originalFileName);
			
			String path = this.FILE_UPLOAD_DIR + File.separator + "test" + File.separator;
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String now = formatter.format(new java.util.Date());
			String fileName = now + UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));
			
			InputStream is = request.getInputStream();
			FileOutputStream fos = new FileOutputStream(path + fileName);
			int i;
			System.out.println("filesize : " + request.getHeader("file-size"));
			byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
			while ((i = is.read(b)) != -1) {
				fos.write(b, 0, i);
			}
			if (is != null) {
				is.close();
			}
			fos.flush();
			fos.close();
			
			result += "&bNewLine=true";
			result += "&sFileName="+ originalFileName;;
			result += "&sFileURL="+"/test/" + fileName;
			PrintWriter print = response.getWriter();
			print.print(result);
			print.flush();
			print.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * DESC 사용자 삭제
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/users/{id}/remove", method=RequestMethod.POST)
	public String remove(@PathVariable(value="id") Long id, HttpServletRequest request, HttpServletResponse response) {
		int removedRow = service.remove(id);
		System.out.println("removedRow : " + removedRow);
		
		super.setResultMessage(UserMessage.FAIL, response);
		if (removedRow == 1) {
			super.setResultMessage(UserMessage.SUCCESS_REMOVE, response);
		}
		return "redirect:/users" + getRefererQueryString(request);
	}
	
	/**
	 * DESC 사용자 멀티 삭제
	 * @param dto
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/users/removes", method=RequestMethod.POST)
	public String removes(@ModelAttribute(value="dto") UserDto dto, HttpServletRequest request, HttpServletResponse response) {
		int removedRow = service.removes(dto.getUsers());
		System.out.println("removedRow : " + removedRow);
		
		super.setResultMessage(UserMessage.FAIL, response);
		if (removedRow > 0) {
			super.setResultMessage(UserMessage.SUCCESS_REMOVE, response);
		}
		return "redirect:/users" + getRefererQueryString(request);
	}
	
	/**
	 * DESC 엑셀 가져오기
	 * @param userExcelFile
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/users/import", method=RequestMethod.POST)
	public String imports(@RequestPart(value="userExcelFile") MultipartFile userExcelFile
			, HttpServletRequest request) throws Exception {
		// core pom.xml - poi dependency 추가
		// applicationContext-core.xml 컴포넌트 스캔 추가
		// applicationContext-web.xml에 CommonsMultipartResolver 추가 (파일업로드)
		// parser package에 자바
		
		if (userExcelFile.getSize() == 0) {
			throw new Exception("파일을 선택후 임포트 해주세요.");
		}
		
		// 엑셀파일읽기
		try {
			List<UserExcel> userExcels = parser.read(userExcelFile, UserExcel.class);
			int importedRow = service.addAll(userExcels);
			System.out.println("importedRow : " + importedRow);
		} catch (Exception e) {
			throw new Exception("엑셀 파일을 읽어오던중 에러가 발생했습니다.");
		}
		return "redirect:/users" + getRefererQueryString(request);
	}
	
	/**
	 * DESC 엑셀 내보내기
	 * @param dto
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/users/export", method=RequestMethod.GET)
	public void export(UserDto dto, HttpServletResponse response) throws Exception {
		// core pom.xml - poi, dependency 추가
		// web pom.xml - jxls-core dependency 추가
		// applicationContext-core.xml 컴포넌트 스캔 추가
		// user_list.xls (엑셀 템플릿 파일)
		
//		File templateFile = new File(WEB_APP_PATH + File.separator + "jxls" + File.separator + "user_list.xls");
		File templateFile = new File("C:\\Users\\awj04\\Desktop\\git\\wes\\school\\school-web\\src\\main\\webapp\\jxls\\user_list.xls");
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("users", service.findAll(dto.getCondition()));
		jxls("user_list", templateFile.getPath(), data, response);
	}
	
}
