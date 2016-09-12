package com.sii.crf.controller;

import java.util.ArrayList;
import java.util.List;

import com.sii.crf.model.Sentence;
import com.sii.crf.mongodb.dao.SentenceDAO;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;
import com.sii.crf.nlpclient.Labelling;
import com.sii.crf.nlpclient.NLPClient;
import com.sii.crf.parser.ParserXML;

public class InsertController {
	
	public static void parseAndInsert() {
		List<Sentence> sentences = ParserXML.parseXMLFileToList();
		SentenceDAO dao = new SentenceDAOImplementation();
		for (Sentence sentence : sentences) {
			if (dao.insertPars(sentence)) {
				System.out.println(sentence.toString());
			}
		}
	}
	
	public static void NLPAndInsert() {
		SentenceDAO dao = new SentenceDAOImplementation();
		List<Sentence> sentences = dao.findAllPars();
		for (Sentence sentence : sentences) {
			Sentence currSentence = NLPClient.getNLPResults(sentence);
			if (dao.insert(currSentence)) {
				System.out.println(currSentence.toString());
			}
		}
	}
	
	public static void LabelInsert() {
		SentenceDAO dao = new SentenceDAOImplementation();
		List<Sentence> sentences = dao.findAll();
		List<Sentence> newSentences = new ArrayList<Sentence>();
		for (Sentence s: sentences) {
			Sentence tmp = Labelling.getLabel(s);
			dao.insertFinal(tmp);
		}
	}
	
	
}
