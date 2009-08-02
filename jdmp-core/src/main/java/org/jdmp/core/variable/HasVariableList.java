package org.jdmp.core.variable;

import org.jdmp.core.util.ObservableList;

public interface HasVariableList extends HasVariables {

	public ObservableList<Variable> getVariableList();

}
