package ttl.larku.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ttl.larku.dao.inmemory.CourseDAO;
import ttl.larku.dao.inmemory.StudentDAO;
import ttl.larku.service.CourseService;
import ttl.larku.service.StudentService;

@Configuration
public class LarkUConfig {
	
	@Bean
	public StudentDAO studentDAO() {
		return new StudentDAO();
	}
	
	@Bean 
	public StudentService studentService() {
		StudentService ss = new StudentService();
		ss.setStudentDAO(studentDAO());
		
		return ss;
	}
	
	@Bean 
	public CourseDAO courseDAO() {
		return new CourseDAO();
	}
	
	@Bean
	public CourseService courseService() {
		CourseService cc = new CourseService();
		cc.setCourseDAO(courseDAO());
		
		return cc;
	}
}
