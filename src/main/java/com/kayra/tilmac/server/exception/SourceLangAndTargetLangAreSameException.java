package com.kayra.tilmac.server.exception;

public class SourceLangAndTargetLangAreSameException extends Exception {

	private static final long serialVersionUID = 1L;

	public SourceLangAndTargetLangAreSameException() {
		super("Source language and target language are cannot be the same.");
	}

}
