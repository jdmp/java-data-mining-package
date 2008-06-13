package org.jdmp.core.sample;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.GUIObject;

public abstract class AbstractSample extends AbstractCoreObject implements Sample {

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


	public ObservableMap<Variable> getVariableList() {
		return variableMap;
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

	
	public Matrix getMatrix(Object variableKey){
		Variable v=getVariableList().get(variableKey);
		if(v!=null){
			return v.getMatrix();
		}else{
			return null;
		}
	}

	public void setMatrix(Object variableKey, Matrix matrix){
		Variable v=getVariableList().get(variableKey);
		if(v!=null){
			 v.addMatrix(matrix);
		}
	}
	
}
