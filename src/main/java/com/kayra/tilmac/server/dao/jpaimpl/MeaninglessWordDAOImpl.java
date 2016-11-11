package com.kayra.tilmac.server.dao.jpaimpl;

import org.springframework.stereotype.Repository;

import com.kayra.tilmac.server.dao.MeaninglessWordDAO;
import com.kayra.tilmac.server.model.MeaninglessWord;

@Repository
public class MeaninglessWordDAOImpl extends BaseDAOImpl<MeaninglessWord> implements MeaninglessWordDAO {

	@Override
	public MeaninglessWord findByName(String text, String langShortName) {
		// TODO Auto-generated method stub
		return null;
	}


}
