package com.kayra.tilmac.server.service.impl;

import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.service.TranslationService;
import com.kayra.tilmac.server.service.response.ResponseCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.response.ResponseParseBaseWordList;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslateApi;

public class TranslationServiceYandexImpl implements TranslationService {

	@Override
	public ResponseParseBaseWordList parseBaseWordList(List<BaseWordDTO> baseWordList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseCheckUnavaibleWordsForMeaninglesInLocal checkUnavailableWordsInLocal(List<BaseWordDTO> unavailableWordList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseSearchInTranslateApi searchInTranslateApi(List<BaseWordDTO> unavailableWordList) {
		// TODO Auto-generated method stub
		return null;
	}

}
