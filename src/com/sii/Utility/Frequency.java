package com.sii.Utility;

import java.util.TreeMap;
import java.util.List;
import java.util.Map.Entry;

import com.sii.POSTagger.POSTagger;
import com.sii.crf.model.Opinion;
import com.sii.crf.model.Sentence;

public class Frequency {
	
	public static TreeMap<String, Integer> frequency(List<Sentence> sentences){
		TreeMap<String,Integer> words=new TreeMap<String,Integer>();
		System.out.println(sentences.size());

		//lista delle words con #occorrenze
		for (Sentence s: sentences){
									//maybe it could work better toLowerCase before generating occurrencies
			String tagged = POSTagger.tagSentence(s.getText());
			System.out.println(tagged);
			String[] parts = POSTagger.NamesAndAdjectives(tagged);
		//	System.out.println(parts.length);
			
			int l = parts.length;
			boolean isThere=false;
			for (int i=0; i<l; i++){
				isThere = words.containsKey(parts[i]);
				int value= 1;
				if (isThere != false){
					value=words.get((parts[i]))+1;
					words.replace(parts[i], value);
				}
				else
					words.put(parts[i],value);
			}
		}
		for(Entry<String, Integer> entry: words.entrySet()){
			System.out.println(entry.getKey() +"    "+ entry.getValue());
		}
		return words;
	}
	
	
	public static TreeMap<String,Integer> frequencyCategory(List<Sentence> sentences){
		TreeMap<String,Integer> categories=new TreeMap<String,Integer>();
		
		for (Sentence s: sentences){
			for(Opinion o : s.getOpinions()){
				int value= 1;
				if(categories.containsKey(o.getCategory())){
					value= categories.get(o.getCategory()) + 1;
					categories.replace(o.getCategory(), value);
				}
				else
					categories.put(o.getCategory(), value);
			}
		}

		
		return categories;
	}

}
