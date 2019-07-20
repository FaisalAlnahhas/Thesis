package weka.api.automation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public static void main(String[] args) {

        String csvFile = "/Users/faisal/Desktop/csvtestfile.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String [] frequency;

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                if (!data[1].equals("-1.0")) {// && ( Double.parseDouble(data[3])>85 || Double.parseDouble(data[3])<225)) {
                	System.out.print( data[2] + "\n");
                }
                

//                System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

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

    }

}
