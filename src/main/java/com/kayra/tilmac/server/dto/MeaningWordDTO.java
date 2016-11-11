package com.kayra.tilmac.server.dto;

import java.util.List;

public class MeaningWordDTO extends BaseWordDTO {

	private List<BaseWordDTO> targetWordList;

	public List<BaseWordDTO> getTargetWordList() {
		return targetWordList;
	}

	public void setTargetWordList(List<BaseWordDTO> targetWordList) {
		this.targetWordList = targetWordList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((targetWordList == null) ? 0 : targetWordList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeaningWordDTO other = (MeaningWordDTO) obj;
		if (targetWordList == null) {
			if (other.targetWordList != null)
				return false;
		} else if (!targetWordList.equals(other.targetWordList))
			return false;
		return true;
	}

}
