package ttl.larku.converters;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * We need this as of tomee 7.0.2 (or thereabouts) to replace
 * Johnzon with Jackson so our @JsonIgnore etc annotations work
 * @author whynot
 *
 */
@Provider
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MyJacksonProvider extends JacksonJsonProvider {
}
