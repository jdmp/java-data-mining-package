package org.jdmp.core.module;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetListEvent;
import org.jdmp.core.dataset.DataSetListListener;
import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.util.AbstractEvent.EventType;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.interfaces.GUIObject;

public abstract class AbstractModule extends AbstractCoreObject implements Module {
	private transient GUIObject guiObject = null;

	protected final List<Algorithm> algorithmList = new CopyOnWriteArrayList<Algorithm>();

	protected final List<DataSet> dataSetList = new CopyOnWriteArrayList<DataSet>();

	protected final ObservableMap<Variable> variableList = new ObservableMap<Variable>();

	protected final ObservableList<Module> moduleList = new ObservableList<Module>();

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

	public void addAlgorithm(Algorithm a) {
		algorithmList.add(a);
	}

	public ObservableList<Module> getModuleList() {
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

	public void removeDataSet(DataSet ds) {
		dataSetList.remove(ds);
		fireDataSetRemoved(new DataSetListEvent(this, EventType.REMOVED, ds));
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

	public final void addDataSetListListener(DataSetListListener l) {
		getListenerList().add(DataSetListListener.class, l);
	}

	public final void removeDataSetListListener(DataSetListListener l) {
		getListenerList().remove(DataSetListListener.class, l);
	}

	public final List<Algorithm> getAlgorithmList() {
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
