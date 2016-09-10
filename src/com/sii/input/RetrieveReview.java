package com.sii.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RetrieveReview {
	
	public static List<String> retrieve(String url, int threshold) throws IOException{
		List<String> reviews = new ArrayList<String>();
		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
		String cssQuery1 ="div.style";
		//String cssQuery2 ="a[class=\"a-link-emphasis a-text-bold\"]";
		String link2reviews = doc.select(cssQuery1); //.select(cssQuery2).get(0).attr("href");
		System.out.println(link2reviews);
		doc = Jsoup.connect(link2reviews).userAgent("Mozilla").get();
		String cssQuery3 ="span[class=\"a-size-base review-text\"]";
		Elements el_reviews = doc.select(cssQuery3);
		String text = "";
		for ( Element el :el_reviews){
			text = el.text();
			reviews.add(text);
			System.out.println(text +"\n"); 
		}
		
		
		return reviews;
	}

}
