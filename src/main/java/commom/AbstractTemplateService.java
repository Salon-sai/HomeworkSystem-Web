package commom;

import java.io.Serializable;
import java.util.List;

/**
 * @author sai
 *
 */
public abstract class AbstractTemplateService<D extends IDAOTemplate<E>,E> {
	
	protected D dao = null ;

	public D getDao() {
		return dao;
	}

	public void setDao(D dao) {
		this.dao = dao;
	}
	
	public void save(E e) {
		dao.save(e) ;
	}
	
	public void delete(java.io.Serializable id) {
		dao.delete(get(id)) ;
	}
	
	
	public void delete(E e){
		dao.delete(e) ;
	}
	
	public E get(Serializable id) {
		// TODO Auto-generated method stub
		return dao.findById(id) ;
	}
	
	public void update(E e) {
		dao.merge(e) ;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		// TODO Auto-generated method stub
		return (List<E>) dao.findAll() ;
	}
	
	@SuppressWarnings("unchecked")
	public E findByName(String name){
		List<?> list = dao.findByProperty("name", name);
		if(list.isEmpty()){
			return null ;
		}
		return (E) list.get(0) ;
	}
	
	public List<?> findByProperty(String propertyName,Object value){
		return dao.findByProperty(propertyName, value) ;
	}
	
	public List<?> findByProperties(String[] names,Object[] values,String Entity){
		return dao.findBySqlProperties(names, Entity, values) ;
	}
}
