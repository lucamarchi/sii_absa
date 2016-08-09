package com.sii.POSTagger;

import java.util.ArrayList;
import java.util.Iterator;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class POSTagger {
	
	public static String tagSentence(String toTag){
		//MaxentTagger tagger = new MaxentTagger("taggers/english-bidirectional-distsim.tagger");
		MaxentTagger tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger"); //should be faster !
		String tagged= tagger.tagString(toTag);
		return tagged;
	}
	
	public static String[] NamesAndAdjectives(String tagged){
		String[] words = tagged.split("[ _]");
		int l= words.length;
		ArrayList<String> NNJJ= new ArrayList<String>() ;
		//Iterator<String> iterator = NNJJ.listIterator();
		
		for (int i=1; i<l; i++ ){
			if (words[i].equals((Object) "NN") || words[i].equals((Object)"NNS") || words[i].equals((Object) "JJ")){
				NNJJ.add(words[i-1]);
				System.out.println(words[i-1] +"  "+ words[i]);
				
			}
			//System.out.println(words[i] );
		}
		System.out.println(NNJJ.size());
	    String[] optNNJJ = new String[NNJJ.size()];
	    optNNJJ=NNJJ.toArray(optNNJJ);
	    
		return optNNJJ;
	}

}
