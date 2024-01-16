package info.wes.school.core.dao;


public interface GenericDao<T> {

	/**
	 * id로 한건의 데이터를 조회한다.
	 * @param id
	 * @return
	 */
	T findById(Object id);
	
	/**
	 * id 가 없을 경우 insert, 있을 경우엔 update 한다.
	 * @param entity
	 * @return
	 */
	int save(T entity);

	/**
	 * 새로운 데이터를 등록한다.
	 * @param entity
	 * @return
	 */
	int insert(T entity);

	/**
	 * 데이터를 갱신한다.
	 * @param entity
	 * @return
	 */
	int update(T entity);

	/**
	 * 데이터를 삭제한다.
	 * @param entity
	 * @return
	 */
	int delete(T entity);

}