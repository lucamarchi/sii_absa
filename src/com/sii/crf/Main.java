package com.sii.crf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.sii.crf.controller.InsertController;
import com.sii.crf.controller.StatisticsController;
import com.sii.crf.mallet.Classify;
import com.sii.crf.mallet.EvaluateClassifier;
import com.sii.crf.mallet.ImportData;
import com.sii.crf.model.Opinion;
import com.sii.crf.model.Sentence;
import com.sii.crf.model.Token;
import com.sii.crf.mongodb.dao.SentenceDAO;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;
import com.sii.crf.nlpclient.Labelling;
import com.sii.crf.nlpclient.NLPClient;
import com.sii.crf.parser.ParserXML;
import com.sii.crf.writer.FileIO;

import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.DecisionTreeTrainer;
import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.classify.NaiveBayesTrainer;
import cc.mallet.classify.Trial;
import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.CharSequenceLowercase;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Labeling;
import cc.mallet.util.Randoms;


public class Main {
	
	final static String DATA_DIR = "/Users/luca/Desktop/data/";
	
	public static void main(String[] args) throws IOException {
		//InsertController.parseAndInsert();
		//InsertController.NLPAndInsert();
		SentenceDAO dao = new SentenceDAOImplementation();
		List<Sentence> sentences = dao.findAll();
		List<Token> tokens = new ArrayList<Token>();
		for (Sentence s : sentences) {
			tokens.addAll(Labelling.getLabel(s.getTokens()));
		}
		for (Token t : tokens) {
			if (t.getLabel().equals("FA") || t.getLabel().equals("FPA")) {
				System.out.println("---------------------------");
				System.out.println("Token -> "+t.getLemma() +", label -> "+t.getLabel());
				System.out.println("---------------------------");
			}
		}
		/*String[] texts = new String[]{"This laptop is amazing","The keyboard is not so comfortable"};
		Sentence s = new Sentence();
		s.setText("The keyboard is not so comfortable.");
		Sentence s1 = NLPClient.getNLPResults(s);
		System.out.println(s1.toString());*/
		/*
		SentenceDAO dao = new SentenceDAOImplementation();
		List<Sentence> sentences = dao.findAll();
		FileIO.createDirectoriesByPolarityLabel(sentences);
		ImportData importer = new ImportData();
        InstanceList instances = importer.readDirectory(new File("/Users/luca/Desktop/data"));
        */
        //classify1.printLabelings(new File("/Users/luca/Desktop/unlabeled.txt"));
		// -------------------- EXAMPLE OF IMPOREXAMPLE -------------- //
		/*ImportExample importer = new ImportExample();
        InstanceList instances = importer.readDirectory(new File("/Users/luca/Downloads/sample-data/web"));
        instances.save(new File("/Users/luca/Downloads/sample-data/web/output.txt"));
		Instance i = instances.get(1);
		System.out.println("--------- "+i.getName().toString()+"--------------");
			System.out.println(i.getLabeling().toString());
			System.out.println("_________________________");
		*/
        // ------------------------ SCRITTURA FILE UNO PER RIGA, ABBOZZO DI MODEL PER CRF ----------------------
		/*SentenceDAO dao = new SentenceDAOImplementation();
		List<Sentence> sentences = dao.findAll();
		sentences.size();
		for (Sentence s : sentences) {
			System.out.println(s.toString());
		}
		File tmp = new File(DATA_DIR);
		tmp.mkdirs();
		File file = new File(DATA_DIR, "sample.txt");
		file.createNewFile();
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter(file));
		for(int i=0; i<sentences.size(); i++) {
			Sentence currSentence = sentences.get(i);
			try {
					for (int j=0;j<currSentence.getTokens().size(); j++) {
						writer.write(currSentence.getTokens().get(j).getOriginalText()+"\t"+currSentence.getTokens().get(j).getPos());
						writer.newLine();
					}
					writer.newLine();
					
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		writer.close();
		*/
		/*List<Sentence> sentences = ParserXML.parseXMLFileToList();
		SentenceDAO dao = new SentenceDAOImplementation();
		for (Sentence sentence : sentences) {
			dao.insert(sentence);
		}
		File tmp = new File(DATA_DIR);
		tmp.mkdirs();
		SentenceDAO dao = new SentenceDAOImplementation();
		List<Sentence> sentences = dao.findAll();
		for(int i=0; i<sentences.size(); i++) {
			Sentence currSentence = sentences.get(i);
			String new_dir_path = DATA_DIR + currSentence.getOpinions().get(0).getPolarity();
			File new_dir = new File(new_dir_path);
			new_dir.mkdirs();
			File file = new File(new_dir.getAbsolutePath(), i + ".txt");
			try {
				if (file.createNewFile()) {
					BufferedWriter writer = null;
					writer = new BufferedWriter(new FileWriter(file));
		            writer.write(currSentence.getText());
					writer.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
	
}
