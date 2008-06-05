package org.jdmp.core.sample;

import org.jdmp.core.matrix.wrappers.SampleInputOutputMatrix;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.exceptions.MatrixException;

public class RegressionSample extends WeightedSample {
	private static final long serialVersionUID = -7326840837391243268L;

	public static final int OUTPUT = 2;

	public static final int DESIREDOUTPUT = 3;

	public static final int OUTPUTERROR = 4;

	public static final int RMSE = 5;


	private transient SampleInputOutputMatrix sampleInputOutputMatrix = null;

	public RegressionSample(String label) {
		super(label);
	}

	public RegressionSample() {
		super();
	}

	public RegressionSample clone() {
		RegressionSample s = new RegressionSample();
		s.setInputMatrix(getInputMatrix().clone());
		s.setDesiredOutputMatrix(getDesiredOutputMatrix().clone());
		return s;
	}

	public void createFromLine(String line, int startPos, int count, int classPos, int classLabelPos)
			throws MatrixException {
		String[] fields = line.split(" ");

		getInputVariable().setMemorySize(1);
		getInputVariable().setSize(count, 1);

		getDesiredOutputVariable().setMemorySize(1);
		getDesiredOutputVariable().setSize(1, 1);
		getDesiredOutputVariable().addMatrix(MatrixFactory.linkToValue(Double.parseDouble(fields[classPos])));

		Matrix m = MatrixFactory.zeros(getInputVariable().getRowCount(), 1);
		for (int i = 0; i < getInputVariable().getRowCount(); i++) {
			m.setDouble(Double.parseDouble(fields[i + startPos]), i, 0);
		}
		getInputVariable().addMatrix(m);

		setLabel(fields[classLabelPos]);
	}

	public Variable getOutputVariable() {
		Variable v = getVariable(OUTPUT);
		if (v == null) {
			v = new DefaultVariable("Output");
			setVariable(OUTPUT, v);
		}
		return v;
	}

	public Variable getDesiredOutputVariable() {
		Variable v = getVariable(DESIREDOUTPUT);
		if (v == null) {
			v = new DefaultVariable("Desired Output");
			setVariable(DESIREDOUTPUT, v);
		}
		return v;
	}

	public Variable getOutputErrorVariable() {
		Variable v = getVariable(OUTPUTERROR);
		if (v == null) {
			v = new DefaultVariable("Output Error");
			setVariable(OUTPUTERROR, v);
		}
		return v;
	}

	public Variable getRMSEVariable() {
		Variable v = getVariable(RMSE);
		if (v == null) {
			v = new DefaultVariable("RMSE");
			setVariable(RMSE, v);
		}
		return v;
	}

	public void setOutputMatrix(Matrix output) {
		getOutputVariable().addMatrix(output);
	}

	public void setDesiredOutputMatrix(Matrix output) {
		getDesiredOutputVariable().addMatrix(output);
	}

	public Matrix getDesiredOutputMatrix() {
		return getDesiredOutputVariable().getMatrix();
	}

	public Matrix getOutputErrorMatrix() {
		return getOutputErrorVariable().getMatrix();
	}

	public Matrix getOutputMatrix() {
		return getOutputVariable().getMatrix();
	}

	public Matrix getMatrix() {
		if (sampleInputOutputMatrix == null) {
			sampleInputOutputMatrix = new SampleInputOutputMatrix(this);
		}
		return sampleInputOutputMatrix;
	}

	public void setOutputErrorMatrix(Matrix outputError) {
		getOutputErrorVariable().addMatrix(outputError);
	}

	public void setRMSEMatrix(Matrix err) {
		getRMSEVariable().addMatrix(err);
	}

	public Matrix getRMSEMatrix() {
		return getRMSEVariable().getMatrix();
	}

}
