package dao;

import java.util.List;

import model.Course;
import model.Document;
import model.User;
import commom.IDAOTemplate;

public interface IDocumentDAO extends IDAOTemplate<Document> {
	
	public List<Document> findhomeworkByCourseOfTeacher(User user,Course course) ;

	public List<Document> findhomworkByCourseAndInformation(String CourseName,String informationName) ;

	public List<Document> findhomeworksByInformationAndTopic(String topic_id, String informationName) ;
}
