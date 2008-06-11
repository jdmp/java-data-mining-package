package org.jdmp.core.sample;

import org.jdmp.core.CoreObject;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.HasMatrix;

public interface Sample extends CoreObject, HasVariables, HasMatrix {

	public Sample clone();

	public void setVariable(int index, Variable v);

	public double getLength();

	public Matrix getInputMatrix();

	public void addSampleListener(SampleListener l);

	public void removeSampleListener(SampleListener l);

	public void clear();
}
