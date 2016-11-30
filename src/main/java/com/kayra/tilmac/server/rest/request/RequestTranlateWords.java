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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseWordList == null) ? 0 : baseWordList.hashCode());
		result = prime * result + ((sourceLang == null) ? 0 : sourceLang.hashCode());
		result = prime * result + ((targetLang == null) ? 0 : targetLang.hashCode());
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
		RequestTranlateWords other = (RequestTranlateWords) obj;
		if (baseWordList == null) {
			if (other.baseWordList != null)
				return false;
		} else if (!baseWordList.equals(other.baseWordList))
			return false;
		if (sourceLang == null) {
			if (other.sourceLang != null)
				return false;
		} else if (!sourceLang.equals(other.sourceLang))
			return false;
		if (targetLang == null) {
			if (other.targetLang != null)
				return false;
		} else if (!targetLang.equals(other.targetLang))
			return false;
		return true;
	}

	
}
