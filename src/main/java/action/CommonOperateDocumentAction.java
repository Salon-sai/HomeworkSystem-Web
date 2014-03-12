package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import model.Course;
import model.Document;
import model.User;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

public class CommonOperateDocumentAction<E> extends ActionTemplate<E> 
	implements SessionAware {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	protected String SWF_path;
//	protected String document_id;
//	protected int score;
//	protected boolean isdownload;
//	protected String downloadfile;
//	protected Document document;
	protected Map<String, Object> session ;
//	protected String teacherName;
//	protected String courseName;
//	protected List<Document> documents;
//	protected String studentName;
//
//	@Action(value="getDocumentView",results={
//			@Result(name="success",type="json",
//					params={"excludeNullProperties","true",
//					"excludeProperties","session"}),
//					@Result(name="input",type="json",
//					params={"excludeNullProperties","true",
//					"excludeProperties","session"})}
//	)
//	public String showDocument() {
//		try{
//			Document document = documentService.get(document_id) ;
//			if(document.getSWF_path() == null || document.getSWF_path() == "")
//				return INPUT ;
//			SWF_path = document.getSWF_path();
//			this.SWF_path = ".." + SWF_path.substring(SWF_path.indexOf('/')) ;
//			if(document.getScore() != null)
//				this.score = document.getScore() ;
//			return SUCCESS ;
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace() ;
//			return INPUT ;
//		}
//	}
//	
//	@Action(value="downloadFile",results={
//			@Result(type="stream",name="download",
//					params={
//						"contentDisposition","attachment;filename=${downloadfile}",
//						"inputName","downloadFile",
//						"buffersize","40960",
//						"contentType","charset=UTF-8"
//						})
//	})
//	public String downloadFile() throws Exception{
//		this.setIsdownload(true) ;
//		return "download";
//	}
//	
//	public InputStream getDownloadFile() throws UnsupportedEncodingException{
//		if(isdownload){
//			try {
//				Document downloadDocument = documentService.get(document_id) ;
//				String fileName = downloadDocument.getSource_file();
//				downloadfile = fileName.substring(fileName.indexOf(File.separator)) ;
//				InputStream is = new FileInputStream(downloadDocument.getSource_file()) ;
////				InputStream is = new FileInputStream("F:/Office/jquery.js");
//				return is ;
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return null ;
//	}
//	
//	@Action(value="deleteDocument",results={
//			@Result(name="success",type="json",
//					params={
//						"excludeNullProperties","true",
//						"excludeProperties","document_id",
//						"excludeProperties","session"
//						}),
//			@Result(name="input",type="json",
//					params={
//						"excludeNullProperties","true",
//						"excludeProperties","document_id",
//						"excludeProperties","session"
//						})}
//	)
//	public String deleteDocumentById(){
//		try{
//			documentService.delete(documentService.get(document_id)) ;
//			return SUCCESS	 ;
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace() ;
//			return INPUT ;
//		}
//	}
//	
//	@Action(value="conversionDocument",results={
//			@Result(name="success",type="json",
//					params={
//						"excludeNullProperties","true",
//						"excludeProperties","document_id",
//						"excludeProperties","session"
//					})
//	})
//	public String conversionDocument(){
//		try{
//			document = documentService.get(document_id) ;
//			if(document.getPdf_path() == null || document.getSWF_path() == ""
//					|| document.getSWF_path() == null || document.getSWF_path() == ""){
//				document = documentService.conversionToSWF(
//						documentService.get(document_id)) ;
//			}
//			SWF_path = document.getSWF_path();
//			this.SWF_path = ".." + SWF_path.substring(SWF_path.indexOf('/')) ;
//			document = null ;
//			return SUCCESS ;
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace() ;
//			return INPUT ;
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Action(value="findTopicByCourseAndTeacher",results={
//			@Result(name="success",type="json"
//					,params={"excludeNullProperties","true",
//					"excludeProperties","session"}),
//			@Result(name="success",type="json"
//					,params={"excludeNullProperties","true",
//					"excludeProperties","session"})			
//			}
//	)
//	public String findDocumentByCourseAndTeacher(){
//		try{
//			User user = null ;
//			if(teacherName == null){
//				String id = ((User)session.get("teacher")).getId();
//				user = userService.get(id) ;
//			}else{
//				user = userService.findByName(teacherName) ;
//			}			
//			Course course = courseService.findByName(courseName) ;
//			documents = (List<Document>)documentService.findByCourseAndUser(course, user) ;
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace() ;
//			return INPUT ;
//		}
//		return SUCCESS ;
//	}
//	
//	@Action(value="findHomeworksByTopicAndStudent",results={
//			@Result(name="success",type="json"
//					,params={"excludeNullProperties","true",
//					"excludeProperties","session"}),
//			@Result(name="success",type="json"
//					,params={"excludeNullProperties","true",
//					"excludeProperties","session"})
//		}
//	)
//	public String findHomeworksByTopicAndStudent(){
//		try{
//			User user ;
//			if(studentName != null && !studentName.equals("")){
//				user = userService.findByName(studentName) ;
//			}else{
//				user = userService.get(((User)session.get("student")).getId()) ;
//			}
//			Document topic = documentService.get(document_id) ;
//			documents = documentService.findhomeworksByTopicAndUser(user, topic) ;
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return INPUT ;
//		}
//		return SUCCESS ;
//	}
//	
//	/**
//	 * 
//	 * getter and setter
//	 */
//	public String getSWF_path() {
//		return SWF_path;
//	}
//	public void setSWF_path(String sWF_path) {
//		SWF_path = sWF_path;
//	}
//	public int getScore() {
//		return score;
//	}
//	public void setScore(int score) {
//		this.score = score;
//	}
//	public void setDocument_id(String document_id) {
//		this.document_id = document_id;
//	}
//	public void setIsdownload(boolean isdownload) {
//		this.isdownload = isdownload;
//	}
//	public String getDownloadfile() {
//		return downloadfile;
//	}
//	public void setDownloadfile(String downloadfile) {
//		this.downloadfile = downloadfile;
//	}
//	public Document getDocument() {
//		return document;
//	}
//	public void setDocument(Document document) {
//		this.document = document;
//	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
//	public void setTeacherName(String teacherName) {
//		this.teacherName = teacherName;
//	}
//	public void setCourseName(String courseName) {
//		this.courseName = courseName;
//	}
//	public void setDocuments(List<Document> documents) {
//		this.documents = documents;
//	}
//	public void setStudentName(String studentName) {
//		this.studentName = studentName;
//	}
}
