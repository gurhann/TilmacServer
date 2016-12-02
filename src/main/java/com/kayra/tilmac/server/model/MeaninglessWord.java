package com.kayra.tilmac.server.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name = "MeaninglessWord")
@Table(name = "meaningless_word")
@NamedQueries({ @NamedQuery(name = MeaninglessWord.FIND_BY_NAME, query = "select w from MeaninglessWord w where w.word=:word and w.lang.shortName=:langShortName") })
public class MeaninglessWord extends BaseWord {

	public static final String FIND_BY_NAME = "MeaninglessWord.findByName";

}
