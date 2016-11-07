package com.kayra.tilmac.server.service;

import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.service.response.ResponseCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.response.ResponseParseBaseWordList;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslateApi;

public interface TranslationService {
	public ResponseParseBaseWordList parseBaseWordList(List<BaseWordDTO> baseWordList);

	public ResponseCheckUnavaibleWordsForMeaninglesInLocal checkUnavailableWordsInLocal(List<BaseWordDTO> unavailableWordList);

	public ResponseSearchInTranslateApi searchInTranslateApi(List<BaseWordDTO> unavailableWordList);
}
