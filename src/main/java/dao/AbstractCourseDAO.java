/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Course;
import model.Information;
import model.User;
import commom.AbstractCommonDAO;

/**
 * @author sai
 *
 */
public abstract class AbstractCourseDAO extends AbstractCommonDAO<Course>implements
		ICourseDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> eagerfindByproperty(String propertyName,String value) {
		// TODO Auto-generated method stub
		String queryString = "from Course as model where model."
				+ propertyName + "= ?";
		Session session = getHibernateTemplate().getSessionFactory().openSession() ;
		List<Course> list = session.createQuery(queryString)
				.setString(0, value).list() ;
		for(Course course : list){
			course.getInformations().isEmpty();
			course.getStudents().isEmpty();
			course.getTeachers().isEmpty() ;
		}
		session.close() ;
		return list;
	}
	
	public Course addObjectToCourse(String propertyName,Object object,String CourseName){
		try{
			String queryString = "from Course as model where model.name = ?";
			Session session = getHibernateTemplate().getSessionFactory().openSession() ;
			Course course = (Course)session.createQuery(queryString)
					.setString(0, CourseName).list().get(0);
			if(propertyName.equals("information")){
				if(course.getInformations().isEmpty()){ 
					course.setInformations(new ArrayList<Information>());
				}
				course.getInformations().add((Information)object) ;
			}
			session.merge(course) ;
			// I think transaction is better than flush 2013.12.17
			session.flush() ;
			session.close() ;
			return course ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return null ;
		}
	}
	
	public List<Information> findInformaitonsByCourse(String courseName){
		List<Information> informations = null ;
		Session session = getHibernateTemplate().getSessionFactory().openSession() ;
		Transaction transaction = session.beginTransaction();
		String queryString = "from Course as model where model.name = ?";
		try{
			Course course = (Course)session.createQuery(queryString)
					.setString(0, courseName).list().get(0);
			if(course.getInformations() != null){
				course.getInformations().isEmpty() ;
			}
			informations = course.getInformations() ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			transaction.rollback();
		}finally{
			transaction.commit();
			session.close();
		}
		return informations ;
	}
	
	public List<User> findTeachersByCourse(String courseName){
		List<User> users = null ;
		Session session = getHibernateTemplate().getSessionFactory().openSession() ;
		Transaction transaction = session.beginTransaction() ;
		String queryString = "from Course as model where model.name = ?";
		try{
			Course course = (Course)session.createQuery(queryString)
					.setString(0, courseName).list().get(0);
			if(course.getTeachers() != null){
				course.getTeachers().isEmpty() ;
			}
			users = course.getTeachers() ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			transaction.rollback();
		}finally{
			transaction.commit() ;
			session.close();
		}
		return users ;
	}
}
