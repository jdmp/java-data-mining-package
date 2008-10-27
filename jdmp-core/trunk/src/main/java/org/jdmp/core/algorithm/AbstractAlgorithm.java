package org.jdmp.core.algorithm;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	private final ObservableMap<Algorithm> algorithmList = new ObservableMap<Algorithm>();

	private final Map<Object, String> edgeLabels = new HashMap<Object, String>();

	private final Map<Object, EdgeDirection> edgeDirections = new HashMap<Object, EdgeDirection>();

	private final List<Object> variableKeys = new ArrayList<Object>();

	private String label = "";

	private String description = "";

	public final void setEdgeLabel(Object key, String edgeLabel) {
		edgeLabels.put(key, edgeLabel);
	}

	public final void addVariableKey(Object key) {
		variableKeys.add(key);
	}

	public void setVariables(Variable... variables) {
		for (int i = 0; i < variables.length && i < getVariableKeys().size(); i++) {
			variableMap.put(variableKeys.get(i), variables[i]);
		}
	}

	public final List<Object> getVariableKeys() {
		return variableKeys;
	}

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

	public AbstractAlgorithm() {
		super();
		setLabel(getClass().getSimpleName());
	}

	public final void setEdgeDirection(Object key, EdgeDirection direction) {
		edgeDirections.put(key, direction);
	}

	public void setVariable(Object index, Variable variable) {
		variableMap.put(index, variable);
	}

	public final void setAlgorithm(Object index, Algorithm a) {
		algorithmList.put(index, a);
	}

	public void clear() {
		algorithmList.clear();
		variableMap.clear();
	}

	public final Map<Object, Matrix> calculate() throws Exception {
		Map<Object, Matrix> input = new HashMap<Object, Matrix>();

		for (Object v : getInputKeys()) {
			input.put(v, getMatrixFromVariable(v));
		}

		Map<Object, Matrix> output = calculate(input);

		for (Object v : getOutputKeys()) {
			setMatrix(v, output.get(v));
		}

		return output;
	}

	public final List<Object> getInputKeys() {
		List<Object> inputKeys = new ArrayList<Object>();
		for (Object v : getVariableKeys()) {
			if (EdgeDirection.Incoming.equals(getEdgeDirection(v))
					|| EdgeDirection.Bidirectional.equals(getEdgeDirection(v))) {
				inputKeys.add(v);
			}
		}
		return inputKeys;
	}

	public final List<Object> getOutputKeys() {
		List<Object> outputKeys = new ArrayList<Object>();
		for (Object v : getVariableKeys()) {
			if (EdgeDirection.Outgoing.equals(getEdgeDirection(v))
					|| EdgeDirection.Bidirectional.equals(getEdgeDirection(v))) {
				outputKeys.add(v);
			}
		}
		return outputKeys;
	}

	public final Map<Object, Matrix> calculate(Matrix... input) throws Exception {
		List<Matrix> inputA = new LinkedList<Matrix>();
		for (int i = 0; i < input.length; i++) {
			inputA.add(input[i]);
		}
		return calculate(inputA);
	}

	public final Map<Object, Matrix> calculate(double... input) throws Exception {
		List<Matrix> inputA = new LinkedList<Matrix>();
		for (int i = 0; i < input.length; i++) {
			inputA.add(MatrixFactory.linkToValue(input[i]));
		}
		return calculate(inputA);
	}

	public final Map<Object, Matrix> calculate(List<Matrix> matrices) throws Exception {
		Map<Object, Matrix> map = new HashMap<Object, Matrix>();
		List<Object> keys = getInputKeys();
		for (int i = 0; i < keys.size(); i++) {
			Object key = keys.get(i);
			map.put(key, matrices.get(i));
		}
		return calculate(map);
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> matrices) throws Exception {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();
		return result;
	}

	public final EdgeDirection getEdgeDirection(Object key) {
		return edgeDirections.get(key);
	}

	public final Matrix getMatrixFromVariable(Object variableIndex) {
		Variable v = variableMap.get(variableIndex);
		if (v == null)
			return null;
		else
			return v.getMatrix();
	}

	public final Matrix getMatrixFromVariable(Object variableIndex, int matrixIndex) {
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

	public final void setMatrixForVariable(Object variableIndex, int matrixIndex, Matrix matrix) {
		Variable v = variableMap.get(variableIndex);
		if (v != null)
			v.setMatrix(matrixIndex, matrix);
	}

	public final ObservableMap<Variable> getVariableList() {
		return variableMap;
	}

	public final ObservableMap<Algorithm> getAlgorithmList() {
		return algorithmList;
	}

	public final String getEdgeLabel(Object key) {
		return edgeLabels.get(key);
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
