package com.sii.crf.nlpclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.sii.crf.model.Sentence;
import com.sii.crf.model.Token;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.pipeline.WordsToSentencesAnnotator;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
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

	

