package com.kayra.tilmac.server.model;

import java.util.List;

public class MeaningWord extends BaseWord {

	private List<BaseWord> targetWordList;

	public List<BaseWord> getTargetWordList() {
		return targetWordList;
	}

	public void setTargetWordList(List<BaseWord> targetWordList) {
		this.targetWordList = targetWordList;
	}
}
