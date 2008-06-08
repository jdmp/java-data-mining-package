package org.jdmp.core.sample;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableListEvent;
import org.jdmp.core.variable.VariableListListener;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.GUIObject;
import org.jdmp.matrix.interfaces.HasMatrix;

public abstract class AbstractSample extends AbstractCoreObject implements Sample, HasVariables,
		HasMatrix {

	private transient GUIObject guiObject=null;
	
	private final List<Variable> variableMap = new CopyOnWriteArrayList<Variable>();

	public AbstractSample() {
		super();
	}

	public AbstractSample(String label) {
		this();
		setLabel(label);
	}

	public abstract Sample clone();

	public void dispose() {
		clear();
	}

	public final void setVariable(int index, Variable v) {
		while (variableMap.size() <= index) {
			variableMap.add(null);
		}
		variableMap.set(index, v);
	}

	public void fireVariableAdded(VariableListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof VariableListListener) {
				((VariableListListener) o).variableAdded(e);
			}
		}
	}

	public void fireVariableRemoved(VariableListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof VariableListListener) {
				((VariableListListener) o).variableRemoved(e);
			}
		}
	}

	public void fireVariableUpdated(VariableListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof VariableListListener) {
				((VariableListListener) o).variableUpdated(e);
			}
		}
	}

	public final String getShortStatus() {
		return getLabel();
	}

	public double getLength() {
		return 0.0;
	}

	public List<Variable> getVariableList() {
		return Collections.unmodifiableList(variableMap);
	}

	public int getVariableCount() {
		return getVariableList().size();
	}

	public abstract Matrix getInputMatrix();

	public Variable getVariable(int pos) {
		if (pos < variableMap.size()) {
			return variableMap.get(pos);
		} else {
			return null;
		}
	}

	public int getIndexOfVariable(Variable variable) {
		return getVariableList().indexOf(variable);
	}

	public void addSampleListener(SampleListener l) {
		getListenerList().add(SampleListener.class, l);
	}

	public void removeSampleListener(SampleListener l) {
		getListenerList().add(SampleListener.class, l);
	}

	public void addVariableListListener(VariableListListener l) {
		getListenerList().add(VariableListListener.class, l);
	}

	public void removeVariableListListener(VariableListListener l) {
		getListenerList().add(VariableListListener.class, l);
	}

	public void addVariable(Variable v) {
	}

	public void removeVariable(Variable v) {
	}

	public String getLongStatus() {
		return null;
	}

	public void clear() {
	}
	
	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.jdmp.gui.sample.SampleGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { Sample.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				logger.log(Level.WARNING, "cannot create sample gui object", e);
			}
		}
		return guiObject;
	}

}
