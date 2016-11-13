package com.kayra.tilmac.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kayra.tilmac.server.dao.MeaningWordDAO;
import com.kayra.tilmac.server.dao.MeaninglessWordDAO;
import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;
import com.kayra.tilmac.server.mapper.MeaningWordMapper;
import com.kayra.tilmac.server.mapper.MeaninglessWordMapper;
import com.kayra.tilmac.server.model.MeaningWord;
import com.kayra.tilmac.server.model.MeaninglessWord;
import com.kayra.tilmac.server.service.TranslationService;
import com.kayra.tilmac.server.service.response.ResponseCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.response.ResponseParseBaseWordList;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslateApi;

@Service
public class TranslationServiceYandexImpl implements TranslationService {

	@Autowired
	private MeaningWordDAO meaningWordDAO;

	@Autowired
	private MeaninglessWordDAO meaninglessWordDAO;

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
		if (unavailableWordList == null || unavailableWordList.isEmpty()) {
			throw new IllegalArgumentException("Request word list cannot be null or empty.");
		}
		ResponseCheckUnavaibleWordsForMeaninglesInLocal resp = new ResponseCheckUnavaibleWordsForMeaninglesInLocal();

		List<MeaninglessWordDTO> meaninglessWordDTOList = null;
		List<BaseWordDTO> unavailableWordDTOList = null;

		for (BaseWordDTO unavailableWord : unavailableWordList) {
			try {
				MeaninglessWord meaninglessWord = meaninglessWordDAO.findByName(unavailableWord.getWord(), unavailableWord.getLang().getShortName());
				if (meaninglessWordDTOList == null) {
					meaninglessWordDTOList = new ArrayList<>();
				}
				meaninglessWordDTOList.add(MeaninglessWordMapper.modelToDto(meaninglessWord));
			} catch (NoResultException e) {
				if (unavailableWordDTOList == null) {
					unavailableWordDTOList = new ArrayList<>();
				}
				unavailableWordDTOList.add(unavailableWord);
			}
		}
		resp.setMeaninglessWordList(meaninglessWordDTOList);
		resp.setUnavailableWordList(unavailableWordDTOList);
		return resp;
	}

	@Override
	public ResponseSearchInTranslateApi searchInTranslateApi(List<BaseWordDTO> unavailableWordList) {
		// TODO Auto-generated method stub
		return null;
	}

}
