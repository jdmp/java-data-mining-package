package org.jdmp.core.dataset;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;

import javax.swing.ListSelectionModel;

import org.jdmp.core.AbstractCoreObject;
import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleListEvent;
import org.jdmp.core.sample.SampleListListener;
import org.jdmp.core.util.AbstractEvent.EventType;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableListEvent;
import org.jdmp.core.variable.VariableListListener;
import org.jdmp.core.variable.VariableListener;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.GUIObject;
import org.jdmp.matrix.interfaces.HasMatrixList;

public abstract class AbstractDataSet extends AbstractCoreObject implements DataSet, HasVariables,
		HasSamples, HasMatrixList {

	private transient GUIObject guiObject=null;
	
	private DataSetType dataSetType = DataSetType.TRAININGSET;

	// CopyOnWriteArrayList so no nullpointerexceptions are thrown
	private final List<Sample> sampleList = new CopyOnWriteArrayList<Sample>();

	private final List<Variable> variableList = new CopyOnWriteArrayList<Variable>();

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

	public List<Sample> getSampleList() {
		return sampleList;
	}

	public final List<Variable> getVariableList() {
		return Collections.unmodifiableList(variableList);
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

	public String getShortStatus() {
		return "" + " " + getLabel() + " " + getSampleCount() + " Samples [" + getDataSetType()
				+ "]";
	}

	public int getSampleCount() {
		return sampleList.size();
	}

	public Sample getSample(int pos) {
		if (pos >= 0 && pos < sampleList.size())
			return sampleList.get(pos);
		else
			return null;
	}

	public void setVariable(int index, Variable v) {
		while (variableList.size() <= index) {
			variableList.add(null);
		}
		variableList.set(index, v);
		fireVariableAdded(new VariableListEvent(this, EventType.ADDED, v));
	}

	public void addSample(Sample sample) {
		sampleList.add(sample);
		fireSampleAdded(new SampleListEvent(this, EventType.ADDED, sample));
	}

	public void removeSample(Sample p) {
		p.dispose();
		sampleList.remove(p);
		fireSampleRemoved(new SampleListEvent(this, EventType.REMOVED, p));
	}

	public String getLongStatus() {
		return "" + getSampleCount() + " Samples";
	}

	public DataSetType getDataSetType() {
		return dataSetType;
	}

	public void setDataSetType(DataSetType dataSetType) {
		this.dataSetType = dataSetType;
	}

	public int getVariableCount() {
		return variableList.size();
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

	public void addVariableListListener(VariableListListener l) {
		getListenerList().add(VariableListListener.class, l);
	}

	public void removeVariableListListener(VariableListListener l) {
		getListenerList().add(VariableListListener.class, l);
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

	public void fireSampleAdded(SampleListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof SampleListListener) {
				((SampleListListener) o).sampleAdded(e);
			}
		}
	}

	public void fireSampleRemoved(SampleListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof SampleListListener) {
				((SampleListListener) o).sampleRemoved(e);
			}
		}
	}

	public void fireSampleUpdated(SampleListEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof SampleListListener) {
				((SampleListListener) o).sampleUpdated(e);
			}
		}
	}

	public void addSampleListListener(SampleListListener l) {
		getListenerList().add(SampleListListener.class, l);
	}

	public void removeSampleListListener(SampleListListener l) {
		getListenerList().remove(SampleListListener.class, l);
	}

	public int getIndexOfSample(Sample p) {
		return sampleList.indexOf(p);
	}

	public void dispose() {
		clear();
	}

	public void addDataSetListener(DataSetListener l) {
		getListenerList().add(DataSetListener.class, l);
	}

	public void removeDataSetListener(DataSetListener l) {
		getListenerList().remove(DataSetListener.class, l);
	}

	public void clear() {
		for (Sample p : getSampleList()) {
			p.dispose();
		}
		for (Variable v : getVariableList()) {
			v.dispose();
		}
		sampleList.clear();
		variableList.clear();
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
				ds.addSample(getSample(ids.remove(0)).clone());
			}
			dataSets.add(ds);
		}
		DataSet ds = new ClassificationDataSet("DataSet" + count.length);
		while (ids.size() != 0) {
			ds.addSample(getSample(ids.remove(0)).clone());
		}
		dataSets.add(ds);

		return dataSets;
	}

	public List<DataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed) {
		List<DataSet> returnDataSets = new ArrayList<DataSet>();
		List<List<Sample>> tempSampleLists = new ArrayList<List<Sample>>();
		List<Sample> allSamples = new ArrayList<Sample>(getSampleList());
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
		fireSampleUpdated(new SampleListEvent(this, EventType.ALLUPDATED));
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
		fireSampleUpdated(new SampleListEvent(this, EventType.ALLUPDATED));
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
}
