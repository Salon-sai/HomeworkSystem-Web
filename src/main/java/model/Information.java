package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="information",catalog="project2")
public class Information implements Serializable {

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
	private Type type ;
	
	@ManyToOne
	@JoinColumn(name="information_id")
	@Fetch(FetchMode.JOIN)
	private Information information ;
	
	@OneToMany(mappedBy="information")
	@Fetch(FetchMode.JOIN)
	private List<Information> informations ;
	
	@ManyToMany(
			targetEntity=Course.class,
			cascade={CascadeType.PERSIST, CascadeType.MERGE}
	)
	@JoinTable(
			name="course_information",
			joinColumns=@JoinColumn(name="information_id"),
			inverseJoinColumns=@JoinColumn(name="course_id")
	)
	@Fetch(FetchMode.SUBSELECT)
	private List<Course> courses ;
	
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

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public List<Information> getInformations() {
		return informations;
	}

	public void setInformations(List<Information> informations) {
		this.informations = informations;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
}
