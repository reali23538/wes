package info.wes.school.biz.article.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//import info.wes.school.biz.article.domain.Article;
//import info.wes.school.biz.article.domain.ArticleCondition;
//import info.wes.school.biz.article.domain.ArticleDto;
//import info.wes.school.biz.article.domain.ArticleExcel;
import info.wes.school.biz.user.domain.User;
import info.wes.school.core.test.TestSupport;

public class ArticleServiceTest extends TestSupport {
	
//	@Autowired
//	private ArticleService service;
//	
//	private Long id1 = 3299L;
//	
//	private Long id2 = 3300L;
//	
//	@Test
//	public void diTest() {
//		Assert.assertNotNull(service);
//	}
//	
//	@Test
//	public void findPaginationTest() {
//		ArticleDto dto = service.findPagination(new ArticleDto());
//		List<Article> articles = dto.getArticles();
//		Long totalCount = dto.getTotalCount();
//		
//		printAll(articles);
//		System.out.println("totalCount ===>" + totalCount);		
//	}
//	
//	@Test
//	public void findAllTest() {
//		List<Article> articles = service.findAll(new ArticleCondition());		
//		printAll(articles);
//	}
//	
//	@Test
//	public void findByIdTest() {
//		Article article = service.findById(id1);
//		System.out.println(article.getId() + " " + article.getTitle());
//	}
//	
//	@Test
//	public void saveTest() {
//		// 등록
////		User user = new User();
////		user.setUserSeq(1L);
////		Article article = new Article();
////		article.setTitle("제목입니다.");
////		article.setContents("내용입니다.");
////		article.setUser(user);
////		article.setCreatedDate(new Date());
//		
//		// 수정
//		User user = new User();
//		user.setUserSeq(1L);
//		Article article = new Article();
//		article.setId(id1);
//		article.setTitle("제목 수정입니다.");
//		article.setContents("내용 수정입니다.");
//		article.setUser(user);
//		article.setUpdatedDate(new Date());
//		
//		int savedRow = service.save(article);
//		System.out.println("savedRow ===>" + savedRow);
//	}
//	
//	@Test
//	public void removeTest() {
//		User user = new User();
//		user.setUserSeq(1L);
//		Article article = new Article();
//		article.setId(id1);
//		article.setUser(user);
//		
//		int removedRow = service.remove(article);
//		System.out.println("removedRow ===>" + removedRow);
//	}
//	
//	@Test
//	public void removesTest() {
//		// 파라미터 셋팅 ########################################
//		User user = new User();
//		user.setUserSeq(1L);
//		Article article = new Article();
//		article.setUser(user);
//		
//		Article article1 = new Article();
//		article1.setId(id1);
//		Article article2 = new Article();
//		article2.setId(id2);
//		List<Article> articles = new ArrayList<Article>();
//		articles.add(article1);
//		articles.add(article2);
//		
//		ArticleDto dto = new ArticleDto();
//		dto.setArticle(article);
//		dto.setArticles(articles);
//		// #####################################################
//		
//		int removedRow = service.removes(dto);
//		System.out.println("removedRow ===>" + removedRow);		
//	}
//	
//	@Test
//	public void addAllTest() {
//		List<ArticleExcel> articleExcels = new ArrayList<ArticleExcel>();
//		for (int i=1; i<=100; i++) {
//			ArticleExcel articleExcel = new ArticleExcel();
//			articleExcel.setTitle("제목" + i);
//			articleExcel.setContents("내용" + i);
//			articleExcels.add(articleExcel);
//		}
//		User user = new User();
//		user.setUserSeq(1L);
//		
//		int addedRow = service.addAll(articleExcels, user);
//		System.out.println("addedRow ===>" + addedRow);
//	}
//	
//	private void printAll(List<Article> articles) {
//		for(Article article : articles) {
//			System.out.println(article.getId() + " " + article.getTitle());
//		}		
//	}

}
