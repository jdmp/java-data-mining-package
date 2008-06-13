package org.jdmp.core.sample;

import org.jdmp.core.CoreObject;
import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.matrix.Matrix;

public interface Sample extends CoreObject, HasVariableMap {

	public Sample clone();

	public Matrix getMatrix(Object variableKey);

	public void setMatrix(Object variableKey, Matrix matrix);

}
