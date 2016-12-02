package com.kayra.tilmac.server.dao.jpaimpl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.kayra.tilmac.server.dao.MeaninglessWordDAO;
import com.kayra.tilmac.server.model.MeaninglessWord;

@Repository
public class MeaninglessWordDAOImpl extends BaseDAOImpl<MeaninglessWord> implements MeaninglessWordDAO {

	@Override
	public MeaninglessWord findByName(String text, String langShortName) {
		Query query = em.createNamedQuery(MeaninglessWord.FIND_BY_NAME);
		query.setParameter("word", text);
		query.setParameter("langShortName", langShortName);
		return (MeaninglessWord) query.getSingleResult();
	}

}
