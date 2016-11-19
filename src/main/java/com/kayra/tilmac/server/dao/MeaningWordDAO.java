package com.kayra.tilmac.server.dao;

import com.kayra.tilmac.server.model.MeaningWord;

public interface MeaningWordDAO extends BaseDAO<MeaningWord> {

	public MeaningWord findByName(String text, String sourceLang, String targetLang);

}
