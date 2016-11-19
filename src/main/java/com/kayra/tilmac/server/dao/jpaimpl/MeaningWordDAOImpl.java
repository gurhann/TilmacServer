package com.kayra.tilmac.server.dao.jpaimpl;

import org.springframework.stereotype.Repository;

import com.kayra.tilmac.server.dao.MeaningWordDAO;
import com.kayra.tilmac.server.model.MeaningWord;

@Repository
public class MeaningWordDAOImpl extends BaseDAOImpl<MeaningWord> implements MeaningWordDAO {

	@Override
	public MeaningWord findByName(String text, String sourceLang, String targetLang) {
		// TODO Auto-generated method stub
		return null;
	}

}
