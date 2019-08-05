package weka.api;

import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class ModelGenerator {
	public Instances loadDataset(String path) {
        Instances dataset = null;
        try {
            dataset = DataSource.read(path);
            if (dataset.classIndex() == -1) {
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }
        } catch (Exception ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataset;
    }
	
	public Classifier buildClassifier(Instances traindataset) {
		MultilayerPerceptron m = new MultilayerPerceptron();
		
		try {
			m.setLearningRate(0.2); //0.2, 9, 300, 0.6, best results
			m.setHiddenLayers("9");
			m.setMomentum(0.6);
			m.setTrainingTime(300); //epochs
			m.buildClassifier(traindataset);

		} catch (Exception ex) {
			 Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
		}
		return m;
	}
	
	public String evaluateModel(Classifier model, Instances traindataset, Instances testdataset) {
        Evaluation eval = null;
        try {
            // Evaluate classifier with test dataset
            eval = new Evaluation(traindataset);
            eval.evaluateModel(model, testdataset);
            double acc = eval.pctCorrect();
            double wrong = eval.pctIncorrect();
            double unclass = eval.pctUnclassified();
            System.out.print("acc: " + acc + "wrong: " + wrong + "unclassified: " + unclass + "\n");
        } catch (Exception ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eval.toSummaryString("", true);
    }

    public void saveModel(Classifier model, String modelpath) {

        try {
            SerializationHelper.write(modelpath, model);
        } catch (Exception ex) {
            Logger.getLogger(ModelGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	

}
