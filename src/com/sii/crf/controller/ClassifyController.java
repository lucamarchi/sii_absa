package com.sii.crf.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sii.crf.model.Sentence;
import com.sii.crf.nlpclient.NLPClient;
import com.sii.crf.writer.FileIO;

public class ClassifyController {
	
	public static File createInput(List<String> sentencesString) {
		List<Sentence> sentences = new ArrayList<Sentence>();
		for (String s : sentencesString) {
			Sentence tmp = new Sentence();
			tmp.setText(s);
			sentences.add(tmp);
		}
		List<Sentence> newSentences = new ArrayList<Sentence>();
		for (Sentence s : sentences) {
			Sentence tmp = NLPClient.getNLPResults(s);
			newSentences.add(tmp);
		}
		File file = FileIO.createInputModel(newSentences);
		return file;
	}
	
}
