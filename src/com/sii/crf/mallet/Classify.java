package com.sii.crf.mallet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;

import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.Trial;
import cc.mallet.pipe.iterator.CsvIterator;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Labeling;
import cc.mallet.util.Randoms;

public class Classify {
	
	private final int TRAINING = 0;
	private final int TESTING = 1;
	private final int VALIDATION = 2;
	private ClassifierTrainer trainer;
	private Classifier classifier;
    private InstanceList[] instanceLists;
	
	public Classify(InstanceList instances, ClassifierTrainer trainer) {
		this.trainer = trainer;
		this.instanceLists = instances.split(new Randoms(), new double[] {0.9, 0.1, 0.0});
		Classifier classifier = trainClassifier(instanceLists[TRAINING]);
        this.classifier = classifier;
	}
	
	private Classifier trainClassifier(InstanceList trainingInstances) {
        return this.trainer.train(trainingInstances);
    }
	
	public Trial testTrainSplit() {
        return new Trial(classifier, instanceLists[TESTING]);
    }
	
	public void printLabelings(File file) throws IOException {
        CsvIterator reader = new CsvIterator(new FileReader(file), "(\\w+)\\s+(\\w+)\\s+(.*)", 3, 2, 1);                                                                                  
        Iterator instances = this.classifier.getInstancePipe().newIteratorFrom(reader);                                                                
        while (instances.hasNext()) {
            Labeling labeling = this.classifier.classify(instances.next()).getLabeling();
            for (int rank = 0; rank < labeling.numLocations(); rank++){
                System.out.print(labeling.getLabelAtRank(rank) + ":" +  labeling.getValueAtRank(rank) + " ");
            }
            System.out.println();
        }
    }
	
}
