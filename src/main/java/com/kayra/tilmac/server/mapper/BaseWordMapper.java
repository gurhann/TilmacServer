package com.kayra.tilmac.server.mapper;

import java.util.ArrayList;
import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.model.MeaningWord;

public class BaseWordMapper {
	private BaseWordMapper() {

	}

	public static MeaningWord dtoToModel(BaseWordDTO dto) {
		if (dto == null) {
			return null;
		}
		MeaningWord baseWord = new MeaningWord();
		baseWord.setId(dto.getId());
		baseWord.setLang(LanguageMapper.dtoToModel(dto.getLang()));
		baseWord.setWord(dto.getWord());
		return baseWord;
	}

	public static BaseWordDTO modelToDto(MeaningWord word) {
		if (word == null) {
			return null;
		}
		BaseWordDTO dto = new BaseWordDTO();
		dto.setId(word.getId());
		dto.setLang(LanguageMapper.modelToDto(word.getLang()));
		dto.setWord(word.getWord());
		return dto;
	}

	public static List<MeaningWord> dtoToModelList(List<BaseWordDTO> dtoList) {
		if (dtoList == null || dtoList.isEmpty()) {
			return null;
		}
		List<MeaningWord> baseWordList = new ArrayList<>();
		for (BaseWordDTO dto : dtoList) {
			baseWordList.add(dtoToModel(dto));
		}
		return baseWordList;
	}

	public static List<BaseWordDTO> modelToDtoList(List<MeaningWord> baseWordList) {
		if (baseWordList == null || baseWordList.isEmpty()) {
			return null;
		}
		List<BaseWordDTO> dtoList = new ArrayList<>();
		for (MeaningWord baseWord : baseWordList) {
			dtoList.add(modelToDto(baseWord));
		}
		return dtoList;
	}

}
