package com.kayra.tilmac.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;
import com.kayra.tilmac.server.exception.RequestWordListIsEmptyException;
import com.kayra.tilmac.server.service.impl.TranslateAPIServiceImpl;
import com.kayra.tilmac.server.service.request.RequestSearchInTranslateApi;
import com.kayra.tilmac.server.service.response.ResponseSearchInDictionary;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslate;
import com.kayra.tilmac.server.util.CreateDTOObjectUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslateAPIServiceTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	public TranslateAPIService translateAPIService = new TranslateAPIServiceImpl();

	@Test
	public void givenNullWordListWhenCallSearchInDictThenThrowException() throws RequestWordListIsEmptyException {
		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(null);
		exception.expect(RequestWordListIsEmptyException.class);
		translateAPIService.searchInDictionary(req);
	}

	@Test
	public void givenEmptyWordListWhenCallSearchInDictThenThrowException() throws RequestWordListIsEmptyException {
		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(new ArrayList<>());
		exception.expect(RequestWordListIsEmptyException.class);
		translateAPIService.searchInDictionary(req);
	}

	@Test
	public void givenNullWordListWhenCallSearchInTranslateThenThrowException() throws RequestWordListIsEmptyException {
		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(null);
		exception.expect(RequestWordListIsEmptyException.class);
		translateAPIService.searchInTranslate(req);
	}

	@Test
	public void givenEmptyWordListWhenCallSearchInTranslateThenThrowException() throws RequestWordListIsEmptyException {
		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(new ArrayList<>());
		exception.expect(RequestWordListIsEmptyException.class);
		translateAPIService.searchInTranslate(req);
	}

	@Test
	public void givenMeaninglessWordListWhenCallSearchInDictThenReturnJustUnavailableWordList() throws RequestWordListIsEmptyException {
		List<BaseWordDTO> meaninglessBaseWordDTOList = CreateDTOObjectUtil.createMeaningLessBaseWordList();

		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(meaninglessBaseWordDTOList);
		req.setSourceLangCode("en");
		req.setTargetLangCode("tr");

		ResponseSearchInDictionary searchInDictionary = translateAPIService.searchInDictionary(req);
		assertNull(searchInDictionary.getMeaningWordList());
		assertEquals(meaninglessBaseWordDTOList, searchInDictionary.getUnavailableWordList());
	}

	@Test
	public void givenMeaninglessWordListWhenCallSearchInTranslateThenReturnJustMeaninglessWordList() throws RequestWordListIsEmptyException {
		List<BaseWordDTO> meaninglessBaseWordDTOList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		List<MeaninglessWordDTO> meaninglessWordDTOList = CreateDTOObjectUtil.createMeaningLessWordList();

		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(meaninglessBaseWordDTOList);
		req.setSourceLangCode("en");
		req.setTargetLangCode("tr");

		ResponseSearchInTranslate searchInTranslate = translateAPIService.searchInTranslate(req);
		assertNull(searchInTranslate.getMeaningWordList());
		assertEquals(meaninglessWordDTOList.get(0).getWord(), searchInTranslate.getMeaninglessWordList().get(0).getWord());
		assertEquals(meaninglessWordDTOList.get(1).getWord(), searchInTranslate.getMeaninglessWordList().get(1).getWord());
	}

	@Test
	public void givenMeaningWordListWhenCallSearchInDictionaryThenReturnJustMeaningWordList() throws RequestWordListIsEmptyException {
		List<BaseWordDTO> meaningBaseWordDTOList = CreateDTOObjectUtil.createMeaningBaseWordList();

		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(meaningBaseWordDTOList);
		req.setSourceLangCode("en");
		req.setTargetLangCode("tr");

		ResponseSearchInDictionary searchInDictionary = translateAPIService.searchInDictionary(req);

		assertNull(searchInDictionary.getUnavailableWordList());
		assertEquals("gidiş", searchInDictionary.getMeaningWordList().get(0).getTargetWordList().get(0).getWord());
		assertEquals("siyah", searchInDictionary.getMeaningWordList().get(1).getTargetWordList().get(0).getWord());

	}

	@Test
	public void givenMeaningWordListWhenCallSearchInTranslateThenReturnJustMeaningWordList() throws RequestWordListIsEmptyException {
		List<BaseWordDTO> meaningBaseWordDTOList = CreateDTOObjectUtil.createMeaningBaseWordsForTranslate();

		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(meaningBaseWordDTOList);
		req.setSourceLangCode("en");
		req.setTargetLangCode("tr");

		ResponseSearchInTranslate searchInTranslate = translateAPIService.searchInTranslate(req);

		assertNull(searchInTranslate.getMeaninglessWordList());
		assertEquals("gidiyor", searchInTranslate.getMeaningWordList().get(0).getTargetWordList().get(0).getWord());
		assertEquals("istedi", searchInTranslate.getMeaningWordList().get(1).getTargetWordList().get(0).getWord());

	}

	@Test
	public void givenBothOfWordListWhenCallSearchInDictionaryThenReturnBothOfWordList() throws RequestWordListIsEmptyException {
		List<BaseWordDTO> baseWordDTOList = CreateDTOObjectUtil.createMeaningBaseWordList();
		baseWordDTOList.addAll(CreateDTOObjectUtil.createMeaningLessBaseWordList());

		List<BaseWordDTO> unavailableWordDTOList = CreateDTOObjectUtil.createMeaningLessBaseWordList();

		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(baseWordDTOList);
		req.setSourceLangCode("en");
		req.setTargetLangCode("tr");

		ResponseSearchInDictionary searchInDictionary = translateAPIService.searchInDictionary(req);
		assertEquals("gidiş", searchInDictionary.getMeaningWordList().get(0).getTargetWordList().get(0).getWord());
		assertEquals("siyah", searchInDictionary.getMeaningWordList().get(1).getTargetWordList().get(0).getWord());
		assertEquals(unavailableWordDTOList, searchInDictionary.getUnavailableWordList());

	}

	@Test
	public void givenBothOfWordListWhenCallSearchInTranslateThenReturnBothOfWordList() throws RequestWordListIsEmptyException {
		List<BaseWordDTO> baseWordDTOList = CreateDTOObjectUtil.createMeaningBaseWordsForTranslate();
		baseWordDTOList.addAll(CreateDTOObjectUtil.createMeaningLessBaseWordList());

		List<MeaninglessWordDTO> meaninglessWordDTOList = CreateDTOObjectUtil.createMeaningLessWordList();

		RequestSearchInTranslateApi req = new RequestSearchInTranslateApi();
		req.setUnavailableWordList(baseWordDTOList);
		req.setSourceLangCode("en");
		req.setTargetLangCode("tr");

		ResponseSearchInTranslate searchInTranslate = translateAPIService.searchInTranslate(req);

		assertEquals("gidiyor", searchInTranslate.getMeaningWordList().get(0).getTargetWordList().get(0).getWord());
		assertEquals("istedi", searchInTranslate.getMeaningWordList().get(1).getTargetWordList().get(0).getWord());
		assertEquals(meaninglessWordDTOList.get(0).getWord(), searchInTranslate.getMeaninglessWordList().get(0).getWord());
		assertEquals(meaninglessWordDTOList.get(1).getWord(), searchInTranslate.getMeaninglessWordList().get(1).getWord());

	}
}
