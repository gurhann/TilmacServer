package com.kayra.tilmac.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kayra.tilmac.server.dao.MeaningWordDAO;
import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.model.Language;
import com.kayra.tilmac.server.model.MeaningWord;
import com.kayra.tilmac.server.service.impl.TranslationServiceYandexImpl;
import com.kayra.tilmac.server.service.response.ResponseParseBaseWordList;
import com.kayra.tilmac.server.util.CreateDTOObjectUtil;
import com.kayra.tilmac.server.util.CreateModelObjectUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslationServiceYandexImplParseWordListTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@InjectMocks
	public TranslationService translationService = new TranslationServiceYandexImpl();

	@Mock
	public MeaningWordDAO meaningWordDAO;


	@Before
	public void beforeClass() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenParseWordListRequestWordListNullThenThrowException() {

		exception.expect(IllegalArgumentException.class);
		translationService.parseBaseWordList(null);
	}

	@Test
	public void givenParseWordListRequestWordListEmptyThenThrowException() {

		exception.expect(IllegalArgumentException.class);
		translationService.parseBaseWordList(new ArrayList<BaseWordDTO>());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenParseWordListAllWordsAreMeaninglessThenResponseReturnJustMeaninglessList() {

		Language engLang = CreateModelObjectUtil.createEngLang();
		List<BaseWordDTO> meaningLessBaseWordDTOList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		when(meaningWordDAO.findByName("ase", engLang.getShortName())).thenThrow(NoResultException.class);
		when(meaningWordDAO.findByName("qwe", engLang.getShortName())).thenThrow(NoResultException.class);
		ResponseParseBaseWordList parseBaseWordList = translationService.parseBaseWordList(meaningLessBaseWordDTOList);
		List<BaseWordDTO> meaninglessWordDTOList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		assertEquals(meaninglessWordDTOList.get(0), parseBaseWordList.getUnavailableWordList().get(0));
		assertEquals(meaninglessWordDTOList.get(1), parseBaseWordList.getUnavailableWordList().get(1));
		assertNull(parseBaseWordList.getMeaningWordList());
	}

	@Test
	public void givenParseWordListAllWordsAreMeaningThenResponseJustMeaningList() {

		Language engLang = CreateModelObjectUtil.createEngLang();
		List<MeaningWord> meaningWordList = CreateModelObjectUtil.createMeaningWordList();
		List<BaseWordDTO> meaningBaseWordDTOList = CreateDTOObjectUtil.createMeaningBaseWordList();
		when(meaningWordDAO.findByName("go", engLang.getShortName())).thenReturn(meaningWordList.get(0));
		when(meaningWordDAO.findByName("black", engLang.getShortName())).thenReturn(meaningWordList.get(1));
		ResponseParseBaseWordList parseBaseWordList = translationService.parseBaseWordList(meaningBaseWordDTOList);
		List<MeaningWordDTO> meaningWordDTOList = CreateDTOObjectUtil.createMeaningWordList();
		assertEquals(meaningWordDTOList.get(0), parseBaseWordList.getMeaningWordList().get(0));
		assertEquals(meaningWordDTOList.get(1), parseBaseWordList.getMeaningWordList().get(1));
		assertNull(parseBaseWordList.getUnavailableWordList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenParseWordListConsistBothWordTypeThenResponseNotConsistNullList() {

		Language engLang = CreateModelObjectUtil.createEngLang();
		List<MeaningWord> meaningWordList = CreateModelObjectUtil.createMeaningWordList();
		List<BaseWordDTO> baseWordList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		baseWordList.addAll(CreateDTOObjectUtil.createMeaningBaseWordList());

		when(meaningWordDAO.findByName("ase", engLang.getShortName())).thenThrow(NoResultException.class);
		when(meaningWordDAO.findByName("qwe", engLang.getShortName())).thenThrow(NoResultException.class);
		when(meaningWordDAO.findByName("go", engLang.getShortName())).thenReturn(meaningWordList.get(0));
		when(meaningWordDAO.findByName("black", engLang.getShortName())).thenReturn(meaningWordList.get(1));

		ResponseParseBaseWordList parseBaseWordList = translationService.parseBaseWordList(baseWordList);
		List<MeaningWordDTO> meaningWordDTOList = CreateDTOObjectUtil.createMeaningWordList();
		List<BaseWordDTO> meaningLessWordDTOList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		assertEquals(meaningWordDTOList.get(0), parseBaseWordList.getMeaningWordList().get(0));
		assertEquals(meaningWordDTOList.get(1), parseBaseWordList.getMeaningWordList().get(1));
		assertEquals(meaningLessWordDTOList.get(0), parseBaseWordList.getUnavailableWordList().get(0));
		assertEquals(meaningLessWordDTOList.get(1), parseBaseWordList.getUnavailableWordList().get(1));

	}

	
}
