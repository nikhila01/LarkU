package ttl.larku.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ttl.larku.controllers.resty.RestResult;

@RestControllerAdvice
public class LastStopHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Throwable.class })
	protected RestResult lastPortOfCall(Exception ex, WebRequest request) {
		RestResult rr = new RestResult("Unexpected Exception: " + ex);
		return rr;
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		BindingResult br = ex.getBindingResult();
		List<String> errors = new ArrayList<>();
		for (FieldError e : br.getFieldErrors()) {
			errors.add(e.getField() + ": " + e.getDefaultMessage() + ", rejected value is " + e.getRejectedValue());
		}
		RestResult rr = new RestResult(errors);
		return ResponseEntity.badRequest().body(rr);
	}
}
