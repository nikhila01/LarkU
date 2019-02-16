package ttl.larku.jconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;

@Configuration
@Import({ LarkUConfig.class, LarkUDBConfig.class })
@Profile("development")
public class LarkUDevelopmentConfig { 

	@Autowired
	private LarkUDBConfig testData;

	@Bean
	public BaseDAO<Student> studentDAO() {
		// return testData.studentDAOWithInitData();
		return testData.jpaStudentDAO();
	}

	@Bean
	public BaseDAO<ScheduledClass> classDAO() {
		// return testData.classDAOWithInitData();
		return testData.jpaClassDAO();
	}

	@Bean
	public BaseDAO<Course> courseDAO() {
		// return testData.courseDAOWithInitData();
		return testData.jpaCourseDAO();
	}
}
