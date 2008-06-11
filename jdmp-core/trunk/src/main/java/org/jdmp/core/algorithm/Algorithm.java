package org.jdmp.core.algorithm;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;

import org.jdmp.core.CoreObject;
import org.jdmp.core.util.interfaces.HasAlgorithmsAndVariables;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;

public interface Algorithm extends CoreObject, HasAlgorithmsAndVariables, HasVariables,
		HasAlgorithms, Callable<List<Matrix>> {

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

	public boolean isRunning();

	public void dispose();

	public void start();

	public void stop();

	public int getInterval();

	public int getStepsToDo();

	public void increaseStepsToDo();

	public void decreaseStepsToDo();

	public void setStepsToDo(int stepsToDo);

	public void createAndStartThreads();

	public void stopAndDestroyThreads();

	public void increaseCount();

	public int getCallsPerSecond();

	public List<Matrix> calculate() throws Exception;

	public List<Matrix> call() throws Exception;

	public List<Matrix> calculate(Matrix... input) throws Exception;

	public List<Matrix> calculate(double... input) throws Exception;

	public abstract List<Matrix> calculate(List<Matrix> matrices) throws Exception;

	public void _calculate();

	public int getCount();

	public void fireCountIncreased();

	public void fireCountIncreased(AlgorithmEvent e);

	public void addAlgorithmListener(AlgorithmListener l);

	public void removeAlgorithmListener(AlgorithmListener l);

	public void setCallsPerSecond(int i);

	public int getEdgeDirectionForVariable(int index);

	public int getEdgeDirectionForAlgorithm(int index);

	public Matrix getMatrixFromVariable(int variableIndex);

	public List<Matrix> calculateAlgorithmForId(int id) throws Exception;

	public Matrix getMatrixFromVariable(int variableIndex, int matrixIndex);

	public void addMatrixForVariable(int variableIndex, Matrix matrix);

	public void setMatrixForVariable(int variableIndex, int matrixIndex, Matrix matrix);

	public void setInterval(int intervall);

	public long getRuntime();

	public void setRuntime(long runTime);

	public String getEdgeLabelForVariable(int index);

	public String getEdgeLabelForAlgorithm(int index);

	public double getCalculateTime();

	public void importFromFile(File file);
}
