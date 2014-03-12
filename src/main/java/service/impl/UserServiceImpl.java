/**
 * 
 */
package service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import model.Course;
import model.Information;
import model.Type;
import model.User;
import service.IUserService;

import commom.AbstractTemplateService;
import dao.IUserDAO;

/**
 * @author sai
 *
 */
@Repository("userService")
public class UserServiceImpl extends AbstractTemplateService<IUserDAO, User>
		implements IUserService {

	@Override
	@Resource(name="UserDAO")
	public void setDao(IUserDAO dao) {
		// TODO Auto-generated method stub
		this.dao = dao ;
	}
	
	public User Login(String IDNum,String password){
		List<?> objects = dao.findBySqlProperties(new String[]{"IdNum","password"},
				"User", new String[]{IDNum,password}) ;
		if(objects.equals(null) || objects.isEmpty()){
			return null ;
		}
		User user =  (User)objects.get(0) ;
		user.setDocuments(null) ;
		user.setFiles(null) ;
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List<User> list = (List<User>)super.findByProperty(propertyName, value) ;
		for(User user : list){
			user.setDocuments(null) ;
			user.setFiles(null) ;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> findByProperties(String[] names, Object[] values,
			String Entity) {
		// TODO Auto-generated method stub
		List<User> users = (List<User>)super.findByProperties(names, values, Entity);
		for(User user : users){
			user.setDocuments(null) ;
			user.setFiles(null) ;
		}
		return users ;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> findUserByInformation(Type Usertype,Information clazz){
		List<User> users = (List<User>)this.findByProperties(new String[]{"type","information"},
				new Object[]{Usertype,clazz}, "User") ;
		for(User user : users){
			this.clearStuCourses(user);
			this.clearTeaCourses(user);
			this.clearInformation(user) ;
		}
		return users ;
	}
	
	public List<Course> findCourses(String id){
		User user = dao.findAllCourse(id) ;
		List<Course> courses = new ArrayList<Course>() ;
		if(user.getStucourses() != null){
			for(Course course : user.getStucourses()){
				courses.add(course) ;
			}
		}else{
			for(Course course : user.getTeacourses()){
				courses.add(course) ;
			}
		}
		if(user.getType().getName().equals("student")){
			if(user.getInformation().getCourses() != null){
				for(Course course : user.getInformation().getCourses()){
					courses.add(course) ;
				}
			}
		}
		for(Course course : courses){
			course.setInformations(null) ;
			course.setStudents(null) ;
			course.setTeachers(null) ;
		}
		return courses ;
	}
	
	@Override
	public void save(User e) {
		// TODO Auto-generated method stub
		super.save(e) ;
		clearStuCourses(e);
		clearTeaCourses(e);
		clearInformation(e) ;
	}
	
	private void clearStuCourses(User user){
		user.setStucourses(null) ;
	}
	
	private void clearTeaCourses(User user){
		user.setTeacourses(null);
	}
	
	private void clearInformation(User user){
		user.setInformation(null) ;
	}
}
