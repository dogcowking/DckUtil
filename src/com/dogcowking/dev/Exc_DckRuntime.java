package com.dogcowking.dev;


/**
 * Dck 의 unchecked Exception
 * @author jshasus
 *
 */
public class Exc_DckRuntime extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -291698536760496189L;

	public Exc_DckRuntime(String message, Throwable cause) {
		super(message, cause);
	}

	public Exc_DckRuntime(String message) {
		super(message);
	}

}
