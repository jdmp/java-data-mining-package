package org.jdmp.libsvm;

import junit.framework.TestCase;

import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.CrossValidation;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.libsvm.SVMClassifier.Kernel;
import org.jdmp.matrix.interfaces.ListMatrix;

public class TestSVMClassifier extends TestCase {

	public void testIrisClassification() throws Exception {
		ClassificationDataSet iris = DataSetFactory.IRIS();
		Classifier c = new SVMClassifier(Kernel.RBF);
		ListMatrix<Double> results = CrossValidation.run(c, iris, 10, 10, 0);
		assertEquals(0.95, results.getMeanValue(), 0.05);
	}

}
