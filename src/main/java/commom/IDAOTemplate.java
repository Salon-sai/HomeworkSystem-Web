package commom;

import java.util.List;

public interface IDAOTemplate<E> {
	
	public void save(E transientInstance) ;

	public void delete(E persistentInstance) ;
	
	public void attachDirty(E instance) ;
	
	public E findById(java.io.Serializable id) ;
	
	public E merge(E detachedInstance) ;

	public void attachClean(E instance) ;
	
	public List<?> findByExample(E instance) ;
	
	public List<?> findByProperty(String propertyName, Object value) ;
	
	public List<?> findAll() ;
	
	public List<?> findByProperties(String[] names, Object[] objects,Class<?> ClassType) ;
	
	public E load(Class<?> entityClass, java.io.Serializable id) ;
	
	public List<E> findBySqlProperties(String[] paramNames, String Entity,Object[] values) ;

	public List<?> findNullProperties(String[] properties,Class<?> classType) ;
	
	public List<?> findNullProperties(Class<?> classType,String... propertyNames) ;
	
	public List<?> findNotNullProperties(Class<?> classType,String... propertyNames) ;
	
	public List<?> findNotNullProperties(String[] properties,Class<?> classType) ;
	
	public List<?> findExcludeProperties(String[] propertyNames,Object[][] values,Class<?> classType) ;

}
