package ttl.larku.service;

import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import ttl.larku.cdi.events.Created;
import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;

public class StudentService {

	@Inject @DBQualifier(DBType.STUDENT)
	private BaseDAO<Student> studentDAO;
	
	@Inject
	@Created
	private Event<Student> createdEvent;
	
	public StudentService() {
		int boo = 10;
	}
	public Student createStudent(String name) {
		Student student = new Student(name);
		return createStudent(student);
	}
	
	public Student createStudent(Student student) {
		student = studentDAO.create(student);
		
		//createdEvent.fire(student);
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
	
	public BaseDAO<Student> getStudentDAO() {
		return studentDAO;
	}

	public void setStudentDAO(BaseDAO<Student> studentDAO) {
		this.studentDAO = studentDAO;
	}

	public void clear() {
		studentDAO.deleteStore();
		studentDAO.createStore();
	}
}
