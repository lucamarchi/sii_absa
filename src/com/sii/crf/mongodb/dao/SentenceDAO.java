package com.sii.crf.mongodb.dao;

import java.util.List;

import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.sii.crf.model.Sentence;

public interface SentenceDAO {
	public List<Sentence> findAllPars();
	public boolean insertPars(Sentence sentence);
	public List<Sentence> findAll();
	public boolean insert(Sentence sentence);
}
