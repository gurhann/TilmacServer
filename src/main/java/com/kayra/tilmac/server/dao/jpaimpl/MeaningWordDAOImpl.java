package com.kayra.tilmac.server.dao.jpaimpl;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.kayra.tilmac.server.dao.MeaningWordDAO;
import com.kayra.tilmac.server.model.MeaningWord;

@Repository
public class MeaningWordDAOImpl extends BaseDAOImpl<MeaningWord> implements MeaningWordDAO {

	@Override
	public MeaningWord findByName(String text, String sourceLang, String targetLang) {
		Query query = em.createNamedQuery(MeaningWord.FIND_BY_NAME);
		query.setParameter("word", text);
		query.setParameter("sourceLang", sourceLang);
		query.setParameter("targetLang", targetLang);
		return (MeaningWord) query.getSingleResult();
	}

	@Override
	public MeaningWord findByNameWithoutTargetLang(String text, String sourceLang) {
		Query query = em.createNamedQuery(MeaningWord.FIND_BY_NAME_WITHOUT_TARGET_LANG);
		query.setParameter("word", text);
		query.setParameter("sourceLang", sourceLang);
		return (MeaningWord) query.getSingleResult();
	}

	@Transactional
	@Override
	public void batchAdd(List<MeaningWord> list) {
		for (MeaningWord meaningWord : list) {
			for (int i = 0; i < meaningWord.getTargetWordList().size(); i++) {

				if (!em.contains(meaningWord.getTargetWordList().get(i))) {
					try {
						MeaningWord findByNameWithoutTargetLang = findByNameWithoutTargetLang(meaningWord.getTargetWordList().get(i).getWord(),
								meaningWord.getTargetWordList().get(i).getLang().getShortName());
						meaningWord.getTargetWordList().set(i, findByNameWithoutTargetLang);
					} catch (Exception e) {
					}
				}
			}
		}
		super.batchAdd(list);
	}
}
