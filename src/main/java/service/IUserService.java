/**
 * 
 */
package service;

import java.util.List;

import model.Course;
import model.Information;
import model.Type;
import model.User;
import commom.IServiceTemplate;
import dao.IUserDAO;

/**
 * @author sai
 *
 */
public interface IUserService extends IServiceTemplate<IUserDAO, User> {

	public User Login(String IDNum,String password) ;
	
	public List<?> findUserByInformation(Type Usertype,Information clazz) ;
	
	public List<Course> findCourses(String id) ;
}
