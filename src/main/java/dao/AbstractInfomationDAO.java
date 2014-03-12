package dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;


import model.Course;
import model.Information;
import commom.AbstractCommonDAO;
import dao.impl.InformationDAOImpl;

public abstract class AbstractInfomationDAO extends
		AbstractCommonDAO<Information> implements IInformationDAO {
	protected static final Logger log = Logger
			.getLogger(InformationDAOImpl.class);
	// property constants
	public static final String NAME = "name";
	
	@SuppressWarnings("unchecked")
	public List<Course> CoursesbyInformationName(String name) {
		List<Course> courses = new ArrayList<Course>();
		try{
			Session session = getHibernateTemplate().getSessionFactory().openSession() ;
			String queryString = "from Information as model where model."
					+ NAME + "= ?";
			List<Information> informations =  session.createQuery(queryString).setString(0, name).list() ;
			if(informations.get(0).getCourses() == null){
				return null ;
			}
			for(Course course : informations.get(0).getCourses()){
				courses.add(course) ;
			}
			session.close() ;
			return courses ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
		}
		return null ;
	}
	

}
