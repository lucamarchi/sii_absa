package com.sii.crf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.bson.Document;

import com.sii.POSTagger.POSTagger;
import com.sii.Utility.Filewriter;
import com.sii.Utility.Frequency;
import com.sii.Utility.TF_IDF;
import com.sii.crf.controller.ParsingAndInsertController;
import com.sii.crf.controller.StatisticsController;
import com.sii.crf.parser.ParserXML;
import com.sii.crf.model.Opinion;
import com.sii.crf.model.Sentence;
import com.sii.crf.mongodb.MongoStart;
import com.sii.crf.mongodb.dao.POSTaggedSentenceDAOImplementation;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;
import com.sii.crf.mongodb.dao.WordFrequencyDAOImplementation;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Main {

	public static void main(String[] args) {
		
		//SentenceDAOImplementation dao = new SentenceDAOImplementation();
		POSTaggedSentenceDAOImplementation tagged_dao = new POSTaggedSentenceDAOImplementation();
		List<Sentence> sentences = tagged_dao.findAll();
		
		double freq=0;
		String word;
		WordFrequencyDAOImplementation dao_frequency = new WordFrequencyDAOImplementation();
	
		Set<String> categories = Frequency.frequencyCategory(sentences).keySet();
		for (String category : categories){
			System.out.println("----------------"+ category);
			// for each category, all the NN&Jj that occurs
			List<Document> docCategory = dao_frequency.find(category);
			for (Document d : docCategory){
				word = d.getString("word");
				List<Document> docWordInCategory = dao_frequency.find(word,category);
				List<Document> docAllCategory = dao_frequency.find(word,"all");
				freq=TF_IDF.tf_idf(word, docAllCategory, docWordInCategory);
				
			}	
			
		}
	}
}
