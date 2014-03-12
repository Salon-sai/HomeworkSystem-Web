/**
 * 
 */
package service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import model.Course;
import model.Information;
import model.User;
import service.ICourseService;

import commom.AbstractTemplateService;
import dao.ICourseDAO;

/**
 * @author sai
 *
 */
@Repository("courseService")
public class CourseServiceImpl extends
		AbstractTemplateService<ICourseDAO, Course> implements ICourseService {

	@Override
	@Resource(name="CourseDAO")
	public void setDao(ICourseDAO dao) {
		// TODO Auto-generated method stub
		this.dao = dao ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Course> findAll() {
		// TODO Auto-generated method stub
		List<Course> list = (List<Course>)dao.findAll() ;
		for(Course course : list){
			course.setInformations(null) ;
			course.setStudents(null) ;
			course.setTeachers(null) ;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Course eagerfindByName(String name) {
		// TODO Auto-generated method stub
		List<Course> courses = (List<Course>)dao.eagerfindByproperty("name",name) ;
		if(courses.isEmpty()){
			return null ;
		}
		return courses.get(0);
	}
	
	@Override
	public Course findByName(String name) {
		// TODO Auto-generated method stub
		List<?> courses = dao.findByProperty("name", name) ;
		if(courses.isEmpty()){
			return null ;
		}
		Course course = (Course)courses.get(0);
		course.setInformations(null);
		course.setStudents(null);
		course.setTeachers(null) ;
		return course;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> NotChooseCourse(List<Course> courses){
		List<Course>  result ;
		if(courses == null || courses.isEmpty()){
			 result = (List<Course>)dao.findAll() ;

		}else{
			String[] values = new String[courses.size()] ;
			for(int i = 0; i < values.length ; i++){
				values[i] = courses.get(i).getId() ;
			}
			result = (List<Course>)dao.findExcludeProperties(
					new String[]{"id"}, new Object[][]{values}, Course.class) ;
		}
		 for(Course course : result){
			 clearinformations(course) ;
			 clearstudent(course) ;
			 clearteacher(course) ;
		 }
		 return result ;
	}
	
	public Course addCourseToClazz(Information information,String CourseName){
		Course course = dao.addObjectToCourse("information", information, CourseName) ;
		clearinformations(course) ;
		clearstudent(course) ;
		clearteacher(course) ;
		return course ;
	}
	
	public List<Information> findInformationByCourse(String courseName){
		List<Information> informations = dao.findInformaitonsByCourse(courseName) ;
		for(Information information : informations){
			clearinformations(information);
			clearinformation(information) ;
			clearcourses(information) ;
		}
		return informations ;
	}
	
	public List<User	> findTeacherByCourse(String courseName){
		List<User> users = dao.findTeachersByCourse(courseName) ;
		for(User user : users){
			clearDocument(user) ;
			clearFile(user) ;
			clearInformation(user) ;
			clearTeacourses(user);
			clearStucourse(user) ;
		}
		return users ;
	}
	
	private void clearinformations(Course course){
		course.setInformations(null) ;
	}
	
	private void clearstudent(Course course){
		course.setStudents(null) ;
	}
	
	private void clearteacher(Course course){
		course.setTeachers(null) ;
	}
	
	private void clearinformations(Information information){
		information.setInformations(null) ;
	}
	
	private void clearcourses(Information information){
		information.setCourses(null) ;
	}
	
	private void clearinformation(Information information){
		information.setInformation(null) ;
	}

	private void clearTeacourses(User user){
		user.setTeacourses(null);
	}
	
	private void clearStucourse(User user){
		user.setStucourses(null) ;
	}
	
	private void clearDocument(User user){
		user.setDocuments(null) ;
	}
	
	private void clearInformation(User user){
		user.setInformation(null);
	}
	
	private void clearFile(User user){
		user.setFiles(null) ;
	}
}
