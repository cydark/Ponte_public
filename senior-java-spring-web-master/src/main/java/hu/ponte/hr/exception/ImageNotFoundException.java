package hu.ponte.hr.exception;

import org.springframework.http.HttpStatus;

public class ImageNotFoundException extends OverrideStatusCodeException {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8561656607125742820L;

	private final static String getErrorMsg(final String id) {
		return "ImageMeta not found by id: " + id;
	}
	public ImageNotFoundException(final String id) {
		super(getErrorMsg(id));
	}

	public ImageNotFoundException(final String id, Throwable t) {
		super(getErrorMsg(id), t);
	}
	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
