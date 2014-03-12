package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.User;
import commom.AbstractCommonDAO;

public abstract class AbstractUserDAO extends AbstractCommonDAO<User> implements
		IUserDAO {

	public User findAllCourse(java.io.Serializable id){
		Session session = getHibernateTemplate().getSessionFactory().openSession() ;
		Transaction transaction = session.beginTransaction();	
		try{
			User user = (User)session.load(User.class, id) ;
			if(user.getType().getName().equals("student")){
				user.getStucourses().isEmpty() ;
				user.setTeacourses(null) ;
			}else{
				user.getTeacourses().isEmpty() ;
				user.setStucourses(null);
			}
			if(user.getInformation() != null){
				user.getInformation().getCourses().isEmpty() ;
			}
			transaction.commit() ;
			session.close() ;
			return user ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
		}
		return null ;
	}
	
}
