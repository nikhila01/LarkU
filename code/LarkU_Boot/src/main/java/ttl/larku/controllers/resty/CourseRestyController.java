package ttl.larku.controllers.resty;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ttl.larku.domain.Course;
import ttl.larku.service.CourseService;

@RestController
@RequestMapping("/adminrest/course")
public class CourseRestyController {
	
	@Resource
	private CourseService courseService;

	@GetMapping
	public List<Course> getCourses() {
		return courseService.getAllCourses();
	}
	
	@GetMapping("/{id}")
	public Object getCourse(@PathVariable("id") int id) {
		Course c = courseService.getCourse(id);
		if(c == null) {
			return new RestResult("Course with id: " + id + " not found");
		}
		return c;
	}
	
	@DeleteMapping("/{id}")
	public void deleteCourse(@PathVariable("id") int id) {
		courseService.deleteCourse(id);
	}
}
