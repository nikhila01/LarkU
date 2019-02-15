package ttl.larku.dao.jpa;

import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static ttl.larku.dao.jpa.JPAHelper.commitAndCloseEM;
import static ttl.larku.dao.jpa.JPAHelper.getEMAndBegin;

public class JPACourseDAO implements BaseDAO<Course> {

	//@PersistenceContext(unitName="LarkUPU_SE")
	private EntityManager entityManager;


	@Override
	public void update(Course updateObject) {
		EntityManager entityManager = getEMAndBegin();
		entityManager.merge(updateObject);
		commitAndCloseEM();
	}

	@Override
	public void delete(Course objToDelete) {
		EntityManager entityManager = getEMAndBegin();
		Course managed = entityManager.find(Course.class, objToDelete.getId());
		entityManager.remove(managed);
		commitAndCloseEM();
	}

	@Override
	public Course create(Course newObject) {
		EntityManager entityManager = getEMAndBegin();
		entityManager.persist(newObject);
		commitAndCloseEM();
		return newObject;
	}

	@Override
	public Course get(int id) {
		EntityManager entityManager = getEMAndBegin();
		Course c = entityManager.find(Course.class, id);
		commitAndCloseEM();
		return c;
	}

	@Override
	public List<Course> getAll() {
		EntityManager entityManager = getEMAndBegin();
		Query query = entityManager.createQuery("Select s from Course s");
		List<Course> courses = query.getResultList();
		commitAndCloseEM();
		return courses;
	}
	
	@Override
	public void deleteStore() {
		EntityManager entityManager = getEMAndBegin();
		Query query = entityManager.createQuery("Delete from Course");
		query.executeUpdate();
		commitAndCloseEM();
	}
	
	@Override
	public void createStore() {
	}
}
