package org.jdmp.core.sample;

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

	public Variable getDesiredOutputVariable() {
		Variable v = getVariableList().get(TARGET);
		if (v == null) {
			v = new DefaultVariable("Desired Output");
			getVariableList().put(TARGET, v);
		}
		return v;
	}

	public void setDesiredOutputMatrix(Matrix output) {
		getDesiredOutputVariable().addMatrix(output);
	}

	public Matrix getDesiredOutputMatrix() {
		return getDesiredOutputVariable().getMatrix();
	}

}
