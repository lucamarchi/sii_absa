package com.sii.crf.model;

public class Opinion {
	
	private String category;
	private String polarity;
	
	public String getCategory() {
		return this.category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPolarity() {
		return this.polarity;
	}
	public void setPolarity(String polarity) {
		this.polarity = polarity;
	}
	
	public String toString() {
		return "Category: "+this.category+", Polarity: "+this.polarity+"\n";
	}
	
}
