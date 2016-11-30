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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseWordList == null) ? 0 : baseWordList.hashCode());
		result = prime * result + ((sourceLangCode == null) ? 0 : sourceLangCode.hashCode());
		result = prime * result + ((targetLangCode == null) ? 0 : targetLangCode.hashCode());
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
		RequestParseBaseWordList other = (RequestParseBaseWordList) obj;
		if (baseWordList == null) {
			if (other.baseWordList != null)
				return false;
		} else if (!baseWordList.equals(other.baseWordList))
			return false;
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
		return true;
	}

}
