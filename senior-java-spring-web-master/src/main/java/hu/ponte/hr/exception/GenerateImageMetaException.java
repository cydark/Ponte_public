package hu.ponte.hr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

public class GenerateImageMetaException extends OverrideStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7235702000499460370L;
	
	private static String getErrorMessage(final MultipartFile file) {
		return "Generate ImageMeta unsuccessfully: (" +file.getOriginalFilename() +") size:" +file.getSize();
	}

	public GenerateImageMetaException(final MultipartFile file) {
		super(getErrorMessage(file));
	}

	public GenerateImageMetaException(final MultipartFile file, Throwable t) {
		super(getErrorMessage(file), t);
	}

	@Override
	public HttpStatus getHttpStatus() {
		
		return HttpStatus.BAD_REQUEST;
	}
	
	

}
