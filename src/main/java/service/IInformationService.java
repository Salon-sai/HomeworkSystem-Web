/**
 * 
 */
package service;

import java.util.List;

import model.Course;
import model.Information;
import model.Type;
import commom.IServiceTemplate;
import dao.IInformationDAO;

/**
 * @author sai
 *
 */
public interface IInformationService extends
		IServiceTemplate<IInformationDAO, Information> {

	public List<?> findBySuperInformation(Type type,Information information) ;
	
	public List<Course> coursesByInformationName(String name) ;
	
}
