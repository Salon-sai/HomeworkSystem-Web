/**
 * 
 */
package action;

import java.io.File;

import model.Course;
import model.Document;
import model.Type;
import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * @author sai
 *
 */
public class TeacherOperateDocumentAction<E> extends CommonOperateDocumentAction<E> {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private String uploadFileFileName;
//	private File uploadFile;
//	private String informationName;
//	
//
//	
//	@Action(value="submitScore",results={
//			@Result(name="success",type="json",
//					params={
//						"excludeNullProperties","true",
//						"excludeProperties","session"
//			}),
//			@Result(name="input",type="json",
//					params={
//					"excludeNullProperties","true",
//					"excludeProperties","session"
//			})
//	})
//	public String submitScore(){
//		try{
//			document = documentService.get(document_id) ;
//			documentService.submitScore(document, score) ;
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace() ;
//			return INPUT ;
//		}
//		return SUCCESS ;
//	}
//	
//	@Action(value="uploadTopic",results={
//			@Result(name="success",type="json",
//					params={
//						"excludeNullProperties","true",
//						"excludeProperties","session"
//					})
//		}
//	)
//	public String uploadTopic(){
//		try{
//			String id = ((User)session.get("teacher")).getId() ;
//			Course course = courseService.findByName(courseName) ;
//			Type type = typeService.findByName("topic");
//			User user = userService.get(id) ;
//			documentService.saveTopic(uploadFileFileName, uploadFile, course, type, user) ;
//			return SUCCESS ;
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace() ;
//		}
//		return INPUT ;
//	}
//	
//	@Action(value="getHomeworkByInformationAndCourse",results={
//			@Result(name="success",type="json",
//					params={"excludeNullProperties","true",
//						"excludeProperties","session"}),
//			@Result(name="success",type="json",
//					params={"excludeNullProperties","true",
//						"excludeProperties","session"})
//		}
//	)
//	public String findHomeworksByInformationAndCourse(){
//		try{
//			documents = documentService
//					.teacherfindhomworkByCourseAndInformation(courseName, informationName) ;
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace() ;
//			return INPUT ;
//		}
//		return SUCCESS ;
//	}
//	
//	@Action(value="getHomeworkdByCourseOfTeacher",results={
//			@Result(name="success",type="json",
//					params={"excludeNullProperties","true",
//						"excludeProperties","session"}),
//			@Result(name="success",type="json",
//					params={"excludeNullProperties","true",
//						"excludeProperties","session"})
//		}
//	)
//	public String findHomeworkByCourseOfTeacher(){
//		try{
//			Course course = courseService.findByName(courseName) ;
//			User teacher = userService.get(((User)session.get("teacher")).getId()) ;
//			documents = documentService.teacherfindhomeworkByCourse(teacher, course) ;
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace() ;
//			return INPUT ;
//		}
//		return SUCCESS ;
//	}
//	
//	@Action(value="findHomeworksByTopicAndClass",results={
//			@Result(name="success",type="json"
//					,params={"excludeNullProperties","true",
//					"excludeProperties","session"}),
//			@Result(name="success",type="json"
//					,params={"excludeNullProperties","true",
//					"excludeProperties","session"})
//		}
//	)
//	public String findHomeworksByTopicAndClass(){
//		try{
//			if(!informationName.equals(" ")){
//				documents = documentService.findHomeworkByTopciAndClass(informationName, document_id) ;
//			}else{
//				Document topic = documentService.get(document_id) ;
//				documents = documentService.findHomeworkByTopic(topic) ;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace() ;
//			return INPUT ;
//		}
//		return SUCCESS ;
//	}
//	
//	
//	/**
//	 * 
//	 * getter and setter
//	 */
//	public void setUploadFileFileName(String uploadFileFileName) {
//		this.uploadFileFileName = uploadFileFileName;
//	}
//	public void setCourseName(String courseName) {
//		this.courseName = courseName;
//	}
//	public void setUploadFile(File uploadFile) {
//		this.uploadFile = uploadFile;
//	}
//	public void setInformationName(String informationName) {
//		this.informationName = informationName;
//	}
}
