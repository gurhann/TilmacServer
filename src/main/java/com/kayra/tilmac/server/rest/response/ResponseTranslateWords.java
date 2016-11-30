package com.kayra.tilmac.server.rest.response;

import java.util.List;

import com.kayra.tilmac.server.dto.LanguageDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;

public class ResponseTranslateWords {
	private List<MeaningWordDTO> meaningWordList;
	private List<MeaninglessWordDTO> meaninglessWordList;
	private LanguageDTO sourceLang;
	private LanguageDTO targetLang;

	public List<MeaningWordDTO> getMeaningWordList() {
		return meaningWordList;
	}

	public void setMeaningWordList(List<MeaningWordDTO> meaningWordList) {
		this.meaningWordList = meaningWordList;
	}

	public List<MeaninglessWordDTO> getMeaninglessWordList() {
		return meaninglessWordList;
	}

	public void setMeaninglessWordList(List<MeaninglessWordDTO> meaninglessWordList) {
		this.meaninglessWordList = meaninglessWordList;
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
		result = prime * result + ((meaningWordList == null) ? 0 : meaningWordList.hashCode());
		result = prime * result + ((meaninglessWordList == null) ? 0 : meaninglessWordList.hashCode());
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
		ResponseTranslateWords other = (ResponseTranslateWords) obj;
		if (meaningWordList == null) {
			if (other.meaningWordList != null)
				return false;
		} else if (!meaningWordList.equals(other.meaningWordList))
			return false;
		if (meaninglessWordList == null) {
			if (other.meaninglessWordList != null)
				return false;
		} else if (!meaninglessWordList.equals(other.meaninglessWordList))
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
