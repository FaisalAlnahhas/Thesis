package weka.api.algorithm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
 
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.pmml.jaxbbindings.NeuralNetwork;
import weka.core.pmml.jaxbbindings.SupportVectorMachine;
import weka.core.pmml.jaxbbindings.SupportVectorMachineModel;
import weka.filters.unsupervised.attribute.RandomSubset;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.ParallelMultipleClassifiersCombiner;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SGD;
import weka.classifiers.functions.SGDText;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.SimpleLinearRegression;
import weka.classifiers.functions.SimpleLogistic;
import weka.classifiers.functions.VotedPerceptron;
import weka.classifiers.lazy.KStar;
import weka.classifiers.lazy.LWL;
import weka.classifiers.meta.IterativeClassifierOptimizer;
import weka.classifiers.meta.LogitBoost;
import weka.classifiers.meta.MultiClassClassifier;
import weka.classifiers.meta.MultiClassClassifierUpdateable;
import weka.classifiers.meta.MultiScheme;
import weka.classifiers.meta.RandomCommittee;
import weka.classifiers.meta.RandomSubSpace;
import weka.classifiers.meta.RegressionByDiscretization;
import weka.classifiers.meta.Stacking;
import weka.classifiers.meta.Vote;
import weka.classifiers.meta.WeightedInstancesHandlerWrapper;
import weka.classifiers.misc.SerializedClassifier;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.DecisionTableHashKey;
import weka.classifiers.rules.JRip;
import weka.classifiers.rules.M5Rules;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.PART;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.*;
import weka.classifiers.trees.lmt.LogisticBase;
import weka.classifiers.trees.m5.RuleNode;
import weka.core.FastVector;
import weka.core.Instances;
import weka.classifiers.bayes.*;
 
public class Models {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public static Evaluation classify(Classifier model,
			Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
 
		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);
 
		return evaluation;
	}
 
	public static double calculateAccuracy(FastVector predictions) {
		double correct = 0;
 
		for (int i = 0; i < predictions.size(); i++) {
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
 
		return 100 * correct / predictions.size();
	}
 
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
 
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
 
		return split;
	}
 
	public static void main(String[] args) throws Exception {
		long startTime = System.nanoTime();
		BufferedReader datafile = readDataFile("/Users/faisal/Desktop/arff-files/training.arff");
 
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
 
		// Do 10-split cross validation
		Instances[][] split = crossValidationSplit(data, 500);
 
		// Separate split into training and testing arrays
		Instances[] trainingSplits = split[0];
		Instances[] testingSplits = split[1];
 
		// Use a set of classifiers
		Classifier[] models = { 
				new IterativeClassifierOptimizer(),
				new J48(), // a decision tree
				new LMT(),
				new LogisticBase(),
				new LogitBoost(),
				new MultiClassClassifier(),
				new NaiveBayesMultinomial(),
				new NaiveBayesMultinomialUpdateable(),
				new RandomSubSpace(),
//				new DecisionTable(),//decision table majority classifier
//				new DecisionStump(), //one-level decision tree
//				new RandomForest(),
//				new RandomTree(),
//				new NaiveBayes(),
//				new Logistic(),
				
				
				
		};
 
		// Run for each model
		for (int j = 0; j < models.length; j++) {
 
			// Collect every group of predictions for current model in a FastVector
			FastVector predictions = new FastVector();
 
			// For each training-testing split pair, train and test the classifier
			for (int i = 0; i < trainingSplits.length; i++) {
				Evaluation validation = classify(models[j], trainingSplits[i], testingSplits[i]);
 
				predictions.appendElements(validation.predictions());
 
				// Uncomment to see the summary for each training-testing pair.
				//System.out.println(models[j].toString());
			}
 
			// Calculate overall accuracy of current classifier on all splits
			double accuracy = calculateAccuracy(predictions);
 
			System.out.println("Accuracy of " + models[j].getClass().getSimpleName() + ": "
					+ String.format("%.2f%%", accuracy)
					+ "\n---------------------------------");
		}
		
	    long endTime   = System.nanoTime();
	    double totalTime = (endTime - startTime)/1_000_000_000.0;
	    System.out.println(totalTime);
 
	}

}