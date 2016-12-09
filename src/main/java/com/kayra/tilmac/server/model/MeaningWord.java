package com.kayra.tilmac.server.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "MeaningWord")
@Table(name = "meaning_word", uniqueConstraints = @UniqueConstraint(columnNames = { "word", "lang_id" }))
@NamedQueries({
		@NamedQuery(name = MeaningWord.FIND_BY_NAME, query = "select w from MeaningWord w join fetch w.targetWordList t where w.word=:word and w.lang.shortName=:sourceLang and t.lang.shortName=:targetLang"),
		@NamedQuery(name = MeaningWord.FIND_BY_NAME_WITHOUT_TARGET_LANG, query = "select w from MeaningWord w where w.word=:word and w.lang.shortName=:sourceLang") })
public class MeaningWord extends BaseWord {

	public static final String FIND_BY_NAME = "MeaningWord.findByName";
	public static final String FIND_BY_NAME_WITHOUT_TARGET_LANG = "MeaningWord.findByNameWithoutTargetLang";

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "word_connection", joinColumns = @JoinColumn(referencedColumnName = "id", unique = false, name = "source_word_id"), inverseJoinColumns = @JoinColumn(referencedColumnName = "id", unique = false, name = "target_word_id"))
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
