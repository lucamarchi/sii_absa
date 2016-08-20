package com.sii.crf.model;

public class Dependency {
	
	private String relation;
	private String dep;
	private int indexDep;
	private String gov;
	private int indexGov;
	
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public int getIndexDep() {
		return indexDep;
	}
	public void setIndexDep(int indexDep) {
		this.indexDep = indexDep;
	}
	
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getGov() {
		return gov;
	}
	public void setGov(String gov) {
		this.gov = gov;
	}
	public int getIndexGov() {
		return indexGov;
	}
	public void setIndexGov(int indexGov) {
		this.indexGov = indexGov;
	}
	
	public String toString() {
		return "Relation: "
				+ this.relation + ", Gov: "
				+ this.gov + ", GovIndex: "
				+ this.indexGov + ", Dep: "
				+ this.dep + ", DepIndex: "
				+ this.indexDep + "\n";
	}
	
	
}
