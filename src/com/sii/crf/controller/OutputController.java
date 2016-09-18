package com.sii.crf.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sii.crf.mallet.Classify;
import com.sii.crf.mallet.ImportData;
import com.sii.crf.mallet.TrainCRF;
import com.sii.crf.writer.FileIO;

import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.types.InstanceList;

public class OutputController {
	
	private final static String DIR1 = "/Users/luca/Desktop/data/label/";
	private String asin;
	private List<String> reviews;
	
	public OutputController(String asin) {
		this.asin = asin;
		this.reviews = new ArrayList<String>();
		//reviews.add("This computer sucks! it is not so good like I was imagine. There are a lot of stuff that are shit.");
		//reviews.add("The battery of my pc could be better");
		List<String> reviewsDownload = ReviewsController.getReviewsByAsin(asin);
		for (String s : reviewsDownload) {
			if (!s.contains("http")) {
				s = s.replaceAll("\n","").trim();
				s = s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
				s.trim();
				this.reviews.add(s);
			} 
		}
	}
	
	public void getPolarityReviews(String asin) {
		ImportData importer = new ImportData();
        InstanceList instances = importer.readDirectory(new File(DIR1));
        ClassifierTrainer maxentTrainer = new MaxEntTrainer();
        Classify classifier = new Classify(instances, maxentTrainer);
        for (int i=0; i<this.reviews.size(); i++) {
        	System.out.println("-------------------- reviews "+ i + "--------------");
        	System.out.println(this.reviews.get(i));
        	File ne = FileIO.createFileTxt(this.reviews.get(i));
        	try {
        		classifier.printLabelings(ne);
        	} catch (Exception e) {
        		//TODO
        	}
        }
	}
	
	
	public void start() throws IOException {
		ImportData importer = new ImportData();
        InstanceList instances = importer.readDirectory(new File(DIR1));
        ClassifierTrainer maxentTrainer = new MaxEntTrainer();
        Classify classifier = new Classify(instances, maxentTrainer);
		ImportData data = new ImportData();
		File n = FileIO.createEvaluationModel();
		File i = ClassifyController.createInput(this.reviews);
		TrainCRF crf = new TrainCRF(n.getAbsolutePath(), i.getAbsolutePath());
		File f = new File("/Users/luca/Desktop/data/output.txtnull110.viterbi");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
		    String text = null;
		    int count = -1;
		    while ((text = reader.readLine()) != null) {
		    	if (!text.contains("Viterbi")) {
			    	if (text.contains("FH")) {
			    		String[] spl = text.split(" ");
			    		for (String s : spl) {
			    			if (!s.contains("@") && !s.contains("=") && !s.contains("-") && !s.contains("/") && !s.contains("DATE") && !s.contains("NN") && !s.contains("O")) {
			    				System.out.println(s);
			    				System.out.println();
			    			}
			    		}
			    	}
		    	} else {
		    		count++;
		    		String rev = this.reviews.get(count);
		    		System.out.println(rev);
		    		File ne = FileIO.createFileTxt(rev);
		    		classifier.printLabelings(ne);
		    		
		    	}
		    }
		} catch (Exception e) {
		
		}
	}
	
}
