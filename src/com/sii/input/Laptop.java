package com.sii.input;

public class Laptop {
	private String name;
	private String link;
	private String model_number;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getModel_number() {
		return model_number;
	}
	public void setModel_number(String model_number) {
		this.model_number = model_number;
	}
	
	public String toString(){
		return this.getName()+"\n"+this.getLink()+"\n"+this.getModel_number()+"\n";
	}
	
}
