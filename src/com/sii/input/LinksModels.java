package com.sii.input;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sii.crf.mongodb.dao.LaptopDAO;

public class LinksModels {
	
	//public LinksModels() throws IOException{}
	//File input = new File ("Users/Giorgio/Download/Amazon.com_Traditional Laptops_Electronics.html");
	public static void collectLinks() throws IOException{
		Document doc = Jsoup.connect("https://www.amazon.com/Traditional-Laptop-Computers/b/"
				+ "ref=sn_gfs_co_Laptops_VS_13896615011_2?ie=UTF8&node=13896615011&pf_rd_p=b4961a0b-c5f6-4b4a"
				+ "-b294-7eae5a5b1705&pf_rd_r=QRWT025QB74DQJTM4KX3&pf_rd_s=pc-subnav-flyout-content-1&pf_rd_t="
				+ "SubnavFlyout").userAgent("Mozilla").get();

		String cssQuery_nextPage = "a[id=\"pagnNextLink\"]";
		String cssQuery="a[class*=s-access-detail-page]";
		Elements Links = doc.select(cssQuery);
		Elements nextPage= doc.select(cssQuery_nextPage); // ce ne è solo uno !
		String link;
		String model;
		String nextURLpage="";
		int i = 0;
		for (Element e : Links){
			i++;
			link = e.attr("href");
			model = e.attr("title");
			Laptop current = new Laptop();
			current.setLink(link);
			current.setName(model);
			System.out.println(i+")  " +model+ "\n" + link +"\n\n\n");
		}
		for (Element next : nextPage){
			nextURLpage = next.attr("abs:href");
			System.out.println(nextURLpage);
		}
		if(!(nextPage.isEmpty()))
			collectLinks(nextURLpage, 2);
		
	}
	
	public static void collectLinks(String page, int cont) throws IOException{
		Document doc = Jsoup.connect(page).userAgent("Mozilla").get();

		String cssQuery_nextPage = "a[id=\"pagnNextLink\"]";
		String cssQuery="a[class*=s-access-detail-page]";
		Elements Links = doc.select(cssQuery);
		Elements nextPage= doc.select(cssQuery_nextPage); // ce ne è solo uno !
		String link;
		String model;
		String nextURLpage="";
		int i = 0;
		for (Element e : Links){
			i++;
			link = e.attr("href");
			model = e.attr("title");
			Laptop current = new Laptop();
			current.setLink(link);
			current.setName(model);
			LaptopDAO.insert(current);
			System.out.println(cont*24+i+")  " +model+ "\n" + link +"\n\n\n");
		}
		for (Element next : nextPage){
			nextURLpage = next.attr("abs:href");
			System.out.println(nextURLpage);
		}
		if(!(nextPage.isEmpty()) && cont<400)
			collectLinks(nextURLpage, cont+1);
		
	}


}
