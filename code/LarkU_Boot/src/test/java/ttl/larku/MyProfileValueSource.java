package ttl.larku;

import java.util.Arrays;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.ProfileValueSource;

@Component
public class MyProfileValueSource implements ProfileValueSource {

	@Override
	public String get(String property) {

		String result = null;
		try {
			Resource resource = new ClassPathResource("application.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			result = props.getProperty(property);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
