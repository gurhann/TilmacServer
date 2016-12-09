package com.kayra.tilmac.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.kayra.tilmac.server.dao.LanguageDAO;
import com.kayra.tilmac.server.dao.MeaningWordDAO;
import com.kayra.tilmac.server.dao.MeaninglessWordDAO;
import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.LanguageDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;
import com.kayra.tilmac.server.exception.RequestWordListIsEmptyException;
import com.kayra.tilmac.server.mapper.LanguageMapper;
import com.kayra.tilmac.server.mapper.MeaningWordMapper;
import com.kayra.tilmac.server.mapper.MeaninglessWordMapper;
import com.kayra.tilmac.server.model.Language;
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

	@Autowired
	private LanguageDAO langDAO;

	@Override
	public ResponseParseBaseWordList parseBaseWordList(RequestParseBaseWordList req) throws RequestWordListIsEmptyException {
		if (req.getBaseWordList() == null || req.getBaseWordList().isEmpty()) {
			throw new RequestWordListIsEmptyException();
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
			} catch (EmptyResultDataAccessException e) {
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
	public ResponseCheckUnavaibleWordsForMeaninglesInLocal checkUnavailableWordsInLocal(RequestCheckUnavaibleWordsForMeaninglesInLocal req) throws RequestWordListIsEmptyException {
		if (req.getUnavailableWordList() == null || req.getUnavailableWordList().isEmpty()) {
			throw new RequestWordListIsEmptyException();
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
			} catch (EmptyResultDataAccessException e) {
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
	public ResponseSearchInTranslateApi searchInTranslateApi(RequestSearchInTranslateApi req) throws RequestWordListIsEmptyException {
		if (req.getUnavailableWordList() == null || req.getUnavailableWordList().isEmpty()) {
			throw new RequestWordListIsEmptyException();
		}
		ResponseSearchInTranslateApi resp = new ResponseSearchInTranslateApi();
		ResponseSearchInDictionary searchInDictionary = translateAPIService.searchInDictionary(req);
		saveMeaningWords(searchInDictionary.getMeaningWordList(), req.getSourceLangCode(), req.getTargetLangCode());
		resp.setMeaningWordList(searchInDictionary.getMeaningWordList());
		if (searchInDictionary.getUnavailableWordList() != null) {
			req.setUnavailableWordList(searchInDictionary.getUnavailableWordList());
			ResponseSearchInTranslate searchInTranslate = translateAPIService.searchInTranslate(req);
			saveMeaninglessWords(searchInTranslate.getMeaninglessWordList(), req.getSourceLangCode());
			saveMeaningWords(searchInDictionary.getMeaningWordList(), req.getSourceLangCode(), req.getTargetLangCode());
			resp.setMeaninglessWordList(searchInTranslate.getMeaninglessWordList());
			if (resp.getMeaningWordList() == null) {
				resp.setMeaningWordList(searchInTranslate.getMeaningWordList());
			} else {
				resp.getMeaningWordList().addAll(searchInTranslate.getMeaningWordList());
			}
		}
		return resp;
	}

	private void saveMeaningWords(List<MeaningWordDTO> meaningWordList, String sourceLang, String targetLang) {

		if (meaningWordList != null) {
			setLangMeaningWord(meaningWordList, sourceLang, targetLang);
			meaningWordDAO.batchAdd(MeaningWordMapper.dtoToModelList(meaningWordList));
		}
	}

	private void saveMeaninglessWords(List<MeaninglessWordDTO> meaninglessWordList, String sourceLang) {
		if (meaninglessWordList != null) {
			setLangMeaninglessWord(meaninglessWordList, sourceLang);
			meaninglessWordDAO.batchAdd(MeaninglessWordMapper.dtoToModelList(meaninglessWordList));
		}
	}

	private void setLangMeaningWord(List<MeaningWordDTO> meaningWordList, String sourceLang, String targetLang) {
		LanguageDTO sourceLangDTO = LanguageMapper.modelToDto(langDAO.findByShortName(sourceLang));
		LanguageDTO targetLangDTO = LanguageMapper.modelToDto(langDAO.findByShortName(targetLang));
		for (MeaningWordDTO meaningWord : meaningWordList) {
			meaningWord.setLang(sourceLangDTO);
			for (BaseWordDTO targetWord : meaningWord.getTargetWordList()) {
				targetWord.setLang(targetLangDTO);
			}
		}
	}

	private void setLangMeaninglessWord(List<MeaninglessWordDTO> meaninglessWordList, String sourceLang) {
		LanguageDTO sourceLangDTO = LanguageMapper.modelToDto(langDAO.findByShortName(sourceLang));
		for (MeaninglessWordDTO meaninglessWord : meaninglessWordList) {
			meaninglessWord.setLang(sourceLangDTO);
		}
	}

}
