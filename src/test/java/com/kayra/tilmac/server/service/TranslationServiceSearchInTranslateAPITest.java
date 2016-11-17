package com.kayra.tilmac.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;
import com.kayra.tilmac.server.service.impl.TranslationServiceImpl;
import com.kayra.tilmac.server.service.response.ResponseSearchInDictionary;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslate;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslateApi;
import com.kayra.tilmac.server.util.CreateDTOObjectUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslationServiceSearchInTranslateAPITest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@InjectMocks
	public TranslationService translationService = new TranslationServiceImpl();

	@Mock
	public TranslateAPIService translateAPIService;

	@Test
	public void givenWordListNullThenThrowException() {
		exception.expect(IllegalArgumentException.class);
		translationService.searchInTranslateApi(null);
	}

	@Test
	public void givenWordListEmptyThenThrowException() {
		exception.expect(IllegalArgumentException.class);
		translationService.searchInTranslateApi(new ArrayList<BaseWordDTO>());
	}

	@Test
	public void givenWordsAreMeaninglessThenReturnJustMeaninglessList() {
		List<BaseWordDTO> meaninglessBaseWordDTOList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		List<MeaninglessWordDTO> meaninglessWordDTOList = CreateDTOObjectUtil.createMeaningLessWordList();

		ResponseSearchInDictionary respSearchInDirectory = new ResponseSearchInDictionary();
		respSearchInDirectory.setMeaningWordList(null);
		respSearchInDirectory.setUnavailableWordList(meaninglessBaseWordDTOList);

		ResponseSearchInTranslate respSearchInTranslate = new ResponseSearchInTranslate();
		respSearchInTranslate.setMeaningWordList(null);
		respSearchInTranslate.setMeaninglessWordList(meaninglessWordDTOList);
		
		when(translateAPIService.searchInDictionary(meaninglessBaseWordDTOList)).thenReturn(respSearchInDirectory);
		when(translateAPIService.searchInTranslate(meaninglessBaseWordDTOList)).thenReturn(respSearchInTranslate);

		ResponseSearchInTranslateApi searchInTranslateApi = translationService.searchInTranslateApi(meaninglessBaseWordDTOList);
//		verify(translateAPIService.searchInDictionary(meaninglessBaseWordDTOList));
//		verify(translateAPIService.searchInTranslate(meaninglessBaseWordDTOList));

		assertEquals(meaninglessWordDTOList.get(0), searchInTranslateApi.getMeaninglessWordList().get(0));
		assertEquals(meaninglessWordDTOList.get(1), searchInTranslateApi.getMeaninglessWordList().get(1));
		assertNull(searchInTranslateApi.getMeaningWordList());
	}

	@Test
	public void givenWordsAreMeaningThenReturnJustmeaningList() {
		List<BaseWordDTO> meaningBaseWordDTOList = CreateDTOObjectUtil.createMeaningBaseWordList();
		List<MeaningWordDTO> meaningWordDTOList = CreateDTOObjectUtil.createMeaningWordList();

		ResponseSearchInDictionary respSearchInDirectory = new ResponseSearchInDictionary();
		respSearchInDirectory.setMeaningWordList(meaningWordDTOList);
		respSearchInDirectory.setUnavailableWordList(null);

		ResponseSearchInTranslate respSearchInTranslate = new ResponseSearchInTranslate();
		respSearchInTranslate.setMeaningWordList(meaningWordDTOList);
		respSearchInTranslate.setMeaninglessWordList(null);
		
		when(translateAPIService.searchInDictionary(meaningBaseWordDTOList)).thenReturn(respSearchInDirectory);
		when(translateAPIService.searchInTranslate(meaningBaseWordDTOList)).thenReturn(respSearchInTranslate);

		ResponseSearchInTranslateApi searchInTranslateApi = translationService.searchInTranslateApi(meaningBaseWordDTOList);
//		verify(translateAPIService.searchInDictionary(meaningBaseWordDTOList));
//		verify(translateAPIService.searchInTranslate(meaningBaseWordDTOList));

		assertEquals(meaningWordDTOList.get(0), searchInTranslateApi.getMeaningWordList().get(0));
		assertEquals(meaningWordDTOList.get(1), searchInTranslateApi.getMeaningWordList().get(1));
		assertNull(searchInTranslateApi.getMeaninglessWordList());
	}

}
