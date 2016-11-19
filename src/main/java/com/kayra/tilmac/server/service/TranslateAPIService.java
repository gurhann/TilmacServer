package com.kayra.tilmac.server.service;

import com.kayra.tilmac.server.service.request.RequestSearchInTranslateApi;
import com.kayra.tilmac.server.service.response.ResponseSearchInDictionary;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslate;

public interface TranslateAPIService {

	public ResponseSearchInDictionary searchInDictionary(RequestSearchInTranslateApi req);

	public ResponseSearchInTranslate searchInTranslate(RequestSearchInTranslateApi req);
}
