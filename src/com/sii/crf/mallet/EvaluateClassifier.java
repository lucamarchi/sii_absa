package com.sii.crf.mallet;

import java.util.ArrayList;

import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.DecisionTreeTrainer;
import cc.mallet.classify.MaxEntL1Trainer;
import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.classify.NaiveBayesTrainer;
import cc.mallet.classify.Trial;
import cc.mallet.types.InstanceList;
import cc.mallet.types.InstanceList.CrossValidationIterator;

public class EvaluateClassifier {
	
	public static void evaluateDataClassify(InstanceList instances, int count) {
		ArrayList<ClassifierTrainer> trainersList = new ArrayList<ClassifierTrainer>();
		ClassifierTrainer maxentTrainer = new MaxEntTrainer();
		ClassifierTrainer naiveBayesTrainer = new NaiveBayesTrainer();
		ClassifierTrainer decisionTree = new DecisionTreeTrainer();
		trainersList.add(naiveBayesTrainer);
		trainersList.add(maxentTrainer);
		trainersList.add(decisionTree);
		for (ClassifierTrainer trainer : trainersList) {
			double accuracyAverage=0;
			double precisionPositiveAverage=0;
			double precisionNeutralAverage=0;
			double precisionNegativeAverage=0;
			double f1PositiveAverage=0;
			double f1NeutralAverage=0;
			double f1NegativeAverage=0;
			for (int i=0; i<count; i++) {
				Classify classifier = new Classify(instances,trainer);
				Trial trial = classifier.testTrainSplit();
				accuracyAverage += trial.getAccuracy();
				f1PositiveAverage += trial.getF1("positive");
				f1NeutralAverage += trial.getF1("neutral");
				f1NegativeAverage += trial.getF1("negative");
				precisionPositiveAverage += trial.getPrecision("positive");
				precisionNeutralAverage += trial.getPrecision("neutral");
				precisionNegativeAverage += trial.getPrecision("negative");
			}
			System.out.println("--------------------- "+ trainer.getClass().getSimpleName() + " ---------------------");
			System.out.println("Accuracy: "+accuracyAverage/count);
			System.out.println("Precision score for class 'positive': "+precisionPositiveAverage/count);
			System.out.println("Precision score for class 'neutral': "+precisionNeutralAverage/count);
			System.out.println("Precision score for class 'negative': "+precisionNegativeAverage/count);
			System.out.println("F1 score for class 'positive': "+f1PositiveAverage/count);
			System.out.println("F1 score for class 'neutral': "+f1NeutralAverage/count);
			System.out.println("F1 score for class 'negative': "+f1NegativeAverage/count);
		}
	} 
	
}
