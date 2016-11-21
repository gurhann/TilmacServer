package com.kayra.tilmac.server.exception;

public class YandexApiConnectException extends Exception {

	private static final long serialVersionUID = 1L;

	public YandexApiConnectException(String message) {
		super("Yandex connection end with exception: " + message);
	}
}
