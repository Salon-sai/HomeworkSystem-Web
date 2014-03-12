package commom;

import java.io.Serializable;
import java.util.List;

public interface IServiceTemplate<D extends IDAOTemplate<E>,E>{
	
	public D getDao() ;
	
	public void setDao(D dao) ;
	
	public void save(E e) ;
	
	public void delete(java.io.Serializable id) ;
	
	public void delete(E e);
	
	public E get(Serializable id) ;
	
	public void update(E e) ;
	
	public List<E> findAll() ;
	
	public E findByName(String name) ;
	
	public List<?> findByProperty(String propertyName,Object value);
	
	public List<?> findByProperties(String[] names,Object[] values,String Entity) ;
}
