package commom;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class AbstractCommonDAO<E> extends HibernateDaoSupport implements
		IDAOTemplate<E> {
	private static final Logger log = Logger
			.getLogger("AbstractCommonDAO<E>");
	
	
	@Override
	public List<?> findByProperties(String[] names, Object[] objects,Class<?> classType) {
		Session session = getHibernateTemplate().getSessionFactory().openSession() ;
		Criteria criteria = session.createCriteria(classType) ;
		
		for(int i = 0; i < names.length ; i++){
			criteria.add(Restrictions.in(names[i], (Object[]) objects[i])) ;
		}
		List<?> list = criteria.list() ;
		session.close() ;
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E load(Class<?> entityClass, Serializable id) {
		// TODO Auto-generated method stub
		return (E) getHibernateTemplate().load(entityClass, id) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<E> findBySqlProperties(String[] paramNames, String Entity,Object[] values) {
		// TODO Auto-generated method stub
		String queryString = "from " + Entity + " as model where " ;
		for(int i = 0; i < paramNames.length ;i++){
			queryString += "model." + paramNames[i] + "=:" + paramNames[i] + " and ";
		}
		queryString = queryString.substring(0, queryString.lastIndexOf("and ")) ;
		return (List<E>) getHibernateTemplate().findByNamedParam(queryString, paramNames, values) ;
	}
	
	public List<?> findNullProperties(Class<?> classType,String... propertyNames){
		try{
			Session session = getHibernateTemplate().getSessionFactory().openSession() ;
			Criteria criteria = session.createCriteria(classType) ;
			for(int i = 0; i < propertyNames.length; i++){
				criteria = criteria.add(Restrictions.isNull(propertyNames[i])) ;
			}
			List<?> list = criteria.list() ;
			session.close() ;
			return list ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return null ;
		}
	}
	
	public List<?> findNullProperties(String[] properties,Class<?> classType) {
		try{
			Session session = getHibernateTemplate().getSessionFactory().openSession() ;
			Criteria criteria = session.createCriteria(classType) ;
			for(int i = 0; i < properties.length; i++){
				criteria = criteria.add(Restrictions.isNull(properties[i])) ;
			}
			List<?> list = criteria.list() ;
			session.close() ;
			return list ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return null ;
		}
	}
	
	public List<?> findNotNullProperties(String[] properties,Class<?> classType){
		try{
			Session session = getHibernateTemplate().getSessionFactory().openSession() ;
			Criteria criteria = session.createCriteria(classType) ;
			for(int i = 0; i < properties.length; i++){
				criteria = criteria.add(Restrictions.isNotNull(properties[i])) ;
			}
			List<?> list = criteria.list() ;
			session.close() ;
			return list ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return null ;
		}
	}
	
	public List<?> findNotNullProperties(Class<?> classType,String... propertyNames){
		try{
			Session session = getHibernateTemplate().getSessionFactory().openSession() ;
			Criteria criteria = session.createCriteria(classType) ;
			
			for(int i = 0; i < propertyNames.length; i++){
				criteria = criteria.add(Restrictions.isNotNull(propertyNames[i])) ;
			}
			List<?> list = criteria.list() ;
			session.close();
			return list ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return null ;
		}
	}
	
	public List<?> findExcludeProperties(String[] propertyNames,Object[][] values,Class<?> classType){
		try{
			Session session = getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(classType) ;
			for(int i= 0; i < propertyNames.length;i++){
				criteria =criteria.add(Restrictions.not(Restrictions.in(propertyNames[i], values[i]))) ;
			}
			List<?> list = criteria.list() ;
			session.close() ;
			return list ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null ;
	}
	
	public void saveOrupdate(E e){
		log.debug("saveOrupdate");
		try {
			getHibernateTemplate().saveOrUpdate(e) ;
		} catch (RuntimeException re) {
			log.error("saveOrupdate failed", re);
			throw re;
		}
	}
}
