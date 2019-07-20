package weka.api;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Debug;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.classifiers.Evaluation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import weka.api.automation.*;

import java.util.List;
import java.util.Random;


import weka.api.*;

public class Trial {
	
	public static final String DATASETPATH = "/Users/faisal/Desktop/arff-files/training.arff";
	public static final String TESTPATH = "/Users/faisal/Desktop/arff-files/test_file_faisal.arff";//
    public static final String MODElPATH = "/Users/faisal/Documents/weka2-api/model.bin";
    public static final String UNLABELED = "/Users/faisal/Desktop/arff-files/labeled.arff";

public static void main(String[] args) throws Exception {
		long startTime = System.nanoTime();
        
        ModelGenerator mg = new ModelGenerator();

        Instances dataset = mg.loadDataset(DATASETPATH);

        Filter filter = new Normalize();
        
        Instances unlabeled = new Instances(new BufferedReader(new FileReader(TESTPATH)));
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

        int trainSize = (int) Math.round(dataset.numInstances() * 0.95);
        int testSize = dataset.numInstances() - trainSize;

        dataset.randomize(new Debug.Random(1));
        
        //Normalize dataset
        filter.setInputFormat(dataset);
        Instances datasetnor = Filter.useFilter(dataset, filter);

        Instances traindataset = new Instances(datasetnor, 0, trainSize);
        Instances testdataset = new Instances(datasetnor, trainSize, testSize);

        // build classifier with train data set             
        MultilayerPerceptron ann = (MultilayerPerceptron) mg.buildClassifier(traindataset);

        // Evaluate classifier with test data set
        String evalsummary = mg.evaluateModel(ann, traindataset, testdataset);
        System.out.println("Evaluation: " + evalsummary);


        //Save model 
        mg.saveModel(ann, MODElPATH);
        long endTime   = System.nanoTime();
        double totalTime = (endTime - startTime)/1_000_000_000.0;
        System.out.println(totalTime);
        
//        System.out.print(unlabeled);
////       
        for (int i = 0; i < unlabeled.numInstances(); i++) {
        	   double clsLabel =ann.classifyInstance(unlabeled.instance(i));
        	   unlabeled.instance(i).setClassValue(clsLabel);
//        	   System.out.println(unlabeled);
        	 }
        	 // save labeled data
        BufferedWriter writer = new BufferedWriter(new FileWriter(UNLABELED));
        writer.write(unlabeled.toString());
        writer.newLine();
        writer.flush();
        writer.close();
//        System.out.println(unlabeled);
        
//        List<Object> person = getDetails(); 
//        System.out.println(person); 

        //classifiy a single instance 
        
        
        
//        System.out.printf("%d, %d, %d, %d", onset.length, sal.length, onsetdiff.length, pitch.length);
//        for (int i = 0; i < vot.length; i++) {
//			String classname =cls.classifiy(Filter.useFilter(cls.createInstance(vot[i],vot_diff[i],sal[i],pitch[i], prob[i],0), filter), MODElPATH);
//			System.out.println("\n The name for the instance:  " +classname);
//
//        }
//        ModelClassifier cls = new ModelClassifier();
//        String classname =cls.classifiy(Filter.useFilter(cls.createInstance(0.28850792889313903,0.0,10.593929290771484,0.90922475,0), filter), MODElPATH);
//        System.out.println("\n The name for the instance:  " +classname);
//        String classname2 =cls.classifiy(Filter.useFilter(cls.createInstance(0.7180770838709375,0.42956915497779846,3.3129000663757324,0.9680298,0), filter), MODElPATH);
//        System.out.println("\n The name for the instance:  " +classname2);
//        String classname3 =cls.classifiy(Filter.useFilter(cls.createInstance(0.8806168044062158,0.16253972053527832,10.276806831359863,0.9680911,0), filter), MODElPATH);
//        System.out.println("\n The name for the instance:  " +classname3);
//        String classname4 =cls.classifiy(Filter.useFilter(cls.createInstance(1.1824761832209132,0.3018593788146974,14.251372337341309,0.99471045,0), filter), MODElPATH);
//        System.out.println("\n The name for the instance:  " +classname4);

    }

}
