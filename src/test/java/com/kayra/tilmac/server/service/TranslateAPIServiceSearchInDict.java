package com.kayra.tilmac.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kayra.tilmac.server.service.impl.TranslateAPIServiceImpl;
import com.kayra.tilmac.server.service.request.RequestSearchInTranslateApi;
import com.kayra.tilmac.server.util.CreateDTOObjectUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslateAPIServiceSearchInDict {

	public TranslateAPIService translateAPIService = new TranslateAPIServiceImpl();

	@Test
	public void ss() {
		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(CreateDTOObjectUtil.createMeaningLessBaseWordList());
		req.setSourceLangCode("en");
		req.setTargetLangCode("tr");
		translateAPIService.searchInDictionary(req);
	}
}
