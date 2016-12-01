package com.kayra.tilmac.server.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.LanguageDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;
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
import com.kayra.tilmac.server.util.CreateDTOObjectUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslateWordServiceTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@InjectMocks
	public TranslateWordService service = new TranslateWordService();

	@Mock
	public TranslationService translationService;

	@Test
	public void givenWordListNullThenThrowException() throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {
		RequestTranlateWords req = new RequestTranlateWords();

		exception.expect(RequestWordListIsEmptyException.class);
		service.translateWords(req);
	}

	@Test
	public void givenWordListEmptyThenThrowException() throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {
		RequestTranlateWords req = new RequestTranlateWords();
		req.setBaseWordList(new ArrayList<>());

		exception.expect(RequestWordListIsEmptyException.class);
		service.translateWords(req);
	}

	@Test
	public void givenSourceLangAndTargetLangSameThenThrowException() throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {
		RequestTranlateWords req = new RequestTranlateWords();
		List<BaseWordDTO> baseWordList = CreateDTOObjectUtil.createMeaningBaseWordList();
		req.setSourceLang(CreateDTOObjectUtil.createEngLangDTO());
		req.setTargetLang(CreateDTOObjectUtil.createEngLangDTO());
		req.setBaseWordList(baseWordList);

		exception.expect(SourceLangAndTargetLangAreSameException.class);
		service.translateWords(req);

	}

	@Test
	public void givenWordListMeaninglessInDBWordThenReturnMeaninglessList() throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {

		LanguageDTO engLang = CreateDTOObjectUtil.createEngLangDTO();
		LanguageDTO trLang = CreateDTOObjectUtil.createTrLangDTO();

		List<BaseWordDTO> meaninglessBaseWordList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		List<MeaninglessWordDTO> meaninglessWordList = CreateDTOObjectUtil.createMeaningLessWordList();

		RequestParseBaseWordList reqParseWords = new RequestParseBaseWordList();
		reqParseWords.setBaseWordList(meaninglessBaseWordList);
		reqParseWords.setSourceLangCode(engLang.getShortName());
		reqParseWords.setTargetLangCode(trLang.getShortName());

		ResponseParseBaseWordList resp = new ResponseParseBaseWordList();
		resp.setUnavailableWordList(meaninglessBaseWordList);
		when(translationService.parseBaseWordList(reqParseWords)).thenReturn(resp);

		RequestCheckUnavaibleWordsForMeaninglesInLocal reqCheckUnavailableWords = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		reqCheckUnavailableWords.setSourceLangCode(engLang.getShortName());
		reqCheckUnavailableWords.setUnavailableWordList(meaninglessBaseWordList);

		ResponseCheckUnavaibleWordsForMeaninglesInLocal respCheckUnavailableWords = new ResponseCheckUnavaibleWordsForMeaninglesInLocal();
		respCheckUnavailableWords.setMeaninglessWordList(meaninglessWordList);
		when(translationService.checkUnavailableWordsInLocal(reqCheckUnavailableWords)).thenReturn(respCheckUnavailableWords);

		RequestSearchInTranslateApi reqSearchInTranslate = new RequestSearchInTranslateApi();
		ResponseSearchInTranslateApi respSearchInTranslate = new ResponseSearchInTranslateApi();
		when(translationService.searchInTranslateApi(reqSearchInTranslate)).thenReturn(respSearchInTranslate);

		RequestTranlateWords req = new RequestTranlateWords();
		req.setBaseWordList(meaninglessBaseWordList);
		req.setSourceLang(engLang);
		req.setTargetLang(trLang);

		ResponseTranslateWords translateWords = service.translateWords(req);
		assertNull(translateWords.getMeaningWordList());
		assertEquals(meaninglessWordList, translateWords.getMeaninglessWordList());
		verify(translationService).parseBaseWordList(reqParseWords);
		verify(translationService).checkUnavailableWordsInLocal(reqCheckUnavailableWords);
		verify(translationService, never()).searchInTranslateApi(reqSearchInTranslate);
	}

	@Test
	public void givenWordListMeaningWordInDBThenReturnMeaningList() throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {

		LanguageDTO engLang = CreateDTOObjectUtil.createEngLangDTO();
		LanguageDTO trLang = CreateDTOObjectUtil.createTrLangDTO();

		List<BaseWordDTO> meaningBaseWordList = CreateDTOObjectUtil.createMeaningBaseWordList();
		List<MeaningWordDTO> meaningWordList = CreateDTOObjectUtil.createMeaningWordList();

		RequestParseBaseWordList reqParseWords = new RequestParseBaseWordList();
		reqParseWords.setBaseWordList(meaningBaseWordList);
		reqParseWords.setSourceLangCode(engLang.getShortName());
		reqParseWords.setTargetLangCode(trLang.getShortName());

		ResponseParseBaseWordList resp = new ResponseParseBaseWordList();
		resp.setMeaningWordList(meaningWordList);
		when(translationService.parseBaseWordList(reqParseWords)).thenReturn(resp);

		RequestCheckUnavaibleWordsForMeaninglesInLocal reqCheckUnavailableWords = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		ResponseCheckUnavaibleWordsForMeaninglesInLocal respCheckUnavailableWords = new ResponseCheckUnavaibleWordsForMeaninglesInLocal();
		when(translationService.checkUnavailableWordsInLocal(reqCheckUnavailableWords)).thenReturn(respCheckUnavailableWords);

		RequestSearchInTranslateApi reqSearchInTranslate = new RequestSearchInTranslateApi();
		ResponseSearchInTranslateApi respSearchInTranslate = new ResponseSearchInTranslateApi();
		when(translationService.searchInTranslateApi(reqSearchInTranslate)).thenReturn(respSearchInTranslate);

		RequestTranlateWords req = new RequestTranlateWords();
		req.setBaseWordList(meaningBaseWordList);
		req.setSourceLang(engLang);
		req.setTargetLang(trLang);

		ResponseTranslateWords translateWords = service.translateWords(req);
		assertNull(translateWords.getMeaninglessWordList());
		assertEquals(meaningWordList, translateWords.getMeaningWordList());
		verify(translationService).parseBaseWordList(reqParseWords);
		verify(translationService, never()).checkUnavailableWordsInLocal(reqCheckUnavailableWords);
		verify(translationService, never()).searchInTranslateApi(reqSearchInTranslate);
	}

	@Test
	public void givenWordListMeaninglessWordNotInDBThenReturnMeaninglessList() throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {

		LanguageDTO engLang = CreateDTOObjectUtil.createEngLangDTO();
		LanguageDTO trLang = CreateDTOObjectUtil.createTrLangDTO();

		List<BaseWordDTO> meaninglessBaseWordList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		List<MeaninglessWordDTO> meaninglessWordList = CreateDTOObjectUtil.createMeaningLessWordList();

		RequestParseBaseWordList reqParseWords = new RequestParseBaseWordList();
		reqParseWords.setBaseWordList(meaninglessBaseWordList);
		reqParseWords.setSourceLangCode(engLang.getShortName());
		reqParseWords.setTargetLangCode(trLang.getShortName());

		ResponseParseBaseWordList resp = new ResponseParseBaseWordList();
		resp.setUnavailableWordList(meaninglessBaseWordList);
		when(translationService.parseBaseWordList(reqParseWords)).thenReturn(resp);

		RequestCheckUnavaibleWordsForMeaninglesInLocal reqCheckUnavailableWords = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		reqCheckUnavailableWords.setSourceLangCode(engLang.getShortName());
		reqCheckUnavailableWords.setUnavailableWordList(meaninglessBaseWordList);

		ResponseCheckUnavaibleWordsForMeaninglesInLocal respCheckUnavailableWords = new ResponseCheckUnavaibleWordsForMeaninglesInLocal();
		respCheckUnavailableWords.setUnavailableWordList(meaninglessBaseWordList);
		when(translationService.checkUnavailableWordsInLocal(reqCheckUnavailableWords)).thenReturn(respCheckUnavailableWords);

		RequestSearchInTranslateApi reqSearchInTranslate = new RequestSearchInTranslateApi();
		reqSearchInTranslate.setUnavailableWordList(respCheckUnavailableWords.getUnavailableWordList());
		reqSearchInTranslate.setSourceLangCode(engLang.getShortName());
		reqSearchInTranslate.setTargetLangCode(trLang.getShortName());

		ResponseSearchInTranslateApi respSearchInTranslate = new ResponseSearchInTranslateApi();
		respSearchInTranslate.setMeaninglessWordList(meaninglessWordList);
		when(translationService.searchInTranslateApi(reqSearchInTranslate)).thenReturn(respSearchInTranslate);

		RequestTranlateWords req = new RequestTranlateWords();
		req.setBaseWordList(meaninglessBaseWordList);
		req.setSourceLang(engLang);
		req.setTargetLang(trLang);

		ResponseTranslateWords translateWords = service.translateWords(req);
		assertNull(translateWords.getMeaningWordList());
		assertEquals(meaninglessWordList, translateWords.getMeaninglessWordList());
		verify(translationService).parseBaseWordList(reqParseWords);
		verify(translationService).checkUnavailableWordsInLocal(reqCheckUnavailableWords);
		verify(translationService).searchInTranslateApi(reqSearchInTranslate);

	}

	@Test
	public void givenWordListMeaningWordNotInDBThenReturnMeaningList() throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {

		LanguageDTO engLang = CreateDTOObjectUtil.createEngLangDTO();
		LanguageDTO trLang = CreateDTOObjectUtil.createTrLangDTO();

		List<BaseWordDTO> meaningBaseWordList = CreateDTOObjectUtil.createMeaningBaseWordList();
		List<MeaningWordDTO> meaningWordList = CreateDTOObjectUtil.createMeaningWordList();

		RequestParseBaseWordList reqParseWords = new RequestParseBaseWordList();
		reqParseWords.setBaseWordList(meaningBaseWordList);
		reqParseWords.setSourceLangCode(engLang.getShortName());
		reqParseWords.setTargetLangCode(trLang.getShortName());

		ResponseParseBaseWordList resp = new ResponseParseBaseWordList();
		resp.setUnavailableWordList(meaningBaseWordList);
		when(translationService.parseBaseWordList(reqParseWords)).thenReturn(resp);

		RequestCheckUnavaibleWordsForMeaninglesInLocal reqCheckUnavailableWords = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		reqCheckUnavailableWords.setSourceLangCode(engLang.getShortName());
		reqCheckUnavailableWords.setUnavailableWordList(resp.getUnavailableWordList());

		ResponseCheckUnavaibleWordsForMeaninglesInLocal respCheckUnavailableWords = new ResponseCheckUnavaibleWordsForMeaninglesInLocal();
		respCheckUnavailableWords.setUnavailableWordList(meaningBaseWordList);
		when(translationService.checkUnavailableWordsInLocal(reqCheckUnavailableWords)).thenReturn(respCheckUnavailableWords);

		RequestSearchInTranslateApi reqSearchInTranslate = new RequestSearchInTranslateApi();
		reqSearchInTranslate.setSourceLangCode(engLang.getShortName());
		reqSearchInTranslate.setTargetLangCode(trLang.getShortName());
		reqSearchInTranslate.setUnavailableWordList(respCheckUnavailableWords.getUnavailableWordList());

		ResponseSearchInTranslateApi respSearchInTranslate = new ResponseSearchInTranslateApi();
		respSearchInTranslate.setMeaningWordList(meaningWordList);
		when(translationService.searchInTranslateApi(reqSearchInTranslate)).thenReturn(respSearchInTranslate);

		RequestTranlateWords req = new RequestTranlateWords();
		req.setBaseWordList(meaningBaseWordList);
		req.setSourceLang(engLang);
		req.setTargetLang(trLang);

		ResponseTranslateWords translateWords = service.translateWords(req);
		assertNull(translateWords.getMeaninglessWordList());
		assertEquals(meaningWordList, translateWords.getMeaningWordList());
		verify(translationService).parseBaseWordList(reqParseWords);
		verify(translationService).checkUnavailableWordsInLocal(reqCheckUnavailableWords);
		verify(translationService).searchInTranslateApi(reqSearchInTranslate);
	}

	@Test
	public void givenWordMeaninglessListBothTypeWordThenReturMeaninglessWordList() throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {

		LanguageDTO engLang = CreateDTOObjectUtil.createEngLangDTO();
		LanguageDTO trLang = CreateDTOObjectUtil.createTrLangDTO();

		List<BaseWordDTO> meaninglessBaseWordList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		List<BaseWordDTO> meaninglessBaseWordListNotInDB = CreateDTOObjectUtil.createMeaningLessBaseWordListNotInDB();

		List<MeaninglessWordDTO> meaninglessWordList = CreateDTOObjectUtil.createMeaningLessWordList();
		List<MeaninglessWordDTO> meaninglessWordListNotInDB = CreateDTOObjectUtil.createMeaningLessWordListNotInDB();

		meaninglessBaseWordList.addAll(meaninglessBaseWordListNotInDB);
		meaninglessWordList.addAll(meaninglessWordListNotInDB);

		RequestParseBaseWordList reqParseWords = new RequestParseBaseWordList();
		reqParseWords.setBaseWordList(meaninglessBaseWordList);
		reqParseWords.setSourceLangCode(engLang.getShortName());
		reqParseWords.setTargetLangCode(trLang.getShortName());

		ResponseParseBaseWordList respParseWordList = new ResponseParseBaseWordList();
		respParseWordList.setUnavailableWordList(meaninglessBaseWordList);
		when(translationService.parseBaseWordList(reqParseWords)).thenReturn(respParseWordList);

		RequestCheckUnavaibleWordsForMeaninglesInLocal reqCheckUnavailableWords = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		reqCheckUnavailableWords.setSourceLangCode(engLang.getShortName());
		reqCheckUnavailableWords.setUnavailableWordList(respParseWordList.getUnavailableWordList());

		ResponseCheckUnavaibleWordsForMeaninglesInLocal respCheckUnavailableWords = new ResponseCheckUnavaibleWordsForMeaninglesInLocal();
		respCheckUnavailableWords.setMeaninglessWordList(CreateDTOObjectUtil.createMeaningLessWordList());
		respCheckUnavailableWords.setUnavailableWordList(CreateDTOObjectUtil.createMeaningLessBaseWordListNotInDB());
		when(translationService.checkUnavailableWordsInLocal(reqCheckUnavailableWords)).thenReturn(respCheckUnavailableWords);

		RequestSearchInTranslateApi reqSearchInTranslate = new RequestSearchInTranslateApi();
		reqSearchInTranslate.setSourceLangCode(engLang.getShortName());
		reqSearchInTranslate.setTargetLangCode(trLang.getShortName());
		reqSearchInTranslate.setUnavailableWordList(respCheckUnavailableWords.getUnavailableWordList());

		ResponseSearchInTranslateApi respSearchInTranslate = new ResponseSearchInTranslateApi();
		respSearchInTranslate.setMeaninglessWordList(meaninglessWordListNotInDB);
		when(translationService.searchInTranslateApi(reqSearchInTranslate)).thenReturn(respSearchInTranslate);

		RequestTranlateWords req = new RequestTranlateWords();
		req.setBaseWordList(meaninglessBaseWordList);
		req.setSourceLang(engLang);
		req.setTargetLang(trLang);

		ResponseTranslateWords translateWords = service.translateWords(req);
		assertNull(translateWords.getMeaningWordList());
		assertEquals(meaninglessWordList, translateWords.getMeaninglessWordList());
		verify(translationService).parseBaseWordList(reqParseWords);
		verify(translationService).checkUnavailableWordsInLocal(reqCheckUnavailableWords);
		verify(translationService).searchInTranslateApi(reqSearchInTranslate);

	}

	@Test
	public void givenWordMeaningListBothTypeWordThenReturMeaningWordList() throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {

		LanguageDTO engLang = CreateDTOObjectUtil.createEngLangDTO();
		LanguageDTO trLang = CreateDTOObjectUtil.createTrLangDTO();

		List<BaseWordDTO> meaningBaseWordList = CreateDTOObjectUtil.createMeaningBaseWordList();
		List<MeaningWordDTO> meaningWordList = CreateDTOObjectUtil.createMeaningWordList();

		List<BaseWordDTO> meaningBaseWordListNotInDB = CreateDTOObjectUtil.createMeaningBaseWordListNotInDB();
		List<MeaningWordDTO> meaningWordListNotInDB = CreateDTOObjectUtil.createMeaningWordListNotInDB();

		meaningBaseWordList.addAll(meaningBaseWordListNotInDB);
		meaningWordList.addAll(meaningWordListNotInDB);

		RequestParseBaseWordList reqParseWords = new RequestParseBaseWordList();
		reqParseWords.setBaseWordList(meaningBaseWordList);
		reqParseWords.setSourceLangCode(engLang.getShortName());
		reqParseWords.setTargetLangCode(trLang.getShortName());

		ResponseParseBaseWordList respParseWordList = new ResponseParseBaseWordList();
		respParseWordList.setMeaningWordList(CreateDTOObjectUtil.createMeaningWordList());
		respParseWordList.setUnavailableWordList(meaningBaseWordListNotInDB);
		when(translationService.parseBaseWordList(reqParseWords)).thenReturn(respParseWordList);

		RequestCheckUnavaibleWordsForMeaninglesInLocal reqCheckUnavailableWords = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		reqCheckUnavailableWords.setSourceLangCode(engLang.getShortName());
		reqCheckUnavailableWords.setUnavailableWordList(respParseWordList.getUnavailableWordList());

		ResponseCheckUnavaibleWordsForMeaninglesInLocal respCheckUnavailableWord = new ResponseCheckUnavaibleWordsForMeaninglesInLocal();
		respCheckUnavailableWord.setUnavailableWordList(meaningBaseWordListNotInDB);
		when(translationService.checkUnavailableWordsInLocal(reqCheckUnavailableWords)).thenReturn(respCheckUnavailableWord);

		RequestSearchInTranslateApi reqSearchInTranslate = new RequestSearchInTranslateApi();
		reqSearchInTranslate.setSourceLangCode(engLang.getShortName());
		reqSearchInTranslate.setTargetLangCode(trLang.getShortName());
		reqSearchInTranslate.setUnavailableWordList(respCheckUnavailableWord.getUnavailableWordList());

		ResponseSearchInTranslateApi respSearchInTranslate = new ResponseSearchInTranslateApi();
		respSearchInTranslate.setMeaningWordList(meaningWordListNotInDB);
		when(translationService.searchInTranslateApi(reqSearchInTranslate)).thenReturn(respSearchInTranslate);

		RequestTranlateWords req = new RequestTranlateWords();
		req.setBaseWordList(meaningBaseWordList);
		req.setSourceLang(engLang);
		req.setTargetLang(trLang);

		ResponseTranslateWords translateWords = service.translateWords(req);
		assertNull(translateWords.getMeaninglessWordList());
		assertEquals(meaningWordList, translateWords.getMeaningWordList());
		verify(translationService).parseBaseWordList(reqParseWords);
		verify(translationService).checkUnavailableWordsInLocal(reqCheckUnavailableWords);
		verify(translationService).searchInTranslateApi(reqSearchInTranslate);

	}

	@Test
	public void givenMeaningAndMeaninglessWordListBothTypeWordThenReturMeaningAndMeaninglessWordList() throws RequestWordListIsEmptyException, SourceLangAndTargetLangAreSameException {
		LanguageDTO engLang = CreateDTOObjectUtil.createEngLangDTO();
		LanguageDTO trLang = CreateDTOObjectUtil.createTrLangDTO();

		List<BaseWordDTO> baseWordList = CreateDTOObjectUtil.createMeaningBaseWordList();
		List<BaseWordDTO> baseWordListNotInDB = CreateDTOObjectUtil.createMeaningBaseWordListNotInDB();
		List<MeaningWordDTO> wordList = CreateDTOObjectUtil.createMeaningWordList();
		List<MeaningWordDTO> wordListNotInDB = CreateDTOObjectUtil.createMeaningWordListNotInDB();

		List<BaseWordDTO> meaninglessBaseWordList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		List<BaseWordDTO> meaninglessBaseWordListNotInDB = CreateDTOObjectUtil.createMeaningLessBaseWordListNotInDB();
		List<MeaninglessWordDTO> meaninglessWordList = CreateDTOObjectUtil.createMeaningLessWordList();
		List<MeaninglessWordDTO> meaninglessWordListNotInDB = CreateDTOObjectUtil.createMeaningLessWordListNotInDB();

		meaninglessBaseWordList.addAll(meaninglessBaseWordListNotInDB);
		meaninglessWordList.addAll(meaninglessWordListNotInDB);
		baseWordList.addAll(baseWordListNotInDB);
		baseWordList.addAll(meaninglessBaseWordList);
		wordList.addAll(wordListNotInDB);

		RequestParseBaseWordList reqParseWords = new RequestParseBaseWordList();
		reqParseWords.setBaseWordList(baseWordList);
		reqParseWords.setSourceLangCode(engLang.getShortName());
		reqParseWords.setTargetLangCode(trLang.getShortName());

		ResponseParseBaseWordList respParseWordList = new ResponseParseBaseWordList();
		respParseWordList.setMeaningWordList(CreateDTOObjectUtil.createMeaningWordList());
		List<BaseWordDTO> tempList = CreateDTOObjectUtil.createMeaningBaseWordListNotInDB();
		tempList.addAll(meaninglessBaseWordList);
		respParseWordList.setUnavailableWordList(tempList);
		when(translationService.parseBaseWordList(reqParseWords)).thenReturn(respParseWordList);

		RequestCheckUnavaibleWordsForMeaninglesInLocal reqCheckUnavailableWords = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		reqCheckUnavailableWords.setSourceLangCode(engLang.getShortName());
		reqCheckUnavailableWords.setUnavailableWordList(respParseWordList.getUnavailableWordList());

		ResponseCheckUnavaibleWordsForMeaninglesInLocal respCheckUnavailableWord = new ResponseCheckUnavaibleWordsForMeaninglesInLocal();
		respCheckUnavailableWord.setMeaninglessWordList(CreateDTOObjectUtil.createMeaningLessWordList());
		respCheckUnavailableWord.setUnavailableWordList(baseWordListNotInDB);
		when(translationService.checkUnavailableWordsInLocal(reqCheckUnavailableWords)).thenReturn(respCheckUnavailableWord);

		RequestSearchInTranslateApi reqSearchInTranslate = new RequestSearchInTranslateApi();
		reqSearchInTranslate.setSourceLangCode(engLang.getShortName());
		reqSearchInTranslate.setTargetLangCode(trLang.getShortName());
		reqSearchInTranslate.setUnavailableWordList(respCheckUnavailableWord.getUnavailableWordList());

		ResponseSearchInTranslateApi respSearchInTranslate = new ResponseSearchInTranslateApi();
		respSearchInTranslate.setMeaningWordList(CreateDTOObjectUtil.createMeaningWordListNotInDB());
		respSearchInTranslate.setMeaninglessWordList(CreateDTOObjectUtil.createMeaningLessWordListNotInDB());
		when(translationService.searchInTranslateApi(reqSearchInTranslate)).thenReturn(respSearchInTranslate);

		RequestTranlateWords req = new RequestTranlateWords();
		req.setBaseWordList(baseWordList);
		req.setSourceLang(engLang);
		req.setTargetLang(trLang);
		
		ResponseTranslateWords translateWords = service.translateWords(req);
		List<MeaningWordDTO> tempListForMemanings = CreateDTOObjectUtil.createMeaningWordList();
		tempListForMemanings.addAll(CreateDTOObjectUtil.createMeaningWordListNotInDB());
		assertEquals(tempListForMemanings, translateWords.getMeaningWordList());
		assertEquals(meaninglessWordList, translateWords.getMeaninglessWordList());
		verify(translationService).parseBaseWordList(reqParseWords);
		verify(translationService).checkUnavailableWordsInLocal(reqCheckUnavailableWords);
		verify(translationService).searchInTranslateApi(reqSearchInTranslate);
	}

}
