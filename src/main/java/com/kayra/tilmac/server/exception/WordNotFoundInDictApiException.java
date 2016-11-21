package com.kayra.tilmac.server.exception;

public class WordNotFoundInDictApiException extends Exception {

	private static final long serialVersionUID = 1L;

	public WordNotFoundInDictApiException(String word) {
		super(String.format("Word:{%s} not found in dictionary api.", word));
	}
}
