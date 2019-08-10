# User Syndication System Using Speech Rhythm

## Setup: 

1)To use this code please download latest stable  Weka API version from https://www.cs.waikato.ac.nz/ml/weka/downloading.html

2)Add weka.jar to the classpath of this project

## Usage:
Feature extraction: the JAR feature extractor file can be found in the weka2-api/Data named FeatureExtractor-2.4.jar
Example: java -jar FeatureExtractor-2.4 onset user.wav
Note: the .wav file has to be compliant with the specs TarsosDSP library (16-bit wave file) you can use https://audio.online-convert.com/convert-to-wav to convert other forms of sound files to .wav files

The full code real-time audio processing library can be found on https://github.com/JorenSix/TarsosDSP you will need ant to build it and use it. Checkout the README.md in the repo for more details

## Algorithm:
Weka.api.attempts: includes code samples for using Weka, as well as working with some various file formats used. This package is only for demonstration and does not contribute to the actual results of the algorithm. It helps in showing basics of using Weka's API.

Weka.api.automation: includes helper classes that make formatting data and usage easier to accomplish classification later.

Weka.api.algorithm: includes the main code for the system

ModelGenerator: building classifier, loading data, setting parameters, evaluation

ModelClassifier: adding attributes, users, and instances to the model

Trial: the main that puts the ModelGenerator and ModelClassifier together. In this file specify the paths for all files used in the algorithm, as well as some other parameters. Run this class to see output of algorithm.

Models: separate algorithm from the previous one. This file uses some of the built-in Weka models to do classification. You can import and use a variety of other algorithm. All such algorithms can be found at: 
http://weka.sourceforge.net/doc.dev/weka/classifiers/Classifier.html

## Data:
The data folder includes samples .arff files used in this research project. Reference the source section below to understand how to use .arff files.

## TODO:
Incorporate an automation to record .wav files then pass automatically to the feature extractor for testing. Example library: https://github.com/adrielcafe/AndroidAudioRecorder 


To improve the system I recommend automating the feature extraction and parsing process. Include codes similar to

	Runtime rt = Runtime.getRuntime();
	
    Process pr = rt.exec("java -jar FeaturesExtractor-2.4.jar <option> <filename>.wav");
    	
Then use the automation classes to help structure the input in .arff format for the mlp

## NOTES:
Running the feature extractor will return some desired, and undesired, values (reference the library for more details on values returned by the feature extractor). Be sure to parse the values as fits the ModelClassifier to 

	public Instances createInstance(double onset, double onset_diff, double sal, double prob, double result)
 
Or change the parameters of ModeClassifier to fit the features extracted from TarsosDSP library.


## Sources
1) TarsosDSP: https://github.com/JorenSix/TarsosDSP

2) Weka: https://www.cs.waikato.ac.nz/ml/weka/downloading.html

3) arff: https://www.cs.waikato.ac.nz/~ml/weka/arff.html
