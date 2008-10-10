package org.jdmp.core.module;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.interfaces.GUIObject;

public abstract class AbstractModule extends AbstractCoreObject implements Module {
	private transient GUIObject guiObject = null;

	protected final ObservableList<Algorithm> algorithmList = new ObservableList<Algorithm>();

	protected final ObservableList<DataSet> dataSetList = new ObservableList<DataSet>();

	protected final ObservableMap<Variable> variableList = new ObservableMap<Variable>();

	protected final ObservableList<Module> moduleList = new ObservableList<Module>();

	public ObservableList<Module> getModuleList() {
		return moduleList;
	}

	public final ObservableList<DataSet> getDataSetList() {
		return dataSetList;
	}

	public final ObservableList<Algorithm> getAlgorithmList() {
		return algorithmList;
	}

	public final ObservableMap<Variable> getVariableList() {
		return variableList;
	}

	public void clear() {
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.jdmp.gui.module.ModuleGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { Module.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				logger.log(Level.WARNING, "cannot create module gui object", e);
			}
		}
		return guiObject;
	}

	@Override
	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

}
