package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Course;
import model.Document;
import model.User;
import commom.AbstractCommonDAO;

public abstract class AbstractDocumentDAO extends AbstractCommonDAO<Document>
		implements IDocumentDAO {
	
	@SuppressWarnings("unchecked")
	public List<Document> findhomeworkByCourseOfTeacher(User user,Course course){
		Session session = getHibernateTemplate().getSessionFactory().openSession() ;
		Transaction transaction = session.beginTransaction() ;
		String hql = "From Document as model where model.user = ? and model.course = ? " ;
		List<Document> homeworks = null ;
		try{
			List<Document> topics = session.createQuery(hql)
					.setParameter(0, user)
					.setParameter(1, course)
					.list();
			if(!topics.isEmpty()){
				homeworks = new ArrayList<Document>() ;
				for(Document topic : topics){
					for(Document homework : topic.getHomeworks()){
						homeworks.add(homework) ;
					}
				}
			}
			transaction.commit() ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			transaction.rollback() ;
		} finally {
			session.close() ;
		}
		return homeworks ;
	}

	//Should be optimization laster 2013.12.29
	@SuppressWarnings("unchecked")
	public List<Document> findhomworkByCourseAndInformation(String CourseName,String informationName){
		List<Document> documents = null ;
		Session session = getHibernateTemplate().getSessionFactory().openSession() ;
		Transaction transaction = session.beginTransaction() ;
		String sql = "select * from (document ,user ,course ,information) " +
				"left outer join topic_homeworks on topic_homeworks.topic_id = document.id " +
				"where information.name = ? and course.name = ? and user.id = document.user_id and " +
				"course.id = document.course_id and user.information_id = information.id;" ;
		try{
			documents = session.createSQLQuery(sql)
					.addEntity(Document.class)
					.setString(0, informationName)
					.setString(1, CourseName).list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			transaction.rollback() ;
		} finally {
			session.close() ;
		}
		return documents ;
	}
	
	//linking is wrong
	@SuppressWarnings("unchecked")
	public List<Document> findhomeworksByInformationAndTopic(String topic_id, String informationName){
		List<Document> homeworks = null;
		String sql = "select * from (document ,information ,user ) " +
				"left outer join " +
				"(select topic_homeworks.topic_id from topic_homeworks" +
					" where topic_homeworks.topic_id = ? " +
					"group by topic_homeworks.topic_id ) topicId " +
				"on topicId.topic_id = document.id " +
				"where information.name = ? and user.id = document.user_id " +
				"and information.id = user.information_id ;" ;
		Session session = getHibernateTemplate().getSessionFactory().openSession() ;
		Transaction transaction = session.beginTransaction() ;
		try{
			homeworks = session.createSQLQuery(sql)
					.addEntity(Document.class)
					.setString(0, topic_id)
					.setString(1, informationName).list() ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			transaction.rollback() ;
		} finally{
			session.close() ;
		}
		return homeworks ;
	}
}
