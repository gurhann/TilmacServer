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
import com.kayra.tilmac.server.service.TranslateAPIService;
import com.kayra.tilmac.server.service.TranslationService;
import com.kayra.tilmac.server.service.request.RequestCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.request.RequestParseBaseWordList;
import com.kayra.tilmac.server.service.request.RequestSearchInTranslateApi;
import com.kayra.tilmac.server.service.response.ResponseCheckUnavaibleWordsForMeaninglesInLocal;
import com.kayra.tilmac.server.service.response.ResponseParseBaseWordList;
import com.kayra.tilmac.server.service.response.ResponseSearchInDictionary;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslate;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslateApi;

@Service
public class TranslationServiceImpl implements TranslationService {

	@Autowired
	private TranslateAPIService translateAPIService;

	@Autowired
	private MeaningWordDAO meaningWordDAO;

	@Autowired
	private MeaninglessWordDAO meaninglessWordDAO;

	@Override
	public ResponseParseBaseWordList parseBaseWordList(RequestParseBaseWordList req) {
		if (req.getBaseWordList() == null || req.getBaseWordList().isEmpty()) {
			throw new IllegalArgumentException("Request word list cannot be null or empty.");
		}
		ResponseParseBaseWordList resp = new ResponseParseBaseWordList();

		List<MeaningWordDTO> meaningWordDTOList = null;
		List<BaseWordDTO> unavailableWordDTOList = null;

		for (BaseWordDTO baseWord : req.getBaseWordList()) {
			try {
				MeaningWord meaningWord = meaningWordDAO.findByName(baseWord.getWord(), req.getSourceLangCode(), req.getTargetLangCode());
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
	public ResponseCheckUnavaibleWordsForMeaninglesInLocal checkUnavailableWordsInLocal(RequestCheckUnavaibleWordsForMeaninglesInLocal req) {
		if (req.getUnavailableWordList() == null || req.getUnavailableWordList().isEmpty()) {
			throw new IllegalArgumentException("Request word list cannot be null or empty.");
		}
		ResponseCheckUnavaibleWordsForMeaninglesInLocal resp = new ResponseCheckUnavaibleWordsForMeaninglesInLocal();

		List<MeaninglessWordDTO> meaninglessWordDTOList = null;
		List<BaseWordDTO> unavailableWordDTOList = null;

		for (BaseWordDTO unavailableWord : req.getUnavailableWordList()) {
			try {
				MeaninglessWord meaninglessWord = meaninglessWordDAO.findByName(unavailableWord.getWord(), req.getSourceLangCode());
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
	public ResponseSearchInTranslateApi searchInTranslateApi(RequestSearchInTranslateApi req) {
		if (req.getUnavailableWordList() == null || req.getUnavailableWordList().isEmpty()) {
			throw new IllegalArgumentException("Request word list cannot be null or empty.");
		}
		ResponseSearchInTranslateApi resp = new ResponseSearchInTranslateApi();
		ResponseSearchInDictionary searchInDictionary = translateAPIService.searchInDictionary(req);
		resp.setMeaningWordList(searchInDictionary.getMeaningWordList());
		if (searchInDictionary.getUnavailableWordList() != null) {
			req.setUnavailableWordList(searchInDictionary.getUnavailableWordList());
			ResponseSearchInTranslate searchInTranslate = translateAPIService.searchInTranslate(req);
			resp.setMeaninglessWordList(searchInTranslate.getMeaninglessWordList());
			if (resp.getMeaningWordList() == null) {
				resp.setMeaningWordList(searchInTranslate.getMeaningWordList());
			} else {
				resp.getMeaningWordList().addAll(searchInTranslate.getMeaningWordList());
			}
		}
		return resp;
	}

}
