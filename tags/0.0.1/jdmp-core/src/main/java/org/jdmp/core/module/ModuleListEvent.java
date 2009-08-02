package org.jdmp.core.module;

import org.jdmp.core.util.AbstractEvent;
import org.jdmp.core.util.AbstractGUIObject;

public class ModuleListEvent extends AbstractEvent {
	private static final long serialVersionUID = 998723920237920377L;

	public ModuleListEvent(HasModules source, EventType type, Object... data) {
		super((AbstractGUIObject) source, type, data);
	}
}
