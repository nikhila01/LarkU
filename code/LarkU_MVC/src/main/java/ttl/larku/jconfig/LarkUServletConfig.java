package ttl.larku.jconfig;

import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@EnableWebMvc  // for <mvc:annotation-config/>
@ComponentScan(basePackages={"ttl.larku.controllers"})
@Profile({"development", "production"})
public class LarkUServletConfig extends WebMvcConfigurerAdapter{

	@Bean 
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
		rbms.setBasename("larkU");

		return rbms;
		
	}

	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("en", "US"));
		//slr.setDefaultLocale(Locale.getDefault());
		
		return slr;
	}
	
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
		lvfb.setValidationMessageSource(messageSource());
		
		return lvfb;
	}
	
	
	@Bean
	public JAXBContext getJaxbContext() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance("ttl.larku.domain");
		return context;
	}
	
	/**
	 * Deal with static resources in the "normal" way
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
