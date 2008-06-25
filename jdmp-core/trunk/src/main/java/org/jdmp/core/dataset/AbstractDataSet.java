package org.jdmp.core.dataset;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.ListSelectionModel;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.util.AbstractEvent.EventType;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableListEvent;
import org.jdmp.core.variable.VariableListListener;
import org.jdmp.core.variable.VariableListener;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.GUIObject;

public abstract class AbstractDataSet extends AbstractCoreObject implements DataSet {

	private transient GUIObject guiObject = null;

	private final ObservableList<Sample> sampleList = new ObservableList<Sample>();

	private final ObservableMap<Variable> variableList = new ObservableMap<Variable>();

	public AbstractDataSet() {
		super();
	}

	public AbstractDataSet(String label) {
		this();
		setLabel(label);
	}

	public void addVariable(Variable v) {
	}

	public void removeVariable(Variable v) {
	}

	public ObservableList<Sample> getSampleList() {
		return sampleList;
	}

	public final ObservableMap<Variable> getVariableList() {
		return variableList;
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

	public int getSampleCount() {
		return sampleList.getSize();
	}

	public Sample getSample(int pos) {
		if (pos >= 0 && pos < sampleList.getSize())
			return sampleList.getElementAt(pos);
		else
			return null;
	}

	public void setVariable(int index, Variable v) {
		variableList.put(index, v);
		fireVariableAdded(new VariableListEvent(this, EventType.ADDED, v));
	}

	public void addSample(Sample sample) {
		sampleList.add(sample);
	}

	public void removeSample(Sample p) {
		sampleList.remove(p);
	}

	public int getVariableCount() {
		return variableList.getSize();
	}

	public final Variable getVariable(int index) {
		return variableList.get(index);
	}

	public final int getIndexOfVariable(Variable v) {
		return variableList.indexOf(v);
	}

	public void addVariableListener(VariableListener l) {
		getListenerList().add(VariableListener.class, l);
	}

	public void removeVariableListener(VariableListener l) {
		getListenerList().add(VariableListener.class, l);
	}

	public final Matrix getMatrixFromVariable(int variableIndex) {
		Variable v = getVariable(variableIndex);
		if (v == null)
			return null;
		else
			return v.getMatrix();
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

	public int getIndexOfSample(Sample p) {
		return sampleList.indexOf(p);
	}

	public void addDataSetListener(DataSetListener l) {
		getListenerList().add(DataSetListener.class, l);
	}

	public void removeDataSetListener(DataSetListener l) {
		getListenerList().remove(DataSetListener.class, l);
	}

	public void clear() {
		sampleList.clear();
	}

	public void fireValueChanged(Matrix m) {
	}

	public ListSelectionModel getColumnSelectionModel() {
		return getMatrixList().getColumnSelectionModel();
	}

	public int getMatrixCount() {
		return getMatrixList().size();
	}

	public final Matrix getMatrix(int index) {
		return getMatrixList().get(index);
	}

	public ListSelectionModel getRowSelectionModel() {
		return getMatrixList().getRowSelectionModel();
	}

	public List<DataSet> splitByCount(boolean shuffle, int... count) {
		List<DataSet> dataSets = new ArrayList<DataSet>();

		List<Integer> ids = new ArrayList<Integer>(getSampleCount());
		for (int i = getSampleCount() - 1; i != -1; i--) {
			ids.add(i);
		}

		if (shuffle) {
			Collections.shuffle(ids);
		}

		for (int i = 0; i < count.length; i++) {
			DataSet ds = new ClassificationDataSet("DataSet" + i);
			for (int c = 0; c < count[i]; c++) {
				ds.getSampleList().add(getSample(ids.remove(0)).clone());
			}
			dataSets.add(ds);
		}
		DataSet ds = new ClassificationDataSet("DataSet" + count.length);
		while (ids.size() != 0) {
			ds.getSampleList().add(getSample(ids.remove(0)).clone());
		}
		dataSets.add(ds);

		return dataSets;
	}

	public List<DataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed) {
		List<DataSet> returnDataSets = new ArrayList<DataSet>();
		List<List<Sample>> tempSampleLists = new ArrayList<List<Sample>>();
		List<Sample> allSamples = new ArrayList<Sample>(getSampleList().toCollection());
		Collections.shuffle(allSamples, new Random(randomSeed));

		for (int set = 0; set < numberOfCVSets; set++) {
			List<Sample> partSamples = new ArrayList<Sample>();
			tempSampleLists.add(partSamples);
		}

		while (!allSamples.isEmpty()) {
			for (int set = 0; set < numberOfCVSets; set++) {
				if (!allSamples.isEmpty()) {
					tempSampleLists.get(set).add(allSamples.remove(0));
				}
			}
		}

		DataSet testSet = new ClassificationDataSet("TestSet" + randomSeed + "-" + idOfCVSet);
		testSet.addAllSamples(tempSampleLists.get(idOfCVSet));
		DataSet trainingSet = new ClassificationDataSet("TrainingSet" + randomSeed + "-"
				+ idOfCVSet);
		for (int i = 0; i < numberOfCVSets; i++) {
			if (i != idOfCVSet) {
				trainingSet.addAllSamples(tempSampleLists.get(i));
			}
		}

		returnDataSets.add(trainingSet);
		returnDataSets.add(testSet);

		return returnDataSets;
	}

	public void addAllSamples(Collection<Sample> samples) {
		sampleList.addAll(samples);
	}

	public List<DataSet> splitByPercent(boolean shuffle, double... percent) {
		int[] counts = new int[percent.length];
		int sampleCount = getSampleCount();
		for (int i = 0; i < percent.length; i++) {
			counts[i] = (int) Math.round(percent[i] * sampleCount);
		}
		return splitByCount(shuffle, counts);
	}

	public void removeAllSamples() {
		sampleList.clear();
	}

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.jdmp.gui.dataset.DataSetGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { DataSet.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				logger.log(Level.WARNING, "cannot create dataset gui object", e);
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
