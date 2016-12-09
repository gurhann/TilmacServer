package com.kayra.tilmac.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kayra.tilmac.server.dao.LanguageDAO;
import com.kayra.tilmac.server.dao.MeaningWordDAO;
import com.kayra.tilmac.server.dao.MeaninglessWordDAO;
import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;
import com.kayra.tilmac.server.exception.RequestWordListIsEmptyException;
import com.kayra.tilmac.server.model.MeaningWord;
import com.kayra.tilmac.server.service.impl.TranslationServiceImpl;
import com.kayra.tilmac.server.service.request.RequestSearchInTranslateApi;
import com.kayra.tilmac.server.service.response.ResponseSearchInDictionary;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslate;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslateApi;
import com.kayra.tilmac.server.util.CreateDTOObjectUtil;
import com.kayra.tilmac.server.util.CreateModelObjectUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslationServiceSearchInTranslateAPITest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@InjectMocks
	public TranslationService translationService = new TranslationServiceImpl();

	@Mock
	public TranslateAPIService translateAPIService;

	@Mock
	public MeaninglessWordDAO meaninglessDAO;

	@Mock
	public MeaningWordDAO meaningWordDAO;

	@Mock
	public LanguageDAO langDAO;

	@Test
	public void givenWordListNullThenThrowException() throws RequestWordListIsEmptyException {
		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(null);
		exception.expect(RequestWordListIsEmptyException.class);
		translationService.searchInTranslateApi(req);
	}

	@Test
	public void givenWordListEmptyThenThrowException() throws RequestWordListIsEmptyException {
		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(new ArrayList<>());
		exception.expect(RequestWordListIsEmptyException.class);
		translationService.searchInTranslateApi(req);
	}

	@Test
	public void givenWordsAreMeaninglessThenReturnJustMeaninglessList() throws RequestWordListIsEmptyException {

		List<BaseWordDTO> meaninglessBaseWordDTOList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		List<MeaninglessWordDTO> meaninglessWordDTOList = CreateDTOObjectUtil.createMeaningLessWordList();

		ResponseSearchInDictionary respSearchInDirectory = new ResponseSearchInDictionary();
		respSearchInDirectory.setMeaningWordList(null);
		respSearchInDirectory.setUnavailableWordList(meaninglessBaseWordDTOList);

		ResponseSearchInTranslate respSearchInTranslate = new ResponseSearchInTranslate();
		respSearchInTranslate.setMeaningWordList(null);
		respSearchInTranslate.setMeaninglessWordList(meaninglessWordDTOList);

		RequestSearchInTranslateApi reqSearchInTranslateApi = new RequestSearchInTranslateApi();
		reqSearchInTranslateApi.setUnavailableWordList(meaninglessBaseWordDTOList);
		reqSearchInTranslateApi.setSourceLangCode("en");
		reqSearchInTranslateApi.setTargetLangCode("tr");
		when(translateAPIService.searchInDictionary(reqSearchInTranslateApi)).thenReturn(respSearchInDirectory);
		when(translateAPIService.searchInTranslate(reqSearchInTranslateApi)).thenReturn(respSearchInTranslate);

		ResponseSearchInTranslateApi searchInTranslateApi = translationService.searchInTranslateApi(reqSearchInTranslateApi);
		// verify(translateAPIService.searchInDictionary(meaninglessBaseWordDTOList));
		// verify(translateAPIService.searchInTranslate(meaninglessBaseWordDTOList));

		assertEquals(meaninglessWordDTOList.get(0), searchInTranslateApi.getMeaninglessWordList().get(0));
		assertEquals(meaninglessWordDTOList.get(1), searchInTranslateApi.getMeaninglessWordList().get(1));
		assertNull(searchInTranslateApi.getMeaningWordList());
	}

	@Test
	public void givenWordsAreMeaningThenReturnJustmeaningList() throws RequestWordListIsEmptyException {
		List<BaseWordDTO> meaningBaseWordDTOList = CreateDTOObjectUtil.createMeaningBaseWordList();
		List<MeaningWordDTO> meaningWordDTOList = CreateDTOObjectUtil.createMeaningWordList();

		ResponseSearchInDictionary respSearchInDirectory = new ResponseSearchInDictionary();
		respSearchInDirectory.setMeaningWordList(meaningWordDTOList);
		respSearchInDirectory.setUnavailableWordList(null);

		ResponseSearchInTranslate respSearchInTranslate = new ResponseSearchInTranslate();
		respSearchInTranslate.setMeaningWordList(meaningWordDTOList);
		respSearchInTranslate.setMeaninglessWordList(null);

		RequestSearchInTranslateApi reqSearchInTranslateApi = new RequestSearchInTranslateApi();
		reqSearchInTranslateApi.setUnavailableWordList(meaningBaseWordDTOList);
		reqSearchInTranslateApi.setSourceLangCode("en");
		reqSearchInTranslateApi.setTargetLangCode("tr");
		when(translateAPIService.searchInDictionary(reqSearchInTranslateApi)).thenReturn(respSearchInDirectory);
		when(translateAPIService.searchInTranslate(reqSearchInTranslateApi)).thenReturn(respSearchInTranslate);

		when(langDAO.findByShortName("en")).thenReturn(CreateModelObjectUtil.createEngLang());
		when(langDAO.findByShortName("tr")).thenReturn(CreateModelObjectUtil.createTrLang());
		List<MeaningWord> createTargetWordsForGo = CreateModelObjectUtil.createTargetWordsForGo();
		List<MeaningWord> createTargetWordsForBlack = CreateModelObjectUtil.createTargetWordsForBlack();
		when(meaningWordDAO.findByNameWithoutTargetLang("gitmek", "tr")).thenReturn(createTargetWordsForGo.get(0));
		when(meaningWordDAO.findByNameWithoutTargetLang("geçmek", "tr")).thenReturn(createTargetWordsForGo.get(1));
		when(meaningWordDAO.findByNameWithoutTargetLang("siyah", "tr")).thenReturn(createTargetWordsForBlack.get(0));
		when(meaningWordDAO.findByNameWithoutTargetLang("kara", "tr")).thenReturn(createTargetWordsForBlack.get(1));
		ResponseSearchInTranslateApi searchInTranslateApi = translationService.searchInTranslateApi(reqSearchInTranslateApi);
		// verify(translateAPIService.searchInDictionary(meaningBaseWordDTOList));
		// verify(translateAPIService.searchInTranslate(meaningBaseWordDTOList));

		assertEquals(meaningWordDTOList.get(0), searchInTranslateApi.getMeaningWordList().get(0));
		assertEquals(meaningWordDTOList.get(1), searchInTranslateApi.getMeaningWordList().get(1));
		assertNull(searchInTranslateApi.getMeaninglessWordList());
	}

}
