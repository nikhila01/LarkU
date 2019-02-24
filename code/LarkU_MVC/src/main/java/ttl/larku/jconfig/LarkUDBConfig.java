package ttl.larku.jconfig;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.hibernate.HibernateStudentDAO;
import ttl.larku.dao.inmemory.InMemoryClassDAO;
import ttl.larku.dao.inmemory.InMemoryCourseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.dao.jdbctemplate.JDBCTemplateClassDAO;
import ttl.larku.dao.jdbctemplate.JDBCTemplateCourseDAO;
import ttl.larku.dao.jdbctemplate.JDBCTemplateStudentDAO;
import ttl.larku.dao.jpahibernate.JPAClassDAO;
import ttl.larku.dao.jpahibernate.JPACourseDAO;
import ttl.larku.dao.jpahibernate.JPAStudentDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;

/**
 * Database Config
 * 
 * @author whynot
 *
 */
@Configuration
@EnableTransactionManagement
@Profile({"development", "production"})
public class LarkUDBConfig implements TransactionManagementConfigurer {

	@Autowired
	private Environment env;
	
	@Autowired
	private ApplicationContext appContext;
	
	// In Memory DAOs
	@Bean(name = { "inMemoryClassDAO" })
	public InMemoryClassDAO inMemoryClassDAO() {
		return new InMemoryClassDAO();
	}

	@Bean(name = { "inMemoryCourseDAO" })
	public InMemoryCourseDAO inMemoryCourseDAO() {
		return new InMemoryCourseDAO();
	}

	@Bean(name = { "inMemoryStudentDAO" })
	public InMemoryStudentDAO inMemoryStudentDAO() {
		return new InMemoryStudentDAO();
	}
	
	@Bean
	public JDBCTemplateClassDAO jdbcTemplateClassDAO() {
		return new JDBCTemplateClassDAO();
	}

	@Bean
	public JDBCTemplateCourseDAO jdbcTemplateCourseDAO() {
		return new JDBCTemplateCourseDAO();
	}

	@Bean
	public JDBCTemplateStudentDAO jdbcTemplateStudentDAO() {
		return new JDBCTemplateStudentDAO();
	}
	
	//Hibernate DAOs
	
	@Bean
	public BaseDAO<Student> hibernateStudentDAO() {
		return new HibernateStudentDAO();
	}

	// JPA DAOs
	@Bean
	public BaseDAO<Student> jpaStudentDAO() {
		return new JPAStudentDAO();
	}

	@Bean
	public BaseDAO<ScheduledClass> jpaClassDAO() {
		return new JPAClassDAO();
	}

	@Bean
	public BaseDAO<Course> jpaCourseDAO() {
		return new JPACourseDAO();
	}


	/**
	 * We give both DataSource producers the same bean name and
	 * different profiles.  Then, for dependency injection, 
	 * do a lookup: appContext.getBean("dataSource", DataSource.class)
	 * @return
	 */
	@Bean(name = "dataSource")
	@Profile("production")
	public DataSource dataSourceReal() {
		SimpleDriverDataSource sdds = new SimpleDriverDataSource();
		sdds.setUrl("jdbc:derby://localhost/LarkUDB");
		sdds.setDriverClass(org.apache.derby.jdbc.ClientDriver.class);
		sdds.setUsername("larku");
		sdds.setPassword("larku");

		return sdds;
	}

	@Bean(name = "dataSource")
	@Profile("development")
	public DataSource dataSourceInMemory() {
		SimpleDriverDataSource sdds = new SimpleDriverDataSource();
		sdds.setUrl("jdbc:derby:memory:LarkUDB;create=true");
		sdds.setDriverClass(org.apache.derby.jdbc.EmbeddedDriver.class);
		sdds.setUsername("larku");
		sdds.setPassword("larku");

		return sdds;
	}
	

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate template = new JdbcTemplate();
		DataSource ds = appContext.getBean("dataSource", DataSource.class);
		template.setDataSource(ds);
		
		return template;
	}

	@Autowired
	private DataSource dataSource;
	
	@Bean //(name = "LarkUPU_SE")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lcemfg = new LocalContainerEntityManagerFactoryBean();
		//We do a name look up here to get dataSource for the current profile
		//so we can set the Unit name properly
		//Note the the names *must* be the names of actual PersitenceUnits
		//in the persistence.xml file
		DataSource ds = appContext.getBean("dataSource", DataSource.class);
		//DataSource ds = dataSource();
		String pu = "LarkUPU_SE";
		if(ds instanceof SimpleDriverDataSource) {
			SimpleDriverDataSource sdds = (SimpleDriverDataSource)ds;
			if(sdds.getDriver().getClass() == EmbeddedDriver.class) {
				pu = "LarkUPU_SE_InMemory";
			}
		}
		//lcemfg.setDataSource(ds);
		lcemfg.setDataSource(ds);
		lcemfg.setPersistenceUnitName(pu);
		
		return lcemfg;
	}

	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		DataSource ds = appContext.getBean("dataSource", DataSource.class);
		lsfb.setDataSource(ds);
		//lsfb.setDataSource(dataSource());
		lsfb.setPackagesToScan("ttl.larku.domain");

		Properties props = new Properties();
		props.put("dialect", "org.hibernate.dialect.DerbyTenSevenDialect");
		props.put("javax.persistence.validation.mode", "none");

		lsfb.setHibernateProperties(props);

		return lsfb;
	}
	

	@Bean
	public JpaTransactionManager jpaTransactionManager() {
		JpaTransactionManager jtm = new JpaTransactionManager();
		jtm.setEntityManagerFactory(entityManagerFactory().getObject());

		return jtm;
	}

	@Bean
	public DataSourceTransactionManager dsTransactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		DataSource ds = appContext.getBean("dataSource", DataSource.class);
		//tm.setDataSource(dataSource());
		tm.setDataSource(ds);

		return tm;
	}

	
	@Bean
	public HibernateTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager jtm = new HibernateTransactionManager();
		jtm.setSessionFactory(sessionFactory().getObject());

		return jtm;
	}

	// Set the transaction manager to be used,
	// as, in <tx:annotation-driven transaction-manager="jpaTransactionManager"
	// 
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return jpaTransactionManager();
	}

	private boolean containsProfile(String[] profiles, String profile) {
		for (String p : profiles) {
			if (p.equals(profile)) {
				return true;
			}
		}

		return false;
	}
}
