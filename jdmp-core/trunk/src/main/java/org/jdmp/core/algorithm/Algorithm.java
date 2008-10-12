package org.jdmp.core.algorithm;

import java.util.List;
import java.util.Map;

import org.jdmp.core.CoreObject;
import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;

public interface Algorithm extends CoreObject, HasVariableMap, HasAlgorithmMap {

	public enum EdgeDirection {
		NotConnected, Incoming, Outgoing, Bidirectional
	};

	public static final int NOTCONNECTED = 0;

	public static final int INCOMING = 1;

	public static final int OUTGOING = 2;

	public static final int BIDIRECTIONAL = 3;

	public void setVariable(Object key, Variable variable);

	public void setAlgorithm(Object key, Algorithm a);

	public Map<Object, Matrix> calculate() throws Exception;

	public Map<Object, Matrix> calculate(Matrix... input) throws Exception;

	public Map<Object, Matrix> calculate(double... input) throws Exception;

	public Map<Object, Matrix> calculate(List<Matrix> matrices) throws Exception;

	public Map<Object, Matrix> calculate(Map<Object, Matrix> matrices) throws Exception;

	public EdgeDirection getEdgeDirection(Object key);

	public Matrix getMatrixFromVariable(Object key, int matrixIndex);

	public void setMatrixForVariable(Object key, int matrixIndex, Matrix matrix);

	public String getEdgeLabel(Object key);

	public void setEdgeDirection(Object key, EdgeDirection direction);

	public void setEdgeLabel(Object key, String edgeLabel);

	public void addVariableKey(Object key);

	public List<Object> getVariableKeys();

	public List<Object> getInputKeys();

	public List<Object> getOutputKeys();

}
