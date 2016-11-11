package com.kayra.tilmac.server.mapper;

import java.util.ArrayList;
import java.util.List;

import com.kayra.tilmac.server.dto.LanguageDTO;
import com.kayra.tilmac.server.model.Language;

public class LanguageMapper {

	private LanguageMapper() {

	}

	public static Language dtoToModel(LanguageDTO dto) {
		if (dto == null) {
			return null;
		}
		Language lang = new Language();
		lang.setId(dto.getId());
		lang.setName(dto.getName());
		lang.setShortName(dto.getShortName());
		return lang;
	}

	public static LanguageDTO modelToDto(Language lang) {
		if (lang == null) {
			return null;
		}
		LanguageDTO dto = new LanguageDTO();
		dto.setId(lang.getId());
		dto.setName(lang.getName());
		dto.setShortName(lang.getShortName());
		return dto;
	}

	public static List<Language> dtoToModelList(List<LanguageDTO> dtoList) {
		if (dtoList == null || dtoList.isEmpty()) {
			return null;
		}
		List<Language> langList = new ArrayList<>();
		for (LanguageDTO dto : dtoList) {
			langList.add(dtoToModel(dto));
		}
		return langList;
	}

	public static List<LanguageDTO> modelToDtoList(List<Language> langList) {
		if (langList == null || langList.isEmpty()) {
			return null;
		}
		List<LanguageDTO> dtoList = new ArrayList<>();
		for (Language lang : langList) {
			dtoList.add(modelToDto(lang));
		}
		return dtoList;
	}
}
