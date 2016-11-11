package com.kayra.tilmac.server.mapper;

import java.util.ArrayList;
import java.util.List;

import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.model.BaseWord;

public class BaseWordMapper {
	private BaseWordMapper() {

	}

	public static BaseWord dtoToModel(BaseWordDTO dto) {
		if (dto == null) {
			return null;
		}
		BaseWord baseWord = new BaseWord();
		baseWord.setId(dto.getId());
		baseWord.setLang(LanguageMapper.dtoToModel(dto.getLang()));
		baseWord.setWord(dto.getWord());
		return baseWord;
	}

	public static BaseWordDTO modelToDto(BaseWord baseWord) {
		if (baseWord == null) {
			return null;
		}
		BaseWordDTO dto = new BaseWordDTO();
		dto.setId(baseWord.getId());
		dto.setLang(LanguageMapper.modelToDto(baseWord.getLang()));
		dto.setWord(baseWord.getWord());
		return dto;
	}

	public static List<BaseWord> dtoToModelList(List<BaseWordDTO> dtoList) {
		if (dtoList == null || dtoList.isEmpty()) {
			return null;
		}
		List<BaseWord> baseWordList = new ArrayList<>();
		for (BaseWordDTO dto : dtoList) {
			baseWordList.add(dtoToModel(dto));
		}
		return baseWordList;
	}

	public static List<BaseWordDTO> modelToDtoList(List<BaseWord> baseWordList) {
		if (baseWordList == null || baseWordList.isEmpty()) {
			return null;
		}
		List<BaseWordDTO> dtoList = new ArrayList<>();
		for (BaseWord baseWord : baseWordList) {
			dtoList.add(modelToDto(baseWord));
		}
		return dtoList;
	}
}
