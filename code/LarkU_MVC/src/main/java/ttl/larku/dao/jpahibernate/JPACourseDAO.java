package ttl.larku.dao.jpahibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;

@Transactional
public class JPACourseDAO implements BaseDAO<Course> {

	@PersistenceContext //(unitName="LarkUPU_SE")
	private EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void update(Course updateObject) {
		entityManager.merge(updateObject);
	}

	@Override
	public void delete(Course objToDelete) {
		Course managed = entityManager.find(Course.class, objToDelete.getId());
		entityManager.remove(managed);
	}

	@Override
	public Course create(Course newObject) {
		entityManager.persist(newObject);
		return newObject;
	}

	@Override
	public Course get(int id) {
		return entityManager.find(Course.class, id);
	}

	@Override
	public List<Course> getAll() {
		Query query = entityManager.createQuery("Select s from Course s", Course.class);
		List<Course> courses = cast(query.getResultList(), Course.class);
		return courses;
	}
	
	@Override
	public void deleteStore() {
		Query query = entityManager.createQuery("Delete from Course");
		query.executeUpdate();
	}
	
	@Override
	public void createStore() {
	}
	
	private <T> List<T> cast(List<?> list, Class<T> cls) {
		List<T> result = new ArrayList<T>();
		for(Object o : list) {
			result.add(cls.cast(o));
		}
		
		return result;
	}
}
