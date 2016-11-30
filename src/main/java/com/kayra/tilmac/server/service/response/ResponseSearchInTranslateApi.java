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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((meaningWordList == null) ? 0 : meaningWordList.hashCode());
		result = prime * result + ((meaninglessWordList == null) ? 0 : meaninglessWordList.hashCode());
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
		ResponseSearchInTranslateApi other = (ResponseSearchInTranslateApi) obj;
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
		return true;
	}

}
