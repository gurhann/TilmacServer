package com.kayra.tilmac.server.mapper;

import java.util.ArrayList;
import java.util.List;

import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.model.MeaningWord;

public class MeaningWordMapper {
	private MeaningWordMapper() {

	}

	public static MeaningWord dtoToModel(MeaningWordDTO dto) {
		if (dto == null) {
			return null;
		}
		MeaningWord meaningWord = new MeaningWord();
		meaningWord.setId(dto.getId());
		meaningWord.setLang(LanguageMapper.dtoToModel(dto.getLang()));
		meaningWord.setWord(dto.getWord());
		meaningWord.setTargetWordList(BaseWordMapper.dtoToModelList(dto.getTargetWordList()));
		return meaningWord;
	}

	public static MeaningWordDTO modelToDto(MeaningWord meaningWord) {
		if (meaningWord == null) {
			return null;
		}
		MeaningWordDTO dto = new MeaningWordDTO();
		dto.setId(meaningWord.getId());
		dto.setLang(LanguageMapper.modelToDto(meaningWord.getLang()));
		dto.setWord(meaningWord.getWord());
		dto.setTargetWordList(BaseWordMapper.modelToDtoList(meaningWord.getTargetWordList()));
		return dto;
	}

	public static List<MeaningWord> dtoToModelList(List<MeaningWordDTO> dtoList) {
		if (dtoList == null || dtoList.isEmpty()) {
			return null;
		}
		List<MeaningWord> meaningWordList = new ArrayList<>();
		for (MeaningWordDTO dto : dtoList) {
			meaningWordList.add(dtoToModel(dto));
		}
		return meaningWordList;
	}

	public static List<MeaningWordDTO> modelToDtoList(List<MeaningWord> meaningWordList) {
		if (meaningWordList == null || meaningWordList.isEmpty()) {
			return null;
		}
		List<MeaningWordDTO> dtoList = new ArrayList<>();
		for (MeaningWord MeaningWord : meaningWordList) {
			dtoList.add(modelToDto(MeaningWord));
		}
		return dtoList;
	}
}
