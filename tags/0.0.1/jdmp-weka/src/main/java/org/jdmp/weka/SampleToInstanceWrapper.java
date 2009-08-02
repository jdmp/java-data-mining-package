package org.jdmp.weka;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;

import weka.core.Instance;

public class SampleToInstanceWrapper extends Instance {
	private static final long serialVersionUID = 6525723600252564795L;

	public SampleToInstanceWrapper(Matrix input, Matrix weight, Matrix desiredOutput, boolean discrete) throws MatrixException {
		super((int) input.getColumnCount() + 1);
		setWeight(weight.getDoubleValue());
		if (input != null) {
			for (int i = 0; i < input.getColumnCount(); i++) {
			  if(discrete){
			    setValue(i, input.getInt(0, i));
			  }else{
				setValue(i, input.getDouble(0, i));
			  }
			}
		}
		if (desiredOutput != null) {
			long[] c = desiredOutput.getCoordinatesOfMaximum();
			setValue((int) input.getColumnCount(), c[Matrix.COLUMN]);
		}
	}

}
