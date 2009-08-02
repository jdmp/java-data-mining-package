package org.jdmp.core.variable;

import java.util.EventListener;

public interface VariableListListener extends EventListener {

	public void variableAdded(VariableListEvent e);

	public void variableRemoved(VariableListEvent e);

	public void variableUpdated(VariableListEvent e);
}
