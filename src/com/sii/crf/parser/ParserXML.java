package com.sii.crf.parser;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sii.crf.model.Opinion;
import com.sii.crf.model.Sentence;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

public class ParserXML {
	
	private final static String XML_FILE_NAME = "/Users/Giorgio/git/sii_absa/src/com/sii/crf/parser/ABSA16_Laptops_Train_SB1_v2.xml";
	
	public static List<Sentence> parseXMLFileToList() {
		File xmlFile = new File(XML_FILE_NAME);
		List<Sentence> sentences = new ArrayList<Sentence>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList sentencesList = doc.getElementsByTagName("sentence");
			for (int i = 0; i < sentencesList.getLength(); i++) {
				Node currSentence = sentencesList.item(i);
				Element eElement = (Element) currSentence;
				NodeList opinionsList = eElement.getElementsByTagName("Opinion");
				if (opinionsList.getLength() != 0) {
					Sentence tmpSentence = new Sentence();
					tmpSentence.setText(eElement.getTextContent().trim());
					List<Opinion> opinions = new ArrayList<Opinion>();
					for (int j=0; j<opinionsList.getLength(); j++) {
						Node currOpinion = opinionsList.item(j);
						Element mElement = (Element) currOpinion;
						Opinion opinion = new Opinion();
						opinion.setCategory(mElement.getAttribute("category"));
						opinion.setPolarity(mElement.getAttribute("polarity"));
						opinions.add(opinion);
					}
					tmpSentence.setOpinions(opinions);
					sentences.add(tmpSentence);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sentences;
	}
	
	
	
	public static List<Sentence> parseXMLFileToListForCategory(String category) {
		File xmlFile = new File(XML_FILE_NAME);
		List<Sentence> sentences = new ArrayList<Sentence>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList sentencesList = doc.getElementsByTagName("sentence");
			for (int i = 0; i < sentencesList.getLength(); i++) {
				Node currSentence = sentencesList.item(i);
				Element eElement = (Element) currSentence;
				NodeList opinionsList = eElement.getElementsByTagName("Opinion");
				if (opinionsList.getLength() != 0) {
					Sentence tmpSentence = new Sentence();
					tmpSentence.setText(eElement.getTextContent().trim());
					List<Opinion> opinions = new ArrayList<Opinion>();
					for (int j=0; j<opinionsList.getLength(); j++) {
						Node currOpinion = opinionsList.item(j);
						Element mElement = (Element) currOpinion;
						String ctgr = mElement.getAttribute("category");
						if(ctgr == category){
							Opinion opinion = new Opinion();
							//System.out.println(mElement.getAttribute("category") +"   "+ category);
							opinion.setCategory(mElement.getAttribute("category"));
							opinion.setPolarity(mElement.getAttribute("polarity"));
							opinions.add(opinion);
						}
					}
					if(tmpSentence.getOpinions() != null){
						tmpSentence.setOpinions(opinions);
						sentences.add(tmpSentence);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sentences;
	}
	
}
