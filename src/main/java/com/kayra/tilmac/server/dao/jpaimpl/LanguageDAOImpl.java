package com.kayra.tilmac.server.dao.jpaimpl;

import org.springframework.stereotype.Repository;

import com.kayra.tilmac.server.dao.LanguageDAO;
import com.kayra.tilmac.server.model.Language;

@Repository
public class LanguageDAOImpl extends BaseDAOImpl<Language> implements LanguageDAO {

	@Override
	public Language findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Language findByShortName(String shortName) {
		// TODO Auto-generated method stub
		return null;
	}

}
