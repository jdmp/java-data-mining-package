package org.jdmp.core.algorithm;

import java.util.List;
import java.util.concurrent.Callable;

import org.jdmp.core.CoreObject;
import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;

public interface Algorithm extends CoreObject, HasVariableMap, HasAlgorithms,
		Callable<List<Matrix>> {

	public static final int NOTCONNECTED = 0;

	public static final int INCOMING = 1;

	public static final int OUTGOING = 2;

	public static final int BIDIRECTIONAL = 3;

	public void setVariable(int index, Variable variable);

	public void setAlgorithm(int index, Algorithm a);

	public void createVariablesAndAlgorithms();

	public void clear();

	public void startCalculate();

	public void endCalculate();

	public List<Matrix> calculate() throws Exception;

	public List<Matrix> calculate(Matrix... input) throws Exception;

	public List<Matrix> calculate(double... input) throws Exception;

	public abstract List<Matrix> calculate(List<Matrix> matrices) throws Exception;

	public void addAlgorithmListener(AlgorithmListener l);

	public void removeAlgorithmListener(AlgorithmListener l);

	public int getEdgeDirectionForVariable(int index);

	public int getEdgeDirectionForAlgorithm(int index);

	public List<Matrix> calculateAlgorithmForId(int id) throws Exception;

	public Matrix getMatrixFromVariable(int variableIndex, int matrixIndex);

	public void setMatrixForVariable(int variableIndex, int matrixIndex, Matrix matrix);

	public String getEdgeLabelForVariable(int index);

	public String getEdgeLabelForAlgorithm(int index);

}
