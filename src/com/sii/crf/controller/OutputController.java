package com.sii.crf.controller;

import java.io.File;
import java.util.List;

import com.sii.crf.mallet.Classify;
import com.sii.crf.mallet.ImportData;
import com.sii.crf.writer.FileIO;

import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.types.InstanceList;

public class OutputController {
	
	private final static String DIR = "/Users/luca/Desktop/data";
	
	public static void getPolarityReviews(String asin) {
		List<String> reviews = ReviewsController.getReviewsByAsin(asin);
		ImportData importer = new ImportData();
        InstanceList instances = importer.readDirectory(new File(DIR));
        ClassifierTrainer maxentTrainer = new MaxEntTrainer();
        Classify classifier = new Classify(instances, maxentTrainer);
        for (int i=0; i<reviews.size(); i++) {
        	System.out.println("-------------------- reviews "+ i + "--------------");
        	System.out.println(reviews.get(i));
        	File ne = FileIO.createFileTxt(reviews.get(i));
        	try {
        		classifier.printLabelings(ne);
        	} catch (Exception e) {
        		//TODO
        	}
        }
	}
	
}
