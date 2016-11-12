package com.kayra.tilmac.server.util;

import java.util.ArrayList;
import java.util.List;

import com.kayra.tilmac.server.model.BaseWord;
import com.kayra.tilmac.server.model.Language;
import com.kayra.tilmac.server.model.MeaningWord;
import com.kayra.tilmac.server.model.MeaninglessWord;

public class CreateModelObjectUtil {

	private CreateModelObjectUtil() {
	}

	public static Language createEngLang() {

		Language lang = new Language();
		lang.setId(1);
		lang.setName("English");
		lang.setShortName("Eng");
		return lang;
	}

	public static Language createTrLang() {

		Language lang = new Language();
		lang.setId(2);
		lang.setName("Turkish");
		lang.setShortName("tr");
		return lang;
	}

	public static List<MeaninglessWord> createMeaningLessWordList() {

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

	public static List<MeaningWord> createMeaningWordList() {

		List<MeaningWord> meaningWordList = new ArrayList<>();
		Language lang = createEngLang();
		MeaningWord meaningWord = new MeaningWord();
		meaningWord.setId(1L);
		meaningWord.setLang(lang);
		meaningWord.setWord("go");
		meaningWord.setTargetWordList(createTargetWordsForGo());
		meaningWordList.add(meaningWord);
		meaningWord = new MeaningWord();
		meaningWord.setId(2L);
		meaningWord.setLang(lang);
		meaningWord.setWord("black");
		meaningWord.setTargetWordList(createTargetWordsForBlack());
		meaningWordList.add(meaningWord);
		return meaningWordList;
	}

	public static List<BaseWord> createTargetWordsForGo() {
		List<BaseWord> targetWordList = new ArrayList<>();
		Language lang = createTrLang();
		BaseWord baseWord = new BaseWord();
		baseWord.setId(10L);
		baseWord.setLang(lang);
		baseWord.setWord("gitmek");
		targetWordList.add(baseWord);
		baseWord = new BaseWord();
		baseWord.setId(11L);
		baseWord.setLang(lang);
		baseWord.setWord("geçmek");
		targetWordList.add(baseWord);
		return targetWordList;
	}

	public static List<BaseWord> createTargetWordsForBlack() {
		List<BaseWord> targetWordList = new ArrayList<>();
		Language lang = createTrLang();
		BaseWord baseWord = new BaseWord();
		baseWord.setId(12L);
		baseWord.setLang(lang);
		baseWord.setWord("siyah");
		targetWordList.add(baseWord);
		baseWord = new BaseWord();
		baseWord.setId(13L);
		baseWord.setLang(lang);
		baseWord.setWord("kara");
		targetWordList.add(baseWord);

		return targetWordList;
	}

}
