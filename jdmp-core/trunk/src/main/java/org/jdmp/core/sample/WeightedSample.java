package org.jdmp.core.sample;

import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.exceptions.MatrixException;

public class WeightedSample extends BasicSample {
	private static final long serialVersionUID = 8787906424200203777L;

	public static final int WEIGHT = 1;

	public WeightedSample(String label) {
		super(label);
	}

	public WeightedSample() {
		super();
	}


	public Variable getWeightVariable() {
		Variable v = getVariable(WEIGHT);
		if (v == null) {
			v = new DefaultVariable("Weight", 1.0);
			setVariable(WEIGHT, v);
		}
		return v;
	}

	public double getWeight() throws MatrixException {
		return getWeightMatrix().getDoubleValue();
	}

	public void setWeight(double weight) {
		Matrix m = MatrixFactory.linkToValue(weight);
		getWeightVariable().addMatrix(m);
	}

	public Matrix getWeightMatrix() {
		return getWeightVariable().getMatrix();
	}

}
