package ttl.larku.controllers.resty;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.ClassService;
import ttl.larku.service.RegistrationService;

@RestController
@RequestMapping("/adminrest/class")
public class RegistrationRestyController {
	
	@Resource
	private RegistrationService regService;
	
	@Resource
	private ClassService classService;
	
	@GetMapping
	public List<ScheduledClass> getAllClasses() {
		List<ScheduledClass> classes = classService.getAllScheduledClasses();
		return classes;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getScheduledClass(@PathVariable("id") int id) {
		ScheduledClass c = classService.getScheduledClass(id);
		if(c == null) {
			RestResult rr = new RestResult("ScheduledClass with id: " + id + " not found");
			return ResponseEntity.badRequest().body(rr);
		}
		return ResponseEntity.ok(c);
	}

	@GetMapping("/{courseCode}/{startDate}/{endDate}")
	public Object getScheduledClassPath(@PathVariable("courseCode") String courseCode,@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {
		List<ScheduledClass> cl = classService.getScheduledClassesByCourseCode(courseCode);
		if(cl == null || cl.size() == 0) {
			return new RestResult("ScheduledClass with code: " +  courseCode + " not found");
		}
		Optional<ScheduledClass> optional = cl.stream()
				.filter(c -> c.getStartDate().equals(startDate) && 
						c.getEndDate().equals(endDate))
				.findFirst();

		return optional.isPresent() ? optional.get() : new RestResult("ScheduledClass with code: " +  courseCode + " not found");
	}
	

	@PostMapping
	public ResponseEntity<?> addClass(@RequestParam("courseCode") String courseCode,
			@RequestParam("startDate") String startDate, 
			@RequestParam("endDate") String endDate,
			UriComponentsBuilder ucb) {
		
		ScheduledClass sc = regService.addNewClassToSchedule(courseCode, startDate, endDate);
				
		UriComponents uriComponents = ucb.path("/class/{id}").buildAndExpand(sc.getId());

		return ResponseEntity.created(uriComponents.toUri()).body(sc);
	}

	@PostMapping(value="/{courseCode}/{startDate}/{endDate}")
	public ResponseEntity<?> addClassPathParams(@PathVariable("courseCode") String courseCode,
			@PathVariable("startDate") String startDate, 
			@PathVariable("endDate") String endDate,
			UriComponentsBuilder ucb) {
		
		return addClass(courseCode, startDate, endDate, ucb);
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public Student registerStudent(@RequestParam int studentId, @RequestParam int classId) {
		
		ScheduledClass sClass = regService.getClassService().getScheduledClass(classId);
		regService.registerStudentForClass(studentId, sClass.getCourse().getCode(), sClass.getStartDate());
		
		Student student = regService.getStudentService().getStudent(studentId);
		
		//return new ModelAndView("/WEB-INF/jsp/showStudent.jsp", "student", student);
		return student;
	}

	@RequestMapping(value="/register/{studentId}/{classId}", method=RequestMethod.POST)
	public Student registerStudentWithPath(@PathVariable int studentId, @PathVariable int classId) {
		
		return registerStudent(studentId, classId);
	}
}
