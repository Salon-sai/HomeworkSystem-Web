package model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user",catalog="project2")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(name="id")
	private String id ;
	
	@Column(name="name")
	private String name ;
	
	@Column(name="idNum")
	private String IdNum ;
	
	@Column(name="password")
	private String password ;
	
	@ManyToOne
	@JoinColumn(name="type_id")
	@Fetch(FetchMode.JOIN)
	private Type type ;
	
	@ManyToOne
	@JoinColumn(name="information_id")
	@Fetch(FetchMode.JOIN)
	private Information information ;
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private Set<Document> documents ;
	
	@OneToMany(mappedBy="user")
	private Set<CompressFile> files ;
	
	@ManyToMany(
			targetEntity=Course.class,
			cascade={CascadeType.PERSIST,CascadeType.MERGE}
	)
	@JoinTable(
			name="course_student",
			inverseJoinColumns=@JoinColumn(name="course_id"),
			joinColumns=@JoinColumn(name="student_id")
	)
	@Fetch(FetchMode.SUBSELECT)
	private List<Course> stucourses ;
	
	@ManyToMany(
			targetEntity=Course.class,
			cascade={CascadeType.PERSIST,CascadeType.MERGE}
	)
	@JoinTable(
			name="course_teacher",
			inverseJoinColumns=@JoinColumn(name="course_id"),
			joinColumns=@JoinColumn(name="teacher_id")
	)
	private List<Course> teacourses ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public Set<CompressFile> getFiles() {
		return files;
	}

	public void setFiles(Set<CompressFile> files) {
		this.files = files;
	}

	public String getIdNum() {
		return IdNum;
	}

	public void setIdNum(String idNum) {
		IdNum = idNum;
	}

	public List<Course> getStucourses() {
		return stucourses;
	}

	public void setStucourses(List<Course> stucourses) {
		this.stucourses = stucourses;
	}

	public List<Course> getTeacourses() {
		return teacourses;
	}

	public void setTeacourses(List<Course> teacourses) {
		this.teacourses = teacourses;
	}
}
