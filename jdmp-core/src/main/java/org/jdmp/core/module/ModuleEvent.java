package org.jdmp.core.module;

import java.util.EventObject;

public class ModuleEvent extends EventObject {
	private static final long serialVersionUID = 4285360341594107061L;

	public ModuleEvent(Module source) {
		super(source);
	}
}
