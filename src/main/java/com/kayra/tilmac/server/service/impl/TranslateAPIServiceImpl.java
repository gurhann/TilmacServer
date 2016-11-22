package com.kayra.tilmac.server.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.kayra.tilmac.server.dto.BaseWordDTO;
import com.kayra.tilmac.server.dto.MeaningWordDTO;
import com.kayra.tilmac.server.dto.MeaninglessWordDTO;
import com.kayra.tilmac.server.exception.WordNotFoundInDictApiException;
import com.kayra.tilmac.server.exception.WordNotFoundInTranslateApiException;
import com.kayra.tilmac.server.exception.YandexApiConnectException;
import com.kayra.tilmac.server.service.TranslateAPIService;
import com.kayra.tilmac.server.service.request.RequestSearchInTranslateApi;
import com.kayra.tilmac.server.service.response.ResponseSearchInDictionary;
import com.kayra.tilmac.server.service.response.ResponseSearchInTranslate;

@Service
public class TranslateAPIServiceImpl implements TranslateAPIService {

	private final ObjectMapper mapper = new ObjectMapper();
	private final JsonFactory factory = mapper.getFactory();
	private final ResourceBundle rb = ResourceBundle.getBundle("yandex_key");

	@Override
	public ResponseSearchInDictionary searchInDictionary(RequestSearchInTranslateApi req) {
		if (req.getUnavailableWordList() == null || req.getUnavailableWordList().isEmpty()) {
			throw new IllegalArgumentException("Request word list can not be null or empty.");
		}

		ResponseSearchInDictionary resp = new ResponseSearchInDictionary();
		List<MeaningWordDTO> meaningWordList = null;
		List<BaseWordDTO> unavailableWordList = null;
		for (BaseWordDTO word : req.getUnavailableWordList()) {
			try {
				JsonNode result = getResultJSONFromApi(word.getWord(), req.getSourceLangCode(), req.getTargetLangCode(), ApiType.DICT);
				List<String> wordListFromDictJson = getWordListFromDictJson(word.getWord(), result, req.getTargetLangCode());
				MeaningWordDTO meaningWord = new MeaningWordDTO();
				meaningWord.setWord(word.getWord());
				List<BaseWordDTO> targetWordList = new ArrayList<>();
				for (String responseWordString : wordListFromDictJson) {
					BaseWordDTO targetWord = new BaseWordDTO();
					targetWord.setWord(responseWordString);
					targetWordList.add(targetWord);
				}
				if (meaningWordList == null) {
					meaningWordList = new ArrayList<>();
				}
				meaningWordList.add(meaningWord);
			} catch (YandexApiConnectException e) {
				System.err.println(e.getMessage());
			} catch (WordNotFoundInDictApiException e) {
				if (unavailableWordList == null) {
					unavailableWordList = new ArrayList<>();
				}
				unavailableWordList.add(word);
			}
		}
		resp.setMeaningWordList(meaningWordList);
		resp.setUnavailableWordList(unavailableWordList);
		return resp;
	}

	@Override
	public ResponseSearchInTranslate searchInTranslate(RequestSearchInTranslateApi req) {
		if (req.getUnavailableWordList() == null || req.getUnavailableWordList().isEmpty()) {
			throw new IllegalArgumentException("Request word list can not be null or empty.");
		}
		ResponseSearchInTranslate resp = new ResponseSearchInTranslate();
		List<MeaningWordDTO> meaningWordList = null;
		List<MeaninglessWordDTO> meaninglessWordList = null;
		for (BaseWordDTO word : req.getUnavailableWordList()) {
			try {
				JsonNode result = getResultJSONFromApi(word.getWord(), req.getSourceLangCode(), req.getTargetLangCode(), ApiType.TRANSLATE);
				List<String> wordListFromTranslateJson = getWordListFromTranslateJson(word.getWord(), result);
				MeaningWordDTO meaningWord = new MeaningWordDTO();
				meaningWord.setWord(word.getWord());
				List<BaseWordDTO> targetWordList = new ArrayList<>();
				for (String responseWordString : wordListFromTranslateJson) {
					BaseWordDTO targetWord = new BaseWordDTO();
					targetWord.setWord(responseWordString);
					targetWordList.add(targetWord);
				}
				if (meaningWordList == null) {
					meaningWordList = new ArrayList<>();
				}
				meaningWordList.add(meaningWord);
			} catch (YandexApiConnectException e) {
				System.err.println(e.getMessage());
			} catch (WordNotFoundInTranslateApiException e) {
				if (meaninglessWordList == null) {
					meaninglessWordList = new ArrayList<>();
				}
				MeaninglessWordDTO meaninglessWord = new MeaninglessWordDTO();
				meaninglessWord.setWord(word.getWord());
				meaninglessWordList.add(meaninglessWord);
			}
		}
		resp.setMeaningWordList(meaningWordList);
		resp.setMeaninglessWordList(meaninglessWordList);
		return resp;
	}

	private JsonNode getResultJSONFromApi(String word, String fromLang, String toLang, ApiType apiType) throws YandexApiConnectException {
		try {
			String surl = String.format(apiType.getCoreRequest(), rb.getString(apiType.getApiKey()), fromLang, toLang, word);
			URL url = new URL(surl);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			JsonNode node = mapper.readTree(factory.createParser(request.getInputStream()));
			return node;
		} catch (Exception e) {
			throw new YandexApiConnectException(e.getMessage());
		}
	}

	private List<String> getWordListFromDictJson(String word, JsonNode node, String targetLangCode) throws WordNotFoundInDictApiException {
		List<String> translationWordList = new ArrayList<>();
		JsonNode defArrayNode = node.get("def");

		if (defArrayNode.getNodeType() == JsonNodeType.ARRAY) {
			if (defArrayNode.size() == 0) {
				throw new WordNotFoundInDictApiException(word);
			}
			for (JsonNode defNode : defArrayNode) {
				JsonNode translationArrayNode = defNode.get(targetLangCode);
				if (translationArrayNode.getNodeType() == JsonNodeType.ARRAY) {
					for (JsonNode translationWordNode : translationArrayNode) {
						translationWordList.add(translationWordNode.get("text").asText().toLowerCase());
					}
				}
			}
		}
		return translationWordList;
	}

	private List<String> getWordListFromTranslateJson(String word, JsonNode node) throws WordNotFoundInTranslateApiException {
		List<String> translationWordList = new ArrayList<>();
		JsonNode translationArrayNode = node.get("text");
		if (translationArrayNode.getNodeType() == JsonNodeType.ARRAY) {
			if (translationArrayNode.size() == 0 || (translationArrayNode.size() == 1 && translationArrayNode.get(0).asText().equals(word))) {
				throw new WordNotFoundInTranslateApiException(word);
			}
			for (int i = 0; i < translationArrayNode.size(); i++) {
				translationWordList.add(translationArrayNode.get(i).asText());
			}
		}
		return translationWordList;
	}

	private enum ApiType {
		DICT("dict.key", "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=%s&lang=%s-%s&text=%s"), TRANSLATE("translate.key",
				"https://translate.yandex.net/api/v1.5/tr.json/translate?key=%s&lang=%s-%s&text=%s");

		private String apiKey;
		private String coreRequest;

		private ApiType(String apiKey, String coreRequest) {
			this.apiKey = apiKey;
			this.coreRequest = coreRequest;
		}

		public String getApiKey() {
			return apiKey;
		}

		public String getCoreRequest() {
			return coreRequest;
		}
	}
}
