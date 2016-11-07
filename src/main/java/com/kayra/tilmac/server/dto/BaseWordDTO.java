package com.kayra.tilmac.server.dto;

public class BaseWordDTO {
	
	private Long id;
	private LanguageDTO lang;
	private String word;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LanguageDTO getLang() {
		return lang;
	}

	public void setLang(LanguageDTO lang) {
		this.lang = lang;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
