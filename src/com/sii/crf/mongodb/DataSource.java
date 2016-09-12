package com.sii.crf.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DataSource {
	
	private final static String DB_NAME = "db_sii2";  //nel codice originale è db_sii
	private MongoClient mongoClient;
	private static DataSource instance = null;
	
	private DataSource() {
		this.mongoClient = new MongoClient();
	}
	
	public static synchronized DataSource getInstance() {
		if (instance == null) {
			instance = new DataSource();
		}
		return instance;
	}
	
	public MongoDatabase getDb() {
		MongoDatabase db = this.mongoClient.getDatabase(DB_NAME);
		return db;
	}
	
	public void closeDb() {
		DataSource.getInstance().mongoClient.close();
	}
	
}
