package ttl.larku.dao.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;

//@DBQualifier(DBType.CLASS)
@ApplicationScoped
public class InMemoryClassDAO implements BaseDAO<ScheduledClass> {

	private Map<Integer, ScheduledClass> classes = new HashMap<Integer, ScheduledClass>();
	private static int nextId = 0;
	
	public InMemoryClassDAO() {
		int i = 10;
	}
	
	
	@Override
	public void update(ScheduledClass updateObject) {
		if(classes.containsKey(updateObject.getId())) {
			classes.put(updateObject.getId(), updateObject);
		}
	}

	@Override
	public void delete(ScheduledClass sc) {
		classes.remove(sc.getId());
	}

	@Override
	public ScheduledClass create(ScheduledClass newObject) {
		//Create a new Id
		int newId = nextId++;
		newObject.setId(newId);
		classes.put(newId, newObject);
		
		return newObject;
	}

	@Override
	public ScheduledClass get(int id) {
		return classes.get(id);
	}

	@Override
	public List<ScheduledClass> getAll() {
		return new ArrayList<ScheduledClass>(classes.values());
	}
	
	@Override
	public void deleteStore() {
		classes = null;
	}
	
	@Override
	public void createStore() {
		classes = new HashMap<Integer, ScheduledClass>();
	}
}
