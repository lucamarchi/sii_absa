package com.sii.crf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.sii.crf.model.Opinion;
import com.sii.crf.model.Sentence;
import com.sii.crf.mongodb.dao.SentenceDAO;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;

public class StatisticsBasic {

	private List<Sentence> sentences;
	
	public StatisticsBasic(List<Sentence> sentences) {
		this.sentences = sentences;
	}
	
	public String sentencesLength() {
		return "Sentences length in db: " + this.sentences.size() + "\n";
	}
	
	public String showCategories() {
		String description = "Category alphabetic number: \n";
		Map<String,Integer> categories = new TreeMap<String,Integer>();
		for (Sentence sentence : this.sentences) {
			List<Opinion> opinions = sentence.getOpinions();
			for (Opinion opinion : opinions) {
				if (categories.containsKey(opinion.getCategory())) {
					Integer count = categories.get(opinion.getCategory())+1;
					categories.put(opinion.getCategory(), count);
				} else {
					categories.put(opinion.getCategory(), new Integer(1));
				}
			}
		}
		for (Map.Entry entry : categories.entrySet()) {
			description += entry.getKey() + " -> " + entry.getValue() +"\n";
		}
		return description;
	}

}
