package org.jdmp.core.variable;

import org.jdmp.core.util.AbstractEvent;
import org.jdmp.core.util.AbstractGUIObject;

public class VariableListEvent extends AbstractEvent {
	private static final long serialVersionUID = 5575250403306877132L;

	public VariableListEvent(HasVariables source, EventType type, Object... data) {
		super((AbstractGUIObject) source, type, data);
	}

	public HasVariables getIVariables() {
		return (HasVariables) source;
	}
}
