package com.kayra.tilmac.server.dao;

import com.kayra.tilmac.server.model.Language;

public interface LanguageDAO extends BaseDAO<Language>{

	public Language findByName(String name);
	
	public Language findByShortName(String shortName);

}
