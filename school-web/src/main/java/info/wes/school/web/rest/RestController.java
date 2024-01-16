package info.wes.school.web.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import info.wes.school.biz.user.domain.User;
import info.wes.school.biz.user.domain.UserDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class RestController {
	
	/**
	 * restTemplate(get)쓰기
	 * restTemplate(get)읽기
	 * restTemplate(post)쓰기
	 * restTemplate(post)읽기
	 * 
	 * jsonArray읽기
	 * jsonArray쓰기
	 * jsonObject읽기
	 * jsonObject쓰기
	 * 
	 * ajax
	 */
	
	// DESC restTemplate(get)쓰기
	@RequestMapping(value="/rest-template/get/data", method=RequestMethod.GET)
	public ResponseEntity<?> restTemplateGet(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		System.out.println("authorization ===> " + authorization);
		
//		ArticleDto articleDto = service.findPagination(new ArticleDto());
//		List<Article> articles = articleDto.getArticles();
//		ResponseEntity<List<Article>> response = new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		
		User user = new User();
		user.setId("user1");
		user.setName("사용자1");
		user.setEmail("user1@wes.com");
		user.setSex(true);
		user.setCreatedDate(new Date());
		ResponseEntity<User> response = new ResponseEntity<User>(user, HttpStatus.OK);
		return response;
	}
	// DESC restTemplate(get)읽기
	@RequestMapping(value="/rest-template/get", method=RequestMethod.GET)
	public String restTemplateGet(ModelMap modelMap) {
		String url = "http://localhost/rest-template/get/data";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "WEB_ELEMENTARY_SCHOOL");
		HttpEntity<Object> entity = new HttpEntity<Object>(headers);
	    
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);
	    User user = response.getBody();
	    
	    String result = "id : " + user.getId() + "<br/>"
	    			  + "name : " + user.getName();
	    modelMap.addAttribute("result", result);
	    return "rest/result";
	}
	
	// DESC restTemplate(post)쓰기
	@RequestMapping(value="/rest-template/post/data", method=RequestMethod.POST)
	public ResponseEntity<Integer> restTemplatePost(HttpServletRequest request, @RequestBody User user) {
		String authorization = request.getHeader("Authorization");
		System.out.println("authorization ===> " + authorization);
		
		System.out.println(user.toString());
		Integer result = 0;
		if (StringUtils.isNotEmpty(user.getId()) && StringUtils.isNotEmpty(user.getPassword())) {
			result = 1;
		}
		
		ResponseEntity<Integer> responseEntity = new ResponseEntity<Integer>(result, HttpStatus.OK);
		return responseEntity;
	}
	// DESC restTemplate(post)읽기
	@RequestMapping(value="/rest-template/post", method=RequestMethod.GET)
	public String restTemplatePost(ModelMap modelMap) {
	    String url = "http://localhost/rest-template/post/data";
	    
		User user = new User();
		user.setId("user1");
		user.setPassword("1111");
	    
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "WEB_ELEMENTARY_SCHOOL");
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
	    
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Integer> response = restTemplate.exchange(url, HttpMethod.POST, entity, Integer.class);
	    Integer insertedRow = response.getBody();
	    
	    String result = "inserted row : " + insertedRow;
	    modelMap.addAttribute("result", result);
		return "rest/result";
	}

	// DESC jsonArray쓰기
	@RequestMapping(value="/json/array/write", method=RequestMethod.GET)
	public String writeJsonArray(ModelMap modelMap) {
		User user = new User();
		user.setUserSeq(1L);
		user.setId("user1");
		List<User> users = new ArrayList<User>();
		users.add(user);
		JSONArray result = JSONArray.fromObject(users);
		
		modelMap.addAttribute("result", result);
		return "rest/result";
	}
	// DESC jsonArray읽기
	@RequestMapping(value="/json/array/read", method=RequestMethod.GET)
	public String readJsonArray(ModelMap modelMap) {
		String strJsonArray = "["
							+ "		{\"title\":\"제목1\", \"contents\":\"내용1\"},"
							+ "		{\"title\":\"제목2\", \"contents\":\"내용2\"}"
							+ "]";
		
		String result = "";
		JSONArray jsonArray = JSONArray.fromObject(strJsonArray);
		for (int i=0; i<jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String title = jsonObject.get("title").toString();
			String contents = jsonObject.get("contents").toString();
			
			result += title + " " + contents + "<br/>";
		}
		
		modelMap.addAttribute("result", result);
		return "rest/result";
	}
	// DESC jsonObject쓰기
	@RequestMapping(value="/json/object/write", method=RequestMethod.GET)
	public String writeValueAsString(ModelMap modelMap) throws Exception {
		User user = new User();
		user.setUserSeq(1L);
		user.setId("user1");
		List<User> users = new ArrayList<User>();
		users.add(user);
		UserDto userDto = new UserDto();
		userDto.setUsers(users);
		userDto.setTotalCount(1L);
		
		ObjectMapper mapper = new ObjectMapper();
		modelMap.addAttribute("result", mapper.writeValueAsString(userDto));
		return "rest/result";
	}
	// DESC jsonObject읽기
	@RequestMapping(value="/json/object/read", method=RequestMethod.GET)
	public String readJsonObject(ModelMap modelMap) {
		String strJsonObject = "{\"title\":\"제목1\", \"contents\":\"내용1\"}";
		
		JSONObject jsonObject = JSONObject.fromObject(strJsonObject);
		String title = jsonObject.get("title").toString();
		String contents = jsonObject.get("contents").toString();
		String result = title + " " + contents;
		
		modelMap.addAttribute("result", result);
		return "rest/result";
	}
	
	// DESC ajax
	@RequestMapping(value="/ajax", method=RequestMethod.GET)
	public String ajax() {
		return "rest/ajax";
	}
	@RequestMapping(value="/ajax", method=RequestMethod.POST)
	public String ajax(User user, ModelMap modelMap) {
		int row = 0;
		if (StringUtils.isNotEmpty(user.getId()) && StringUtils.isNotEmpty(user.getPassword())) {
			row = 1;
		}
		
		modelMap.addAttribute("result", row);
		return "rest/result";
	}
	
}
