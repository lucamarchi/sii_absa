package com.sii.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RetrieveReview {

	public static List<String> retrieve(String url, int threshold) throws IOException, InterruptedException{

		
		String ua2 = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)";
		String ua ="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/601.6.17 (KHTML, like Gecko)";
		List<String> reviews = new ArrayList<String>();
		Document doc = Jsoup.connect(url).userAgent(ua).get();
		int cont = 0;
		//TimeUnit.MILLISECONDS.sleep(200);
		String cssQuery2 ="a[class=\"a-link-emphasis a-text-bold\"]";
		Elements urls = doc.select(cssQuery2);
		String link2reviews = null;
		if(urls.isEmpty()){
			String cssQuery1 ="a:matches(^See all)";
			urls = doc.select(cssQuery1);
			if(!(urls.isEmpty()))
				link2reviews = urls.last().attr("abs:href"); 
		}
		else link2reviews = urls.first().attr("abs:href");
		if (!(link2reviews == null)){
			//link2reviews = urls.first().attr("abs:href");

			//
			System.out.println(link2reviews);
			reviews = collectFromPageReview(link2reviews, threshold);
			/*doc = Jsoup.connect(link2reviews).userAgent(ua).get();
			String cssQuery3 ="div[class=\"a-fixed-right-grid-col a-col-left\"] div[class=\"a-row review-data\"]";


			String text = "";
			boolean continua = true; 
			while (continua == true ){
				doc = Jsoup.connect(link2reviews).userAgent(ua).get();
				Elements el_reviews = doc.select(cssQuery3);
				for ( Element el :el_reviews){
					cont ++;
					text = el.text();
					reviews.add(text);
					System.out.println(text +"\n");    //for testing
				}
				Elements Urls = doc.select("ul li +li a[href]");
				for ( Element el_url : Urls){
					if (el_url.text().contains("Next") && cont < threshold ){
						continua = true;
						link2reviews=el_url.attr("abs:href");
						System.out.println(link2reviews +"\n\n");
						break;
					}
					else{

						continua = false;
					}
				}
			} */
		}
		else{
			reviews = collectFromPageReview(urlForReviews(url), threshold);
			if(reviews.size() == 0){
				System.out.println("MainPage retrieve");
				reviews = RetrieveReview.retrieveFromMainPage(url, threshold);
			}
		}
		System.out.println(reviews.size());
		return reviews;
	}
	
	private static List<String> collectFromPageReview(String link2reviews, int threshold) throws IOException{
		String ua2 = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)";
		String ua ="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/601.6.17 (KHTML, like Gecko)";
		//Document doc = Jsoup.connect(link2reviews).userAgent(ua).get();
		String cssQuery3 ="div[class=\"a-fixed-right-grid-col a-col-left\"] div[class=\"a-row review-data\"]";
		int cont =0;
		String text = "";
		List <String > reviews = new ArrayList<String>();
		boolean continua = true; 
		while (continua == true ){
			Document doc = Jsoup.connect(link2reviews).userAgent(ua).get();
			Elements el_reviews = doc.select(cssQuery3);
			for ( Element el :el_reviews){
				cont ++;
				text = el.text();
				reviews.add(text);
				System.out.println(text +"\n");    //for testing
			}
			Elements Urls = doc.select("ul li +li a[href]");
			for ( Element el_url : Urls){
				if (el_url.text().contains("Next") && cont < threshold ){
					continua = true;
					link2reviews=el_url.attr("abs:href");
					System.out.println(link2reviews +"\n\n");
					break;
				}
				else{

					continua = false;
				}
			}
		}
		return reviews;

	}

	private static String urlForReviews(String url){
		String parts[]=url.split("/");
		int cont=-1;
		for(int i=0; i<parts.length;i++){
			if(parts[i].equals((Object) "dp")){
				parts[i] = "product-reviews";
				cont = i;
			}
		}
		String new_url="";
		for(int i=0; i<parts.length;i++){
			new_url = new_url +parts[i]+"/";
			if (i == cont)
				break;
		}
		System.out.println(new_url+LinksModels.asinFROMurl(url)+"/");
		return new_url+LinksModels.asinFROMurl(url)+"/";
	}

	public static List<String> retrieveFromMainPage(String url, int threshold) throws IOException{
		List<String> reviews = new ArrayList<String>();
		String ua2 = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)";
		String ua ="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/601.6.17 (KHTML, like Gecko)";
		Document doc = Jsoup.connect(url).followRedirects(true).userAgent(ua).get();
		Elements el_reviews;
		//String cssQuery1 ="a:matches(^See)"; // not used 


		String cssQuery2 ="div#revMHRL span ~ div.a-section";
		String cssQuery3 ="div#revMH";

		el_reviews = doc.select(cssQuery2);

		if(el_reviews.isEmpty()){
			System.out.println("empty");
			el_reviews = doc.select(cssQuery3); 
		}
		if(el_reviews.isEmpty())
			System.out.println("empty");

		String text = "";
		for ( Element el :el_reviews){
			text = el.text();
			reviews.add(text);
			System.out.println(text); 
		}
		System.out.println( "# review: "+reviews.size()+"\n");
		return reviews;
	}

}
