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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sourceLangCode == null) ? 0 : sourceLangCode.hashCode());
		result = prime * result + ((targetLangCode == null) ? 0 : targetLangCode.hashCode());
		result = prime * result + ((unavailableWordList == null) ? 0 : unavailableWordList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestSearchInTranslateApi other = (RequestSearchInTranslateApi) obj;
		if (sourceLangCode == null) {
			if (other.sourceLangCode != null)
				return false;
		} else if (!sourceLangCode.equals(other.sourceLangCode))
			return false;
		if (targetLangCode == null) {
			if (other.targetLangCode != null)
				return false;
		} else if (!targetLangCode.equals(other.targetLangCode))
			return false;
		if (unavailableWordList == null) {
			if (other.unavailableWordList != null)
				return false;
		} else if (!unavailableWordList.equals(other.unavailableWordList))
			return false;
		return true;
	}

}
