/**
 * 
 */
package action;

import java.util.List;

import model.Type;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;


/**
 * @author sai
 *
 */
@Scope(value="request")
@Repository("typeAction")
@ParentPackage("json-default")
@Namespace("/typeOperate")
public class TypeOperateAction extends ActionTemplate<Type> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String typeName ;
	private String typeId ;
	private Type type ;
	private String returnMessage ;
	private List<?> types ;
	
	@Action(value="saveType",results={
			@Result(name="success",type="json"),
			@Result(name="input",type="json")}
	)
	public String save() {
		try{
			type = new Type() ;
			type.setName(typeName) ;
			typeService.save(type) ;
			returnMessage = "success to save the type!!" ;
			return SUCCESS ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}
	
	@Action(value="getTypeList",results={
			@Result(name="success",type="json"),
			@Result(name="input",type="json")}	
	)
	public String getTypeList(){
		try{
			types = typeService.findAll() ;
			return SUCCESS ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}

	@Action(value="deleteType",results={
			@Result(name="sucess",type="json"),
			@Result(name="input",type="json")}
	)
	public String delete() {
		try{
			typeService.delete(typeId) ;
			returnMessage = "success to delete the type!!" ;
			return SUCCESS ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
			return INPUT ;
		}
	}
	
	@Action(value="deleteTypeByName",results={
			@Result(name="success",type="json"),
			@Result(name="input",type="json")}
	)
	public String deleteByName(){
		try{
			type = typeService.findByName(typeName) ;
			typeService.delete(type);
			return SUCCESS ;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return INPUT ;
		}
		
	}
	
	/**
	 * getter and setter
	 * 
	 */
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public List<?> getTypes() {
		return types;
	}

	public void setTypes(List<?> types) {
		this.types = types;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
