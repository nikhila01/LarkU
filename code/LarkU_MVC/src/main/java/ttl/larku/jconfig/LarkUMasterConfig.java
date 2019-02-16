package ttl.larku.jconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Import({LarkUDevelopmentConfig.class, LarkUProductionConfig.class})
@Profile({"development", "production"})
public class LarkUMasterConfig { 
	
}
