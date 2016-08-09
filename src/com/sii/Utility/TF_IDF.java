package com.sii.Utility;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import org.bson.Document;

import com.sii.crf.parser.ParserXML;

public class TF_IDF {
	
	
	//calcola tf-idf per una parola rispetto ad una categoria.
	public static double tf_idf(String current_word, Collection<Document> docWordAllCategory, Collection<Document> docWordInCategory  ){
		
		double relative_occurrencies = 0.0 ;
		double total_occurrencies = 0.0;
		
		for(Document w : docWordInCategory){
			relative_occurrencies += w.getInteger("#times");
		}
		for(Document w : docWordAllCategory){
			total_occurrencies += w.getInteger("#times");
		}
		
		double result = relative_occurrencies/total_occurrencies;
		System.out.println(current_word +"   "+ result);
		System.out.println();
		return result;
	}

}
