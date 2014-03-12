package dao;

import model.User;
import commom.IDAOTemplate;

public interface IUserDAO extends IDAOTemplate<User> {

	public User findAllCourse(java.io.Serializable id) ;
	
}