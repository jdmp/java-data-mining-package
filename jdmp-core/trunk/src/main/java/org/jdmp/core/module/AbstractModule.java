package org.jdmp.core.module;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.AlgorithmListEvent;
import org.jdmp.core.algorithm.AlgorithmListListener;
import org.jdmp.core.algorithm.HasAlgorithms;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetListEvent;
import org.jdmp.core.dataset.DataSetListListener;
import org.jdmp.core.dataset.HasDataSets;
import org.jdmp.core.util.AbstractCoreObject;
import org.jdmp.core.util.AbstractEvent.EventType;
import org.jdmp.core.util.interfaces.HasAlgorithmsAndVariables;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableListEvent;
import org.jdmp.core.variable.VariableListListener;
import org.jdmp.core.variable.WorkspaceVariable;
import org.jdmp.matrix.interfaces.GUIObject;

public abstract class AbstractModule extends AbstractCoreObject implements Module,
		HasAlgorithmsAndVariables, HasModules, HasAlgorithms, HasVariables, HasDataSets {

	private static Module module = null;
	
	private transient GUIObject guiObject =null;

	protected final List<Algorithm> algorithmList = new CopyOnWriteArrayList<Algorithm>();

	protected final List<DataSet> dataSetList = new CopyOnWriteArrayList<DataSet>();

	protected final List<Variable> variableList = new CopyOnWriteArrayList<Variable>();

	protected final List<Module> moduleList = new CopyOnWriteArrayList<Module>();

	public static final Module getInstance() {
		if (module == null) {
			module = new DefaultModule();
			module.addVariable(WorkspaceVariable.getInstance());
		}
		return module;
	}

	public final void fireVariableAdded(VariableListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof VariableListListener) {
				((VariableListListener) o).variableAdded(e);
			}
		}
	}

	public final void fireVariableRemoved(VariableListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof VariableListListener) {
				((VariableListListener) o).variableRemoved(e);
			}
		}
	}

	public final void fireVariableUpdated(VariableListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof VariableListListener) {
				((VariableListListener) o).variableUpdated(e);
			}
		}
	}

	public final void fireDataSetAdded(DataSetListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof DataSetListListener) {
				((DataSetListListener) o).dataSetAdded(e);
			}
		}
	}

	public final void fireDataSetRemoved(DataSetListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof DataSetListListener) {
				((DataSetListListener) o).dataSetRemoved(e);
			}
		}
	}

	public final void fireDataSetUpdated(DataSetListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof DataSetListListener) {
				((DataSetListListener) o).dataSetUpdated(e);
			}
		}
	}

	public final void fireModuleAdded(ModuleListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof ModuleListListener) {
				((ModuleListListener) o).moduleAdded(e);
			}
		}
	}

	public final void fireModuleRemoved(ModuleListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof ModuleListListener) {
				((ModuleListListener) o).moduleRemoved(e);
			}
		}
	}

	public final void fireModuleUpdated(ModuleListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof ModuleListListener) {
				((ModuleListListener) o).moduleUpdated(e);
			}
		}
	}

	public final void fireAlgorithmAdded(AlgorithmListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof AlgorithmListListener) {
				((AlgorithmListListener) o).algorithmAdded(e);
			}
		}
	}

	public final void fireAlgorithmUpdated(AlgorithmListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof AlgorithmListListener) {
				((AlgorithmListListener) o).algorithmUpdated(e);
			}
		}
	}

	public final void fireAlgorithmRemoved(AlgorithmListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof AlgorithmListListener) {
				((AlgorithmListListener) o).algorithmRemoved(e);
			}
		}
	}

	public void addAlgorithm(Algorithm a) {
		algorithmList.add(a);
		fireAlgorithmAdded(new AlgorithmListEvent(this, EventType.ADDED, a));
	}

	public void addVariable(Variable v) {
		variableList.add(v);
		fireVariableAdded(new VariableListEvent(this, EventType.ADDED, v));
	}

	public void addModule(Module m) {
		moduleList.add(m);
		fireModuleAdded(new ModuleListEvent(this, EventType.ADDED, m));
	}

	public List<Module> getModuleList() {
		return moduleList;
	}

	public void addDataSet(DataSet ds) {
		dataSetList.add(ds);
		fireDataSetAdded(new DataSetListEvent(this, EventType.ADDED, ds));
	}

	public final List<DataSet> getDataSetList() {
		return dataSetList;
	}

	public final int getIndexOfDataSet(DataSet ds) {
		return dataSetList.indexOf(ds);
	}

	public final DataSet getDataSet(int pos) {
		return dataSetList.get(pos);
	}

	public final Module getModule(int pos) {
		return moduleList.get(pos);
	}

	public void addModuleListListener(ModuleListListener l) {
		getListenerList().add(ModuleListListener.class, l);
	}

	public int getIndexOfModule(Module m) {
		return moduleList.indexOf(m);
	}

	public int getModuleCount() {
		return moduleList.size();
	}

	public void removeAlgorithm(Algorithm algorithm) {
		algorithm.dispose();
		algorithmList.remove(algorithm);
		fireAlgorithmRemoved(new AlgorithmListEvent(this, EventType.REMOVED, algorithm));
	}

	public void removeVariable(Variable variable) {
		variable.dispose();
		variableList.remove(variable);
		fireVariableRemoved(new VariableListEvent(this, EventType.REMOVED, variable));
	}

	public void removeDataSet(DataSet ds) {
		ds.dispose();
		dataSetList.remove(ds);
		fireDataSetRemoved(new DataSetListEvent(this, EventType.REMOVED, ds));
	}

	public void removeModule(Module m) {
		m.dispose();
		moduleList.remove(m);
		fireModuleRemoved(new ModuleListEvent(this, EventType.REMOVED, m));
	}

	public void removeModuleListListener(ModuleListListener l) {
		getListenerList().remove(ModuleListListener.class, l);
	}

	public final int getDataSetCount() {
		return dataSetList.size();
	}

	public int getAlgorithmCount() {
		return algorithmList.size();
	}

	public final Algorithm getAlgorithm(int pos) {
		return algorithmList.get(pos);
	}

	public final int getIndexOfAlgorithm(Algorithm algorithm) {
		return algorithmList.indexOf(algorithm);
	}

	public final void addAlgorithmListListener(AlgorithmListListener l) {
		getListenerList().add(AlgorithmListListener.class, l);
	}

	public final void removeAlgorithmListListener(AlgorithmListListener l) {
		getListenerList().add(AlgorithmListListener.class, l);
	}

	public final void addDataSetListListener(DataSetListListener l) {
		getListenerList().add(DataSetListListener.class, l);
	}

	public final void removeDataSetListListener(DataSetListListener l) {
		getListenerList().remove(DataSetListListener.class, l);
	}

	public final void addModuleListener(ModuleListener l) {
		getListenerList().add(ModuleListener.class, l);
	}

	public final void removeModuleListener(ModuleListener l) {
		getListenerList().remove(ModuleListener.class, l);
	}

	public final List<Algorithm> getAlgorithmList() {
		return algorithmList;
	}

	public final int getVariableCount() {
		return variableList.size();
	}

	public final Variable getVariable(int pos) {
		return variableList.get(pos);
	}

	public final List<Variable> getVariableList() {
		return variableList;
	}

	public final int getIndexOfVariable(Variable variable) {
		return variableList.indexOf(variable);
	}

	public final void addVariableListListener(VariableListListener l) {
		getListenerList().add(VariableListListener.class, l);
	}

	public final void removeVariableListListener(VariableListListener l) {
		getListenerList().add(VariableListListener.class, l);
	}

	public void dispose() {
		clear();
	}

	public void clear() {
		while (!moduleList.isEmpty()) {
			Module m = moduleList.get(0);
			removeModule(m);
		}
		while (!dataSetList.isEmpty()) {
			DataSet ds = dataSetList.get(0);
			removeDataSet(ds);
		}
		while (!variableList.isEmpty()) {
			Variable v = variableList.get(0);
			removeVariable(v);
		}
		while (!algorithmList.isEmpty()) {
			Algorithm a = algorithmList.get(0);
			removeAlgorithm(a);
		}
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

}
