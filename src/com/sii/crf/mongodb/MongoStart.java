package com.sii.crf.mongodb;

import java.util.Collection;
import java.util.List;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sii.crf.model.Sentence;
import com.sii.crf.mongodb.dao.POSTaggedSentenceDAOImplementation;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;
import com.sii.crf.parser.ParserXML;


//

public class MongoStart {

	public static void start(){
		List<Sentence> sentences =ParserXML.parseXMLFileToList();
		SentenceDAOImplementation dao = new SentenceDAOImplementation();
		for (Sentence s : sentences ){
			boolean insert = false;
			insert=dao.insert(s);
			System.out.println(insert);
		}

	}
	
	public static void startPosTagging(Collection<Sentence> sentences){ 
		POSTaggedSentenceDAOImplementation dao = new POSTaggedSentenceDAOImplementation();
		for(Sentence s: sentences){
			boolean insert = false;
			insert = dao.insert(s);
			System.out.println(insert);
		}
			
	}
}
