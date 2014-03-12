package action;

import java.util.List;
import java.util.Map;

import model.Course;
import model.Information;
import model.Type;
import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * 
 * @author sai
 *
 */

@Scope("request")
@Repository("userAction")
@ParentPackage("json-default")
public class UserAction extends ActionTemplate<User> implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String defaultPassword = "111111" ;
	private static final String Type_Student = "student" ;
	
	
	private String userName ;
	private String IDNum ;
	private String typeName ;
	private String password ;
	private List<?> users ;
	private String returnMsg ;
	private User user ;
	private List<Course> courses ;
	private String courseName ;
	private String informationName ;
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
	
	@Action(value="LoginUser",results={
			@Result(name="student",type="redirectAction",
					params={"actionName","LoginSuccess.action","typeName","student"}),
			@Result(name="teacher",type="redirectAction",
					params={"actionName","LoginSuccess.action","typeName","teacher"})
	})
	public String loginUser(){
		return typeName ;
	}

	@Action(value="LoginSuccess",results={
			@Result(name="student",location="/WEB-INF/test/student_index.jsp"),
			@Result(name="teacher",location="/WEB-INF/test/teacher_index.jsp")
	})
	public String loginSuccess(){
		return typeName ;
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
	
	@Action(value="checkLogin",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true",
						"excludeProperties","session"}),
			@Result(name="input",type="json",
					params={"excludeNullProperties","true"})
	})
	public String checkLogin(){
		try{
			User user = userService.Login(IDNum, password) ;
			if(user == null){
				returnMsg = "password or ID erreo" ;
				return INPUT ;
			}else{
				typeName = user.getType().getName() ;
				session.put(typeName, user) ;
			}
		}catch (Exception e) {
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
	
	//did not distribute
	@Action(value="getCourseByStudent",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true","excludeProperties","session"})
		}
	)
	public String findCourseByStudent(){
		try{
			String id = ((User)session.get("student")).getId() ;
			courses = userService.findCourses(id) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT ;
		}
		return SUCCESS;
	}
	
	//did not distribute
	@Action(value="getCourseByTeacher",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true","excludeProperties","session"})
		}
	)
	public String findCourseByTeacher(){
		try{
			String id = ((User)session.get("teacher")).getId() ;
			courses = userService.findCourses(id) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
	 * getter and setter
	 */
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getIDNum() {
		return IDNum;
	}
	public void setIDNum(String iDNum) {
		IDNum = iDNum;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeName() {
		return typeName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<?> getUsers() {
		return users;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public void setInformationName(String informationName) {
		this.informationName = informationName;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}