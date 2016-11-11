package com.kayra.tilmac.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
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

import com.kayra.tilmac.server.dao.LanguageDAO;
import com.kayra.tilmac.server.dao.MeaningWordDAO;
import com.kayra.tilmac.server.dao.MeaninglessWordDAO;
import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.LanguageDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;
import com.kayra.tilmac.server.model.Language;
import com.kayra.tilmac.server.model.MeaningWord;
import com.kayra.tilmac.server.model.MeaninglessWord;
import com.kayra.tilmac.server.service.impl.TranslationServiceYandexImpl;
import com.kayra.tilmac.server.service.response.ResponseParseBaseWordList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslationServiceYandexImplParseWordListTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@InjectMocks
	public TranslationService translationService = new TranslationServiceYandexImpl();

	@Mock
	public MeaninglessWordDAO meaninglessWordDAO;

	@Mock
	public MeaningWordDAO meaningWordDAO;

	@Mock
	public LanguageDAO languageDAO;

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
		Language engLang = createEngLang();
		List<BaseWordDTO> meaningLessBaseWordDTOList = createMeaningLessBaseWordDTOList();
		when(meaningWordDAO.findByName("ase", engLang.getShortName())).thenThrow(NoResultException.class);
		when(meaningWordDAO.findByName("qwe", engLang.getShortName())).thenThrow(NoResultException.class);

		ResponseParseBaseWordList parseBaseWordList = translationService.parseBaseWordList(meaningLessBaseWordDTOList);
		List<BaseWordDTO> meaninglessWordDTOList = createMeaningLessBaseWordDTOList();
		assertEquals(meaninglessWordDTOList.get(0), parseBaseWordList.getUnavailableWordList().get(0));
		assertEquals(meaninglessWordDTOList.get(1), parseBaseWordList.getUnavailableWordList().get(1));
		assertNull(parseBaseWordList.getMeaningWordList());
	}

	@Test
	public void givenParseWordListAllWordsAreMeaningThenResponseJustMeaningList() {
		Language engLang = createEngLang();
		List<MeaningWord> meaningWordList = createMeaningWordList();
		List<BaseWordDTO> meaningBaseWordDTOList = createMeaningBaseWordDTOList();
		
		when(meaningWordDAO.findByName("go", engLang.getShortName())).thenReturn(meaningWordList.get(0));
		when(meaningWordDAO.findByName("black", engLang.getShortName())).thenReturn(meaningWordList.get(1));

		ResponseParseBaseWordList parseBaseWordList = translationService.parseBaseWordList(meaningBaseWordDTOList);
		List<MeaningWordDTO> meaningWordDTOList = createMeaningWordDTOList();
		assertEquals(meaningWordDTOList.get(0), parseBaseWordList.getMeaningWordList().get(0));
		assertEquals(meaningWordDTOList.get(1), parseBaseWordList.getMeaningWordList().get(1));
		assertNull(parseBaseWordList.getUnavailableWordList());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void givenParseWordListConsistBothWordTypeThenResponseNotConsistNullList() {
		Language engLang = createEngLang();
		List<MeaninglessWord> meaninglessWordList = createMeaningLessWordList();
		List<MeaningWord> meaningWordList = createMeaningWordList();
		List<BaseWordDTO> baseWordList = createMeaningLessBaseWordDTOList();
		baseWordList.addAll(createMeaningBaseWordDTOList());

		when(meaningWordDAO.findByName("ase", engLang.getShortName())).thenThrow(NoResultException.class);
		when(meaningWordDAO.findByName("qwe", engLang.getShortName())).thenThrow(NoResultException.class);
		when(meaninglessWordDAO.findByName("ase", engLang.getShortName())).thenReturn(meaninglessWordList.get(0));
		when(meaninglessWordDAO.findByName("qwe", engLang.getShortName())).thenReturn(meaninglessWordList.get(1));
		when(meaningWordDAO.findByName("go", engLang.getShortName())).thenReturn(meaningWordList.get(0));
		when(meaningWordDAO.findByName("black", engLang.getShortName())).thenReturn(meaningWordList.get(1));

		ResponseParseBaseWordList parseBaseWordList = translationService.parseBaseWordList(baseWordList);
		List<MeaningWordDTO> meaningWordDTOList = createMeaningWordDTOList();
		List<BaseWordDTO> meaningLessWordDTOList = createMeaningLessBaseWordDTOList();
		assertEquals(meaningWordDTOList.get(0), parseBaseWordList.getMeaningWordList().get(0));
		assertEquals(meaningWordDTOList.get(1), parseBaseWordList.getMeaningWordList().get(1));
		assertEquals(meaningLessWordDTOList.get(0), parseBaseWordList.getUnavailableWordList().get(0));
		assertEquals(meaningLessWordDTOList.get(1), parseBaseWordList.getUnavailableWordList().get(1));

	}

	private List<MeaninglessWordDTO> createMeaningLessWordDTOList() {
		List<MeaninglessWordDTO> meaninglessWordDTOList = new ArrayList<>();
		LanguageDTO langDTO = createEngLangDTO();
		MeaninglessWordDTO meaninglessWordDTO = new MeaninglessWordDTO();
		meaninglessWordDTO.setId(1L);
		meaninglessWordDTO.setLang(langDTO);
		meaninglessWordDTO.setWord("ase");
		meaninglessWordDTOList.add(meaninglessWordDTO);
		meaninglessWordDTO = new MeaninglessWordDTO();
		meaninglessWordDTO.setId(2L);
		meaninglessWordDTO.setLang(langDTO);
		meaninglessWordDTO.setWord("qwe");
		meaninglessWordDTOList.add(meaninglessWordDTO);
		return meaninglessWordDTOList;
	}

	private List<BaseWordDTO> createMeaningLessBaseWordDTOList() {
		List<BaseWordDTO> meaninglessWordDTOList = new ArrayList<>();
		LanguageDTO langDTO = createEngLangDTO();
		BaseWordDTO meaninglessWordDTO = new BaseWordDTO();
		meaninglessWordDTO.setId(1L);
		meaninglessWordDTO.setLang(langDTO);
		meaninglessWordDTO.setWord("ase");
		meaninglessWordDTOList.add(meaninglessWordDTO);
		meaninglessWordDTO = new BaseWordDTO();
		meaninglessWordDTO.setId(2L);
		meaninglessWordDTO.setLang(langDTO);
		meaninglessWordDTO.setWord("qwe");
		meaninglessWordDTOList.add(meaninglessWordDTO);
		return meaninglessWordDTOList;
	}

	private List<MeaningWordDTO> createMeaningWordDTOList() {
		List<MeaningWordDTO> meaningWordDTOLis = new ArrayList<>();
		LanguageDTO langDTO = createEngLangDTO();
		MeaningWordDTO meaningWordDTO = new MeaningWordDTO();
		meaningWordDTO.setId(1L);
		meaningWordDTO.setLang(langDTO);
		meaningWordDTO.setWord("go");
		meaningWordDTOLis.add(meaningWordDTO);
		meaningWordDTO = new MeaningWordDTO();
		meaningWordDTO.setId(2L);
		meaningWordDTO.setLang(langDTO);
		meaningWordDTO.setWord("black");
		meaningWordDTOLis.add(meaningWordDTO);
		return meaningWordDTOLis;
	}

	private Language createEngLang() {
		Language lang = new Language();
		lang.setId(1);
		lang.setName("English");
		lang.setShortName("Eng");
		return lang;
	}

	private List<BaseWordDTO> createMeaningBaseWordDTOList() {
		List<BaseWordDTO> meaningWordDTOLis = new ArrayList<>();
		LanguageDTO lang = createEngLangDTO();
		BaseWordDTO meaningWordDTO = new BaseWordDTO();
		meaningWordDTO.setId(1L);
		meaningWordDTO.setLang(lang);
		meaningWordDTO.setWord("go");
		meaningWordDTOLis.add(meaningWordDTO);
		meaningWordDTO = new BaseWordDTO();
		meaningWordDTO.setId(2L);
		meaningWordDTO.setLang(lang);
		meaningWordDTO.setWord("black");
		meaningWordDTOLis.add(meaningWordDTO);
		return meaningWordDTOLis;
	}

	private List<MeaninglessWord> createMeaningLessWordList() {
		List<MeaninglessWord> meaninglessWordList = new ArrayList<>();
		Language lang = createEngLang();
		MeaninglessWord meaninglessWord = new MeaninglessWord();
		meaninglessWord.setId(1L);
		meaninglessWord.setLang(lang);
		meaninglessWord.setWord("ase");
		meaninglessWordList.add(meaninglessWord);
		meaninglessWord = new MeaninglessWord();
		meaninglessWord.setId(2L);
		meaninglessWord.setLang(lang);
		meaninglessWord.setWord("qwe");
		meaninglessWordList.add(meaninglessWord);
		return meaninglessWordList;
	}

	private List<MeaningWord> createMeaningWordList() {
		List<MeaningWord> meaningWordList = new ArrayList<>();
		Language lang = createEngLang();
		MeaningWord meaningWord = new MeaningWord();
		meaningWord.setId(1L);
		meaningWord.setLang(lang);
		meaningWord.setWord("go");
		meaningWordList.add(meaningWord);
		meaningWord = new MeaningWord();
		meaningWord.setId(2L);
		meaningWord.setLang(lang);
		meaningWord.setWord("black");
		meaningWordList.add(meaningWord);
		return meaningWordList;
	}

	private LanguageDTO createEngLangDTO() {
		LanguageDTO langDTO = new LanguageDTO();
		langDTO.setId(1);
		langDTO.setName("English");
		langDTO.setShortName("Eng");
		return langDTO;
	}

}
