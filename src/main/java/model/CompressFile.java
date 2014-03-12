/**
 * 
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author sai
 *
 */
@Entity
@Table(name="compressFile",catalog="project2")
public class CompressFile extends File {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="uncompressFile_path")
	private String UncompressFile_path ;
	
	@Column(name="score")
	private int score ;
	
	@ManyToOne
	@JoinColumn(name="type_id")
	private Type type ;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user ;

	public String getUncompressFile_path() {
		return UncompressFile_path;
	}
	public void setUncompressFile_path(String uncompressFile_path) {
		UncompressFile_path = uncompressFile_path;
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
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
