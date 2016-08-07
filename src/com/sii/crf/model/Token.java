package com.sii.crf.model;

public class Token {
	
	private int index;
	private String word;
	private String lemma;
	private int characterOffsetBegin;
	private int characterOffsetEnd;
	private String pos;
	private String ner;
	private String speaker;
	private String label;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getWord() {
		return word;
	}
	public int getCharacterOffsetEnd() {
		return characterOffsetEnd;
	}
	public void setCharacterOffsetEnd(int characterOffsetEnd) {
		this.characterOffsetEnd = characterOffsetEnd;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getLemma() {
		return lemma;
	}
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}
	public int getCharacterOffsetBegin() {
		return characterOffsetBegin;
	}
	public void setCharacterOffsetBegin(int characterOffsetBegin) {
		this.characterOffsetBegin = characterOffsetBegin;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getNer() {
		return ner;
	}
	public void setNer(String ner) {
		this.ner = ner;
	}
	public String getSpeaker() {
		return speaker;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public String toString() {
		return "Original: "+this.word +", pos: "+this.pos+"\n";
	}
	
}
