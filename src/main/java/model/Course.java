/**
 * 
 */
package model;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author sai
 *
 */
@Entity
@Table(name="course",catalog="project2")
public class Course implements Serializable {

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
	
	@ManyToOne
	@JoinColumn(name="type_id")
	@Fetch(FetchMode.JOIN)
	private Type type ;
	
	@ManyToMany(
			fetch=FetchType.LAZY,
			targetEntity=User.class,
			cascade={CascadeType.PERSIST,CascadeType.MERGE}
	)
	@JoinTable(
			name="course_student",
			joinColumns=@JoinColumn(name="course_id"),
			inverseJoinColumns=@JoinColumn(name="student_id")
	)
	@Fetch(FetchMode.SUBSELECT)
	private List<User> students ;
	
	@ManyToMany(
			targetEntity=User.class,
			cascade={CascadeType.PERSIST,CascadeType.MERGE}
	)
	@JoinTable(
			name="course_teacher",
			joinColumns=@JoinColumn(name="course_id"),
			inverseJoinColumns=@JoinColumn(name="teacher_id")
	)
	@Fetch(FetchMode.SUBSELECT)
	private List<User> teachers ;
	
	@ManyToMany(
			targetEntity=Information.class,
			cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(
			name="course_information",
			joinColumns=@JoinColumn(name="course_id"),
			inverseJoinColumns=@JoinColumn(name="information_id")
	)
	@Fetch(FetchMode.SUBSELECT)
	private List<Information> informations ;

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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<User> getStudents() {
		return students;
	}

	public void setStudents(List<User> students) {
		this.students = students;
	}

	public List<User> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<User> teachers) {
		this.teachers = teachers;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}
}
