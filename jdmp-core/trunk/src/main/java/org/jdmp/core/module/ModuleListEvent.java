package org.jdmp.core.module;

import org.jdmp.core.util.AbstractEvent;

public class ModuleListEvent extends AbstractEvent {
	private static final long serialVersionUID = 998723920237920377L;

	public ModuleListEvent(HasModules source, EventType type, Object... data) {
		super(source, type, data);
	}
}
