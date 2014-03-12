package action;

import java.util.Map;

import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
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
@Repository("loginAction")
@ParentPackage("json-default")
@Namespace("/login")
public class LoginAction<E> extends ActionTemplate<E> implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String IDNum ;
	private String typeName ;
	private String password ;
	private String returnMsg ;
	private Map<String, Object> session ;
	
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
	
	/**
	 * 
	 * getter and setter
	 */
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}	
	public String getReturnMsg() {
		return returnMsg;
	}
}
