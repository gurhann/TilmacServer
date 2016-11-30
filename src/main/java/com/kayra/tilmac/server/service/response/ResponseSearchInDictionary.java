package com.kayra.tilmac.server.service.response;

import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;

public class ResponseSearchInDictionary {

	private List<MeaningWordDTO> meaningWordList;
	private List<BaseWordDTO> unavailableWordList;

	public List<MeaningWordDTO> getMeaningWordList() {
		return meaningWordList;
	}

	public void setMeaningWordList(List<MeaningWordDTO> meaningWordList) {
		this.meaningWordList = meaningWordList;
	}

	public List<BaseWordDTO> getUnavailableWordList() {
		return unavailableWordList;
	}

	public void setUnavailableWordList(List<BaseWordDTO> unavailableWordList) {
		this.unavailableWordList = unavailableWordList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((meaningWordList == null) ? 0 : meaningWordList.hashCode());
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
		ResponseSearchInDictionary other = (ResponseSearchInDictionary) obj;
		if (meaningWordList == null) {
			if (other.meaningWordList != null)
				return false;
		} else if (!meaningWordList.equals(other.meaningWordList))
			return false;
		if (unavailableWordList == null) {
			if (other.unavailableWordList != null)
				return false;
		} else if (!unavailableWordList.equals(other.unavailableWordList))
			return false;
		return true;
	}

}
