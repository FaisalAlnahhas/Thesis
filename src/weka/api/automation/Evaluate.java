package weka.api.automation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Evaluate {

    public static List<double[]> Evluate(String csvFile) {
    	double [] vot = new double[100];
        double [] vot_diff = new double[100];
        double [] sal = new double[100];
        double [] pitch = new double[100];
        double [] prob = new double[100];
        String [] names = new String[100];
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String [] frequency;
        
        System.out.println("here");
        
        int i=0;

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                vot[i] = Double.parseDouble(data[i]);
                vot_diff[i] = Double.parseDouble(data[i]);
                sal[i] = Double.parseDouble(data[i]);
                pitch[i] = Double.parseDouble(data[i]);
                prob[i] = Double.parseDouble(data[i]);
                names[i] = data[i];
                i +=1;
                System.out.println(vot[i]);

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
        
        return Arrays.asList(vot, vot_diff, sal, pitch, prob);

    }
    
    public static void main(String[] args) {
    	String TESTPATH = "/Users/faisal/Desktop/csvtrial.csv";
    	Evaluate eval = new Evaluate();
    	eval.Evluate(TESTPATH);
    }
    
    
}
