package ttl.larku.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ttl.larku.domain.Student;

@RepositoryRestResource(path="rr")
public interface StudentRepo extends JpaRepository<Student, Integer>{

	@Query("select s from Student s where s.name = :name")
	public List<Student> findByName(@Param("name") String name);
}
