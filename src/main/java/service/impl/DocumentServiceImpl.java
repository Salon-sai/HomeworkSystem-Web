/**
 * 
 */
package service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import model.Course;
import model.Document;
import model.Type;
import model.User;
import service.IDocumentService;

import commom.AbstractTemplateService;
import dao.IDocumentDAO;
import factory.OfficeToPDFFactory;
import factory.PDFtoSWFFactory;
import factory.StringFactory;

/**
 * @author sai
 *
 */
@Repository("documentService")
public class DocumentServiceImpl extends
		AbstractTemplateService<IDocumentDAO, Document> implements
		IDocumentService {

	protected static final String OFFICE_ROOT = "F:/office" ;
	protected static final String PDF_ROOT = "F:/PDF";
	protected static final String SWF_ROOT = "F:/SWF" ;
	
	@Override
	@Resource(name="DocumentDAO")
	public void setDao(IDocumentDAO dao) {
		// TODO Auto-generated method stub
		super.setDao(dao);
	}
	
	/**
	 * Not Useful
	 */
	@Override
	public boolean save(File file, String documentName) {
		// TODO Auto-generated method stub
		File officePath = new File(OFFICE_ROOT) ;
		Document document = new Document() ;
		
		if(!officePath.exists()){
			officePath.mkdir() ;
		}
		try{
			InputStream is = new FileInputStream(file) ;
			File destFile = new File(OFFICE_ROOT, documentName) ;
			OutputStream os = new FileOutputStream(destFile) ;
			byte[] buffer = new byte[1024] ;
			int length = 0 ;
			while(-1 != (length=is.read(buffer))){
				os.write(buffer, 0, length) ;
			}
			is.close();
			os.close() ;
			document.setSource_file(destFile.getAbsolutePath().replace('\\', '/')) ;
			document.setName(documentName
					.substring(0, documentName.indexOf("."))) ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return false ;
		}
		dao.save(document) ;
		return true ;
	}
	
	public void saveToDesk(String sourcePath,String FileName,File file,Document document) {
		// TODO Auto-generated method stub
		File office_path = new File(sourcePath) ;
		InputStream is ;
		OutputStream os ;
		
		if(!office_path.exists()){
			office_path.mkdirs() ;
		}
		try{
			is = new FileInputStream(file);
			File target = new File(sourcePath, FileName) ;
			os = new FileOutputStream(target) ;
			byte[] buffer = new byte[1024] ;
			int length = 0;
			while(-1 != (length=is.read(buffer))){
				os.write(buffer, 0, length) ;
			}
			is.close();
			os.close();
			document.setSource_file(target.getAbsolutePath().replace('\\', '/')) ;
			document.setName(StringFactory.cutPreStringBysplit(FileName, ".")) ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
		}
	}
	
	/**
	 * Not useful
	 */
	@Override
	public void save(String FileName,File file,Course course,Type type,User user){
		// office/type/user/course/FileName
		String office_path = null;
		if(type.getName().equals("report")){
			office_path = saveHomeworkPath(course, user, type) ;
		}else if(type.getName().equals("topic")){
			office_path = saveTopicPath(course, user, type) ;
		}
		Document document ;
		document = new Document() ;
		document.setType(type) ;
		document.setUser(user) ;
		document.setCourse(course) ;
		this.saveToDesk(office_path, FileName, file, document) ;
		dao.save(document);
	}
	
	public void saveHomework(String FileName,File file,Course course,Type type,User user,Document topic){
		Document document = this.findSimlarDocument(user, course, topic) ;
		String office_path = saveHomeworkPath(course, user, type) ;
		if(document != null){
			delete(document) ;
			document.setSource_file(null);
			document.setPdf_path(null);
			document.setSWF_path(null) ;
			this.saveToDesk(office_path, FileName, file, document) ;
			dao.merge(document) ;
		}else{
			Document newdocument = saveDocument(type, user, course, topic) ;
			this.saveToDesk(office_path, FileName, file, newdocument) ;
			dao.save(newdocument) ;
		}
	}
	
	public void saveTopic(String FileName,File file,Course course,Type type,User user){
		Document document = saveDocument(type, user, course, null) ;
		String sourcePath = this.saveTopicPath(course, user, type) ;
		this.saveToDesk(sourcePath, FileName, file, document) ;
		dao.save(document) ;
	}
	
	public Document saveDocument(Type type,User user,Course course,Document topic){
		Document document = new Document();
		document.setCourse(course) ;
		document.setUser(user) ;
		document.setType(type) ;
		document.setTopic(topic) ;
		return document ;
	}
	
	/**
	 * Not useful
	 */
	@Override
	public void save(String FileName,File file,Course course,Type type,User user,Document topic) {
		// TODO Auto-generated method stub
		Document document = this.findSimlarDocument(user, course, topic) ;
		if(document == null){
			this.save(FileName, file, course, type, user) ;
		}else{
			deleteFileInDisk(document);
			document.setSource_file(null);
			document.setPdf_path(null);
			document.setSWF_path(null) ;
			String office_path = null;
			if(type.getName() == "homework"){
				office_path = saveHomeworkPath(course, user, type) ;
			}else if(type.getName() == "topic"){
				office_path = saveTopicPath(course, user, type) ;
			}
			this.saveToDesk(office_path, FileName, file, document) ;
			dao.merge(document) ;
		}
	}
	
		//office/homework type/department/major/class/course name/user name
	public String saveHomeworkPath(Course course,User user,Type type){
		String office_path = OFFICE_ROOT + File.separator + type.getName() 
				+ File.separator + user.getInformation().getInformation().getInformation().getName() //department 
				+ File.separator + user.getInformation().getInformation().getName() //major 
				+ File.separator + user.getInformation().getName() //  class
				+ File.separator + course.getName() + File.separator + user.getName() ;
		return office_path ;
	}
	
		//office/topic type/user/course
	public String saveTopicPath(Course course,User user,Type type){
		String office_path = OFFICE_ROOT + File.separator + type.getName()
				+ File.separator + user.getName() + File.separator + course.getName() ;
		return office_path ;
	}
	
	@Override
	public void delete(Document e) {
		// TODO Auto-generated method stub
		this.deleteFileInDisk(e) ;
		List<Document> homeworks = null ;
		if(e.getType().getName().equals("topic")){
			homeworks = this.findHomeworkByTopic(e) ;
			for(Document homework : homeworks){
				this.deleteFileInDisk(homework);
			}
		}
		super.delete(e);
	}
	
	public void deleteFileInDisk(Document document){
		if(document.getPdf_path() != null){
			File pdfFile = new File(document.getPdf_path()) ;
			pdfFile.delete() ;
		}
		if(document.getSWF_path() != null){
			File swfFile = new File(document.getSWF_path()) ;
			swfFile.delete() ;
		}
		if(document.getSource_file() != null){
			File sourceFile = new File(document.getSource_file()) ;
			sourceFile.delete() ;
		}
	}
	
	public Document conversionToSWF(Document document){
		String PDFPATH = OfficeToPDFFactory.
				OfficeToPDF(document.getSource_file(),
				document.getSource_file().replace(OFFICE_ROOT, PDF_ROOT));
		if(PDFPATH != null){
			document.setPdf_path(PDFPATH) ;
			String SWFPATH = null ;
			try{
				SWFPATH = PDFtoSWFFactory
						.ConvertToSWF(document.getPdf_path(),
						document.getSource_file().replace(OFFICE_ROOT, SWF_ROOT)) ;
				document.setSWF_path(SWFPATH) ;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace() ;
			}
		}
		this.update(document) ;
		return document ;
	}
	
	@SuppressWarnings("unchecked")
	public List<?> ConvertedDocuments(){
//		return dao.findNotNullByProperty("SWF_path", Document.class) ;
		//not finish
		List<?> list = dao.findNotNullProperties(Document.class, "SWF_path");
		List<Document> documents = new ArrayList<Document>() ;
		for(Document document : (List<Document>)list){
			if(document.getScore() == null)
				documents.add(document) ;
		}
		return documents ;
	}
	
	public List<?> findByCourseAndUser(Course course,User user){
		List<Document> documents = dao.findBySqlProperties
				(new String[]{"user","course"}, "Document", new Object[]{user,course}) ;
		for(Document document : documents){
			clearCourse(document) ;
			clearUser(document) ;
			clearHomeworks(document);
			clearTopic(document);
		}
		return documents ;
	}
	
	public List<Document> teacherfindhomeworkByCourse(User teacher,Course course){
		List<Document> homeworks = dao.findhomeworkByCourseOfTeacher(teacher, course) ;
		if(homeworks == null){
			return null ;
		}
		for(Document homework : homeworks){
			clearCourse(homework) ;
			clearHomeworks(homework) ;
			clearpath(homework) ;
			clearTopic(homework) ;
			clearUserinformation(homework.getUser()) ;
		}
		return homeworks ;
	}
	
	public Document findSimlarDocument(User user,Course course,Document topic){
		Document document = null ;
		List<Document> documents = dao.findBySqlProperties
				(new String[]{"user","course","topic"}, "Document", new Object[]{user,course,topic}) ;
		if(!documents.isEmpty()){
			document = documents.get(0) ;
		}
		return document ;
	}
	
	@SuppressWarnings("unchecked")
	public List<Document> findHomeworksByTopicOrStudent(Document topic,User student){
		if(topic == null){
			return (List<Document>)dao.findByProperty("user", student);
		}
		if(student == null){
			return (List<Document>)dao.findByProperty("topic", topic) ;
		}
		return (List<Document>)dao.findBySqlProperties(new String[]{"user","topic"}
		, "Document", new Object[]{student,topic}) ;
	}
	
	public List<Document> teacherfindhomworkByCourseAndInformation(String CourseName,String informationName){
		List<Document> homeworks = dao.findhomworkByCourseAndInformation(CourseName, informationName) ;
		if(homeworks == null){
			return null ;
		}
		for(Document homework : homeworks){
			clearCourse(homework);
			clearHomeworks(homework) ;
			clearpath(homework);
			clearTopic(homework);
			clearUserinformation(homework.getUser());
		}
		return homeworks ;
	}
	
	public List<?> NotConvertDocuments(){
		return dao.findNullProperties(Document.class,"SWF_path") ;
	}
	
	public List<?> scoredHomework(){
		return dao.findNotNullProperties(Document.class, "score") ;
	}
	
	//not useful
	public List<Document> teacherfindByCourseAndUserAndTopic(Course course,User teacher,Document topic){
		List<Document> documents = dao.findBySqlProperties(
				new String[]{"course","user","topic"}, "Document", new Object[]{course, teacher, topic});
		for(Document document : documents){
			clearCourse(document) ;
			clearHomeworks(document);
			clearTopic(document);
			clearUserinformation(document.getUser());
			clearpath(document);
		}
		return documents ;
	}
	
	@SuppressWarnings("unchecked")
	public List<Document> findHomeworkByTopic(Document topic){
		List<Document> homeworks = (List<Document>)dao.findByProperty("topic",	topic) ;
		for(Document document : homeworks){
			clearCourse(document) ;
			clearHomeworks(document);
			clearTopic(document);
			clearUserinformation(document.getUser());
			clearpath(document);
		}
		return homeworks ;
	}
	
	public List<Document> findHomeworkByTopciAndClass(String className,String topic_id){
		List<Document> documents = dao.findhomeworksByInformationAndTopic(topic_id, className) ;
		if(documents == null){
			return null;
		}
		for(Document homework : documents){
			clearCourse(homework) ;
			clearHomeworks(homework) ;
			clearpath(homework) ;
			clearUserinformation(homework.getUser()) ;
			clearTopic(homework) ;
		}
		return documents ;
	}
	
	public Document submitScore(Document document,int score){
		document.setScore(score) ;
		this.update(document) ;
		clearCourse(document) ;
		clearHomeworks(document) ;
		clearpath(document) ;
		clearUserinformation(document.getUser()) ;
		clearTopic(document) ;
		return document ;
	}
	
	@SuppressWarnings("unchecked")
	public List<Document> findhomeworksByTopicAndUser(User user,Document topic){
		List<Document> documents = (List<Document>)this
				.findByProperties(new String[]{"topic","user"}, new Object[]{topic,user}, "Document") ;
		if(documents != null){
			for(Document homework : documents){
				clearCourse(homework) ;
				clearHomeworks(homework) ;
				clearpath(homework);
				clearUserinformation(homework.getUser()) ;
				clearTopic(homework);
			}	
		}
		return documents;
	}
	
	public List<?> noscoredHomework(){
		return null ;
	}
	
	private void clearUser(Document document){
		document.setUser(null) ;
	}
	
	private void clearCourse(Document document){
		document.setCourse(null) ;
	}
	
	private void clearHomeworks(Document document){
		document.setHomeworks(null) ;
	}
	
	private void clearTopic(Document document){
		document.setTopic(null);
	}
	
	private void clearpath(Document document){
		document.setSource_file(null);
		document.setPdf_path(null);
		document.setSWF_path(null);
	}
	
	private void clearUserinformation(User user){
		user.setInformation(null) ;
		user.setStucourses(null) ;
		user.setTeacourses(null) ;
		user.setDocuments(null) ;
		user.setFiles(null) ;
	}
}
