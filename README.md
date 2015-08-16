# Java Data Mining Package
> #### A Java library for machine learning and data analytics

## Project Website: 

https://jdmp.org

## About

The Java Data Mining Package (JDMP) is an open source Java library for data analysis and machine learning. 
It facilitates the access to data sources and machine learning algorithms (e.g. clustering, regression, 
classification, graphical models, optimization) and provides visualization modules. 
JDMP provides a number of algorithms and tools, but also interfaces to other machine learning and data 
mining packages (Weka, LibLinear, Elasticsearch, LibSVM, Mallet, Lucene, Octave).

##In a Nutshell:

- Includes many machine learning algorithms
- Multi-threaded and lighting fast
- Handle terabyte-sized data
- Visualize and edit as heatmap, graph, plot
- Treat every type of data as a matrix
- TXT, CSV, PNG, JPG, HTML, XLS, XLSX, PDF, LaTeX, Matlab, MDB
- Free and open source (LGPL)

## Quick Start

```
// load example data set
ListDataSet dataSet = DataSet.Factory.IRIS();

// create a classifier
NaiveBayesClassifier classifier = new NaiveBayesClassifier();

// train the classifier using all data
classifier.trainAll(dataSet);

// use the classifier to make predictions
classifier.predictAll(dataSet);

// get the results
double accuracy = dataSet.getAccuracy();

System.out.println("accuracy: " + accurary);
```

## References

- Holger Arndt: [The Java Data Mining Package – A Data Processing Library for Java](https://holger-arndt.de/library/COMPSAC2009-jdmp-draft.pdf), 33rd Annual IEEE International Computer Software and Applications Conference (COMPSAC), 2009

## License

The Java Data Mining Package is licensed under the [GNU Lesser General Public License v3.0](http://www.gnu.org/licenses/lgpl-3.0.en.html).

