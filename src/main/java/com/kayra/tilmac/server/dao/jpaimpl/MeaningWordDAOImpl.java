package com.kayra.tilmac.server.dao.jpaimpl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.kayra.tilmac.server.dao.MeaningWordDAO;
import com.kayra.tilmac.server.model.MeaningWord;

@Repository
public class MeaningWordDAOImpl extends BaseDAOImpl<MeaningWord> implements MeaningWordDAO {

	@Override
	public MeaningWord findByName(String text, String sourceLang, String targetLang) {
		Query query = em.createNamedQuery(MeaningWord.FIND_BY_NAME);
		query.setParameter("word", text);
		query.setParameter("sourceLang", sourceLang);
		query.setParameter("targetLang", targetLang);
		return (MeaningWord) query.getSingleResult();
	}

}
