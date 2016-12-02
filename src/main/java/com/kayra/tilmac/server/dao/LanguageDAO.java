package com.kayra.tilmac.server.dao;

import com.kayra.tilmac.server.model.Language;

public interface LanguageDAO extends BaseDAO<Language> {

	public Language findByShortName(String shortName);

}
