package com.sii.crf.mongodb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.sii.crf.mongodb.DataSource;
import com.sii.input.Laptop;

public class LaptopDAO {

	
	private final static String DB_COLLECTION_NAME= "amazon_links";
	
	public static boolean insert(Laptop lap){
		boolean inserted= true;
		MongoDatabase db = DataSource.getInstance().getDb();
		Document doc =new Document();
		doc.put("name", lap.getName());
		doc.put("link", lap.getLink());
		doc.put("model_number", lap.getModel_number());
		try{
			db.getCollection(DB_COLLECTION_NAME).insertOne(doc);
		} catch(Exception e) {
			e.printStackTrace();
			inserted = false;
		}
		//DataSource.getInstance().closeDb();
		return inserted;
	}
	
	public static List<Laptop> findAll(){
		MongoDatabase db = DataSource.getInstance().getDb();
		List<Laptop> laptops=new ArrayList<Laptop>();
		Document regQuery= new Document();
		regQuery.append("$regex", "^https://www.amazon.com");
		Document query = new Document();
		query.append("link", regQuery);
		//Pattern p = Pattern.compile("^https://www.amazon.com");
		ArrayList<Document> docs = db.getCollection(DB_COLLECTION_NAME).find(query).into(new ArrayList<Document>());
		
		for (Document doc : docs){
			Laptop lap = new Laptop();
			lap.setName(doc.getString("name"));
			lap.setLink(doc.getString("link"));
			lap.setModel_number(doc.getString("model_number"));
			lap.setAsin(doc.getString("asin"));
			laptops.add(lap);
			System.out.println(lap.toString());
		}
		return laptops;
	}

	public static void update(Laptop lap) { 
		// aggioran il model number di chi ha lo stesso link, nel caso ci fossero doppioni e l ASIN
		MongoDatabase db = DataSource.getInstance().getDb();
		Document filter = new Document("link", lap.getLink());
		Document update = new Document("$set", new Document("model_number", lap.getModel_number()).append("asin", lap.getAsin()));
		UpdateResult docsUpdated = db.getCollection(DB_COLLECTION_NAME).updateMany(filter, update);
		System.out.println(docsUpdated.getModifiedCount() );
	}
}
