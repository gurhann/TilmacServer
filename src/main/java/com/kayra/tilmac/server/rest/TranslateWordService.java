package com.kayra.tilmac.server.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.kayra.tilmac.server.exception.RequestWordListIsEmptyException;
import com.kayra.tilmac.server.exception.SourceLangAndTargetLangAreSameException;
import com.kayra.tilmac.server.rest.request.RequestTranlateWords;
import com.kayra.tilmac.server.rest.response.ResponseTranslateWords;
import com.kayra.tilmac.server.service.TranslationService;
import com.kayra.tilmac.server.service.request.RequestCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.request.RequestParseBaseWordList;
import com.kayra.tilmac.server.service.request.RequestSearchInTranslateApi;
import com.kayra.tilmac.server.service.response.ResponseCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.response.ResponseParseBaseWordList;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslateApi;

public class TranslateWordService {

	@Autowired
	private TranslationService translationService;

	public ResponseTranslateWords translateWords(RequestTranlateWords req) throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {

		checkInput(req);

		ResponseTranslateWords resp = new ResponseTranslateWords();

		RequestParseBaseWordList requestParseWords = new RequestParseBaseWordList();
		requestParseWords.setBaseWordList(req.getBaseWordList());
		requestParseWords.setSourceLangCode(req.getSourceLang().getShortName());
		requestParseWords.setTargetLangCode(req.getTargetLang().getShortName());

		ResponseParseBaseWordList respParseBaseWordList = translationService.parseBaseWordList(requestParseWords);

		if (respParseBaseWordList.getMeaningWordList() != null) {
			resp.setMeaningWordList(respParseBaseWordList.getMeaningWordList());
		}

		if (respParseBaseWordList.getUnavailableWordList() != null && !respParseBaseWordList.getUnavailableWordList().isEmpty()) {

			RequestCheckUnavaibleWordsForMeaninglesInLocal requestCheckUnavailableWords = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
			requestCheckUnavailableWords.setUnavailableWordList(respParseBaseWordList.getUnavailableWordList());
			requestCheckUnavailableWords.setSourceLangCode(req.getSourceLang().getShortName());

			ResponseCheckUnavaibleWordsForMeaninglesInLocal respCheckUnaavailableWords = translationService.checkUnavailableWordsInLocal(requestCheckUnavailableWords);

			if (respCheckUnaavailableWords.getMeaninglessWordList() != null && !respCheckUnaavailableWords.getMeaninglessWordList().isEmpty()) {
				resp.setMeaninglessWordList(respCheckUnaavailableWords.getMeaninglessWordList());
			}

			if (respCheckUnaavailableWords.getUnavailableWordList() != null && !respCheckUnaavailableWords.getUnavailableWordList().isEmpty()) {

				RequestSearchInTranslateApi requestSearchInTranslateApi = new RequestSearchInTranslateApi();
				requestSearchInTranslateApi.setUnavailableWordList(respCheckUnaavailableWords.getUnavailableWordList());
				requestSearchInTranslateApi.setSourceLangCode(req.getSourceLang().getShortName());
				requestSearchInTranslateApi.setTargetLangCode(req.getTargetLang().getShortName());

				ResponseSearchInTranslateApi respSearchInTranslateApi = translationService.searchInTranslateApi(requestSearchInTranslateApi);

				if (respSearchInTranslateApi.getMeaningWordList() != null && !respSearchInTranslateApi.getMeaningWordList().isEmpty()) {
					if (resp.getMeaningWordList() == null) {
						resp.setMeaningWordList(respSearchInTranslateApi.getMeaningWordList());
					} else {
						resp.getMeaningWordList().addAll(respSearchInTranslateApi.getMeaningWordList());
					}
				}

				if (respSearchInTranslateApi.getMeaninglessWordList() != null && !respSearchInTranslateApi.getMeaninglessWordList().isEmpty()) {

					if (resp.getMeaninglessWordList() == null) {
						resp.setMeaninglessWordList(respSearchInTranslateApi.getMeaninglessWordList());
					} else {
						resp.getMeaninglessWordList().addAll(respSearchInTranslateApi.getMeaninglessWordList());
					}
				}
			}
		}
		resp.setSourceLang(req.getSourceLang());
		resp.setTargetLang(req.getTargetLang());
		return resp;
	}

	private void checkInput(RequestTranlateWords req) throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {
		if (req.getBaseWordList() == null || req.getBaseWordList().isEmpty()) {
			throw new RequestWordListIsEmptyException();
		}

		if (req.getSourceLang() == null || req.getTargetLang() == null) {
			throw new RequestWordListIsEmptyException();
		}

		if (req.getSourceLang().equals(req.getTargetLang() == null)) {
			throw new SourceLangAndTargetLangAreSameException();
		}
	}
}