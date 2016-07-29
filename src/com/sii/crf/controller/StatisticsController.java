package com.sii.crf.controller;

import java.util.List;

import com.sii.crf.model.Sentence;
import com.sii.crf.mongodb.dao.SentenceDAO;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;

public class StatisticsController {
	
	public static void showStatistics() {
		SentenceDAO dao = new SentenceDAOImplementation();
		List<Sentence> sentences = dao.findAll();
		StatisticsBasic stats = new StatisticsBasic(sentences);
		System.out.println("----------------- SHOW STATISTICS ----------------\n");
		System.out.println(stats.sentencesLength());
		System.out.println(stats.showCategories());
	}
	
}
