package com.kayra.tilmac.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.service.TranslateAPIService;
import com.kayra.tilmac.server.service.response.ResponseSearchInDictionary;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslate;

@Service
public class TranslateAPIServiceImpl implements TranslateAPIService {

	@Override
	public ResponseSearchInDictionary searchInDictionary(List<BaseWordDTO> unavailableWordList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseSearchInTranslate searchInTranslate(List<BaseWordDTO> unavailableWordList) {
		// TODO Auto-generated method stub
		return null;
	}

}
