package com.sii.crf.nlpclient;

import java.util.List;

import com.sii.crf.model.Dependency;
import com.sii.crf.model.Sentence;
import com.sii.crf.model.Token;

public class Labelling {
	
	public static Sentence getLabel(Sentence sentence) {
		List<Token> tokenList = sentence.getTokens();
		for (Token t : tokenList) {
			if (t.getPos().equals("JJ")) {
				List<Dependency> dependencyList = sentence.getDependecies();
				for (Dependency d : dependencyList) {
					if (d.getRelation().equals("nsubj") && d.getIndexGov()==t.getIndex()) {
						if ((getTokenByIndex(tokenList,d.getIndexDep()).getPos().equals("NN"))) {
							if (t.getLabel() == null) {
								if (t.getIndex() > d.getIndexDep()) {
									getTokenByIndex(tokenList,d.getIndexGov()).setLabel("FPA");
									getTokenByIndex(tokenList,d.getIndexDep()).setLabel("FH");
								} else {
									getTokenByIndex(tokenList,d.getIndexGov()).setLabel("FA");
									getTokenByIndex(tokenList,d.getIndexDep()).setLabel("FH");
								}
							}
						}
					}
					else if (d.getRelation().equals("nsubj") && d.getIndexDep()==t.getIndex()) {
						if ((getTokenByIndex(tokenList,d.getIndexGov()).getPos().equals("NN"))) {
							if (t.getLabel() == null) {
								if (t.getIndex() > d.getIndexGov()) {
									getTokenByIndex(tokenList,d.getIndexGov()).setLabel("FPA");
									getTokenByIndex(tokenList,d.getIndexDep()).setLabel("FH");
								} else {
									getTokenByIndex(tokenList,d.getIndexGov()).setLabel("FA");
									getTokenByIndex(tokenList,d.getIndexDep()).setLabel("FH");
								}
							}
						}
					}
				}
			} 
			
		}
		for (Token t : tokenList) {
			if (t.getPos().equals("NN") && t.getLabel() == null) {
				t.setLabel("FH");
			}
		}
		for (Token t : tokenList) {
			if (t.getLabel() == null) {
				t.setLabel("O");
			}
		}
		return sentence;
	}
	
	public static Token getTokenByIndex(List<Token> tokens, int index) {
		for (Token t : tokens) {
			if (t.getIndex() == index) {
				return t;
			}
		}
		return null;
	}
	
}
