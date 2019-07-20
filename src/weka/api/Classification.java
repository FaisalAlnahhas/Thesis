package weka.api;
import weka.core.Instances;
import weka.classifiers.functions.SMO;
import weka.core.converters.ConverterUtils.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;

public class Classification {
	public static void main(String [] args) throws Exception{
		DataSource source = new DataSource("/Users/faisal/Desktop/iris.arff");
		Instances dataset = source.getDataSet();
//		Instances dataset = new Instances(new BufferedReader(new FileReader("/Users/faisal/Desktop/iris.arff")));

		dataset.setClassIndex(dataset.numAttributes()-1);
		SMO svm = new SMO();
		svm.buildClassifier(dataset);
		System.out.println(svm.getCapabilities().toString());

	}

}
