package ttl.larku.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ttl.larku.service.StudentService;

public class TestDemo {
	
	
	@BeforeClass
	public static void beforeEverything() {
		
	}

	@Before
	public void setup() {
		
	}
	
	@Test
	public void foo() {
		int i = 10;
		
		StudentService ss = context.getBean("studentService");
		
		assertEquals(19, i);
	}

}
