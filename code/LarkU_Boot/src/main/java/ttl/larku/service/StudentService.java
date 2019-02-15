package ttl.larku.service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;

@Service
@Transactional
public class StudentService {

	@Resource(name = "studentDAO")
	private BaseDAO<Student> studentDAO;

	public Student createStudent(String name) {
		Student student = new Student(name);
		student = studentDAO.create(student);

		return student;
	}

	public Student createStudent(Student student) {
		student = studentDAO.create(student);

		return student;
	}

	public void deleteStudent(int id) {
		Student student = studentDAO.get(id);
		if (student != null) {
			studentDAO.delete(student);
		}
	}

	public void updateStudent(Student student) {
		studentDAO.update(student);
	}

	public void updateStudentPartial(int id, Map<String, Object> props) {
		Student s = studentDAO.get(id);
		if (s != null) {
			Class<?> clazz = Student.class;
			Map<String, Method> methods = Arrays.asList(clazz.getMethods()).stream()
					.filter(m -> m.getName().startsWith("set")).collect(Collectors.toMap(m -> m.getName(), m -> m));

			props.forEach((name, value) -> {
				if (!name.equals("id")) {
					String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
					Method m = methods.get(setMethodName);
					if (m != null) {
						Class<?>[] paramTypes = m.getParameterTypes();
						if (paramTypes.length == 1) {
							Class<?> pClass = paramTypes[0];
							try {
								m.invoke(s, pClass.cast(value));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}

			});
		}
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
