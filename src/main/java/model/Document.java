package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name="document",catalog="project2")
public class Document extends File {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="pdf_path")
	private String pdf_path ;
	
	@Column(name="SWF_path")
	private String SWF_path ;
	
	@Column(name="score")
	private Integer score ;
	
	@ManyToOne
	@JoinColumn(name="type_id")
	private Type type ;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@Fetch(FetchMode.JOIN)
	private User user ;

	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course ;
	
	@OneToMany(
			cascade={CascadeType.PERSIST,CascadeType.MERGE},
			fetch=FetchType.LAZY
	)
	@JoinTable(name="topic_homeworks",
			joinColumns=@JoinColumn(name="topic_id"),
			inverseJoinColumns=@JoinColumn(name="homework_id")
	)
	private List<Document> homeworks ;
	
	@ManyToOne
	@JoinTable(name="topic_homeworks",
		joinColumns=@JoinColumn(name="homework_id"),
		inverseJoinColumns=@JoinColumn(name="topic_id")
	)
	private Document topic ;
	
	public String getPdf_path() {
		return pdf_path;
	}

	public void setPdf_path(String pdf_path) {
		this.pdf_path = pdf_path;
	}

	public String getSWF_path() {
		return SWF_path;
	}

	public void setSWF_path(String sWF_path) {
		SWF_path = sWF_path;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Document> getHomeworks() {
		return homeworks;
	}

	public void setHomeworks(List<Document> homeworks) {
		this.homeworks = homeworks;
	}

	public Document getTopic() {
		return topic;
	}

	public void setTopic(Document topic) {
		this.topic = topic;
	}
}
