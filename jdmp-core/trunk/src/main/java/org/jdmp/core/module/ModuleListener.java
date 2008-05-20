package org.jdmp.core.module;

import java.util.EventListener;

public interface ModuleListener extends EventListener {

	public void nextDataSetSelected(ModuleEvent e);

	public void nextModuleSelected(ModuleEvent e);

	public void epochIncreased(ModuleEvent e);

	public void connected(ModuleEvent e);

	public void clientConnected(ModuleEvent e);

	public void disconnected(ModuleEvent e);

	public void clientDisconnected(ModuleEvent e);
}
