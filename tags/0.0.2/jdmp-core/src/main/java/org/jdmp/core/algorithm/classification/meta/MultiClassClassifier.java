package org.jdmp.core.algorithm.classification.meta;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;

public class MultiClassClassifier extends AbstractClassifier {
	private static final long serialVersionUID = 466059743021340944L;

	private Classifier singleClassClassifier = null;

	private int classCount = 0;

	private boolean twoColumns = false;

	private List<Classifier> singleClassClassifiers = new ArrayList<Classifier>();

	public MultiClassClassifier(Classifier singleClassClassifier, boolean twoColumns) {
		super("MultiClassClassifier [" + singleClassClassifier.toString() + "]");
		this.singleClassClassifier = singleClassClassifier;
		this.twoColumns = twoColumns;
	}

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		double[] results = new double[classCount];
		for (int i = 0; i < classCount; i++) {
			Classifier c = singleClassClassifiers.get(i);
			results[i] = c.predict(input, sampleWeight).getAsDouble(0, 0);
		}
		return MatrixFactory.linkToArray(results).transpose();
	}

	@Override
	public void reset() throws Exception {
		singleClassClassifiers.clear();
	}

	@Override
	public void train(RegressionDataSet dataSet) throws Exception {
		reset();
		classCount = ((ClassificationDataSet) dataSet).getClassCount();

		for (int i = 0; i < classCount; i++) {
			System.out.println("Training class " + i);
			Classifier c = singleClassClassifier.emptyCopy();
			singleClassClassifiers.add(c);
			Matrix input = dataSet.getInputMatrix();
			Matrix target = dataSet.getTargetMatrix().selectColumns(Ret.LINK, i);
			if (twoColumns) {
				Matrix target2 = target.minus(1).abs(Ret.NEW);
				target = MatrixFactory.horCat(target, target2);
			}
			ClassificationDataSet ds = ClassificationDataSet.linkToMatrix(input, target);
			c.train(ds);
		}

	}

	@Override
	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw new Exception("not supported");
	}

	public Classifier emptyCopy() {
		return new MultiClassClassifier(singleClassClassifier, twoColumns);
	}
}
