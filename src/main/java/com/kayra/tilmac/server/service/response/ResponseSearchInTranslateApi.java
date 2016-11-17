package com.kayra.tilmac.server.service.response;

import java.util.List;

import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;

public class ResponseSearchInTranslateApi {
	private List<MeaningWordDTO> meaningWordList;
	private List<MeaninglessWordDTO> meaninglessWordList;

	public List<MeaningWordDTO> getMeaningWordList() {
		return meaningWordList;
	}

	public void setMeaningWordList(List<MeaningWordDTO> meaningWordList) {
		this.meaningWordList = meaningWordList;
	}

	public List<MeaninglessWordDTO> getMeaninglessWordList() {
		return meaninglessWordList;
	}

	public void setMeaninglessWordList(List<MeaninglessWordDTO> meaningListWordList) {
		this.meaninglessWordList = meaningListWordList;
	}
}
