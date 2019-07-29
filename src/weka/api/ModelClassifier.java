package weka.api;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class ModelClassifier {
	private Attribute onset;
	private Attribute sal; 
	private Attribute onset_diff;
	private Attribute pitch;
	private Attribute prob;
	private Attribute beat;
	
	private ArrayList<Attribute> attributes;
	private ArrayList<String> classval;
	
	private Instances dataRaw;
	
	public ModelClassifier() {
		onset = new Attribute("onset");
		sal = new Attribute("sal");
		onset_diff = new Attribute("onset_diff");
		pitch = new Attribute("pitch");
		prob = new Attribute("prob");
		beat = new Attribute("beat");
		attributes = new ArrayList<Attribute>();
		classval = new ArrayList<String>();
		classval.add("Areen");
		classval.add("Daniel");
		classval.add("Eryn");
		classval.add("Jin");
		classval.add("Faisal");
		classval.add("Li");
		classval.add("Peter");
		classval.add("Sandhya");
		classval.add("Sanjay");
		classval.add("Soni");
		classval.add("Xiao");
		classval.add("Yang");
		classval.add("Yafang");
		
		
		attributes.add(onset);
		attributes.add(sal);
		attributes.add(onset_diff);
//		attributes.add(pitch);
		attributes.add(prob);
//		attributes.add(beat);
		attributes.add(new Attribute("name", classval));
		
		dataRaw = new Instances("TestInstances", attributes, 0);
		dataRaw.setClassIndex(dataRaw.numAttributes()-1);
	}
	
	public Instances createInstance(double onset, double onset_diff, double sal, double prob, double result) {
		dataRaw.clear();
		double[] instanceValue1 = new double[] {onset, onset_diff, sal, prob, 0};
		dataRaw.add(new DenseInstance(1.0, instanceValue1));
		return dataRaw;
	}
	
	public String classifiy(Instances insts, String path) {
      String result = "Unkown";
      Classifier cls = null;
      try {
          cls = (MultilayerPerceptron) SerializationHelper.read(path);
          result = classval.get((int) cls.classifyInstance(insts.firstInstance()));
      } catch (Exception ex) {
          Logger.getLogger(ModelClassifier.class.getName()).log(Level.SEVERE, null, ex);
      }
      return result;
  }
	
  public Instances getInstance() {
	  return dataRaw;
  }

	    

}
