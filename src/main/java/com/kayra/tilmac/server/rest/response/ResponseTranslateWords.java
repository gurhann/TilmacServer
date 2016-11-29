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

}
