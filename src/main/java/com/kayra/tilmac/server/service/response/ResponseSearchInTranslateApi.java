package com.kayra.tilmac.server.service.response;

import java.util.List;

import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;

public class ResponseSearchInTranslateApi {
	private List<MeaningWordDTO> meaningWordList;
	private List<MeaninglessWordDTO> meaningListWordList;

	public List<MeaningWordDTO> getMeaningWordList() {
		return meaningWordList;
	}

	public void setMeaningWordList(List<MeaningWordDTO> meaningWordList) {
		this.meaningWordList = meaningWordList;
	}

	public List<MeaninglessWordDTO> getMeaningListWordList() {
		return meaningListWordList;
	}

	public void setMeaningListWordList(List<MeaninglessWordDTO> meaningListWordList) {
		this.meaningListWordList = meaningListWordList;
	}
}
