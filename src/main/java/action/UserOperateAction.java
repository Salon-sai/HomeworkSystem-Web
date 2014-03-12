package action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


import model.Information;
import model.Type;
import model.User;

/**
 * 
 * @author sai
 *
 */

@Scope("request")
@Repository("/userOpeareAction")
@ParentPackage("json-default")
@Namespace("/userOperate")
public class UserOperateAction<E> extends ActionTemplate<E> implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String defaultPassword = "111111" ;
	private static final String Type_Student = "student" ;
	
	
	private String userName ;
	private String IDNum ;
	private String typeName ;
	private List<?> users ;
	private User user ;
	private String courseName ;
	private String informationName ;
	@SuppressWarnings("unused")
	private Map<String, Object> session ;
	
	@Action(value="saveUser",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true","excludeProperties","session"}),
			@Result(name="input",type="json",
					params={"excludeNullProperties","true"})
	})
	public String saveUser(){
		try{
			user = new User() ;
			Information information = null ;
			if(informationName != null){
				information = informationService.findByName(informationName) ;
			}
			user.setName(userName) ;
			user.setPassword(defaultPassword) ;
			user.setIdNum(IDNum) ;
			user.setType(typeService.findByName(typeName)) ;
			user.setInformation(information) ;
			userService.save(user) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="getUsersList",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true"}),
			@Result(name="input",type="json",
					params={"excludeNullProperties","true"})
	})
	public String UserList(){
		try{
			this.users = userService.findByProperty("type",
					typeService.findByName(typeName)) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}

	@Action(value="getStudentByClazz",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true","excludeProperties","session"}),
			@Result(name="input",type="json",
					params={"excludeNullProperties","true"})
	})
	public String findStudentsByClass(){
		try{
			Type userType = typeService.findByName(Type_Student) ;
			Information clazz = informationService.findByName(informationName) ;
			users = userService.findUserByInformation(userType, clazz) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="getTeachersByCourse",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true","excludeProperties","session"})
		}
	)
	public String findTeacherByCourse(){
		try{
			users = courseService.findTeacherByCourse(courseName) ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}

	/**
	 * 
	 * getter and setter
	 */
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIDNum() {
		return IDNum;
	}
	public void setIDNum(String iDNum) {
		IDNum = iDNum;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<?> getUsers() {
		return users;
	}
	public User getUser() {
		return user;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public void setInformationName(String informationName) {
		this.informationName = informationName;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
