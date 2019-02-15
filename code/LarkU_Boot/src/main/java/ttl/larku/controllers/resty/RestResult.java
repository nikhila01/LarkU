package ttl.larku.controllers.resty;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RestResult {
	
	public enum Status {
		Ok,
		Error
	}

	@XmlElement
	private Status status;
	
	@XmlElement
	private List<String> errors;
	
	public RestResult() {}
	
	public RestResult(String message) {
		errors = new ArrayList<>();
		errors.add(message);
	}
	
	public RestResult(List<String> errors) {
		this.errors = errors;
	}

	public String getErrorMessage() {
		return errors.get(0);
	}

	public void setErrorMessage(String errorMessage) {
		errors.add(0, errorMessage);
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "RestResult [errors=" + errors + "]";
	}
}
