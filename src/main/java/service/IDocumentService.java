/**
 * 
 */
package service;

import java.io.File;
import java.util.List;

import model.Course;
import model.Document;
import model.Type;
import model.User;
import commom.IServiceTemplate;
import dao.IDocumentDAO;

/**
 * @author sai
 *
 */
public interface IDocumentService extends
		IServiceTemplate<IDocumentDAO, Document> {
	
	public boolean save(File file,String documentName) ;
	
	public Document conversionToSWF(Document document) ;
	
	public List<?> ConvertedDocuments() ;
	
	public List<?> NotConvertDocuments() ;
	
	public List<?> scoredHomework() ;
	
	public List<?> noscoredHomework() ;
	
	public void save(String FileName,File file,Course course,Type type,User user) ;
	
	public List<?> findByCourseAndUser(Course course,User user);

	void save(String FileName, File file, Course course, Type type, User user,
			Document topic);
	
	public void saveHomework(String FileName,File file,Course course,Type type,User user,Document topic);
	
	public void saveTopic(String FileName,File file,Course course,Type type,User user);

	public List<Document> teacherfindByCourseAndUserAndTopic(Course course,User teacher,Document topic) ;

	public List<Document> findHomeworkByTopic(Document topic) ;
	
	public List<Document> teacherfindhomeworkByCourse(User teacher,Course course) ;
	
	public List<Document> teacherfindhomworkByCourseAndInformation(String CourseName,String informationName) ;

	public List<Document> findHomeworkByTopciAndClass(String className,String topic_id) ;
	
	public Document submitScore(Document document, int score) ;

	public List<Document> findhomeworksByTopicAndUser(User user,Document topic) ;
}
