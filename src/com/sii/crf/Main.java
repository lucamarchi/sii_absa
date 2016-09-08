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
import com.sii.crf.model.Dependency;
import com.sii.crf.model.Opinion;
import com.sii.crf.model.Sentence;
import com.sii.crf.model.Token;
import com.sii.crf.mongodb.dao.SentenceDAO;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;
import com.sii.crf.nlpclient.Labelling;
import com.sii.crf.nlpclient.NLPClient;
import com.sii.crf.parser.ParserXML;
import com.sii.crf.writer.FileIO;
import com.sii.input.LinksModels;

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
		/*SentenceDAO dao = new SentenceDAOImplementation();
		List<Sentence> sentenceList = dao.findAll();
		int i = 0;
		for (Sentence s : sentenceList) {
			Sentence x = Labelling.getLabel(s);
			for (Token t : s.getTokens()) {
				if (t.getLabel().equals("FH")) {
					i++;
					System.out.println(x);
				}
				break;
			}
			System.out.println("Frasi con label: "+i+",su totale: "+sentenceList.size());
		} */
		String p = "https://www.amazon.com/s/ref=sr_pg_191/158-1671565-1841957?rh=n%3A172282%2Cn%3A%21493964%2Cn%3A541966%2Cn%3A13896617011%2Cn%3A565108%2Cn%3A13896615011&page=191&ie=UTF8&qid=1473356431&spIA=B01GKC5RSG,B01606M7VM,B01DOU2LPE,B01EBC0JI0,B016QY83MK,B01D0YWR9E,B01COWF3G2,B01AZUQFGC,B01IJ4H3JY,B01HM5UVEA,B01H4QE364,B00U7QWIGQ,B01GA54HAC,B017Y6C1L8,B01EGAQ3SW,B01B6ST8DY,B01HZ1G0VO,B017LQ9NMG,B01GRE7MXU,B01GSAA456,B00XZUCBUO,B01KAE0K5A,B01C71F19O,B00UTKZZRE,B01DIPNNBG,B01HXIDJYA,B01B1AJ1S4,B01EYZWA68,B01D0DT0GI,B01DTHWQIO,B01FISV7YG,B01AJ4CL08,B01I3UQJTE,B01FLAV0DO,B01C70QWMU,B01F2OSQUE,B00GT79B80,B01EXCTKHO,B00T7EXKLG,B01DUML0S0,"
				+ "B01AAY73YG,B01I3U9XFQ,B01LBZ26E0,B00FEJ07V4,B01F0ALBIO,B01LDF9I";
		LinksModels.collectLinks(p,192);
		
	
	}
	
	public static void prova(String[] args) throws IOException {
		
		
		/*	InsertController.parseAndInsert() prende le singole sentences dall'xml 
		 * 	e le salva nel db; il file xml si trova nel package parser, il db si chiama
		 * 	db_sii, la collezione trainSentencesT1.
		 */
		InsertController.parseAndInsert();
		
		
		/*	Mostra alcune informazioni sul dataset
		 */
		
		StatisticsController.showStatistics();
		
		
		/*	InsertController.NLPAndInsert() prende le singole sentences 
		 * 	dal db del comando prima, e prende i risultati del NLP salvando
		 * 	il tutto nella collezione trainSentencesNLP; NB: bisogna eseguire
		 * 	questo comando con il server NLP in esecuzione.
		 */
		InsertController.NLPAndInsert();
		
		
		/*	FileIO.createDirectoriesByPolarityLabel(sentences) dato un'array di 
		 * 	sentences crea le tre cartelle negative, neutral e positive 
		 * 	ognuna delle quali contentente tanti file quante sentences
		 * 	(una per file) con quella class target; NB: cambiare la path
		 * 	in FileIO delle directory. 
		 */
		
		SentenceDAO dao = new SentenceDAOImplementation();
		List<Sentence> sentences = dao.findAll();
		FileIO.createDirectoriesByPolarityLabel(sentences);
		
		
		/*	ImporterData prende come input le istanze nei file del passo precendente
		 * 	e le importa in Mallet.
		 */
		
		ImportData importer = new ImportData();
        InstanceList instances = importer.readDirectory(new File("/Users/luca/Desktop/data"));
        
        
        /*	Esegue per 10 volte tre classificatori (MaxEntr,NaiveBayes,DecTree) 
         * 	e visualizza i valori medi di accuracy e f1.
         */
        int num = 10;
        EvaluateClassifier.evaluateDataClassify(instances,10);
        
   // -------------------------------------------------------------------------------------------------------     
        // QUESTA PARTE SOTTO E' ZOZZUME CHE DEVO SISTEMA BENE
        
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
