package com.sii.crf.mongodb.dao;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;
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
}
