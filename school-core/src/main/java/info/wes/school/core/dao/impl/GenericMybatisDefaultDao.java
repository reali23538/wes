package info.wes.school.core.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import info.wes.school.core.dao.GenericDao;
import info.wes.school.core.domain.CoreDomain;


@SuppressWarnings("rawtypes")
public abstract class GenericMybatisDefaultDao<T extends CoreDomain> extends SqlSessionTemplate implements GenericDao<T> {

	private Class<T>	domainClass;

	protected GenericMybatisDefaultDao(SqlSessionFactory sqlSessionFactory, Class<T> domainClass) {
		super(sqlSessionFactory);
		this.domainClass = domainClass;
	}
	
	@Override
	public T findById(Object id) {
		return selectOne(domainClass.getSimpleName() + ".findById", id);
	}

	@Override
	public int save(T entity) {
		int savedRow = 0;
		
		if(entity.getGenericKey() == null) {
			savedRow = insert(entity);
		} else {
			savedRow = update(entity);
		}
		return savedRow;
	}

	@Override
	public int insert(T entity) {
		return insert(domainClass.getSimpleName() + ".insert", entity);
	}

	@Override
	public int update(T entity) {
		return update(domainClass.getSimpleName() + ".update", entity);
	}

	@Override
	public int delete(T entity) {
		return delete(domainClass.getSimpleName() + ".delete", entity);
	}

}