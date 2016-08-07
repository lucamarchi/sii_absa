package com.sii.crf.nlpclient;

import java.util.List;

import com.sii.crf.model.Token;

public class Labelling {
	
	public static List<Token> getLabel(List<Token> tokens) {
		for (int i=0; i<tokens.size(); i++) {
			if (tokens.get(i).getPos().equals("NN") || tokens.get(i).getPos().equals("NNS")) {
				tokens.get(i).setLabel("FH");
				if ((i-1>=0) && (tokens.get(i-1).equals("JJ") || tokens.get(i-1).equals("JJR") || tokens.get(i-1).equals("JJS"))) {
					tokens.get(i).setLabel("FA");
				}
				if ((i+1<=tokens.size()-1) && (tokens.get(i+1).equals("JJ") || tokens.get(i+1).equals("JJR") || tokens.get(i+1).equals("JJS"))) {
					tokens.get(i).setLabel("FPA");
				}
			} else {
				tokens.get(i).setLabel("O");
			}
		}
		return tokens;
	}
	
}
