package weka.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Debug;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

public class CrossValidation {
	public static final String DATASETPATH = "/Users/faisal/Desktop/arff-files/training0530.arff";
	public static final String TESTPATH = "/Users/faisal/Desktop/arff-files/test_file_faisal.arff";//
    public static final String MODElPATH = "/Users/faisal/Documents/weka2-api/model.bin";

public static void main(String[] args) throws Exception {
        
        ModelGenerator mg = new ModelGenerator();

        Instances dataset = mg.loadDataset(DATASETPATH);

        Filter filter = new Normalize();
        
        Instances unlabeled = new Instances(new BufferedReader(new FileReader("/Users/faisal/Desktop/arff-files/test_file_faisal.arff")));
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
        Instances labeled = new Instances(unlabeled);

        // divide dataset to train dataset 80% and test dataset 20%
        int trainSize = (int) Math.round(dataset.numInstances() * 0.98);
        int testSize = dataset.numInstances() - trainSize;

        dataset.randomize(new Debug.Random(1));// if you comment this line the accuracy of the model will be droped from 96.6% to 80%
        
        //Normalize dataset
        filter.setInputFormat(dataset);
        Instances datasetnor = Filter.useFilter(dataset, filter);
        Evaluation eval = new Evaluation(datasetnor);
        

        Instances traindataset = new Instances(datasetnor, 0, trainSize);
        Instances testdataset = new Instances(datasetnor, trainSize, testSize);
//        Instances testdataset = mg.loadDataset(TESTPATH);

        // build classifier with train dataset             
        MultilayerPerceptron ann = (MultilayerPerceptron) mg.buildClassifier(traindataset);
        Classifier c = new NaiveBayes();
        eval.crossValidateModel(c, dataset, 10, new Random(1));
        System.out.println("Estimated Accuracy: "+Double.toString(eval.pctCorrect()));
        
//        Classifier c = new NaiveBayes();
//        c.buildClassifier(dataset);

        // Evaluate classifier with test dataset
//        String evalsummary = mg.evaluateModel(ann, traindataset, testdataset);
//        System.out.println("Evaluation: " + evalsummary);
//
//
//        //Save model 
//        mg.saveModel(ann, MODElPATH);
//        
//        for (int i = 0; i < unlabeled.numInstances(); i++) {
//        	   double clsLabel =ann.classifyInstance(unlabeled.instance(i));
//        	   labeled.instance(i).setClassValue(clsLabel);
//        	 }
//        System.out.println(labeled.toString());
//
//
    }


}
