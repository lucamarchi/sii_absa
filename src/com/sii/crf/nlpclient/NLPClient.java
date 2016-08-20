package com.sii.crf.nlpclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.sii.crf.model.Dependency;
import com.sii.crf.model.Sentence;
import com.sii.crf.model.Token;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.pipeline.WordsToSentencesAnnotator;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.BasicDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

public class NLPClient {

	public static Sentence getNLPResults(Sentence sentence) {
		Properties props = new Properties();
	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    Annotation annotation = new Annotation(sentence.getText());
	    Sentence newSentence = new Sentence();
	    newSentence.setText(sentence.getText());
	    newSentence.setOpinions(sentence.getOpinions());
	    pipeline.annotate(annotation);
	    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	    if (sentences != null && ! sentences.isEmpty()) {
	      CoreMap sentenceMap = sentences.get(0);
	      List<Token> tokenList = new ArrayList<Token>();
	      for (CoreMap token : sentenceMap.get(CoreAnnotations.TokensAnnotation.class)) {
	    	  Token modelToken = new Token();
	    	  modelToken.setIndex(token.get(CoreAnnotations.IndexAnnotation.class));
	    	  modelToken.setWord(token.get(CoreAnnotations.TextAnnotation.class));
	    	  modelToken.setLemma(token.get(CoreAnnotations.LemmaAnnotation.class));
	    	  modelToken.setCharacterOffsetBegin(token.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class));
	    	  modelToken.setCharacterOffsetEnd(token.get(CoreAnnotations.CharacterOffsetEndAnnotation.class));
	    	  modelToken.setPos(token.get(CoreAnnotations.PartOfSpeechAnnotation.class));
	    	  modelToken.setNer(token.get(CoreAnnotations.NamedEntityTagAnnotation.class));
	    	  modelToken.setSpeaker(token.get(CoreAnnotations.SpeakerAnnotation.class));
	    	  tokenList.add(modelToken);
	      }
	      newSentence.setTokens(tokenList);
	      List<Dependency> dependenciesList = new ArrayList<Dependency>();
	      SemanticGraph dependencies1 = sentenceMap.get(CollapsedCCProcessedDependenciesAnnotation.class);
	      String dep_type = "CollapsedDependenciesAnnotation";
	      //System.out.println(dep_type+" ===>>");
	      //System.out.println("Sentence: "+sentence.toString());
	      //System.out.println("DEPENDENCIES: "+dependencies1.toList());
	      //System.out.println("DEPENDENCIES SIZE: "+dependencies1.size());
	      
	      List<SemanticGraphEdge> edge_set1 = dependencies1.edgeListSorted();
	      int j=0;

	      for(SemanticGraphEdge edge : edge_set1){
	          j++;
	          //System.out.println("------EDGE DEPENDENCY: "+j);
	          Iterator<SemanticGraphEdge> it = edge_set1.iterator();
	          IndexedWord dep = edge.getDependent();
	          String dependent = dep.word();
	          int dependent_index = dep.index();
	          IndexedWord gov = edge.getGovernor();
	          String governor = gov.word();
	          int governor_index = gov.index();
	          GrammaticalRelation relation = edge.getRelation();
	          Dependency currDependency = new Dependency();
	          currDependency.setRelation(relation.toString());
	          currDependency.setDep(dependent.toString());
	          currDependency.setIndexDep(dependent_index);
	          currDependency.setGov(governor.toString());
	          currDependency.setIndexGov(governor_index);
	          dependenciesList.add(currDependency);
	          //System.out.println("No:"+j+" Relation: "+relation.toString()+" 	 Dependent: "+dependent.toString()+"Depen ind: "+dep.index()+" Governor: "+governor.toString()+"gover ind: "+gov.index());
	      }
	      newSentence.setDependencies(dependenciesList);
	      /*
	      SemanticGraph sentenceGraph = sentenceMap.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
	      Collection<TypedDependency> dependencies = sentenceGraph.typedDependencies();
	      for (TypedDependency dep : dependencies) {
	    	  System.out.println(dep.gov().index());
	      }*/
	    }
	    return newSentence;
	  }
}

	

