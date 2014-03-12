/**
 * 
 */
package service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import model.Course;
import model.Information;
import model.Type;
import service.IInformationService;

import commom.AbstractTemplateService;
import dao.IInformationDAO;

/**
 * @author sai
 *
 */
@Repository("informationService")
public class InformationServiceImpl extends
		AbstractTemplateService<IInformationDAO, Information> implements
		IInformationService {
	
	@Override
	@Resource(name="InformationDAO")
	public void setDao(IInformationDAO dao) {
		// TODO Auto-generated method stub
		super.setDao(dao);
	}
	
	public List<?> findByProperties(String[] names, Object[] values) {
		// TODO Auto-generated method stub
		return super.findByProperties(names, values, "Information");
	}
	
	@SuppressWarnings("unchecked")
	public List<?> findBySuperInformation(Type type,Information information){
		if(information == null){
			return this.findByProperty("type", type) ;
			}
		List<Information> list = (List<Information>)this.findByProperties(
				new String[]{"type","information"}, new Object[]{type,information}) ;
		for(Information subinformation : list){
			subinformation.setCourses(null) ;
		}
		return list ;
	}
	
	@Override
	public List<Information> findAll() {
		// TODO Auto-generated method stub
		List<Information> list = super.findAll() ;
		for(Information information : list){
			clearcourse(information) ;
		}
		return list ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		List<Information> list =(List<Information>) super
				.findByProperty(propertyName, value) ;
		for(Information information : list){
			clearcourse(information) ;
		}
		return list ;
	}
	
	public List<Course> coursesByInformationName(String name){
		List<Course> courses = dao.CoursesbyInformationName(name) ;
		for(Course course : courses){
			clearinformation(course) ;
			clearstudent(course) ;
			clearteacher(course) ;
		}
		return courses ;
	}
	
	public void clearcourse(Information information) {
		information.setCourses(null) ;
		if(!information.getInformations().isEmpty()){
			for(Information subinformation : information.getInformations()){
				clearcourse(subinformation) ;
			}
		}
	}
	
	public void clearinformation(Course course){
		course.setInformations(null) ;
	}
	
	public void clearstudent(Course course){
		course.setStudents(null);
	}
	
	public void clearteacher(Course course){
		course.setTeachers(null) ;
	}

}
