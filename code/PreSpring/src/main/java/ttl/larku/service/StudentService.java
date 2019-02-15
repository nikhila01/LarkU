package ttl.larku.service;

import java.util.List;

import ttl.larku.dao.inmemory.StudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.domain.Student.Status;

public class StudentService {

	private StudentDAO studentDAO;
	
	public StudentService() {
		studentDAO = new StudentDAO();
	}
	
	public Student createStudent(String name, String phoneNumber, Status status) {
		Student student = new Student(name, phoneNumber, status);
		student = studentDAO.create(student);
		
		return student;
	}
	
	public Student createStudent(Student student) {
		student = studentDAO.create(student);
		
		return student;
	}
	
	public void deleteStudent(int id) {
		Student student = studentDAO.get(id);
		if(student != null) {
			studentDAO.delete(student);
		}
	}
	
	public void updateStudent(Student student) {
		studentDAO.update(student);
	}
	
	public Student getStudent(int id) {
		return studentDAO.get(id);
	}
	
	public List<Student> getAllStudents() {
		return studentDAO.getAll();
	}
	
	public StudentDAO getStudentDAO() {
		return studentDAO;
	}

	public void setStudentDAO(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

	public void clear() {
		studentDAO.deleteStore();
		studentDAO.createStore();
	}
}
