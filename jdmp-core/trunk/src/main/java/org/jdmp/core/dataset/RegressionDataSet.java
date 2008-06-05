package org.jdmp.core.dataset;

import org.jdmp.core.matrix.wrappers.DataSetDesiredOutputMatrixWrapper;
import org.jdmp.core.matrix.wrappers.DataSetInputOutputMatrixWrapper;
import org.jdmp.core.matrix.wrappers.DataSetOutputMatrixWrapper;
import org.jdmp.core.sample.RegressionSample;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.collections.DefaultMatrixList;
import org.jdmp.matrix.collections.MatrixList;
import org.jdmp.matrix.exceptions.MatrixException;

public class RegressionDataSet extends BasicDataSet {
	private static final long serialVersionUID = -3243395577983195632L;

	public static final int RMSE = 0;

	private Matrix inputOutputMatrix = null;

	private Matrix outputMatrix = null;

	private Matrix desiredOutputMatrix = null;


	public RegressionDataSet(String label) {
		super(label);
		setVariable(RMSE, new DefaultVariable("RMSE", 10000));
	}

	public RegressionDataSet() {
		this(null);
	}

	public static RegressionDataSet copyFromMatrix(Matrix input, Matrix desiredOutput) throws MatrixException {
		RegressionDataSet ds = new RegressionDataSet();
		for (int i = 0; i < input.getRowCount(); i++) {
			RegressionSample s = new RegressionSample();
			Matrix in = input.subMatrix(Ret.NEW, i, 0, i, input.getColumnCount() - 1);
			Matrix out = desiredOutput.subMatrix(Ret.NEW, i, 0, i, desiredOutput.getColumnCount() - 1);
			s.setInputMatrix(in);
			s.setDesiredOutputMatrix(out);
			ds.addSample(s);
		}
		return ds;
	}

	public RegressionDataSet(String label, Matrix input, Matrix desiredOutput) {
		this(label);
		long count = input.getRowCount();
		for (int i = 0; i < count; i++) {
			RegressionSample s = new RegressionSample();

			Matrix si = input.subMatrix(Ret.LINK, i, 0, i, input.getColumnCount() - 1);
			Matrix so = desiredOutput.subMatrix(Ret.LINK, i, 0, i, desiredOutput.getColumnCount() - 1);

			s.setInputMatrix(si);
			s.setDesiredOutputMatrix(so);

			addSample(s);
		}
	}

	public RegressionDataSet(Matrix input, Matrix desiredOutput) {
		this(null, input, desiredOutput);
	}

	public static final RegressionDataSet create(String label, Matrix input, Matrix desiredOutput) {
		return new RegressionDataSet(label, input, desiredOutput);
	}

	public static final RegressionDataSet create(Matrix input, Matrix desiredOutput) {
		return new RegressionDataSet(null, input, desiredOutput);
	}

	public Variable getRMSEVariable() {
		return getVariable(RMSE);
	}

	public void appendRMSEMatrix(Matrix m) {
		getRMSEVariable().addMatrix(m);
	}

	public Matrix getInputOutputMatrix() {
		if (inputOutputMatrix == null) {
			inputOutputMatrix = new DataSetInputOutputMatrixWrapper(this);
		}
		return inputOutputMatrix;
	}

	public Matrix getDesiredOutputMatrix() {
		if (desiredOutputMatrix == null) {
			desiredOutputMatrix = new DataSetDesiredOutputMatrixWrapper(this);
		}
		return desiredOutputMatrix;
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
			matrixList.add(getOutputMatrix());
			matrixList.add(getDesiredOutputMatrix());
			matrixList.add(getInputOutputMatrix());
		}
		return matrixList;
	}

	public Matrix getOutputMatrix() {
		if (outputMatrix == null) {
			outputMatrix = new DataSetOutputMatrixWrapper(this);
		}
		return outputMatrix;
	}

	public RegressionSample getSample(int pos) {
		return (RegressionSample) super.getSample(pos);
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
		return getMatrixFromVariable(RMSE);
	}
}
