package com.kayra.tilmac.server.service;

import com.kayra.tilmac.server.service.request.RequestCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.request.RequestParseBaseWordList;
import com.kayra.tilmac.server.service.request.RequestSearchInTranslateApi;
import com.kayra.tilmac.server.service.response.ResponseCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.response.ResponseParseBaseWordList;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslateApi;

public interface TranslationService {
	public ResponseParseBaseWordList parseBaseWordList(RequestParseBaseWordList req);

	public ResponseCheckUnavaibleWordsForMeaninglesInLocal checkUnavailableWordsInLocal(RequestCheckUnavaibleWordsForMeaninglesInLocal req);

	public ResponseSearchInTranslateApi searchInTranslateApi(RequestSearchInTranslateApi req);
}
