package org.jdmp.core.algorithm;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.interfaces.GUIObject;

public abstract class AbstractAlgorithm extends AbstractCoreObject implements Algorithm {

	protected static transient Logger logger = Logger.getLogger(Algorithm.class.getName());

	private transient GUIObject guiObject = null;

	public static final int NOTCONNECTED = 0;

	public static final int INCOMING = 1;

	public static final int OUTGOING = 2;

	public static final int BIDIRECTIONAL = 3;

	private final ObservableMap<Variable> variableMap = new ObservableMap<Variable>();

	private final List<Algorithm> algorithmList = new CopyOnWriteArrayList<Algorithm>();

	private transient EventListenerList listenerList = null;

	private String label = "";

	private String description = "";

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public final void setLabel(String label) {
		this.label = label;
	}

	public final EventListenerList getListenerList() {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		return listenerList;
	}

	public AbstractAlgorithm(String label) {
		super();
		setLabel(label);
	}

	public void setVariable(int index, Variable variable) {
		variableMap.put(index, variable);
	}

	public final void setAlgorithm(int index, Algorithm a) {
		while (algorithmList.size() <= index) {
			algorithmList.add(null);
		}
		algorithmList.set(index, a);
	}

	public void createVariablesAndAlgorithms() {
	}

	public void clear() {
		algorithmList.clear();
		variableMap.clear();
	}

	public final List<Matrix> calculate() throws Exception {
		List<Matrix> input = new LinkedList<Matrix>();
		int size = variableMap.getSize();
		for (int v = 0; v < size; v++) {
			if (getEdgeDirectionForVariable(v) == INCOMING
					|| getEdgeDirectionForVariable(v) == BIDIRECTIONAL) {
				input.add(getMatrixFromVariable(v));
			}
		}
		List<Matrix> output = calculate(input);
		for (int v = 0, i = 0; v < size; v++) {
			if (getEdgeDirectionForVariable(v) == OUTGOING
					|| getEdgeDirectionForVariable(v) == BIDIRECTIONAL) {
				setMatrix(v, output.get(i++));
			}
		}
		return output;
	}

	public final List<Matrix> calculate(Matrix... input) throws Exception {
		List<Matrix> inputA = new LinkedList<Matrix>();
		for (int i = 0; i < input.length; i++) {
			inputA.add(input[i]);
		}
		return calculate(inputA);
	}

	public final List<Matrix> calculate(double... input) throws Exception {
		List<Matrix> inputA = new LinkedList<Matrix>();
		for (int i = 0; i < input.length; i++) {
			inputA.add(MatrixFactory.linkToValue(input[i]));
		}
		return calculate(inputA);
	}

	public abstract List<Matrix> calculate(List<Matrix> matrices) throws Exception;

	public int getEdgeDirectionForVariable(int index) {
		return NOTCONNECTED;
	}

	public int getEdgeDirectionForAlgorithm(int index) {
		return NOTCONNECTED;
	}

	public final Matrix getMatrixFromVariable(int variableIndex) {
		Variable v = variableMap.get(variableIndex);
		if (v == null)
			return null;
		else
			return v.getMatrix();
	}

	public final List<Matrix> calculateAlgorithmForId(int id) throws Exception {
		return getAlgorithm(id).calculate();
	}

	public final Matrix getMatrixFromVariable(int variableIndex, int matrixIndex) {
		Variable v = variableMap.get(variableIndex);
		if (v == null)
			return null;
		else
			return v.getMatrix(matrixIndex);
	}

	public Matrix getMatrix(Object variableKey) {
		Variable v = getVariableList().get(variableKey);
		if (v != null) {
			return v.getMatrix();
		} else {
			return null;
		}
	}

	public void setMatrix(Object variableKey, Matrix matrix) {
		Variable v = getVariableList().get(variableKey);
		if (v == null) {
			v = VariableFactory.labeledVariable(variableKey.toString());
			getVariableList().put(variableKey, v);
		}
		v.addMatrix(matrix);
	}

	public final void setMatrixForVariable(int variableIndex, int matrixIndex, Matrix matrix) {
		Variable v = variableMap.get(variableIndex);
		if (v != null)
			v.setMatrix(matrixIndex, matrix);
	}

	public final ObservableMap<Variable> getVariableList() {
		return variableMap;
	}

	public void addAlgorithm(Algorithm a) {
		setAlgorithm(getAlgorithmCount(), a);
	}

	public final int getAlgorithmCount() {
		return algorithmList.size();
	}

	public final List<Algorithm> getAlgorithmList() {
		return algorithmList;
	}

	public final int getIndexOfAlgorithm(Algorithm a) {
		return algorithmList.indexOf(a);
	}

	public final Algorithm getAlgorithm(int pos) {
		return algorithmList.get(pos);
	}

	public String getEdgeLabelForVariable(int index) {
		return "";
	}

	public String getEdgeLabelForAlgorithm(int index) {
		return "";
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.jdmp.gui.algorithm.AlgorithmGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { Algorithm.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				logger.log(Level.WARNING, "cannot create sample gui object", e);
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
