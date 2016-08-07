package com.sii.crf.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sentence {
	
	private String id;
	private String text;
	private List<Opinion> opinions;
	
	public Sentence(){
		this.setId(null);
		this.setText(null);
		this.setOpinions(null);
	}
	
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
	
	public String toString() {
		String description = "------------ Sentence --------------- \n";
		description += "Text: "+ this.getText() + "\n";
		for (int i=0; i<this.getOpinions().size(); i++) {
			description += this.opinions.get(i).toString();
		}
		return description;
	}
}
