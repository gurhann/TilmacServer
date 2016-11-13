package com.kayra.tilmac.server.service;

import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.service.response.ResponseSearchInDictionary;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslate;

public interface TranslateAPIService {

	public ResponseSearchInDictionary searchInDictionary(List<BaseWordDTO> unavailableWordList);

	public ResponseSearchInTranslate searchInTranslate(List<BaseWordDTO> unavailableWordList);
}
