package action;

import java.io.File;
import java.util.List;

import model.Course;
import model.Document;
import model.Type;
import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;


public class StudentOperateDocumentAction<E> extends CommonOperateDocumentAction<E>{
//
//	
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private String uploadFileFileName;
//	private File uploadFile;
//	
//	
//	@Action(value="uploadHomework",results={
//			@Result(name="success",type="json",
//					params={
//						"excludeNullProperties","true",
//						"excludeProperties","session"
//					})
//		}
//	)
//	public String uploadHomework(){
//		try{
//			String id = ((User)session.get("student")).getId() ;
//			Course course = courseService.findByName(courseName) ;
//			Type type = typeService.findByName("report");
//			User user = userService.get(id) ;
//			Document topic = documentService.get(document_id) ;
//			documentService.saveHomework(uploadFileFileName, uploadFile, course, type, user, topic) ;
//			return SUCCESS ;
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace() ;
//			return INPUT ;
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Action(value="gethomeworksByCourseAndStudent",results={
//			@Result(name="success",type="json"
//					,params={"excludeNullProperties","true",
//					"excludeProperties","session"}),
//			@Result(name="success",type="json"
//					,params={"excludeNullProperties","true",
//					"excludeProperties","session"})
//		}	
//	)
//	public String findHomeworksByCourseAndStudent(){
//		try{
//			User student = null;
//			Course course = null ;
//			student = userService.get(((User)session.get("student")).getId()) ;
//			course = courseService.findByName(courseName) ;
//			documents = (List<Document>) documentService.findByCourseAndUser(course, student) ;
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return INPUT ;
//		}
//		return SUCCESS;
//	}
//	
//
//	/**
//	 * 
//	 * getter and setter
//	 */
//	public void setCourseName(String courseName) {
//		this.courseName = courseName;
//	}
//	public void setUploadFileFileName(String uploadFileFileName) {
//		this.uploadFileFileName = uploadFileFileName;
//	}
//	public void setUploadFile(File uploadFile) {
//		this.uploadFile = uploadFile;
//	}
}
