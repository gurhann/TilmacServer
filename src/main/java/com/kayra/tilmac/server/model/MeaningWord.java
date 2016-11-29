package com.kayra.tilmac.server.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "MeaningWord")
@Table(name = "meaning_word")
public class MeaningWord extends BaseWord {

	@OneToMany
	private List<MeaningWord> targetWordList;

	public List<MeaningWord> getTargetWordList() {
		return targetWordList;
	}

	public void setTargetWordList(List<MeaningWord> targetWordList) {
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
		MeaningWord other = (MeaningWord) obj;
		if (targetWordList == null) {
			if (other.targetWordList != null)
				return false;
		} else if (!targetWordList.equals(other.targetWordList))
			return false;
		return true;
	}

}
