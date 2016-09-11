package com.sii.crf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sii.crf.controller.InsertController;
import com.sii.crf.controller.StatisticsController;
import com.sii.crf.mallet.Classify;
import com.sii.crf.mallet.EvaluateClassifier;
import com.sii.crf.mallet.ImportData;
import com.sii.crf.model.Dependency;
import com.sii.crf.model.Opinion;
import com.sii.crf.model.Sentence;
import com.sii.crf.model.Token;
import com.sii.crf.mongodb.DataSource;
import com.sii.crf.mongodb.dao.LaptopDAO;
import com.sii.crf.mongodb.dao.SentenceDAO;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;
import com.sii.crf.nlpclient.Labelling;
import com.sii.crf.nlpclient.NLPClient;
import com.sii.crf.parser.ParserXML;
import com.sii.crf.writer.FileIO;
import com.sii.input.Laptop;
import com.sii.input.LinksModels;
import com.sii.input.RetrieveReview;

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
	final static int max_reviews = 25;
	
	public static void main(String[] args) throws IOException, InterruptedException {
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
		
	//retrieveReviews("https://www.amazon.com/Acer-Aspire-NVIDIA-Windows-E5-575G-53VG/dp/B01DT4A2R4/ref=lp_13896615011_1_1?s=pc&ie=UTF8&qid=1473630725&sr=1-1");
		LinksModels.collectModelIDs();
		DataSource.getInstance().closeDb();
	}
	
	public static Laptop retrieveLaptop(String asin) {
		Laptop lap = LaptopDAO.findASIN(asin);
		if (lap != null){
			System.out.println("model retrieved");
			//RetrieveReview.retrieve(lap.getLink(), max_reviews);
		}
		return lap;	
	}
	
	public static List<String> retrieveReviews(String url) throws IOException, InterruptedException{
		List<String> reviews = RetrieveReview.retrieve(url, max_reviews);
		if (reviews.isEmpty())
			System.out.println("Doesn' t exist any review for this item !  :/");
		return reviews;
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
