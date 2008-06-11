package org.jdmp.core.variable;

import java.util.List;

public interface HasVariables {

	public void addVariable(Variable v);

	public int getVariableCount();

	public Variable getVariable(int pos);

	public int getIndexOfVariable(Variable v);

	public void addVariableListListener(VariableListListener l);

	public void removeVariableListListener(VariableListListener l);

	public List<Variable> getVariableList();

	public void removeVariable(Variable variable);

	public void fireVariableAdded(VariableListEvent e);

	public void fireVariableRemoved(VariableListEvent e);

	public void fireVariableUpdated(VariableListEvent e);
}
