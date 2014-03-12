/**
 * 
 */
package action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import model.Information;

/**
 * @author sai
 *
 */
@Scope(value="request")
@Namespace("/informationOperate")
@Repository(value="informationAction")
@ParentPackage(value="json-default")
public class InformationOperateAction extends ActionTemplate<Information> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String informationName ;
	private Information information ;
	private String TypeName ;
	private String superInformationName ;
	private List<?> informations ;
	private String courseName;
	
	@Action(value="saveInformation",results={
			@Result(name="success",type="json",
					params={
					"excludeNullProperties","true"
					}),
			@Result(name="input",type="json",
					params={
					"excludeNullProperties","true"
					})
			}
	)
	public String saveInformation() {
		try{
			information = new Information() ;
			information.setName(informationName) ;
			information.setType(typeService.findByName(TypeName)) ;
			information.setInformation(informationService.findByName(superInformationName)) ;
			informationService.save(information) ;
			return SUCCESS ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}

	@Action(value="getInformations",results={
			@Result(name="success",type="json",
					params={
					"excludeNullProperties","true"
					}),
			@Result(name="input",type="json",
					params={
					"excludeNullProperties","true"
					})
	})
	public String InformationList(){
		try{
			informations = informationService
					.findBySuperInformation(typeService.findByName(TypeName),
							informationService.findByName(superInformationName)) ;
			return SUCCESS ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT ;
		}
	}
	
	@Action(value="getDepartments",results={
			@Result(name="success",type="json",
					params={
					"excludeNullProperties","true"
					}),
			@Result(name="input",type="json",
					params={
					"excludeNullProperties","true"
					})
	})
	public String AllInformationList(){
		try{
			informations = informationService.findByProperty("type",
					typeService.findByName("department"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT ;
		}
		return SUCCESS ;
	}
	
	@Action(value="deleteInformationByName",results={
			@Result(name="success",type="json",
					params={
					"excludeNullProperties","true"
					}),
			@Result(name="input",type="json",
					params={
					"excludeNullProperties","true"
					})
	})
	public String deleteInformationByName(){
		try{
			informationService.delete(informationService
					.findByName(informationName)) ;
			return SUCCESS ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}
	
	@Action(value="getInformationByCourse",results={
			@Result(name="success",type="json",
					params={
					"excludeNullProperties","true"
					}),
			@Result(name="input",type="json",
					params={
					"excludeNullProperties","true"
					})
		}
	)
	public String findInformationByCourse(){
		try{
			informations = courseService.findInformationByCourse(courseName) ;
			return SUCCESS;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT;
		}
	}
	
	/**
	 * getter and setter
	 */

	public Information getInformation() {
		return information;
	}
	public String getInformationName() {
		return informationName;
	}
	public void setInformationName(String informationName) {
		this.informationName = informationName;
	}
	public void setInformation(Information information) {
		this.information = information;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	public String getSuperInformationName() {
		return superInformationName;
	}
	public void setSuperInformationName(String superInformationName) {
		this.superInformationName = superInformationName;
	}
	public List<?> getInformations() {
		return informations;
	}
	public void setInformations(List<?> informations) {
		this.informations = informations;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
