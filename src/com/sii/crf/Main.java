package com.sii.crf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.sii.Utility.Filewriter;
import com.sii.Utility.Frequency;
import com.sii.crf.controller.ParsingAndInsertController;
import com.sii.crf.controller.StatisticsController;
import com.sii.crf.parser.ParserXML;
import com.sii.crf.model.Opinion;
import com.sii.crf.model.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Main {

	public static void main(String[] args) {
		//StatisticsController.showStatistics();
		List<Sentence> sentences =ParserXML.parseXMLFileToList();
		//List<Sentence> sentencesCategory =ParserXML.parseXMLFileToListForCategory("LAPTOP#USABILITY");
		List<Sentence> sentencesForCategory= new ArrayList<Sentence>()  ;
		List<Opinion> tmpOpinions= new ArrayList<Opinion>();
		for (Sentence s : sentences ){
			tmpOpinions = s.getOpinions();
			for (Opinion o: tmpOpinions){
				if (o.getCategory().equals((Object)"GRAPHICS#GENERAL"))
					sentencesForCategory.add(s);
			}
		}
		//TreeMap<String,Integer> NNJJ =Frequency.frequency(sentencesForCategory);
		TreeMap<String,Integer> categories = Frequency.frequencyCategory(sentences);
		Filewriter.writeOnFile(categories, "occurenciescategories.txt");
	
	}

}
