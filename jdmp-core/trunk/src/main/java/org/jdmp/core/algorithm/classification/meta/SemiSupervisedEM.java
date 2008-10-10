package org.jdmp.core.algorithm.classification.meta;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;

public class SemiSupervisedEM extends AbstractClassifier implements SemiSupervisedClassifier {
	private static final long serialVersionUID = 7362798845466035645L;

	private int iterations = 10;

	private Classifier classifier = null;

	private boolean useRawPrediction = false;

	public SemiSupervisedEM(Classifier singleClassClassifier, boolean useRawPrediction) {
		super("MultiClassClassifier [" + singleClassClassifier.toString() + "]");
		this.classifier = singleClassClassifier;
		this.useRawPrediction = useRawPrediction;
	}

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		return classifier.predict(input, sampleWeight);
	}

	@Override
	public void reset() throws Exception {
		classifier.reset();
	}

	public void train(RegressionDataSet dataSet) throws Exception {
		throw new Exception("use train(labeledData,unlabeledData)");
	}

	public void train(RegressionDataSet labeledData, RegressionDataSet unlabeledData)
			throws Exception {
		int classCount = ((ClassificationDataSet) labeledData).getClassCount();
		System.out.println("Step 0");
		classifier.reset();
		classifier.train(labeledData);
		classifier.predict(unlabeledData);
		for (Sample s : unlabeledData.getSampleList()) {
			Matrix predicted = s.getMatrix(Sample.PREDICTED);
			if (useRawPrediction) {
				s.setMatrix(Sample.TARGET, predicted);
			} else {
				int max = (int) predicted.indexOfMax(Ret.NEW, Matrix.COLUMN).getAsDouble(0, 0);
				Matrix target = MatrixFactory.zeros(1, classCount);
				target.setAsDouble(1.0, 0, max);
				s.setMatrix(Sample.TARGET, target);
			}
		}
		ClassificationDataSet completeData = new ClassificationDataSet();
		completeData.getSampleList().addAll(labeledData.getSampleList().toCollection());
		completeData.getSampleList().addAll(unlabeledData.getSampleList().toCollection());
		classifier.reset();
		classifier.train(completeData);

		for (int i = 0; i < iterations; i++) {
			System.out.println("Step " + (i + 1));
			classifier.predict(unlabeledData);
			for (Sample s : unlabeledData.getSampleList()) {
				Matrix predicted = s.getMatrix(Sample.PREDICTED);
				int max = (int) predicted.indexOfMax(Ret.NEW, Matrix.COLUMN).getAsDouble(0, 0);
				Matrix target = MatrixFactory.zeros(1, classCount);
				target.setAsDouble(1.0, 0, max);
				s.setMatrix(Sample.TARGET, target);
			}
			completeData = new ClassificationDataSet();
			completeData.getSampleList().addAll(labeledData.getSampleList().toCollection());
			completeData.getSampleList().addAll(unlabeledData.getSampleList().toCollection());
			classifier.reset();
			classifier.train(completeData);
		}
	}

	public Classifier emptyCopy() throws Exception {
		return new SemiSupervisedEM(classifier.emptyCopy(), useRawPrediction);
	}

	@Override
	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw new Exception("not supported");
	}

}
