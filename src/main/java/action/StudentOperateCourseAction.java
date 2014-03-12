/**
 * 
 */
package action;

import java.util.List;
import java.util.Map;

import model.Course;
import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * @author sai
 *
 */
@Repository("studentOperatecourse")
@Namespace("/studentOperate")
@Scope("request")
@ParentPackage("json-default")
public class StudentOperateCourseAction<E> extends ActionTemplate<E>
	implements SessionAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private List<Course> courses;
	
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
		
		/**
		 * getter and setter
		 * @return
		 */
		public List<Course> getCourses() {
			return courses;
		}
		public void setSession(Map<String, Object> session) {
			this.session = session;
		}
}
