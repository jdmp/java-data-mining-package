package org.jdmp.core.dataset;

import org.jdmp.core.matrix.wrappers.DataSetPredictedMatrixWrapper;
import org.jdmp.core.matrix.wrappers.DataSetTargetOutputMatrixWrapper;
import org.jdmp.core.sample.DefaultSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.collections.DefaultMatrixList;
import org.ujmp.core.collections.MatrixList;
import org.ujmp.core.exceptions.MatrixException;

public class RegressionDataSet extends BasicDataSet {
	private static final long serialVersionUID = -3243395577983195632L;

	public static final int RMSE = 0;

	private Matrix predictedMatrix = null;

	protected Matrix targetMatrix = null;

	public RegressionDataSet(String label) {
		super(label);
		getVariableList().put(RMSE, new DefaultVariable("RMSE", 10000));
	}

	public RegressionDataSet() {
		this(null);
	}

	public static RegressionDataSet copyFromMatrix(Matrix input, Matrix target)
			throws MatrixException {
		RegressionDataSet ds = new RegressionDataSet();
		for (int i = 0; i < input.getRowCount(); i++) {
			Sample s = new DefaultSample();
			Matrix in = input.subMatrix(Ret.NEW, i, 0, i, input.getColumnCount() - 1);
			Matrix out = target.subMatrix(Ret.NEW, i, 0, i, target.getColumnCount() - 1);
			s.setMatrix(Sample.INPUT, in);
			s.setMatrix(Sample.TARGET, out);
			ds.getSampleList().add(s);
		}
		return ds;
	}

	public RegressionDataSet(String label, Matrix input, Matrix target) {
		this(label);
		long count = input.getRowCount();
		for (int i = 0; i < count; i++) {
			Sample s = new DefaultSample();

			Matrix si = input.subMatrix(Ret.LINK, i, 0, i, input.getColumnCount() - 1);
			Matrix so = target.subMatrix(Ret.LINK, i, 0, i, target.getColumnCount() - 1);

			s.setMatrix(Sample.INPUT, si);
			s.setMatrix(Sample.TARGET, so);

			getSampleList().add(s);
		}
	}

	public RegressionDataSet(Matrix input, Matrix targetOutput) {
		this(null, input, targetOutput);
	}

	public static final RegressionDataSet create(String label, Matrix input, Matrix targetOutput) {
		return new RegressionDataSet(label, input, targetOutput);
	}

	public static final RegressionDataSet create(Matrix input, Matrix targetOutput) {
		return new RegressionDataSet(null, input, targetOutput);
	}

	public Variable getRMSEVariable() {
		return getVariableList().get(RMSE);
	}

	public void appendRMSEMatrix(Matrix m) {
		getRMSEVariable().addMatrix(m);
	}

	public Matrix getTargetMatrix() {
		if (targetMatrix == null) {
			targetMatrix = new DataSetTargetOutputMatrixWrapper(this);
		}
		return targetMatrix;
	}

	public double getEarlyStoppingRMSE(int numberOfSteps) {
		int index = getEarlyStoppingIndex(numberOfSteps);
		if (index >= 0) {
			return getRMSEVariable().getMatrix(index).getEuklideanValue();
		}
		return -1;
	}

	public boolean isEarlyStoppingReached(int numberOfSteps) {
		return getEarlyStoppingIndex(numberOfSteps) >= 0;
	}

	public int getEarlyStoppingIndex(int numberOfSteps) {
		Variable v = getRMSEVariable();
		if (v.getMatrixCount() <= numberOfSteps) {
			return -1;
		}

		double minRMSE = Double.MAX_VALUE;
		int position = -1;
		for (int i = 0; i < v.getMatrixCount(); i++) {
			double e = v.getMatrix(i).getEuklideanValue();
			if (e < minRMSE) {
				minRMSE = e;
				position = i;
			}
			if (i == position + numberOfSteps) {
				return position;
			}
		}
		return -1;
	}

	public MatrixList getMatrixList() {
		if (matrixList == null) {
			matrixList = new DefaultMatrixList();
			matrixList.add(getInputMatrix());
			matrixList.add(getPredictedMatrix());
			matrixList.add(getTargetMatrix());
		}
		return matrixList;
	}

	public Matrix getPredictedMatrix() {
		if (predictedMatrix == null) {
			predictedMatrix = new DataSetPredictedMatrixWrapper(this);
		}
		return predictedMatrix;
	}

	public double getRMSE() throws MatrixException {
		Matrix m = getRMSEMatrix();
		if (m == null) {
			return Double.NaN;
		} else {
			return m.getEuklideanValue();
		}
	}

	public Matrix getRMSEMatrix() {
		return getRMSEVariable().getMatrix();
	}

	public int getFeatureCount() {
		Matrix m = getInputMatrix();
		if (m == null) {
			return 0;
		} else {
			return (int) m.getColumnCount();
		}
	}
}
