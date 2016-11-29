package com.kayra.tilmac.server.util;

import java.util.ArrayList;
import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.LanguageDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;

public class CreateDTOObjectUtil {

	private CreateDTOObjectUtil() {

	}

	public static LanguageDTO createEngLangDTO() {

		LanguageDTO langDTO = new LanguageDTO();
		langDTO.setName("English");
		langDTO.setShortName("en");
		return langDTO;
	}

	public static LanguageDTO createTrLangDTO() {
		LanguageDTO langDTO = new LanguageDTO();
		langDTO.setName("Turkish");
		langDTO.setShortName("tr");
		return langDTO;
	}

	public static List<BaseWordDTO> createMeaningBaseWordList() {

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

	public static List<MeaninglessWordDTO> createMeaningLessWordList() {

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

	public static List<BaseWordDTO> createMeaningLessBaseWordList() {

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

	public static List<MeaningWordDTO> createMeaningWordList() {

		List<MeaningWordDTO> meaningWordDTOLis = new ArrayList<>();
		LanguageDTO langDTO = createEngLangDTO();
		MeaningWordDTO meaningWordDTO = new MeaningWordDTO();
		meaningWordDTO.setId(1L);
		meaningWordDTO.setLang(langDTO);
		meaningWordDTO.setWord("go");
		meaningWordDTO.setTargetWordList(createTargetWordsForGo());
		meaningWordDTOLis.add(meaningWordDTO);
		meaningWordDTO = new MeaningWordDTO();
		meaningWordDTO.setId(2L);
		meaningWordDTO.setLang(langDTO);
		meaningWordDTO.setWord("black");
		meaningWordDTO.setTargetWordList(createTargetWordsForBlack());
		meaningWordDTOLis.add(meaningWordDTO);
		return meaningWordDTOLis;
	}

	public static List<BaseWordDTO> createTargetWordsForGo() {
		List<BaseWordDTO> targetWordDTOList = new ArrayList<>();
		LanguageDTO langDTO = createTrLangDTO();
		BaseWordDTO baseWordDTO = new BaseWordDTO();
		baseWordDTO.setId(10L);
		baseWordDTO.setLang(langDTO);
		baseWordDTO.setWord("gitmek");
		targetWordDTOList.add(baseWordDTO);
		baseWordDTO = new BaseWordDTO();
		baseWordDTO.setId(11L);
		baseWordDTO.setLang(langDTO);
		baseWordDTO.setWord("geçmek");
		targetWordDTOList.add(baseWordDTO);
		return targetWordDTOList;
	}

	public static List<BaseWordDTO> createTargetWordsForBlack() {
		List<BaseWordDTO> targetWordDTOList = new ArrayList<>();
		LanguageDTO langDTO = createTrLangDTO();
		BaseWordDTO baseWordDTO = new BaseWordDTO();
		baseWordDTO.setId(12L);
		baseWordDTO.setLang(langDTO);
		baseWordDTO.setWord("siyah");
		targetWordDTOList.add(baseWordDTO);
		baseWordDTO = new BaseWordDTO();
		baseWordDTO.setId(13L);
		baseWordDTO.setLang(langDTO);
		baseWordDTO.setWord("kara");
		targetWordDTOList.add(baseWordDTO);
		return targetWordDTOList;
	}

	public static List<BaseWordDTO> createMeaningBaseWordsForTranslate() {
		List<BaseWordDTO> meaningWordDTOLis = new ArrayList<>();
		LanguageDTO langDTO = createEngLangDTO();
		MeaningWordDTO meaningWordDTO = new MeaningWordDTO();
		meaningWordDTO.setId(21L);
		meaningWordDTO.setLang(langDTO);
		meaningWordDTO.setWord("going");
		meaningWordDTO.setTargetWordList(createTargetWordsForGo());
		meaningWordDTOLis.add(meaningWordDTO);
		meaningWordDTO = new MeaningWordDTO();
		meaningWordDTO.setId(22L);
		meaningWordDTO.setLang(langDTO);
		meaningWordDTO.setWord("asked");
		meaningWordDTO.setTargetWordList(createTargetWordsForBlack());
		meaningWordDTOLis.add(meaningWordDTO);
		return meaningWordDTOLis;
	}

}
