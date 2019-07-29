package weka.api.automation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                System.out.print( data[0] + "," + data[1] + "," + data[2] + "," + data[4] + "," + data[5] + "\n");
                
                

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
