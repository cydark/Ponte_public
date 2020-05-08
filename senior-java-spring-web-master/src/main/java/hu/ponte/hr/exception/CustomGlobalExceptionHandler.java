package hu.ponte.hr.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);

	// Let Spring handle the exception, we just override the status code

	@ExceptionHandler(OverrideStatusCodeException.class)
	public ResponseEntity<CustomErrorResponse> customHandleExceptions(Exception ex, WebRequest request) {

		log.error(ex.getMessage());

		CustomErrorResponse errors = new CustomErrorResponse();
		errors.setTimestamp(LocalDateTime.now());
		errors.setError(ex.getMessage());
		errors.setStatus(((OverrideStatusCodeException) ex).getHttpStatus().value());

		return new ResponseEntity<>(errors, ((OverrideStatusCodeException) ex).getHttpStatus());

	}
}
