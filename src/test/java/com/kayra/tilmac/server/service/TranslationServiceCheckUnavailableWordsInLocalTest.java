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

import com.kayra.tilmac.server.dao.MeaninglessWordDAO;
import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;
import com.kayra.tilmac.server.exception.RequestWordListIsEmptyException;
import com.kayra.tilmac.server.model.Language;
import com.kayra.tilmac.server.model.MeaninglessWord;
import com.kayra.tilmac.server.service.impl.TranslationServiceImpl;
import com.kayra.tilmac.server.service.request.RequestCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.response.ResponseCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.util.CreateDTOObjectUtil;
import com.kayra.tilmac.server.util.CreateModelObjectUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslationServiceCheckUnavailableWordsInLocalTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@InjectMocks
	public TranslationService translationService = new TranslationServiceImpl();

	@Mock
	public MeaninglessWordDAO meaningLessWordDAO;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenWordListIstNullThenThrowException() throws RequestWordListIsEmptyException {
		RequestCheckUnavaibleWordsForMeaninglesInLocal req = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		req.setUnavailableWordList(null);
		exception.expect(RequestWordListIsEmptyException.class);
		translationService.checkUnavailableWordsInLocal(req);
	}

	@Test
	public void givenWordListIsEmptyThenThrowException() throws RequestWordListIsEmptyException {
		RequestCheckUnavaibleWordsForMeaninglesInLocal req = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		req.setUnavailableWordList(new ArrayList<>());
		exception.expect(RequestWordListIsEmptyException.class);
		translationService.checkUnavailableWordsInLocal(req);
	}

	@Test
	public void givenWordListAllWordsAreMeaninglessThenResponseJustMeaninglessList() throws RequestWordListIsEmptyException {
		Language engLang = CreateModelObjectUtil.createEngLang();
		List<BaseWordDTO> meaninglessBaseWordDTOList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		List<MeaninglessWord> meaninglessWordList = CreateModelObjectUtil.createMeaningLessWordList();
		when(meaningLessWordDAO.findByName("ase", engLang.getShortName())).thenReturn(meaninglessWordList.get(0));
		when(meaningLessWordDAO.findByName("qwe", engLang.getShortName())).thenReturn(meaninglessWordList.get(1));

		RequestCheckUnavaibleWordsForMeaninglesInLocal req = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		req.setUnavailableWordList(meaninglessBaseWordDTOList);
		req.setSourceLangCode(engLang.getShortName());
		ResponseCheckUnavaibleWordsForMeaninglesInLocal checkUnavailableWordsInLocal = translationService.checkUnavailableWordsInLocal(req);
		List<MeaninglessWordDTO> meaninglessWordDTOList = CreateDTOObjectUtil.createMeaningLessWordList();
		assertEquals(meaninglessWordDTOList.get(0), checkUnavailableWordsInLocal.getMeaninglessWordList().get(0));
		assertEquals(meaninglessWordDTOList.get(1), checkUnavailableWordsInLocal.getMeaninglessWordList().get(1));
		assertNull(checkUnavailableWordsInLocal.getUnavailableWordList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenWordListAllWordsAreUnavailableThenResponseJustUnavailableList() throws RequestWordListIsEmptyException {

		Language engLang = CreateModelObjectUtil.createEngLang();
		List<BaseWordDTO> meaningBaseWordDTOList = CreateDTOObjectUtil.createMeaningBaseWordList();
		when(meaningLessWordDAO.findByName("go", engLang.getShortName())).thenThrow(NoResultException.class);
		when(meaningLessWordDAO.findByName("black", engLang.getShortName())).thenThrow(NoResultException.class);

		RequestCheckUnavaibleWordsForMeaninglesInLocal req = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		req.setUnavailableWordList(meaningBaseWordDTOList);
		req.setSourceLangCode(engLang.getShortName());
		ResponseCheckUnavaibleWordsForMeaninglesInLocal checkUnavailableWordsInLocal = translationService.checkUnavailableWordsInLocal(req);
		assertEquals(meaningBaseWordDTOList.get(0), checkUnavailableWordsInLocal.getUnavailableWordList().get(0));
		assertEquals(meaningBaseWordDTOList.get(1), checkUnavailableWordsInLocal.getUnavailableWordList().get(1));
		assertNull(checkUnavailableWordsInLocal.getMeaninglessWordList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenWordListConsistBothWordTypeThenResponseNotConsistNullList() throws RequestWordListIsEmptyException {
		Language engLang = CreateModelObjectUtil.createEngLang();
		List<MeaninglessWord> meaninglessWordList = CreateModelObjectUtil.createMeaningLessWordList();
		List<BaseWordDTO> baseWordList = CreateDTOObjectUtil.createMeaningLessBaseWordList();
		baseWordList.addAll(CreateDTOObjectUtil.createMeaningBaseWordList());
		when(meaningLessWordDAO.findByName("go", engLang.getShortName())).thenThrow(NoResultException.class);
		when(meaningLessWordDAO.findByName("black", engLang.getShortName())).thenThrow(NoResultException.class);
		when(meaningLessWordDAO.findByName("ase", engLang.getShortName())).thenReturn(meaninglessWordList.get(0));
		when(meaningLessWordDAO.findByName("qwe", engLang.getShortName())).thenReturn(meaninglessWordList.get(1));

		RequestCheckUnavaibleWordsForMeaninglesInLocal req = new RequestCheckUnavaibleWordsForMeaninglesInLocal();
		req.setUnavailableWordList(baseWordList);
		req.setSourceLangCode(engLang.getShortName());
		ResponseCheckUnavaibleWordsForMeaninglesInLocal checkUnavailableWordsInLocal = translationService.checkUnavailableWordsInLocal(req);
		List<MeaninglessWordDTO> meaninglessWordDTOList = CreateDTOObjectUtil.createMeaningLessWordList();
		List<BaseWordDTO> unavailableWordDTOList = CreateDTOObjectUtil.createMeaningBaseWordList();
		assertEquals(meaninglessWordDTOList.get(0), checkUnavailableWordsInLocal.getMeaninglessWordList().get(0));
		assertEquals(meaninglessWordDTOList.get(1), checkUnavailableWordsInLocal.getMeaninglessWordList().get(1));
		assertEquals(unavailableWordDTOList.get(0), checkUnavailableWordsInLocal.getUnavailableWordList().get(0));
		assertEquals(unavailableWordDTOList.get(1), checkUnavailableWordsInLocal.getUnavailableWordList().get(1));
	}

}
