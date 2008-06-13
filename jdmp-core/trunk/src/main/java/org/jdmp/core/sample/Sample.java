package org.jdmp.core.sample;

import org.jdmp.core.CoreObject;
import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.HasMatrix;

public interface Sample extends CoreObject, HasVariableMap, HasMatrix {

	public Sample clone();

	public Matrix getInputMatrix();

}
