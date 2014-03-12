package dao;

import java.util.List;

import model.Course;
import model.Information;
import commom.IDAOTemplate;

public interface IInformationDAO extends IDAOTemplate<Information> {
	public List<Course> CoursesbyInformationName(String name) ;
}
