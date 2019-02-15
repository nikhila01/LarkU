package ttl.larku.controllers.rest.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import ttl.larku.controllers.resty.TemplateErrorHandler;
import ttl.larku.domain.Student;

public class RestClientTest {

	// GET with url parameters
	String rootUrl = "http://localhost:8080/adminrest/student";
	String oneStudentUrl = rootUrl + "/{id}";
	RestTemplate rt; 
	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() {
		rt = new RestTemplateBuilder().errorHandler(new TemplateErrorHandler()).build();
	}

	@Test
	public void testGetOneStudent() throws IOException {
		ResponseEntity<String> response = rt.getForEntity(oneStudentUrl, String.class, 1);
		assertEquals(200, response.getStatusCodeValue());

		String raw = response.getBody();
		JsonNode jsonNode = mapper.readTree(raw);
		JsonNode jn = jsonNode.findValue("errors");
		assertTrue(jn == null);

		Student s = mapper.treeToValue(jsonNode, Student.class);
		assertEquals("Manoj", s.getName());
	}

	@Test
	public void testGetOneStudentBadId() throws IOException {
		ResponseEntity<String> response = rt.getForEntity(oneStudentUrl, String.class, 10000);
		assertEquals(400, response.getStatusCodeValue());

		String raw = response.getBody();
		JsonNode jsonNode = mapper.readTree(raw);
		ArrayNode jn = (ArrayNode)jsonNode.findValue("errors");
		assertTrue(jn != null);

		StringBuffer sb = new StringBuffer(100);
		jn.forEach(node -> {
			sb.append(node.asText());
		});
		String reo = sb.toString();
		System.out.println("Error is " + reo);
		assertTrue(reo.contains("not found"));
	}
}
