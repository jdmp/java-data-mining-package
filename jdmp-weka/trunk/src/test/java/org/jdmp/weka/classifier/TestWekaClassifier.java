package org.jdmp.weka.classifier;

import junit.framework.TestCase;

import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.CrossValidation;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.weka.classifier.WekaClassifier.WekaClassifierType;
import org.ujmp.core.ListMatrix;

public class TestWekaClassifier extends TestCase {

	public void testIrisClassification() throws Exception {
		ClassificationDataSet iris = DataSetFactory.IRIS();
		Classifier c = new WekaClassifier(WekaClassifierType.AdaBoostM1, false);
		ListMatrix<Double> results = CrossValidation.run(c, iris, 10, 10, 0);
		assertEquals(0.954, results.getMeanValue(), 0.001);
	}

}
