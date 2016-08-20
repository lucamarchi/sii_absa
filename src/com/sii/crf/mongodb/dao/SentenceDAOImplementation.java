package com.sii.crf.mongodb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.sii.crf.model.Dependency;
import com.sii.crf.model.Opinion;
import com.sii.crf.model.Sentence;
import com.sii.crf.model.Token;
import com.sii.crf.mongodb.DataSource;

public class SentenceDAOImplementation implements SentenceDAO {
	
	private final static String DB_COLLECTION_NAME1 = "trainSentencesT1";
	private final static String DB_COLLECTION_NAME2 = "trainSentencesNLP";
	
	public List<Sentence> findAllPars() {
		MongoDatabase db = DataSource.getInstance().getDb();
		List<Sentence> sentences = new ArrayList<Sentence>();
		List<Document> documents = db.getCollection(DB_COLLECTION_NAME1).find().into(new ArrayList<Document>());
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

	public boolean insertPars(Sentence sentence) {
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
		try{
			db.getCollection(DB_COLLECTION_NAME1).insertOne(document);
		} catch(Exception e) {
			e.printStackTrace();
			check = false;
		}
		return check;
	}

	public List<Sentence> findAll() {
		MongoDatabase db = DataSource.getInstance().getDb();
		List<Sentence> sentences = new ArrayList<Sentence>();
		List<Document> documents = db.getCollection(DB_COLLECTION_NAME2).find().into(new ArrayList<Document>());
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
				List<Token> tokens = new ArrayList<Token>();
				List<Document> documentsToken = (List<Document>) currSentence.get("tokens");
				for (Document currToken : documentsToken) {
					Token token = new Token();
					token.setIndex(currToken.getInteger("index"));
					token.setWord(currToken.getString("word"));
					token.setIndex(currToken.getInteger("index"));
					token.setLemma(currToken.getString("lemma"));
					token.setCharacterOffsetBegin(currToken.getInteger("characterOffsetBegin"));
					token.setCharacterOffsetEnd(currToken.getInteger("characterOffsetEnd"));
					token.setPos(currToken.getString("pos"));
					token.setNer(currToken.getString("ner"));
					token.setSpeaker(currToken.getString("speaker"));
					tokens.add(token);
				}
				List<Dependency> dependencies = new ArrayList<Dependency>();
				List<Document> documentsDependency = (List<Document>) currSentence.get("dependencies");
				for (Document currDependency : documentsDependency) {
					Dependency dependency = new Dependency();
					dependency.setRelation(currDependency.getString("relation"));
					dependency.setGov(currDependency.getString("gov"));
					dependency.setIndexGov(currDependency.getInteger("indexGov"));
					dependency.setDep(currDependency.getString("dep"));
					dependency.setIndexDep(currDependency.getInteger("indexDep"));
					dependencies.add(dependency);
				}
				sentence.setOpinions(opinions);
				sentence.setTokens(tokens);
				sentence.setDependencies(dependencies);
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
		List<Document> tokensDoc = new ArrayList<Document>();
		List<Token> tokens = sentence.getTokens();
		for (int i=0; i<tokens.size(); i++) {
			Document token = new Document();
			token.put("index", tokens.get(i).getIndex());
			token.put("word", tokens.get(i).getWord());
			token.put("lemma", tokens.get(i).getLemma());
			token.put("characterOffsetBegin", tokens.get(i).getCharacterOffsetBegin());
			token.put("characterOffsetEnd", tokens.get(i).getCharacterOffsetEnd());
			token.put("pos", tokens.get(i).getPos());
			token.put("ner", tokens.get(i).getNer());
			token.put("speaker", tokens.get(i).getSpeaker());
			tokensDoc.add(token);
		}
		List<Document> dependenciesDoc = new ArrayList<Document>();
		List<Dependency> dependencies = sentence.getDependecies();
		for (int i=0; i<dependencies.size(); i++) {
			Document dependency = new Document();
			dependency.put("relation", dependencies.get(i).getRelation());
			dependency.put("gov", dependencies.get(i).getGov());
			dependency.put("indexGov", dependencies.get(i).getIndexGov());
			dependency.put("dep", dependencies.get(i).getDep());
			dependency.put("indexDep", dependencies.get(i).getIndexDep());
			dependenciesDoc.add(dependency);
		}
		document.put("opinions", opinionsDoc);
		document.put("tokens", tokensDoc);
		document.put("dependencies", dependenciesDoc);
		try{
			db.getCollection(DB_COLLECTION_NAME2).insertOne(document);
		} catch(Exception e) {
			e.printStackTrace();
			check = false;
		}
		return check;
	}
	
}
