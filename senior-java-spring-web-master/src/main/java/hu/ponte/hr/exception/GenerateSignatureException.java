package hu.ponte.hr.exception;

import org.springframework.http.HttpStatus;

public class GenerateSignatureException extends OverrideStatusCodeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4608963422640658784L;
	
	private static String getErrorMsg(String msg) {
		return "Generate Signature unsuccessfully: "+msg;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.FAILED_DEPENDENCY;
	}

	public GenerateSignatureException(String msg) {
		super(getErrorMsg(msg));
	}

	public GenerateSignatureException(String msg,Throwable t) {
		super(getErrorMsg(msg), t);
	}
}
