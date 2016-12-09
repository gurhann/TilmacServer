package com.kayra.tilmac.server.dao.jpaimpl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.kayra.tilmac.server.dao.LanguageDAO;
import com.kayra.tilmac.server.model.Language;

@Repository
public class LanguageDAOImpl extends BaseDAOImpl<Language> implements LanguageDAO {

	@Override
	public Language findByShortName(String shortName) {
		Query query = em.createNamedQuery(Language.FIND_BY_SHORT_NAME).setParameter("shortName", shortName);
		return (Language) query.getSingleResult();
	}

}
