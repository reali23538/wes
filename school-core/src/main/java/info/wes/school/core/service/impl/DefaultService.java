package info.wes.school.core.service.impl;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import info.wes.school.core.dao.GenericDao;
import info.wes.school.core.domain.CoreDomain;
import info.wes.school.core.service.CoreService;

@SuppressWarnings("rawtypes")
public class DefaultService<REPOSITORY extends GenericDao<DOMAIN>, DOMAIN extends CoreDomain> implements CoreService<REPOSITORY, DOMAIN>, ApplicationContextAware, InitializingBean {

	protected Class<REPOSITORY> daoClass;

	protected REPOSITORY repository;

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public DefaultService() {
	}
	
	public DefaultService(Class<REPOSITORY> daoClass) {
		this.daoClass = daoClass;
	}

	public void afterPropertiesSet() throws Exception {
		if(repository == null){
			repository = (REPOSITORY) getBeanByType(applicationContext,daoClass);
		}
	}

	public static <T> T getBeanByType(ApplicationContext applicationContext, Class<T> clazz) {
		Map<String, T> beanMap = applicationContext.getBeansOfType(clazz);
		int size = beanMap.size();
		if (size == 0) {
			if (applicationContext.getParent() != null) return getBeanByType(applicationContext.getParent(), clazz);
			throw new NoSuchBeanDefinitionException(clazz.getSimpleName());
		}
		if (size > 1) throw new NoSuchBeanDefinitionException("No unique bean definition type [" + clazz.getSimpleName() + "]");
		return (T) beanMap.values().iterator().next();
	}
	
	@Override
	public DOMAIN findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public int save(DOMAIN domain) {
		return repository.save(domain);
	}

	@Override
	public int add(DOMAIN domain) {
		return repository.insert(domain);
	}

	@Override
	public int set(DOMAIN domain) {
		return repository.update(domain);
	}

	@Override
	public int remove(DOMAIN domain) {
		return repository.delete(domain);
	}

}