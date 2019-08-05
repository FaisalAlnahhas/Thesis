package weka.api.automation;

public class DifferenceCalculator {

	public void calculate(double[] array) {
		
		if (array.length == 0)
		{
			return;
		}
		
		double diff_array[] = array;
		diff_array[0] = array[0];
		for (int counter = 0; counter < array.length; ++counter) {
		    double diff = counter == 0 ? 0 : array[counter] - array[counter - 1];
		    System.out.println(diff);
		}
	}
	
	public static void main(String[] args) {
		double my_array[] = {
				0.5090974772425195,
				0.7238820994349023,
				0.9212516749353906,
				1.2985760176630519,
				2.0474195683451195,
				};
		DifferenceCalculator calc = new DifferenceCalculator(); 
		calc.calculate(my_array);
	}

}
