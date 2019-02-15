package ttl.larku.dao.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ttl.larku.domain.Student;

@Repository
@Transactional
public interface StudentRepo extends JpaRepository<Student, Integer>{

	@Query("select s from Student s where s.name = :name")
	public List<Student> findByName(@Param("name") String name);
}
