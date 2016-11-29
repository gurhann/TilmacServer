package com.kayra.tilmac.server.service;

import com.kayra.tilmac.server.exception.RequestWordListIsEmptyException;
import com.kayra.tilmac.server.service.request.RequestSearchInTranslateApi;
import com.kayra.tilmac.server.service.response.ResponseSearchInDictionary;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslate;

public interface TranslateAPIService {

	public ResponseSearchInDictionary searchInDictionary(RequestSearchInTranslateApi req) throws RequestWordListIsEmptyException;

	public ResponseSearchInTranslate searchInTranslate(RequestSearchInTranslateApi req) throws RequestWordListIsEmptyException;
}
