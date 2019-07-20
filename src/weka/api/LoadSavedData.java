package weka.api;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileReader;

public class LoadSavedData {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		DataSource source = new DataSource("/Users/faisal/Desktop/iris.arff");
//		DataSource source = new DataSource("/Users/faisal/Desktop/iris.arff");
		Instances dataset = source.getDataSet();
		//Instances dataset = new Instances(new BufferedReader(new FileReader("/home/likewise-open/ACADEMIC/csstnns/test/house.arff")));		
		
		System.out.println(dataset.toSummaryString());
		
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataset);
		saver.setFile(new File("/Users/faisal/Desktop/new.arff"));
		saver.writeBatch();

	}

}
