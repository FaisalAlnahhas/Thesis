package weka.api.attempts;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Debug;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;
import weka.api.*;
import weka.api.algorithm.ModelGenerator;

public class Test {
	public static final String DATASETPATH = "/Users/faisal/Desktop/arff-files/test3-copy.arff";
    public static final String MODElPATH = "/Users/faisal/Documents/weka2-api/model.bin";

    public static void main(String[] args) throws Exception {
        
        ModelGenerator mg = new ModelGenerator();

        Instances dataset = mg.loadDataset(DATASETPATH);

        Filter filter = new Normalize();

        // divide dataset to train dataset 80% and test dataset 20%
        int trainSize = (int) Math.round(dataset.numInstances() * 0.80);
        int testSize = dataset.numInstances() - trainSize;

        dataset.randomize(new Debug.Random(1));// if you comment this line the accuracy of the model will be droped from 96.6% to 80%
        
        //Normalize dataset
        filter.setInputFormat(dataset);
        Instances datasetnor = Filter.useFilter(dataset, filter);

        Instances traindataset = new Instances(datasetnor, 0, trainSize);
        Instances testdataset = new Instances(datasetnor, trainSize, testSize);

        // build classifier with train dataset             
        MultilayerPerceptron ann = (MultilayerPerceptron) mg.buildClassifier(traindataset);

        // Evaluate classifier with test dataset
        String evalsummary = mg.evaluateModel(ann, traindataset, testdataset);
        System.out.println("Evaluation: " + evalsummary);

        //Save model 
        mg.saveModel(ann, MODElPATH);

        //classifiy a single instance 
//        ModelClassifier cls = new ModelClassifier();
//        String classname =cls.classifiy(Filter.useFilter(cls.createInstance(1.653841265,	7.242738199,	0.595243553,	133.3694452,0), filter), MODElPATH);
//        System.out.println("\n The name for the instance:  " +classname);

    }

}
