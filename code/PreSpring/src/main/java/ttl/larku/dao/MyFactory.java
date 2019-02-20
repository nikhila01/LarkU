package ttl.larku.dao;

import java.util.ResourceBundle;

import ttl.larku.dao.inmemory.InMemoryCourseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.dao.jpa.JpaCourseDAO;
import ttl.larku.dao.jpa.JpaStudentDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.Student;
import ttl.larku.service.CourseService;
import ttl.larku.service.StudentService;

public class MyFactory {

	private static BaseDAO<Student> studentDAO;

	public static BaseDAO<Student> studentDAO() {
		if (studentDAO == null) {
			ResourceBundle bundle = ResourceBundle.getBundle("larkUContext");
			String profile = bundle.getString("larku.profile.active");
			switch (profile) {
			case "dev":
				return studentDAO = new InMemoryStudentDAO();
			case "prod":
				return studentDAO = new JpaStudentDAO();
			default:
				throw new RuntimeException("No profile set");
			}
		} else {
			return studentDAO;
		}
	}

	public static BaseDAO<Course> courseDAO() {
		ResourceBundle bundle = ResourceBundle.getBundle("larkUContext");
		String profile = bundle.getString("larku.profile.active");
		switch (profile) {
		case "dev":
			return new InMemoryCourseDAO();
		case "prod":
			return new JpaCourseDAO();
		default:
			throw new RuntimeException("No profile set");
		}
	}

	public static StudentService studentService() {
		StudentService ss = new StudentService();

		ss.setStudentDAO(studentDAO());

		return ss;
	}

	public static CourseService courseService() {
		CourseService ss = new CourseService();

		ss.setCourseDAO(courseDAO());

		return ss;
	}
}
