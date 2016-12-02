package com.kayra.tilmac.server.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "MeaningWord")
@Table(name = "meaning_word")
@NamedQueries({ @NamedQuery(name = MeaningWord.FIND_BY_NAME, query = "select w from MeaningWord w join fetch w.targetWordList t where w.word=:word and w.lang.shortName=:sourceLang and t.lang.shortName=:targetLang") })
public class MeaningWord extends BaseWord {

	public static final String FIND_BY_NAME = "MeaningWord.findByName";

	@OneToMany(fetch = FetchType.LAZY)
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
