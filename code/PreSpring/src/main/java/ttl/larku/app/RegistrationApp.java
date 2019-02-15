package ttl.larku.app;

import java.util.List;

import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

public class RegistrationApp {
	
	public static void main(String[] args) {
		StudentService ss = new StudentService();
		init(ss);
		
		List<Student> students = ss.getAllStudents();
		students.forEach(System.out::println);
	}
	
	public static void init(StudentService ss) {
		ss.createStudent("Manoj", "282 939 9944", Student.Status.FULL_TIME);
		ss.createStudent("Charlene", "282 898 2145", Student.Status.FULL_TIME);
		ss.createStudent("Firoze", "228 678 8765", Student.Status.HIBERNATING);
		ss.createStudent("Joe", "3838 678 3838", Student.Status.PART_TIME);
	}

}
