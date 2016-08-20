package com.sii.crf.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sentence {
	
	private String id;
	private String text;
	private List<Opinion> opinions;
	private List<Token> tokens;
	private List<Dependency> dependencies;

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public List<Opinion> getOpinions() {
		return this.opinions;
	}
	
	public void setOpinions(List<Opinion> opinions) {
		this.opinions = opinions;
	}
	
	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	public List<Dependency> getDependecies() {
		return this.dependencies;
	}

	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}
	
	public String toString() {
		String description = "------------ Sentence --------------- \n";
		description += "Text: "+ this.getText() + "\n";
		for (int i=0; i<this.getOpinions().size(); i++) {
			description += this.opinions.get(i).toString();
		}
		if (this.getTokens() != null) {
			description += "[Tokens= ";
			for (int j=0; j<this.getTokens().size(); j++) {
				description += this.tokens.get(j).toString();
			}
			description += "]\n";
		}
		if (this.getDependecies() != null) {
			description += "[Dependencies= ";
			for (int j=0; j<this.getDependecies().size(); j++) {
				description += this.dependencies.get(j).toString();
			}
			description += "]\n";
		}
		return description;
	}
}
