package org.jdmp.core.dataset;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.ListSelectionModel;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;

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

	public ObservableList<Sample> getSampleList() {
		return sampleList;
	}

	public final ObservableMap<Variable> getVariableList() {
		return variableList;
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

		List<Integer> ids = new ArrayList<Integer>(getSampleList().getSize());
		for (int i = getSampleList().getSize() - 1; i != -1; i--) {
			ids.add(i);
		}

		if (shuffle) {
			Collections.shuffle(ids);
		}

		for (int i = 0; i < count.length; i++) {
			DataSet ds = new ClassificationDataSet("DataSet" + i);
			for (int c = 0; c < count[i]; c++) {
				ds.getSampleList().add(getSampleList().getElementAt(ids.remove(0)).clone());
			}
			dataSets.add(ds);
		}
		DataSet ds = new ClassificationDataSet("DataSet" + count.length);
		while (ids.size() != 0) {
			ds.getSampleList().add(getSampleList().getElementAt(ids.remove(0)).clone());
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
		testSet.getSampleList().addAll(tempSampleLists.get(idOfCVSet));
		DataSet trainingSet = new ClassificationDataSet("TrainingSet" + randomSeed + "-"
				+ idOfCVSet);
		for (int i = 0; i < numberOfCVSets; i++) {
			if (i != idOfCVSet) {
				trainingSet.getSampleList().addAll(tempSampleLists.get(i));
			}
		}

		returnDataSets.add(trainingSet);
		returnDataSets.add(testSet);

		return returnDataSets;
	}

	public List<DataSet> splitByPercent(boolean shuffle, double... percent) {
		int[] counts = new int[percent.length];
		int sampleCount = getSampleList().getSize();
		for (int i = 0; i < percent.length; i++) {
			counts[i] = (int) Math.round(percent[i] * sampleCount);
		}
		return splitByCount(shuffle, counts);
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
