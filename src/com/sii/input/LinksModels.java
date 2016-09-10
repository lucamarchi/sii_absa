package com.sii.input;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sii.crf.mongodb.dao.LaptopDAO;

public class LinksModels {
	
	
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
	
	public static void collectModelIDs() throws IOException, InterruptedException{
		List<Laptop> laps = LaptopDAO.findAll();
		final int step = 5;
		int cont = 0;
		int real_cont = 0;
		String userAgent= "";
		Document doc;
		for(Laptop lap: laps){
			cont++;
			real_cont++;
			if((lap.getModel_number() == null) || 
					(lap.getModel_number().equals((Object)lap.getAsin() )&& real_cont>500) || 
					(lap.getAsin().equals((Object)"sconosciuto") )   ) 
			{
				switch(cont){
					case 1: userAgent="Mozilla"; 
					case 2: userAgent="Safari";
					case 3: userAgent="Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)";
					case 4: userAgent="Opera";
					case 5: userAgent="Camino";
					case 6: userAgent="Chrome";
					
					default : userAgent="Mozilla";
				}
				
				doc = Jsoup.connect(lap.getLink()).userAgent(userAgent).get();
				String mod_num = LinksModels.findModelNumber(doc);
				String asin = LinksModels.findASINnumber(doc);
				if(mod_num != null)
					lap.setModel_number(mod_num);
				else
					lap.setModel_number("NOT FOUND");
				if(asin != null)
					lap.setAsin(asin);
				else
					lap.setAsin("NOT FOUND");
				LaptopDAO.update(lap);
				System.out.println("sono entrato al "+real_cont);
				
				TimeUnit.SECONDS.sleep(3);
				if(cont == 6)
					cont = 0;
			}
			//System.out.println(cont);
			
		}
		
	}

	private static String findModelNumber(Document d){
		String mod_num = null;
		String cssQuery1 = "table[id=\"productDetails_techSpec_section_2\"] " ;
		//String cssQuery1bis = "table[id=\"productDetails_detailBullets_sections1\"]";
		String cssQuery2 = "[class]";
		Elements table = d.select(cssQuery1);
		Elements thtd = table.select(cssQuery2);
		boolean next = false;
		for (Element el : thtd){
			
			if (next == true){
				System.out.println(el.text());
				mod_num = el.text();
				next = false;
				return mod_num;
			}
			if( !next && (el.text().equals((Object)("Item model number")) )){
				next = true;
			}
			
		}
		
		//System.out.println(mod_num +"  "+next);
		return mod_num;
	}
	
	private static String findASINnumber(Document d){
		String asin = null;
		String cssQuery1 = "table[id=\"productDetails_detailBullets_sections1\"]";
		String cssQuery2 = "[class]";
		Elements table = d.select(cssQuery1);
		Elements thtd = table.select(cssQuery2);
		boolean next = false;
		for (Element el : thtd){
			
			if (next == true){
				System.out.println(el.text());
				asin = el.text();
				next = false;
				return asin;
			}
			if( !next && (el.text().equals((Object)("ASIN")) )){
				next = true;
			}
			
		}
		
		//System.out.println(mod_num +"  "+next);
		return asin;
	}
	
	

}
