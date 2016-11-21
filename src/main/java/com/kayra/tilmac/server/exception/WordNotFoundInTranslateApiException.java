package com.kayra.tilmac.server.exception;

public class WordNotFoundInTranslateApiException extends Exception {

	private static final long serialVersionUID = 1L;

	public WordNotFoundInTranslateApiException(String word) {
		super(String.format("Word: {%s} not found in translate api.", word));
	}
}
