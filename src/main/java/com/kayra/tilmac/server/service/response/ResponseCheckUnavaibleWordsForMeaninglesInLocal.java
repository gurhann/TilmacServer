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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((meaninglessWordList == null) ? 0 : meaninglessWordList.hashCode());
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
		ResponseCheckUnavaibleWordsForMeaninglesInLocal other = (ResponseCheckUnavaibleWordsForMeaninglesInLocal) obj;
		if (meaninglessWordList == null) {
			if (other.meaninglessWordList != null)
				return false;
		} else if (!meaninglessWordList.equals(other.meaninglessWordList))
			return false;
		if (unavailableWordList == null) {
			if (other.unavailableWordList != null)
				return false;
		} else if (!unavailableWordList.equals(other.unavailableWordList))
			return false;
		return true;
	}
	
	
}
