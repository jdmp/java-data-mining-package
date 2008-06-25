package org.jdmp.core.variable;

import org.jdmp.core.util.ObservableMap;
import org.jdmp.matrix.Matrix;

public interface HasVariableMap extends HasVariables {

	public ObservableMap<Variable> getVariableList();

	public Matrix getMatrix(Object variableKey);

	public void setMatrix(Object variableKey, Matrix matrix);

}
