package com.kayra.tilmac.server.mapper;

import java.util.ArrayList;
import java.util.List;

import com.kayra.tilmac.server.dto.MeaninglessWordDTO;
import com.kayra.tilmac.server.model.MeaninglessWord;

public class MeaninglessWordMapper {
	private MeaninglessWordMapper() {

	}

	public static MeaninglessWord dtoToModel(MeaninglessWordDTO dto) {
		if (dto == null) {
			return null;
		}
		MeaninglessWord MeaninglessWord = new MeaninglessWord();
		MeaninglessWord.setId(dto.getId());
		MeaninglessWord.setLang(LanguageMapper.dtoToModel(dto.getLang()));
		MeaninglessWord.setWord(dto.getWord());
		return MeaninglessWord;
	}

	public static MeaninglessWordDTO modelToDto(MeaninglessWord MeaninglessWord) {
		if (MeaninglessWord == null) {
			return null;
		}
		MeaninglessWordDTO dto = new MeaninglessWordDTO();
		dto.setId(MeaninglessWord.getId());
		dto.setLang(LanguageMapper.modelToDto(MeaninglessWord.getLang()));
		dto.setWord(MeaninglessWord.getWord());
		return dto;
	}

	public static List<MeaninglessWord> dtoToModelList(List<MeaninglessWordDTO> dtoList) {
		if (dtoList == null || dtoList.isEmpty()) {
			return null;
		}
		List<MeaninglessWord> MeaninglessWordList = new ArrayList<>();
		for (MeaninglessWordDTO dto : dtoList) {
			MeaninglessWordList.add(dtoToModel(dto));
		}
		return MeaninglessWordList;
	}

	public static List<MeaninglessWordDTO> modelToDtoList(List<MeaninglessWord> MeaninglessWordList) {
		if (MeaninglessWordList == null || MeaninglessWordList.isEmpty()) {
			return null;
		}
		List<MeaninglessWordDTO> dtoList = new ArrayList<>();
		for (MeaninglessWord MeaninglessWord : MeaninglessWordList) {
			dtoList.add(modelToDto(MeaninglessWord));
		}
		return dtoList;
	}
}
