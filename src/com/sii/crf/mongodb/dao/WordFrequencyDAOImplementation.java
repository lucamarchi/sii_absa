package com.sii.crf.mongodb.dao;

import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import com.sii.crf.model.Sentence;
import com.sii.crf.mongodb.DataSource;

public class WordFrequencyDAOImplementation implements SentenceDAO {

	private final static String DB_COLLECTION_NAME = "allWORD_occurrenciesT1";
	
	@Override
	public List<Sentence> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Sentence sentence) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public boolean insert(String word, int occurrencies){
		MongoDatabase db = DataSource.getInstance().getDb();
		Document document = new Document();
		document.put("word", word);
		document.put("#times", occurrencies);
		document.put("category", "all");
		boolean check = true;
		try{
			db.getCollection(DB_COLLECTION_NAME).insertOne(document);
		} catch(Exception e) {
			e.printStackTrace();
			check = false;
		} 
		System.out.println(check);
		return check;

	}
	
	public boolean insert(String word, int occurrencies, String category){
		MongoDatabase db = DataSource.getInstance().getDb();
		Document document = new Document();
		document.put("word", word);
		document.put("#times", occurrencies);
		document.put("category", category);
		boolean check = true;
		try{
			db.getCollection(DB_COLLECTION_NAME).insertOne(document);
		} catch(Exception e) {
			e.printStackTrace();
			check = false;
		} 
		System.out.println(check);
		return check;

	}
	
	private String capital(String s){
		char start  = s.charAt(0);
		start=  Character.toUpperCase(start);
		return start+s.substring(1);
	}
	
	public List<Document>find(String word, String category){
		String lower_word = word.toLowerCase();
		String Capital_word= capital(word);
		String UPPER_word=word.toUpperCase();
		MongoDatabase db = DataSource.getInstance().getDb();
		List<Document> in_category = db.getCollection(DB_COLLECTION_NAME).find(
				new Document(
						"$or", asList(new Document("word",word),new Document("word",lower_word), new Document("word", Capital_word), new Document("word", UPPER_word)))
				.append("category",category))
				.into(new ArrayList<Document>());
		System.out.println(in_category.size());
		return in_category;
	}
	
	public List<Document>find( String category){
		
		MongoDatabase db = DataSource.getInstance().getDb();
		List<Document> in_category = db.getCollection(DB_COLLECTION_NAME).find(
				new Document("category",category)).into(new ArrayList<Document>());
		
		return in_category;
	}
	
	

}
