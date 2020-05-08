package hu.ponte.hr.exception;

import org.springframework.http.HttpStatus;

/*
 * // Let Spring handle the exception, we just override the status code
 */
public abstract class OverrideStatusCodeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1715893715803467654L;

	public OverrideStatusCodeException(String msg) {
		super(msg);
	}

	public OverrideStatusCodeException(String msg, Throwable t) {
		super(msg, t);
	}

	public abstract HttpStatus getHttpStatus();
}
