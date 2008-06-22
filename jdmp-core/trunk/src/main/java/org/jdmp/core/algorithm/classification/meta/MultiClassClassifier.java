package org.jdmp.core.algorithm.classification.meta;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.matrix.Matrix;

public class MultiClassClassifier extends AbstractClassifier {
	private static final long serialVersionUID = 466059743021340944L;

	private Classifier singleClassClassifier = null;

	private List<Classifier> singleClassClassifiers = new ArrayList<Classifier>();

	public MultiClassClassifier(Classifier singleClassClassifier) {
		super("MultiClassClassifier [" + singleClassClassifier.toString() + "]");
		this.singleClassClassifier = singleClassClassifier;
	}

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		return null;
	}

	@Override
	public void reset() throws Exception {
		singleClassClassifiers.clear();
	}

	@Override
	public void train(RegressionDataSet dataSet) throws Exception {
		clear();
		int classCount = ((ClassificationDataSet) dataSet).getClassCount();

		Classifier c = singleClassClassifier.emptyCopy();
		c.reset();

		for (int i = 0; i < classCount; i++) {
			singleClassClassifiers.add(singleClassClassifier.emptyCopy());
		}

	}

	@Override
	public void train(Matrix input, Matrix sampleWeight, Matrix desiredOutput) throws Exception {
		throw new Exception("not supported");
	}

	public Classifier emptyCopy() {
		return new MultiClassClassifier(singleClassClassifier);
	}
}
