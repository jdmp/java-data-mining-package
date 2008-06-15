package org.jdmp.core.sample;

import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;

public class RegressionSample extends DefaultSample {
	private static final long serialVersionUID = -7326840837391243268L;

	public RegressionSample(String label) {
		super(label);
	}

	public RegressionSample() {
		super();
	}

	public RegressionSample clone() {
		RegressionSample s = new RegressionSample();
		s.setMatrix(INPUT, getMatrix(INPUT).clone());
		s.setMatrix(TARGET, getMatrix(TARGET).clone());
		return s;
	}

	public Variable getOutputVariable() {
		Variable v = getVariableList().get(Regressor.PREDICTED);
		if (v == null) {
			v = new DefaultVariable("Output");
			getVariableList().put(Regressor.PREDICTED, v);
		}
		return v;
	}

	public Variable getDesiredOutputVariable() {
		Variable v = getVariableList().get(Regressor.TARGET);
		if (v == null) {
			v = new DefaultVariable("Desired Output");
			getVariableList().put(Regressor.TARGET, v);
		}
		return v;
	}

	public Variable getOutputErrorVariable() {
		Variable v = getVariableList().get(Regressor.DIFFERENCE);
		if (v == null) {
			v = new DefaultVariable("Output Error");
			getVariableList().put(Regressor.DIFFERENCE, v);
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

	public void setOutputErrorMatrix(Matrix outputError) {
		getOutputErrorVariable().addMatrix(outputError);
	}

}
