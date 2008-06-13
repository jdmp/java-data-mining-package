package org.jdmp.core.sample;

import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;

public class BasicSample extends AbstractSample {
	private static final long serialVersionUID = -3649758882404748630L;

	public BasicSample(Matrix m) {
		this(m.getLabel());
		setInputMatrix(m);
	}

	public BasicSample(String label) {
		super(label);
	}

	public BasicSample() {
		super();
	}

	public BasicSample clone() {
		BasicSample s = new BasicSample();
		s.setInputMatrix(getInputMatrix().clone());
		return s;
	}

	public Variable getInputVariable() {
		Variable v = getVariableList().get(Regressor.INPUT);
		if (v == null) {
			v = new DefaultVariable("Input", 1);
			getVariableList().put(Regressor.INPUT, v);
		}
		return v;
	}

	public Matrix getInputMatrix() {
		return getInputVariable().getMatrix();
	}

	public void setInputMatrix(Matrix input) {
		getInputVariable().addMatrix(input);
	}

}
