package com.kayra.tilmac.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kayra.tilmac.server.dao.MeaningWordDAO;
import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.mapper.MeaningWordMapper;
import com.kayra.tilmac.server.model.MeaningWord;
import com.kayra.tilmac.server.service.TranslationService;
import com.kayra.tilmac.server.service.response.ResponseCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.response.ResponseParseBaseWordList;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslateApi;

@Service
public class TranslationServiceYandexImpl implements TranslationService {

	@Autowired
	private MeaningWordDAO meaningWordDAO;

	@Override
	public ResponseParseBaseWordList parseBaseWordList(List<BaseWordDTO> baseWordList) {
		if (baseWordList == null || baseWordList.isEmpty()) {
			throw new IllegalArgumentException("Request word list cannot be null or empty.");
		}
		ResponseParseBaseWordList resp = new ResponseParseBaseWordList();

		List<MeaningWordDTO> meaningWordDTOList = null;
		List<BaseWordDTO> unavailableWordDTOList = null;

		for (BaseWordDTO baseWord : baseWordList) {
			try {
				MeaningWord meaningWord = meaningWordDAO.findByName(baseWord.getWord(), baseWord.getLang().getShortName());
				if (meaningWordDTOList == null) {
					meaningWordDTOList = new ArrayList<>();
				}
				meaningWordDTOList.add(MeaningWordMapper.modelToDto(meaningWord));
			} catch (NoResultException e) {
				if (unavailableWordDTOList == null) {
					unavailableWordDTOList = new ArrayList<>();
				}
				unavailableWordDTOList.add(baseWord);
			}

		}
		resp.setMeaningWordList(meaningWordDTOList);
		resp.setUnavailableWordList(unavailableWordDTOList);
		return resp;
	}

	@Override
	public ResponseCheckUnavaibleWordsForMeaninglesInLocal checkUnavailableWordsInLocal(List<BaseWordDTO> unavailableWordList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseSearchInTranslateApi searchInTranslateApi(List<BaseWordDTO> unavailableWordList) {
		// TODO Auto-generated method stub
		return null;
	}

}
