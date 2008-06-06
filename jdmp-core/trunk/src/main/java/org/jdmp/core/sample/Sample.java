package org.jdmp.core.sample;

import java.util.List;

import org.jdmp.core.util.CoreObject;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableListEvent;
import org.jdmp.core.variable.VariableListListener;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.HasMatrix;

public interface Sample extends CoreObject, HasVariables, HasMatrix {

	public abstract Sample clone();

	public void dispose();

	public void setVariable(int index, Variable v);

	public void fireVariableAdded(VariableListEvent e);

	public void fireVariableRemoved(VariableListEvent e);

	public void fireVariableUpdated(VariableListEvent e);

	public String getShortStatus();

	public double getLength();

	public List<Variable> getVariableList();

	public int getVariableCount();

	public Matrix getInputMatrix();

	public Variable getVariable(int pos);

	public int getIndexOfVariable(Variable variable);

	public void addSampleListener(SampleListener l);

	public void removeSampleListener(SampleListener l);

	public void addVariableListListener(VariableListListener l);

	public void removeVariableListListener(VariableListListener l);

	public void addVariable(Variable v);

	public void removeVariable(Variable v);

	public String getLongStatus();

	public void clear();
}
