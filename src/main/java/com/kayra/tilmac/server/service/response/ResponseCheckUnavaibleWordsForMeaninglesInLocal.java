package com.kayra.tilmac.server.service.response;

import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;

public class ResponseCheckUnavaibleWordsForMeaninglesInLocal {
	private List<MeaninglessWordDTO> meaninglessWordList;
	private List<BaseWordDTO> unavailableWordList;

	public List<MeaninglessWordDTO> getMeaninglessWordList() {
		return meaninglessWordList;
	}

	public void setMeaninglessWordList(List<MeaninglessWordDTO> meaninglessWordList) {
		this.meaninglessWordList = meaninglessWordList;
	}

	public List<BaseWordDTO> getUnavailableWordList() {
		return unavailableWordList;
	}

	public void setUnavailableWordList(List<BaseWordDTO> unavailableWordList) {
		this.unavailableWordList = unavailableWordList;
	}
}
