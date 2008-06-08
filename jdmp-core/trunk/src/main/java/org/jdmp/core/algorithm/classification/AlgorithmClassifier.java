package org.jdmp.core.algorithm.classification;

import org.jdmp.core.algorithm.regression.AlgorithmRegression;

public abstract class AlgorithmClassifier extends AlgorithmRegression implements Classifier {

	public AlgorithmClassifier(String label) {
		super(label);
	}

}
