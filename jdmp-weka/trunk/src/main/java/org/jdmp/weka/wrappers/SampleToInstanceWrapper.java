package org.jdmp.weka.wrappers;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;

import weka.core.Instance;

public class SampleToInstanceWrapper extends Instance {
	private static final long serialVersionUID = 6525723600252564795L;

	public SampleToInstanceWrapper(Matrix input, Matrix sampleWeight, Matrix targetOutput,
			boolean discrete, boolean includeTarget) throws MatrixException {
		super((int) input.getColumnCount() + 1);
		if (sampleWeight != null) {
			setWeight(sampleWeight.getDoubleValue());
		} else {
			setWeight(1.0);
		}
		if (input != null) {
			for (int i = 0; i < input.getColumnCount(); i++) {
				if (discrete) {
					setValue(i, (int) input.getDouble(0, i));
				} else {
					setValue(i, input.getDouble(0, i));
				}
			}
		}
		if (includeTarget && targetOutput != null) {
			long[] c = targetOutput.getCoordinatesOfMaximum();
			setValue((int) input.getColumnCount(), c[Matrix.COLUMN]);
		}
	}

}
