package org.jdmp.core.sample;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableListListener;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.GUIObject;
import org.jdmp.matrix.interfaces.HasMatrix;

public abstract class AbstractSample extends AbstractCoreObject implements Sample, HasVariables,
		HasMatrix {

	private transient GUIObject guiObject=null;
	
	private final ObservableMap<Variable> variableMap = new ObservableMap<Variable>();

	public AbstractSample() {
		super();
	}

	public AbstractSample(String label) {
		this();
		setLabel(label);
	}

	public abstract Sample clone();

	public void dispose() {
	}

	public final void setVariable(int index, Variable v) {
		variableMap.put(index, v);
	}







	public final String getShortStatus() {
		return getLabel();
	}



	public ObservableMap<Variable> getVariableList() {
		return variableMap;
	}

	public int getVariableCount() {
		return getVariableList().getSize();
	}

	public abstract Matrix getInputMatrix();

	public Variable getVariable(Object pos) {
			return variableMap.get(pos);
	}

	public int getIndexOfVariable(Variable variable) {
		return getVariableList().indexOf(variable);
	}



	public void addVariableListListener(VariableListListener l) {
		getListenerList().add(VariableListListener.class, l);
	}

	public void removeVariableListListener(VariableListListener l) {
		getListenerList().add(VariableListListener.class, l);
	}



	public String getLongStatus() {
		return null;
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
