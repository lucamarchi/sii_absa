package com.sii.crf.mongodb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.sii.crf.model.Opinion;
import com.sii.crf.model.Sentence;
import com.sii.crf.mongodb.DataSource;

public class SentenceDAOImplementation implements SentenceDAO {
	
	private final static String DB_COLLECTION_NAME = "trainSentencesT1";
	
	public List<Sentence> findAll() {
		MongoDatabase db = DataSource.getInstance().getDb();
		List<Sentence> sentences = new ArrayList<Sentence>();
		List<Document> documents = db.getCollection(DB_COLLECTION_NAME).find().into(new ArrayList<Document>());
		if (documents.size() != 0) {
			for (Document currSentence : documents) {
				Sentence sentence = new Sentence();
				List<Opinion> opinions = new ArrayList<Opinion>();
				sentence.setText(currSentence.getString("text"));
				List<Document> documentsOpinion = (List<Document>) currSentence.get("opinions");
				for (Document currOpinion : documentsOpinion) {
					Opinion opinion = new Opinion();
					opinion.setCategory(currOpinion.getString("category"));
					opinion.setPolarity(currOpinion.getString("polarity"));
					opinions.add(opinion);
				}
				sentence.setOpinions(opinions);
				sentences.add(sentence);
			}
		}
		return sentences;
	}

	public boolean insert(Sentence sentence) {
		MongoDatabase db = DataSource.getInstance().getDb();
		boolean check = true;
		String text = sentence.getText();
		Document document = new Document();
		document.put("text", text);
		List<Document> opinionsDoc = new ArrayList<Document>();
		List<Opinion> opinions = sentence.getOpinions();
		for (int i=0; i<opinions.size(); i++) {
			Document opinion = new Document();
			opinion.put("category", opinions.get(i).getCategory());
			opinion.put("polarity", opinions.get(i).getPolarity());
			opinionsDoc.add(opinion);
		}
		document.put("opinions", opinionsDoc);
	/*	try{
			db.getCollection(DB_COLLECTION_NAME).insertOne(document);
		} catch(Exception e) {
			e.printStackTrace();
			check = false;
		} */
		return check;
	}
	
}
