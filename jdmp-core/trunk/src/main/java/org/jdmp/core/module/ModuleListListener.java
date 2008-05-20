package org.jdmp.core.module;

import java.util.EventListener;

public interface ModuleListListener extends EventListener {

	public void moduleAdded(ModuleListEvent e);

	public void moduleRemoved(ModuleListEvent e);

	public void moduleUpdated(ModuleListEvent e);
}
