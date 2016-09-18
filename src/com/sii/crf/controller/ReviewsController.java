package com.sii.crf.controller;

import java.io.IOException;
import java.util.List;

import com.sii.crf.mongodb.dao.LaptopDAO;
import com.sii.input.Laptop;
import com.sii.input.RetrieveReview;

public class ReviewsController {
	
	final static int max_reviews = 25;
	
	public static List<String> getReviewsByAsin(String asin) {
		Laptop lap = retrieveLaptop(asin);
		List<String> reviews = null;
		try {
			reviews = retrieveReviews(lap.getLink());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reviews;
	}
	
	private static Laptop retrieveLaptop(String asin) {
		Laptop lap = LaptopDAO.findASIN(asin);
		if (lap != null){
			System.out.println("model retrieved");
			//RetrieveReview.retrieve(lap.getLink(), max_reviews);
		}
		return lap;	
	}
	
	private static List<String> retrieveReviews(String url) throws IOException, InterruptedException{
		List<String> reviews = RetrieveReview.retrieve(url, max_reviews);
		if (reviews.isEmpty())
			System.out.println("Doesn' t exist any review for this item !  :/");
		return reviews;
	}
	
}
