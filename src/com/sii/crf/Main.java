package com.sii.crf;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.sii.crf.controller.InsertController;
import com.sii.crf.controller.OutputController;
import com.sii.crf.controller.StatisticsController;
import com.sii.crf.mallet.EvaluateClassifier;
import com.sii.crf.mallet.ImportData;
import com.sii.crf.model.Sentence;
import com.sii.crf.mongodb.dao.SentenceDAO;
import com.sii.crf.mongodb.dao.SentenceDAOImplementation;
import com.sii.crf.writer.FileIO;

import cc.mallet.types.InstanceList;


public class Main {
	
	final static String DATA_DIR = "/Users/luca/Desktop/data/";
	final static int max_reviews = 25;
	
	public static void main(String[] args) throws IOException {
		
		
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
        EvaluateClassifier.evaluateDataClassify(instances,num);
        
        /*  Fa il retrieve delle review corrispondenti al modello indicato con il codice asin
         *  e mostra l'output; avviene creazione dell'evaluation model e classificazione.
         */
        
        OutputController o = new OutputController("B01GFYP8II");
		o.start();
        
	}
	
}
