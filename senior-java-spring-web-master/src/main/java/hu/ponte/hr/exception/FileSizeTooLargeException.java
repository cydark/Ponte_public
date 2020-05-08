package hu.ponte.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeTooLargeException extends OverrideStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 505100503237158011L;

	private static String getErrorString(final MultipartFile file) {
		return file.getOriginalFilename() + " file size is too large: " + file.getSize()
				+ ", maximum upload size: 2 MB";
	}

	public FileSizeTooLargeException(final MultipartFile file) {
		super(getErrorString(file));
	}

	public FileSizeTooLargeException(final MultipartFile file, Throwable t) {
		super(getErrorString(file), t);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_ACCEPTABLE;
	}

}
