package ttl.larku.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Class to initialize Web Application with no need for Web.xml
 * @author whynot
 *
 */
public class LarkUInitializer { //implements WebApplicationInitializer {
	//@Override
	public void onStartup(ServletContext container) {
		//The application level context
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();


		appContext.register(ttl.larku.jconfig.LarkUMasterConfig.class);
		container.addListener(new ContextLoaderListener(appContext));
		
		//active profile
		appContext.getEnvironment().setActiveProfiles( "development" );

		appContext.refresh();

		//The Framework level context.  We make the appContext the parent.
		//So we can see app beans, but app beans can't see mvc elements
		AnnotationConfigWebApplicationContext dispatcherContext = 
				new AnnotationConfigWebApplicationContext();
		dispatcherContext.setParent(appContext);
		dispatcherContext.register(ttl.larku.jconfig.LarkUServletConfig.class);


		DispatcherServlet ds = new DispatcherServlet(dispatcherContext);

		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", ds);

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}
