package ttl.larku.controllers.resty;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

@RestController
@RequestMapping("/rester/student")
public class Studenter {
	
	@Resource
	private StudentService studentService;

	@GetMapping
	public List<Student> getAllStudents() {
		List<Student> students = studentService.getAllStudents();
		return students;
	}
	
	@PostMapping
	public ResponseEntity<?> createStudent(@RequestBody Student s, 
			UriComponentsBuilder ucb, HttpServletRequest requst) {
		s = studentService.createStudent(s);
		UriComponents uriComponents = ucb.path("/student/{id}")
				.buildAndExpand(s.getId());

		return ResponseEntity.created(uriComponents.toUri()).body(s);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getStudent(@PathVariable("id") int id) {
		Student s = studentService.getStudent(id);
		if(s == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(s);
	}
}
