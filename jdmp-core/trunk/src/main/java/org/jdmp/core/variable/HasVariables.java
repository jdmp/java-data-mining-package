package org.jdmp.core.variable;

import java.util.List;

public interface HasVariables {

	public void addVariable(AbstractVariable v);

	public int getVariableCount();

	public AbstractVariable getVariable(int pos);

	public int getIndexOfVariable(AbstractVariable v);

	public void addVariableListListener(VariableListListener l);

	public void removeVariableListListener(VariableListListener l);

	public List<AbstractVariable> getVariableList();

	public void removeVariable(AbstractVariable variable);

	public void fireVariableAdded(VariableListEvent e);

	public void fireVariableRemoved(VariableListEvent e);

	public void fireVariableUpdated(VariableListEvent e);
}
