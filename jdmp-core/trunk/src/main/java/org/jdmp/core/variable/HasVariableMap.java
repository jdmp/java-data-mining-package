package org.jdmp.core.variable;

import org.jdmp.core.util.ObservableMap;

public interface HasVariableMap extends HasVariables {

	public ObservableMap<Variable> getVariableList();

}
