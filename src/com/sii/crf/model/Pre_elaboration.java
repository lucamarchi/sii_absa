package com.sii.crf.model;

public class Pre_elaboration {

	private String pos_tagging;
	
	public Pre_elaboration(){
		this.pos_tagging = null;
	}
	
	public Pre_elaboration(String pos){
		this.pos_tagging = pos;
	}

	public String getPos_tagging() {
		return this.pos_tagging;
	}

	public void setPos_tagging(String pos_tagging) {
		this.pos_tagging = pos_tagging;
	}
	
	
}
