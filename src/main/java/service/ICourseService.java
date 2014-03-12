/**
 * 
 */
package service;

import java.util.List;

import model.Course;
import model.Information;
import model.User;
import commom.IServiceTemplate;
import dao.ICourseDAO;

/**
 * @author sai
 *
 */
public interface ICourseService extends IServiceTemplate<ICourseDAO, Course> {

	public Course eagerfindByName(String name) ;
	
	public List<?> NotChooseCourse(List<Course> courses) ;
	
	public Course addCourseToClazz(Information information,String CourseName) ;
	
	public List<Information> findInformationByCourse(String courseName) ;
	
	public List<User	> findTeacherByCourse(String courseName) ;
	

}
