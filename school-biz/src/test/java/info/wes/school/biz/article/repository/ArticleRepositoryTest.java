package info.wes.school.biz.article.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//import info.wes.school.biz.article.domain.Article;
//import info.wes.school.biz.article.domain.ArticleCondition;
//import info.wes.school.biz.article.domain.ArticleDto;
import info.wes.school.biz.user.domain.User;
import info.wes.school.core.test.TestSupport;

public class ArticleRepositoryTest extends TestSupport {
	
//	@Autowired
//	private ArticleRepository repository;
//	
//	private Long id1 = 3299L;
//	private Long id2 = 3300L;
//	
//	@Test
//	public void diTest() {
//		Assert.assertNotNull(repository);
//	}
//	
//	@Test
//	public void countTest() {
//		ArticleCondition condition = new ArticleCondition();
//		condition.setQ("제목");		
//		
//		Long count = repository.count(condition);
//		System.out.println("count ===>" + count);
//	}
//	
//	@Test
//	public void findPaginationTest() {
//		ArticleCondition condition = new ArticleCondition();
//		condition.setQ("제목");
//		
//		List<Article> articles = repository.findPagination(condition);
//		printAll(articles);
//	}
//	
//	@Test
//	public void findAllTest() {
//		ArticleCondition condition = new ArticleCondition();
//		condition.setQ("제목");
//		
//		List<Article> articles = repository.findAll(condition);
//		printAll(articles);		
//	}
//	
//	@Test
//	public void findByIdTest() {
//		Article article = repository.findById(id1);
//		System.out.println(article.getId() + " " + article.getTitle());		
//	}
//	
//	@Test
//	public void insertTest() {
//		User user = new User();
//		user.setUserSeq(1L);
//		Article article = new Article();
//		article.setTitle("제목");
//		article.setContents("내용");
//		article.setUser(user);
//		article.setCreatedDate(new Date());
//
//		int insertedRow = repository.insert(article);
//		System.out.println("insertedRow ===>" + insertedRow);
//	}
//	
//	@Test
//	public void updateTest() {
//		User user = new User();
//		user.setUserSeq(1L);
//		Article article = new Article();
//		article.setId(id1);
//		article.setTitle("제목 수정");
//		article.setContents("내용 수정");
//		article.setUser(user);
//		article.setUpdatedDate(new Date());
//		
//		int updatedRow = repository.update(article);
//		System.out.println("updatedRow ===>" + updatedRow);
//	}
//	
//	@Test
//	public void deleteTest() {
//		User user = new User();
//		user.setUserSeq(1L);
//		Article article = new Article();
//		article.setId(id1);
//		article.setUser(user);
//		
//		int deletedRow = repository.delete(article);
//		System.out.println("deletedRow ===>" + deletedRow);
//	}
//	
//	@Test
//	public void deletesTest() {
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
//		int deletedRow = repository.deletes(dto);
//		System.out.println("deletedRow ===>" + deletedRow);		
//	}
//	
//	@Test
//	public void updateViewCountTest() {
//		int updatedRow = repository.updateViewCount(id1);
//		System.out.println("updatedRow ===>" + updatedRow);
//	}
//	
//	private void printAll(List<Article> articles) {
//		for(Article article : articles) {
//			System.out.println(article.getId() + " " + article.getTitle());
//		}		
//	}

}