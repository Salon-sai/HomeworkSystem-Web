/**
 * 
 */
package dao;

import java.util.List;

import model.Course;
import model.Information;
import model.User;
import commom.IDAOTemplate;

/**
 * @author sai
 *
 */
public interface ICourseDAO extends IDAOTemplate<Course> {
	
	public List<?> eagerfindByproperty(String propertyName,String value) ;
	
	public Course addObjectToCourse(String propertyName,Object object,String CourseName) ;
	
	public List<Information> findInformaitonsByCourse(String CourseName) ;
	
	public List<User> findTeachersByCourse(String courseName) ;
}
