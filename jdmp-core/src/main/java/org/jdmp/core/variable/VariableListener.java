package org.jdmp.core.variable;

import java.util.EventListener;

public interface VariableListener extends EventListener {

	public void valueChanged(VariableEvent e);
}
