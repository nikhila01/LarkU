package ttl.larku.controllers.resty;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import ttl.larku.domain.Student;
import ttl.larku.domain.StudentListHolder;
import ttl.larku.service.RegistrationService;

@Controller
@RequestMapping("/adminrest/student")
public class StudentRestyController {

	@Resource
	private RegistrationService regService;

	@RequestMapping(value = "/getStudent", method = RequestMethod.GET, consumes = { "text/html" })
	public ModelAndView getStudentWithForm(@RequestParam int id) {
		Student student = regService.getStudentService().getStudent(id);
		if (student == null) {
			// return new ModelAndView("/WEB-INF/jsp/error/studentNotFound.jsp");
			return new ModelAndView("error/studentNotFound");
		}
		// ModelAndView mav = new ModelAndView("/WEB-INF/jsp/showStudent.jsp",
		// "student", student);
		ModelAndView mav = new ModelAndView("showStudent", "student", student);
		return mav;
	}

	@RequestMapping(value = "/addStudents", method = RequestMethod.GET)
	public ModelAndView addStudentsResty() {
		// ModelAndView mav = new ModelAndView("/WEB-INF/jsp/addStudentsJQueryAjax.jsp",
		// "statusList", Student.Status.values());
		ModelAndView mav = new ModelAndView("addStudentsJQueryAjax", "statusList", Student.Status.values());
		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET,
			//produces = { "application/xml", "application/json", "application/csv" })
			produces = { "application/xml" })
	public @ResponseBody Object getStudentXJWithPathVariable(@PathVariable("id") int id) {
		Student student = regService.getStudentService().getStudent(id);
		if (student == null) {
			// return new RestResult("Student with id: " + id + " not found");
			return new ResponseEntity<RestResult>(new RestResult("Student with id: " + id + " not found"),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Student>(student, HttpStatus.OK);
		// return student;
	}

	@RequestMapping(value = "/studentsParam", method = RequestMethod.GET, produces = { "application/xml",
			"application/json" })
	public @ResponseBody Object getStudentXJWithRequestParam(@RequestParam("id") int id) {
		Student student = regService.getStudentService().getStudent(id);
		if (student == null) {
			return new RestResult("Student with id: " + id + " not found");
		}
		return student;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/xml", "application/json" }, produces = {
			"application/xml", "application/json" })
	public @ResponseBody ResponseEntity<?> addStudent(@RequestBody Student student, 
			UriComponentsBuilder ucb) {
		
		Student student2 = regService.getStudentService().createStudent(student);

		UriComponents uriComponents = ucb.path("/student/{id}").buildAndExpand(student2.getId());

		return ResponseEntity.created(uriComponents.toUri()).body(student2);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/xml",
			"application/json" }, produces = { "application/xml", "application/json" })
	public @ResponseBody void updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
		regService.getStudentService().updateStudent(student);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PATCH, consumes = { "application/xml",
			"application/json" }, produces = { "application/xml", "application/json" })
	public @ResponseBody void updateStudentPartial(@PathVariable("id") int id, @RequestBody Map<String, Object> props) {
		regService.getStudentService().updateStudentPartial(id, props);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/xml",
			"application/json" }, produces = { "application/xml", "application/json" })
	public @ResponseBody void deleteStudent(@PathVariable("id") int id) {
		regService.getStudentService().deleteStudent(id);
	}

	@RequestMapping(value = "/showStudents", method = RequestMethod.GET)
	public ModelAndView showStudents() {
		List<Student> students = regService.getStudentService().getAllStudents();
		// return new ModelAndView("/WEB-INF/jsp/showStudentsJQuery.jsp", "students",
		// students);
		return new ModelAndView("showStudentsJQuery", "students", students);
	}

	@RequestMapping(method = RequestMethod.GET, produces = { "application/xml", "application/json" })
	public @ResponseBody List<Student> getStudentsXmlJson() {
		List<Student> students = regService.getStudentService().getAllStudents();
		return students;
	}

	@RequestMapping(value = "/heldstudents", method = RequestMethod.GET, produces = { "application/xml",
			"application/json" })
	public @ResponseBody StudentListHolder getStudentsXmlJsonHolder() {
		List<Student> students = regService.getStudentService().getAllStudents();
		return new StudentListHolder(students);
	}

}