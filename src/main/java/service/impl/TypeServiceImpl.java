/**
 * 
 */
package service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import model.Type;
import service.ITypeService;

import commom.AbstractTemplateService;
import dao.ITypeDAO;

/**
 * @author sai
 *
 */
@Repository("typeService")
public class TypeServiceImpl extends AbstractTemplateService<ITypeDAO, Type>
		implements ITypeService {
	
	@Override
	@Resource(name="TypeDAO")
	public void setDao(ITypeDAO dao) {
		// TODO Auto-generated method stub
		this.dao = dao ;
	}
}
