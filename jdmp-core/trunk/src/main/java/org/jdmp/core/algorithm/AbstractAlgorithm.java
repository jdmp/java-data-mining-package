package org.jdmp.core.algorithm;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.util.AbstractEvent.EventType;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.interfaces.GUIObject;

public abstract class AbstractAlgorithm extends AbstractCoreObject implements Algorithm {

	protected static transient Logger logger = Logger.getLogger(Algorithm.class.getName());

	private transient GUIObject guiObject = null;

	public static final int NOTCONNECTED = 0;

	public static final int INCOMING = 1;

	public static final int OUTGOING = 2;

	public static final int BIDIRECTIONAL = 3;

	private final ObservableMap<Variable> variableList = new ObservableMap<Variable>();

	private final List<Algorithm> algorithmList = new CopyOnWriteArrayList<Algorithm>();

	private int interval = 0;

	private int totalCount = 0;

	private long startTime = 0l;

	private int callsPerSecond = 0;

	private long calculateTime = 0;

	private int stepsToDo = 0;

	private long runtime = 0;

	private transient AlgorithmThread algorithmThread = null;

	private transient Thread algorithmSpeedThread = null;

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
		variableList.put(index, variable);
	}

	public final void setAlgorithm(int index, Algorithm a) {
		while (algorithmList.size() <= index) {
			algorithmList.add(null);
		}
		algorithmList.set(index, a);
		fireAlgorithmAdded(new AlgorithmListEvent(this, EventType.ADDED, a));
	}

	public void createVariablesAndAlgorithms() {
	}

	public void fireAlgorithmAdded(AlgorithmListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof AlgorithmListListener) {
				((AlgorithmListListener) o).algorithmAdded(e);
			}
		}
	}

	public void fireAlgorithmRemoved(AlgorithmListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof AlgorithmListListener) {
				((AlgorithmListListener) o).algorithmRemoved(e);
			}
		}
	}

	public void fireAlgorithmUpdated(AlgorithmListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof AlgorithmListListener) {
				((AlgorithmListListener) o).algorithmUpdated(e);
			}
		}
	}

	public void clear() {

	}

	public void startCalculate() {
		startTime = System.nanoTime();
		createVariablesAndAlgorithms();
	}

	public void endCalculate() {
		calculateTime = System.nanoTime() - startTime;
		increaseCount();
	}

	public boolean isRunning() {
		return getStepsToDo() > 0;
	}

	public void start() {
		setStepsToDo(1000000);
	}

	public void stop() {
		setStepsToDo(0);
	}

	public int getInterval() {
		return interval;
	}

	public int getStepsToDo() {
		return stepsToDo;
	}

	public void increaseStepsToDo() {
		stepsToDo++;
		if (stepsToDo > 0)
			createAndStartThreads();
	}

	public void decreaseStepsToDo() {
		stepsToDo--;
		if (stepsToDo <= 0) {
			stepsToDo = 0;
			stopAndDestroyThreads();
		}
	}

	public void setStepsToDo(int stepsToDo) {
		if (stepsToDo > 0)
			createAndStartThreads();
		else
			stopAndDestroyThreads();
		this.stepsToDo = stepsToDo;
	}

	public void createAndStartThreads() {
		if (algorithmSpeedThread == null) {
			algorithmSpeedThread = new AlgorithmSpeedThread(this);
			algorithmSpeedThread.start();
		}
		if (algorithmThread == null) {
			algorithmThread = new AlgorithmThread(this);
			algorithmThread.start();
		}
	}

	public void stopAndDestroyThreads() {
		if (algorithmThread != null) {
			algorithmThread.interrupt();
			algorithmThread = null;
		}
		if (algorithmSpeedThread != null) {
			algorithmSpeedThread.interrupt();
			algorithmSpeedThread = null;
		}
	}

	public final void increaseCount() {
		totalCount++;
		fireCountIncreased();
	}

	public int getCallsPerSecond() {
		return callsPerSecond;
	}

	public final List<Matrix> calculate() throws Exception {
		return call();
	}

	public final List<Matrix> call() throws Exception {
		List<Matrix> input = new LinkedList<Matrix>();
		int size = variableList.getSize();
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
				addMatrixForVariable(v, output.get(i++));
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

	public void _calculate() {
		increaseStepsToDo();
	}

	public int getCount() {
		return totalCount;
	}

	public void fireCountIncreased() {
		fireCountIncreased(new AlgorithmEvent(this, EventType.UPDATED));
	}

	public void fireCountIncreased(AlgorithmEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof AlgorithmListener) {
				((AlgorithmListener) o).algorithmCountIncreased(e);
			}
		}
	}

	public void addAlgorithmListener(AlgorithmListener l) {
		getListenerList().add(AlgorithmListener.class, l);
	}

	public void removeAlgorithmListener(AlgorithmListener l) {
		getListenerList().remove(AlgorithmListener.class, l);
	}

	public void setCallsPerSecond(int i) {
		this.callsPerSecond = i;
	}

	public void addVariable(Variable v) {
	}

	public final int getIndexOfVariable(Variable v) {
		return variableList.indexOf(v);
	}

	public int getEdgeDirectionForVariable(int index) {
		return NOTCONNECTED;
	}

	public int getEdgeDirectionForAlgorithm(int index) {
		return NOTCONNECTED;
	}

	public final Matrix getMatrixFromVariable(int variableIndex) {
		Variable v = getVariable(variableIndex);
		if (v == null)
			return null;
		else
			return v.getMatrix();
	}

	public final List<Matrix> calculateAlgorithmForId(int id) throws Exception {
		return getAlgorithm(id).calculate();
	}

	public final Matrix getMatrixFromVariable(int variableIndex, int matrixIndex) {
		Variable v = getVariable(variableIndex);
		if (v == null)
			return null;
		else
			return v.getMatrix(matrixIndex);
	}

	public final void addMatrixForVariable(int variableIndex, Matrix matrix) {
		Variable v = getVariable(variableIndex);
		if (v != null)
			v.addMatrix(matrix);
	}

	public final void setMatrixForVariable(int variableIndex, int matrixIndex, Matrix matrix) {
		Variable v = getVariable(variableIndex);
		if (v != null)
			v.setMatrix(matrixIndex, matrix);
	}

	public final Variable getVariable(int index) {
		return variableList.get(index);
	}

	public int getVariableCount() {
		return variableList.getSize();
	}

	public final ObservableMap<Variable> getVariableList() {
		return variableList;
	}

	public void removeVariable(Variable variable) {
	}

	public void addAlgorithm(Algorithm a) {
		setAlgorithm(getAlgorithmCount(), a);
	}

	public void addAlgorithmListListener(AlgorithmListListener l) {
		getListenerList().add(AlgorithmListListener.class, l);
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

	public void removeAlgorithm(Algorithm algorithm) {
	}

	public void removeAlgorithmListListener(AlgorithmListListener l) {
		getListenerList().remove(AlgorithmListListener.class, l);
	}

	public void setInterval(int intervall) {
		if (interval >= 0)
			this.interval = intervall;
	}

	public long getRuntime() {
		return runtime;
	}

	public void setRuntime(long runTime) {
		this.runtime = runTime;
	}

	public String getEdgeLabelForVariable(int index) {
		return "";
	}

	public String getEdgeLabelForAlgorithm(int index) {
		return "";
	}

	public double getCalculateTime() {
		return calculateTime;
	}

	public final void importFromFile(File file) {
		if (file == null) {
			logger.log(Level.WARNING, "no filename provided");
			return;
		}

		if (file.getAbsolutePath().toLowerCase().endsWith(".alg")) {
		}
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

	public final String toString() {
		if (getLabel() == null) {
			return getClass().getSimpleName();
		} else {
			return getClass().getSimpleName() + " [" + getLabel() + "]";
		}
	}

}
