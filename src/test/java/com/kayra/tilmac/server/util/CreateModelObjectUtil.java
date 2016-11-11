package com.kayra.tilmac.server.util;

import java.util.ArrayList;
import java.util.List;

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
		meaningWordList.add(meaningWord);
		meaningWord = new MeaningWord();
		meaningWord.setId(2L);
		meaningWord.setLang(lang);
		meaningWord.setWord("black");
		meaningWordList.add(meaningWord);
		return meaningWordList;
	}

}
