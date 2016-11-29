package com.kayra.tilmac.server.exception;

public class RequestWordListIsEmptyException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequestWordListIsEmptyException() {
		super("Request word list cannot be null or empty.");
	}
}
