package com.sii.crf.controller;

import java.util.List;

import com.sii.crf.model.Sentence;
import com.sii.crf.mongodb.dao.SentenceDAO;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;
import com.sii.crf.parser.ParserXML;

public class ParsingAndInsertController {
	
	public static void parseAndInsert() {
		List<Sentence> sentences = ParserXML.parseXMLFileToList();
		SentenceDAO dao = new SentenceDAOImplementation();
		for (Sentence sentence : sentences) {
			if (dao.insert(sentence)) {
				System.out.println(sentence.toString());
			}
		}
	}
	
	public static void prova() {
		SentenceDAO dao = new SentenceDAOImplementation();
		List<Sentence> s = dao.findAll();
		for (Sentence x : s) {
			System.out.println(x.toString());
		}
	}
	
}
