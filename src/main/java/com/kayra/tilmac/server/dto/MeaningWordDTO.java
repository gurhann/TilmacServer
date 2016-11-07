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
}
