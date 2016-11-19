package com.kayra.tilmac.server.service.request;

import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;

public class RequestParseBaseWordList {

	private List<BaseWordDTO> baseWordList;
	private String sourceLangCode;
	private String targetLangCode;

	public List<BaseWordDTO> getBaseWordList() {
		return baseWordList;
	}

	public void setBaseWordList(List<BaseWordDTO> baseWordList) {
		this.baseWordList = baseWordList;
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
