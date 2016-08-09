package com.sii.crf.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sentence {
	
	private String id;
	private String text;
	private List<Opinion> opinions;
	private Pre_elaboration pre_works;
	
	

	public Sentence(){
		this.setId(null);
		this.setText(null);
		this.setOpinions(null);
		this.setPre_works();
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
	
	public Pre_elaboration getPre_works() {
		return this.pre_works;
	}

	public void setPre_works(Pre_elaboration pre_works) {
		this.pre_works = pre_works;
	}
	
	private void setPre_works() {
		this.pre_works = new Pre_elaboration();
	}
	
	public String toString() {
		String description = "------------ Sentence --------------- \n";
		description += "Text: "+ this.getText() + "\n";
		for (int i=0; i<this.getOpinions().size(); i++) {
			description += this.opinions.get(i).toString();
		}
		description +="\n POSTagging: "+ this.getPre_works().getPos_tagging();
		return description;
	}
}
