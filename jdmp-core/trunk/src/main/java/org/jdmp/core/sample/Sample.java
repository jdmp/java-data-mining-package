package org.jdmp.core.sample;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.AbstractVariable;
import org.jdmp.core.variable.VariableListEvent;
import org.jdmp.core.variable.VariableListListener;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.HasMatrix;

public abstract class Sample extends AbstractGUIObject implements HasVariables, HasMatrix {

	private final List<AbstractVariable> variableMap = new CopyOnWriteArrayList<AbstractVariable>();

	public Sample() {
		super();
	}

	public Sample(String label) {
		this();
		setLabel(label);
	}

	public abstract Sample clone();
	
	public void dispose() {
		clear();
	}

	public final void setVariable(int index, AbstractVariable v) {
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

	public List<AbstractVariable> getVariableList() {
		return Collections.unmodifiableList(variableMap);
	}

	public int getVariableCount() {
		return getVariableList().size();
	}

	public abstract Matrix getInputMatrix();

	public AbstractVariable getVariable(int pos) {
		if (pos < variableMap.size()) {
			return variableMap.get(pos);
		} else {
			return null;
		}
	}

	public int getIndexOfVariable(AbstractVariable variable) {
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

	public void addVariable(AbstractVariable v) {
	}

	public void removeVariable(AbstractVariable v) {
	}

	@Override
	public String getLongStatus() {
		return null;
	}

	@Override
	public void clear() {
	}

}
