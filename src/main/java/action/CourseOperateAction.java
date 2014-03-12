/**
 * 
 */
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


import model.Course;
import model.Information;
import model.User;

/**
 * @author sai
 *
 */
@Scope("request")
@Namespace("/courseOperate")
@ParentPackage("json-default")
@Repository("courseAction")
public class CourseOperateAction extends ActionTemplate<Course>
		implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String courseName ;
	private Course course ;
	private String typeName ;
	private List<?> courses ;
	private List<Course> chosenCourses ;
	private String alteredName ;
	private String InformationName ;
	private Map<String, Object> session;
	
	@Action(value="createCourse",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true"}),
			@Result(name="input",type="json",
					params={"excludeNullProperties","true"})}
	)
	public String createCourse() {
		try{
			course = new Course() ;
			course.setName(courseName) ;
			course.setType(typeService.findByName(typeName)) ;
			courseService.save(course);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}

	@Action(value="findAllCourse",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true"}),
			@Result(name="input",type="json",
					params={"excludeNullProperties","true"})
		}
	)
	public String allcourses() {
		try{
			courses = courseService.findAll() ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="deleteCourse",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true"}),
			@Result(name="input",type="json",
					params={"excludeNullProperties","true"})
		}
	)
	public String deleteCourse() {
		try{
			courseService.delete(
					courseService.findByName(courseName)) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="alterCourse",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true"}),
			@Result(name="input",type="json",
					params={"excludeNullProperties","true"})
		}
	)
	public String alterCourse() {
		try{
			course = courseService.eagerfindByName(courseName) ;
			course.setName(alteredName) ;
			courseService.update(course) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="notChooseCourse",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true"})
		}
	)
	public String excludeClazz(){
		try{
			// Is not a good chosen to select the course of exclude class 2013.12.16
			chosenCourses = informationService.coursesByInformationName(InformationName) ;
			courses = courseService.NotChooseCourse(chosenCourses) ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="addCourseToClass",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true"})
		}
	)
	public String addCoursetoClass() {
		try{
			Information clazz = informationService.findByName(InformationName) ;
			course = courseService.addCourseToClazz(clazz, courseName) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
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
				
		
	
	/**
	 * getter and setter
	 */
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<?> getCourses() {
		return courses;
	}
	public String getAlteredName() {
		return alteredName;
	}
	public void setAlteredName(String alteredName) {
		this.alteredName = alteredName;
	}
	public void setInformationName(String informationName) {
		InformationName = informationName;
	}
	public List<Course> getChosenCourses() {
		return chosenCourses;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}
}
