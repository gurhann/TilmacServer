package com.kayra.tilmac.server.dao;

import com.kayra.tilmac.server.model.MeaninglessWord;

public interface MeaninglessWordDAO extends BaseDAO<MeaninglessWord>{

	public MeaninglessWord findByName(String text, String langShortName);

}
