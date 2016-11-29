package com.kayra.tilmac.server.rest.request;

import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.LanguageDTO;

public class RequestTranlateWords {

	private List<BaseWordDTO> baseWordList;
	private LanguageDTO sourceLang;
	private LanguageDTO targetLang;

	public List<BaseWordDTO> getBaseWordList() {
		return baseWordList;
	}

	public void setBaseWordList(List<BaseWordDTO> baseWordList) {
		this.baseWordList = baseWordList;
	}

	public LanguageDTO getSourceLang() {
		return sourceLang;
	}

	public void setSourceLang(LanguageDTO sourceLang) {
		this.sourceLang = sourceLang;
	}

	public LanguageDTO getTargetLang() {
		return targetLang;
	}

	public void setTargetLang(LanguageDTO targetLang) {
		this.targetLang = targetLang;
	}

}
