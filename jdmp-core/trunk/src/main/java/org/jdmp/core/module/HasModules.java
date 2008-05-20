package org.jdmp.core.module;

import java.util.List;

public interface HasModules {

	public void addModule(Module m);

	public int getModuleCount();

	public Module getModule(int pos);

	public void removeModule(Module m);

	public void addModuleListListener(ModuleListListener l);

	public void removeModuleListListener(ModuleListListener l);

	public int getIndexOfModule(Module i);

	public List<Module> getModuleList();

	public void fireModuleAdded(ModuleListEvent e);

	public void fireModuleRemoved(ModuleListEvent e);

	public void fireModuleUpdated(ModuleListEvent e);
}
