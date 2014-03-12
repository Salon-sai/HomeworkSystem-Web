/**
 * 
 */
package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import model.Course;
import model.Document;
import model.Type;
import model.User;

/**
 * @author sai
 *
 */
@Scope("request")
@Repository("documentAction")
@ParentPackage("json-default")
public class DocumentAction extends ActionTemplate<Document> implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Document document ;
	private File uploadFile ;
	private String uploadFileFileName ;
	private String uploadFileContentType ;
	private String downloadfile ;
	private List<?> documents ;
	private List<?> convertedDocuments ;
	private List<?> notConvertDocuments ;
	private List<?> scoredDocuments ;
	private String document_id;
	private String documentName ;
	private String SWF_path ;
	private String informationName ;
	private int score ;
	private String courseName ;
	private String studentName;
	private String teacherName ;
	private Map<String, Object> session ;
	private boolean isdownload = false ;
	
	@Action(value="getDocumentView",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true",
					"excludeProperties","session"}),
					@Result(name="input",type="json",
					params={"excludeNullProperties","true",
					"excludeProperties","session"})}
	)
	public String showDocument() {
		try{
			Document document = documentService.get(document_id) ;
			if(document.getSWF_path() == null || document.getSWF_path() == "")
				return INPUT ;
			SWF_path = document.getSWF_path();
			this.SWF_path = ".." + SWF_path.substring(SWF_path.indexOf('/')) ;
			if(document.getScore() != null)
				this.score = document.getScore() ;
			return SUCCESS ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}

	@Action(value="uploadDocument",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true",
					"excludeProperties","session"}),
			@Result(name="input",type="json",
					params={"excludeNullProperties","true",
					"excludeProperties","session"})}
	)
	public String uploadDocument(){
		try{
			documentService.save(uploadFile, uploadFileFileName) ;
			return SUCCESS ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}
	
	@Action(value="getDocumentList",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true",
					"excludeProperties","session"}),
			@Result(name="input",type="json",
					params={"excludeNullProperties","true",
					"excludeProperties","session"})}
	)
	public String DocumentList(){
		try{
			documents = documentService.findAll() ;
			return SUCCESS;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}
	
	@Action(value="downloadFile",results={
			@Result(type="stream",name="download",
					params={
						"contentDisposition","attachment;filename=${downloadfile}",
						"inputName","downloadFile",
						"buffersize","40960",
						"contentType","charset=UTF-8"
						})
	})
	public String downloadFile() throws Exception{
		this.setIsdownload(true) ;
		return "download";
	}
	
	public InputStream getDownloadFile() throws UnsupportedEncodingException{
		if(isdownload){
			try {
				Document downloadDocument = documentService.get(document_id) ;
				String fileName = downloadDocument.getSource_file();
				downloadfile = fileName.substring(fileName.indexOf(File.separator)) ;
				InputStream is = new FileInputStream(downloadDocument.getSource_file()) ;
//				InputStream is = new FileInputStream("F:/Office/jquery.js");
				return is ;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null ;
	}
		
	@Action(value="deleteDocument",results={
			@Result(name="success",type="json",
					params={
						"excludeNullProperties","true",
						"excludeProperties","document_id",
						"excludeProperties","session"
						}),
			@Result(name="input",type="json",
					params={
						"excludeNullProperties","true",
						"excludeProperties","document_id",
						"excludeProperties","session"
						})}
	)
	public String deleteDocumentById(){
		try{
			documentService.delete(documentService.get(document_id)) ;
			return SUCCESS	 ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}
	
	@Action(value="conversionDocument",results={
			@Result(name="success",type="json",
					params={
						"excludeNullProperties","true",
						"excludeProperties","document_id",
						"excludeProperties","session"
					})
	})
	public String conversionDocument(){
		try{
			document = documentService.get(document_id) ;
			if(document.getPdf_path() == null || document.getSWF_path() == ""
					|| document.getSWF_path() == null || document.getSWF_path() == ""){
				document = documentService.conversionToSWF(
						documentService.get(document_id)) ;
			}
			SWF_path = document.getSWF_path();
			this.SWF_path = ".." + SWF_path.substring(SWF_path.indexOf('/')) ;
			document = null ;
			return SUCCESS ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}
	
	@Action(value="getconvertedDocuements",results={
			@Result(name="success",type="json",
					params={
						"excludeNullProperties","true",
						"excludeProperties","document_id",
						"excludeProperties","session"
					}),
			@Result(name="input",type="json",
					params={
						"excludeNullProperties","true",
						"excludeProperties","document_id",
						"excludeProperties","session"
					})
	})
	public String ConverstedDocuments(){
		try{
			convertedDocuments =
					documentService.ConvertedDocuments() ;
			return SUCCESS ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}
	
	@Action(value="getnotconvertdocuments",results={
			@Result(name="success",type="json",
					params={
						"excludeNullProperties","true",
						"excludeProperties","session"
			}),
			@Result(name="input",type="json",
					params={
					"excludeNullProperties","true",
					"excludeProperties","session"
			})
	})
	public String NotConverstDocument(){
		try{
			notConvertDocuments = 
					documentService.NotConvertDocuments() ;
			return SUCCESS ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}
	
	@Action(value="submitScore",results={
			@Result(name="success",type="json",
					params={
						"excludeNullProperties","true",
						"excludeProperties","session"
			}),
			@Result(name="input",type="json",
					params={
					"excludeNullProperties","true",
					"excludeProperties","session"
			})
	})
	public String submitScore(){
		try{
			document = documentService.get(document_id) ;
			documentService.submitScore(document, score) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="getScoredHomework",results={
			@Result(name="success",type="json",
					params={
						"excludeNullProperties","true",
						"excludeProperties","session"
			}),
			@Result(name="input",type="json",
					params={
					"excludeNullProperties","true",
					"excludeProperties","session"
			})
	})
	public String scoredHomework(){
		try{
			scoredDocuments = documentService.scoredHomework() ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT	;
		}
		return SUCCESS ;
	}
	
	@Action(value="uploadTopic",results={
			@Result(name="success",type="json",
					params={
						"excludeNullProperties","true",
						"excludeProperties","session"
					})
		}
	)
	public String uploadTopic(){
		try{
			String id = ((User)session.get("teacher")).getId() ;
			Course course = courseService.findByName(courseName) ;
			Type type = typeService.findByName("topic");
			User user = userService.get(id) ;
			documentService.saveTopic(uploadFileFileName, uploadFile, course, type, user) ;
			return SUCCESS ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
		}
		return INPUT ;
	}
	
	@Action(value="uploadHomework",results={
			@Result(name="success",type="json",
					params={
						"excludeNullProperties","true",
						"excludeProperties","session"
					})
		}
	)
	public String uploadHomework(){
		try{
			String id = ((User)session.get("student")).getId() ;
			Course course = courseService.findByName(courseName) ;
			Type type = typeService.findByName("report");
			User user = userService.get(id) ;
			Document topic = documentService.get(document_id) ;
			documentService.saveHomework(uploadFileFileName, uploadFile, course, type, user, topic) ;
			return SUCCESS ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}
	
	@Action(value="findTopicByCourseAndTeacher",results={
			@Result(name="success",type="json"
					,params={"excludeNullProperties","true",
					"excludeProperties","session"}),
			@Result(name="success",type="json"
					,params={"excludeNullProperties","true",
					"excludeProperties","session"})			
			}
	)
	public String findDocumentByCourseAndTeacher(){
		try{
			User user = null ;
			if(teacherName == null){
				String id = ((User)session.get("teacher")).getId();
				user = userService.get(id) ;
			}else{
				user = userService.findByName(teacherName) ;
			}			
			Course course = courseService.findByName(courseName) ;
			documents = documentService.findByCourseAndUser(course, user) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	// 2014.1.4 copy
	@Action(value="gethomeworksByCourseAndStudent",results={
			@Result(name="success",type="json"
					,params={"excludeNullProperties","true",
					"excludeProperties","session"}),
			@Result(name="success",type="json"
					,params={"excludeNullProperties","true",
					"excludeProperties","session"})
		}	
	)
	public String findHomeworksByCourseAndStudent(){
		try{
			User student = null;
			Course course = null ;
			student = userService.get(((User)session.get("student")).getId()) ;
			course = courseService.findByName(courseName) ;
			documents = documentService.findByCourseAndUser(course, student) ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT ;
		}
		return SUCCESS;
	}
	
	@Action(value="getHomeworkByInformationAndCourse",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true",
						"excludeProperties","session"}),
			@Result(name="success",type="json",
					params={"excludeNullProperties","true",
						"excludeProperties","session"})
		}
	)
	public String findHomeworksByInformationAndCourse(){
		try{
			documents = documentService
					.teacherfindhomworkByCourseAndInformation(courseName, informationName) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="getHomeworkdByCourseOfTeacher",results={
			@Result(name="success",type="json",
					params={"excludeNullProperties","true",
						"excludeProperties","session"}),
			@Result(name="success",type="json",
					params={"excludeNullProperties","true",
						"excludeProperties","session"})
		}
	)
	public String findHomeworkByCourseOfTeacher(){
		try{
			Course course = courseService.findByName(courseName) ;
			User teacher = userService.get(((User)session.get("teacher")).getId()) ;
			documents = documentService.teacherfindhomeworkByCourse(teacher, course) ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="findHomeworksByTopicAndClass",results={
			@Result(name="success",type="json"
					,params={"excludeNullProperties","true",
					"excludeProperties","session"}),
			@Result(name="success",type="json"
					,params={"excludeNullProperties","true",
					"excludeProperties","session"})
		}
	)
	public String findHomeworksByTopicAndClass(){
		try{
			if(!informationName.equals(" ")){
				documents = documentService.findHomeworkByTopciAndClass(informationName, document_id) ;
			}else{
				Document topic = documentService.get(document_id) ;
				documents = documentService.findHomeworkByTopic(topic) ;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="findHomeworksByTopicAndStudent",results={
			@Result(name="success",type="json"
					,params={"excludeNullProperties","true",
					"excludeProperties","session"}),
			@Result(name="success",type="json"
					,params={"excludeNullProperties","true",
					"excludeProperties","session"})
		}
	)
	public String findHomeworksByTopicAndStudent(){
		try{
			User user ;
			if(studentName != null && !studentName.equals("")){
				user = userService.findByName(studentName) ;
			}else{
				user = userService.get(((User)session.get("student")).getId()) ;
			}
			Document topic = documentService.get(document_id) ;
			documents = documentService.findhomeworksByTopicAndUser(user, topic) ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	
	
	/**
	 * getter and setter
	 */
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public String getSWF_path() {
		return SWF_path;
	}
	public void setSWF_path(String sWF_path) {
		SWF_path = sWF_path;
	}
	public String getDocument_id() {
		return document_id;
	}
	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}
	public File getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	public String getUploadFileContentType() {
		return uploadFileContentType;
	}
	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}
	public List<?> getDocuments() {
		return documents;
	}
	public void setDocuments(List<?> documents) {
		this.documents = documents;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDownloadfile() {
		return downloadfile;
	}
	public void setDownloadfile(String downloadfile) {
		this.downloadfile = downloadfile;
	}
	public void setIsdownload(boolean isdownload) {
		this.isdownload = isdownload;
	}
	public List<?> getConvertedDocuments() {
		return convertedDocuments;
	}
	public List<?> getNotConvertDocuments() {
		return notConvertDocuments;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	public List<?> getScoredDocuments() {
		return scoredDocuments;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public void setInformationName(String informationName) {
		this.informationName = informationName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
}