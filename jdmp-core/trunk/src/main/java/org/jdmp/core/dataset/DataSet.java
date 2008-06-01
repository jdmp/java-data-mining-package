package org.jdmp.core.dataset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ListSelectionModel;

import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleListEvent;
import org.jdmp.core.sample.SampleListListener;
import org.jdmp.core.sample.WeightedSample;
import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.core.util.AbstractEvent.EventType;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableListEvent;
import org.jdmp.core.variable.VariableListListener;
import org.jdmp.core.variable.VariableListener;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public abstract class DataSet extends AbstractGUIObject implements HasVariables, HasSamples, HasMatrixList {
	private static final long serialVersionUID = 5741377222915590132L;

	private DataSetType dataSetType = DataSetType.TRAININGSET;

	// CopyOnWriteArrayList so no nullpointerexceptions are thrown
	private final List<Sample> sampleList = new CopyOnWriteArrayList<Sample>();

	private final List<Variable> variableList = new CopyOnWriteArrayList<Variable>();

	public DataSet() {
		super();
	}

	public DataSet(String label) {
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
		return "" + " " + getLabel() + " " + getSampleCount() + " Samples [" + getDataSetType() + "]";
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

	@Override
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

	public static ClassificationDataSet XOR() {
		ClassificationDataSet xor = new ClassificationDataSet("XOR-Problem");

		ClassificationSample x000 = new ClassificationSample("00=01");
		x000.setInputMatrix(MatrixFactory.fromArray(new double[] { 0, 0 }).transpose());
		x000.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0, 1 }).transpose());
		xor.addSample(x000);

		ClassificationSample x011 = new ClassificationSample("01=10");
		x011.setInputMatrix(MatrixFactory.fromArray(new double[] { 0, 1 }).transpose());
		x011.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 1, 0 }).transpose());
		xor.addSample(x011);

		ClassificationSample x101 = new ClassificationSample("10=10");
		x101.setInputMatrix(MatrixFactory.fromArray(new double[] { 1, 0 }).transpose());
		x101.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 1, 0 }).transpose());
		xor.addSample(x101);

		ClassificationSample x110 = new ClassificationSample("11=01");
		x110.setInputMatrix(MatrixFactory.fromArray(new double[] { 1, 1 }).transpose());
		x110.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0, 1 }).transpose());
		xor.addSample(x110);

		return xor;
	}

	public static ClassificationDataSet OR() {
		ClassificationDataSet or = new ClassificationDataSet("OR-Problem");

		ClassificationSample x000 = new ClassificationSample("00=01");
		x000.setInputMatrix(MatrixFactory.fromArray(new double[] { 0, 0 }).transpose());
		x000.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0, 1 }).transpose());
		or.addSample(x000);

		ClassificationSample x011 = new ClassificationSample("01=10");
		x011.setInputMatrix(MatrixFactory.fromArray(new double[] { 0, 1 }).transpose());
		x011.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 1, 0 }).transpose());
		or.addSample(x011);

		ClassificationSample x101 = new ClassificationSample("10=10");
		x101.setInputMatrix(MatrixFactory.fromArray(new double[] { 1, 0 }).transpose());
		x101.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 1, 0 }).transpose());
		or.addSample(x101);

		ClassificationSample x110 = new ClassificationSample("11=10");
		x110.setInputMatrix(MatrixFactory.fromArray(new double[] { 1, 1 }).transpose());
		x110.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 1, 0 }).transpose());
		or.addSample(x110);

		return or;
	}

	public static ClassificationDataSet Linear1() {
		ClassificationDataSet or = new ClassificationDataSet("Linear1");

		ClassificationSample x0 = new ClassificationSample("0.0=0.0");
		x0.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.0 }).transpose());
		x0.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.0 }).transpose());
		or.addSample(x0);

		ClassificationSample x1 = new ClassificationSample("0.1=0.1");
		x1.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.1 }).transpose());
		x1.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.1 }).transpose());
		or.addSample(x1);

		ClassificationSample x2 = new ClassificationSample("0.2=0.2");
		x2.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.2 }).transpose());
		x2.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.2 }).transpose());
		or.addSample(x2);

		ClassificationSample x3 = new ClassificationSample("0.3=0.3");
		x3.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.3 }).transpose());
		x3.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.3 }).transpose());
		or.addSample(x3);

		ClassificationSample x4 = new ClassificationSample("0.4=0.4");
		x4.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.4 }).transpose());
		x4.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.4 }).transpose());
		or.addSample(x4);

		ClassificationSample x5 = new ClassificationSample("0.5=0.5");
		x5.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		x5.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		or.addSample(x5);

		return or;
	}
	
	public static ClassificationDataSet Linear3() {
		ClassificationDataSet or = new ClassificationDataSet("Linear3");

		ClassificationSample x0 = new ClassificationSample("0.0=0.5");
		x0.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.0 }).transpose());
		x0.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		or.addSample(x0);

		ClassificationSample x1 = new ClassificationSample("0.1=0.5");
		x1.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.1 }).transpose());
		x1.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		or.addSample(x1);

		ClassificationSample x2 = new ClassificationSample("0.2=0.5");
		x2.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.2 }).transpose());
		x2.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		or.addSample(x2);

		ClassificationSample x3 = new ClassificationSample("0.3=0.5");
		x3.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.3 }).transpose());
		x3.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		or.addSample(x3);

		ClassificationSample x4 = new ClassificationSample("0.4=0.5");
		x4.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.4 }).transpose());
		x4.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		or.addSample(x4);

		ClassificationSample x5 = new ClassificationSample("0.5=0.5");
		x5.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		x5.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		or.addSample(x5);

		return or;
	}

	public static ClassificationDataSet Linear2() {
		ClassificationDataSet or = new ClassificationDataSet("Linear2");

		ClassificationSample x0 = new ClassificationSample("0.0=0.1");
		x0.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.0 }).transpose());
		x0.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.1 }).transpose());
		or.addSample(x0);

		ClassificationSample x1 = new ClassificationSample("0.1=0.2");
		x1.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.1 }).transpose());
		x1.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.2 }).transpose());
		or.addSample(x1);

		ClassificationSample x2 = new ClassificationSample("0.2=0.3");
		x2.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.2 }).transpose());
		x2.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.3 }).transpose());
		or.addSample(x2);

		ClassificationSample x3 = new ClassificationSample("0.3=0.4");
		x3.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.3 }).transpose());
		x3.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.4 }).transpose());
		or.addSample(x3);

		ClassificationSample x4 = new ClassificationSample("0.4=0.5");
		x4.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.4 }).transpose());
		x4.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		or.addSample(x4);

		ClassificationSample x5 = new ClassificationSample("0.5=0.6");
		x5.setInputMatrix(MatrixFactory.fromArray(new double[] { 0.5 }).transpose());
		x5.setDesiredOutputMatrix(MatrixFactory.fromArray(new double[] { 0.6 }).transpose());
		or.addSample(x5);

		return or;
	}

	public static BasicDataSet ANIMALS() throws MatrixException {
		BasicDataSet animals = new BasicDataSet("Animals");

		WeightedSample pigeon = new WeightedSample("Pigeon");
		pigeon.setInputMatrix(MatrixFactory.fromString("1 0 0 1 0 0 0 0 1 0 0 1 0"));
		animals.addSample(pigeon);

		WeightedSample chicken = new WeightedSample("Chicken");
		chicken.setInputMatrix(MatrixFactory.fromString("1 0 0 1 0 0 0 0 1 0 0 0 0"));
		animals.addSample(chicken);

		WeightedSample duck = new WeightedSample("Duck");
		duck.setInputMatrix(MatrixFactory.fromString("1 0 0 1 0 0 0 0 1 0 0 0 1"));
		animals.addSample(duck);

		WeightedSample goose = new WeightedSample("Goose");
		goose.setInputMatrix(MatrixFactory.fromString("1 0 0 1 0 0 0 0 1 0 0 1 1"));
		animals.addSample(goose);

		WeightedSample owl = new WeightedSample("Owl");
		owl.setInputMatrix(MatrixFactory.fromString("1 0 0 1 0 0 0 0 1 1 0 1 0"));
		animals.addSample(owl);

		WeightedSample falcon = new WeightedSample("Falcon");
		falcon.setInputMatrix(MatrixFactory.fromString("1 0 0 1 0 0 0 0 1 1 0 1 0"));
		animals.addSample(falcon);

		WeightedSample eagle = new WeightedSample("Eagle");
		eagle.setInputMatrix(MatrixFactory.fromString("0 1 0 1 0 0 0 0 1 1 0 1 0"));
		animals.addSample(eagle);

		WeightedSample fox = new WeightedSample("Fox");
		fox.setInputMatrix(MatrixFactory.fromString("0 1 0 0 1 1 0 0 0 1 0 0 0"));
		animals.addSample(fox);

		WeightedSample dog = new WeightedSample("Dog");
		dog.setInputMatrix(MatrixFactory.fromString("0 1 0 0 1 1 0 0 0 0 1 0 0"));
		animals.addSample(dog);

		WeightedSample wolf = new WeightedSample("Wolf");
		wolf.setInputMatrix(MatrixFactory.fromString("0 1 0 0 1 1 0 1 0 1 1 0 0"));
		animals.addSample(wolf);

		WeightedSample cat = new WeightedSample("Cat");
		cat.setInputMatrix(MatrixFactory.fromString("1 0 0 0 1 1 0 0 0 1 0 0 0"));
		animals.addSample(cat);

		WeightedSample tiger = new WeightedSample("Tiger");
		tiger.setInputMatrix(MatrixFactory.fromString("0 0 1 0 1 1 0 0 0 1 1 0 0"));
		animals.addSample(tiger);

		WeightedSample lion = new WeightedSample("Lion");
		lion.setInputMatrix(MatrixFactory.fromString("0 0 1 0 1 1 0 1 0 1 1 0 0"));
		animals.addSample(lion);

		WeightedSample horse = new WeightedSample("Horse");
		horse.setInputMatrix(MatrixFactory.fromString("0 0 1 0 1 1 1 1 0 0 1 0 0"));
		animals.addSample(horse);

		WeightedSample zebra = new WeightedSample("Zebra");
		zebra.setInputMatrix(MatrixFactory.fromString("0 0 1 0 1 1 1 1 0 0 1 0 0"));
		animals.addSample(zebra);

		WeightedSample cow = new WeightedSample("Cow");
		cow.setInputMatrix(MatrixFactory.fromString("0 0 1 0 1 1 1 0 0 0 0 0 0"));
		animals.addSample(cow);

		return animals;
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
		DataSet trainingSet = new ClassificationDataSet("TrainingSet" + randomSeed + "-" + idOfCVSet);
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
}
