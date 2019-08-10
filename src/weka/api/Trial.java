package weka.api;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Debug;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.classifiers.Evaluation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.api.automation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import weka.api.*;

public class Trial {
	
	public static final String DATASETPATH = "/Users/faisal/Desktop/arff-files/training.arff";
	public static final String TESTPATH = "/Users/faisal/Desktop/arff-files/test_file_faisal.arff";//
    public static final String MODElPATH = "/Users/faisal/Documents/weka2-api/model.bin";
    public static final String UNLABELED = "/Users/faisal/Desktop/arff-files/labeled.txt";

public static void main(String[] args) throws Exception {
		long startTime = System.nanoTime();
        
        ModelGenerator mg = new ModelGenerator();

        Instances dataset = mg.loadDataset(DATASETPATH);

        Filter filter = new Normalize();
        
        Instances unlabeled = new Instances(new BufferedReader(new FileReader(TESTPATH)));
        unlabeled.setClassIndex(unlabeled.numAttributes() - 1);

        int trainSize = (int) Math.round(dataset.numInstances() * 0.97);
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


        ModelClassifier cls = new ModelClassifier();
//        for (int i = 0; i < unlabeled.numInstances(); i++) {
//        	   double clsLabel =ann.classifyInstance(unlabeled.instance(i));
//        	   System.out.print("ID: " + unlabeled.instance(i).value(0) + "\n");
//        	   System.out.print("actual: " + unlabeled.classAttribute().value((int) unlabeled.instance(i).classValue()) + " \n");
//        	   System.out.println("predicted: " + unlabeled.classAttribute().value((int) clsLabel));
////        	   unlabeled.instance(i).setClassValue(clsLabel);
//        	 }
////        System.out.println(unlabeled);

       
        
        ///////////////////////////////////////////
        String csvFile = "/Users/faisal/Desktop/arff-files/csvtest.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int array_legnth =unlabeled.numInstances();
//        System.out.print(array_legnth + "\n");
        String[] names = new String[array_legnth];
        String[] names_copy = new String[array_legnth];
        int i = 0;
        
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
            	String[] data = line.split(cvsSplitBy);
                String classname =cls.classifiy(Filter.useFilter(cls.createInstance(Double.parseDouble(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]),0), filter), MODElPATH);
                names[i] = classname;
                names_copy[i] = classname;
                i++;
                
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        List<String> list = Arrays.asList(names);
        int max = 0, curr = 0;
        String current = null;
        Set<String> unique = new HashSet<String>(list);
        for (String key : unique) {
            curr = Collections.frequency(list, key);

           if(max < curr){
             max = curr;
             current = key;
            }
        }
        
        if (current != null && Arrays.asList(names).contains(current)) {
        	System.out.print("Authenticated!\n");
        } else {
        	System.out.print("Failed authentication \n");
        	System.exit(0);
        }
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(UNLABELED));
        writer.write(current.toString());
        writer.newLine();
        writer.flush();
        writer.close();
        
        mg.saveModel(ann, MODElPATH);
        long endTime   = System.nanoTime();
        double totalTime = (endTime - startTime)/1_000_000_000.0;
        System.out.println("the algorithms ran in: " + totalTime);
//        System.out.println("Evaluation: " + evalsummary);
        



        //TODO: add command line to main
//        ModelClassifier cls = new ModelClassifier();
//        String classname =cls.classifiy(Filter.useFilter(cls.createInstance(1.6, 0.2, 0), filter), MODElPATH);
//        System.out.println("\n The class name for the instance with petallength = 1.6 and petalwidth =0.2 is  " +classname);

        //classifiy a single instance 
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
