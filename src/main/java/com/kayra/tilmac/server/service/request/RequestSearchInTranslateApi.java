package com.kayra.tilmac.server.service.request;

import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;

public class RequestSearchInTranslateApi {

	private List<BaseWordDTO> unavailableWordList;
	private String sourceLangCode;
	private String targetLangCode;

	public List<BaseWordDTO> getUnavailableWordList() {
		return unavailableWordList;
	}

	public void setUnavailableWordList(List<BaseWordDTO> unavailableWordList) {
		this.unavailableWordList = unavailableWordList;
	}

	public String getSourceLangCode() {
		return sourceLangCode;
	}

	public void setSourceLangCode(String sourceLangCode) {
		this.sourceLangCode = sourceLangCode;
	}

	public String getTargetLangCode() {
		return targetLangCode;
	}

	public void setTargetLangCode(String targetLangCode) {
		this.targetLangCode = targetLangCode;
	}

}
