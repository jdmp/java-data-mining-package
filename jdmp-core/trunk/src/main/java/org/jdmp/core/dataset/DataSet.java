package org.jdmp.core.dataset;

import java.util.Collection;
import java.util.List;

import javax.swing.ListSelectionModel;

import org.jdmp.core.CoreObject;
import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleListEvent;
import org.jdmp.core.sample.SampleListListener;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableListEvent;
import org.jdmp.core.variable.VariableListListener;
import org.jdmp.core.variable.VariableListener;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.HasMatrixList;

public interface DataSet extends CoreObject, HasVariables, HasSamples, HasMatrixList {

	public void addVariable(Variable v);

	public void removeVariable(Variable v);

	public List<Sample> getSampleList();

	public List<Variable> getVariableList();

	public void fireVariableAdded(VariableListEvent e);

	public void fireVariableRemoved(VariableListEvent e);

	public void fireVariableUpdated(VariableListEvent e);

	public String getShortStatus();

	public int getSampleCount();

	public Sample getSample(int pos);

	public void setVariable(int index, Variable v);

	public void addSample(Sample sample);

	public void removeSample(Sample p);

	public String getLongStatus();

	public DataSetType getDataSetType();

	public void setDataSetType(DataSetType dataSetType);

	public int getVariableCount();

	public Variable getVariable(int index);

	public int getIndexOfVariable(Variable v);

	public void addVariableListener(VariableListener l);

	public void removeVariableListener(VariableListener l);

	public void addVariableListListener(VariableListListener l);

	public void removeVariableListListener(VariableListListener l);

	public Matrix getMatrixFromVariable(int variableIndex);

	public Matrix getMatrixFromVariable(int variableIndex, int matrixIndex);

	public void addMatrixForVariable(int variableIndex, Matrix matrix);

	public void setMatrixForVariable(int variableIndex, int matrixIndex, Matrix matrix);

	public void fireSampleAdded(SampleListEvent e);

	public void fireSampleRemoved(SampleListEvent e);

	public void fireSampleUpdated(SampleListEvent e);

	public void addSampleListListener(SampleListListener l);

	public void removeSampleListListener(SampleListListener l);

	public int getIndexOfSample(Sample p);

	public void dispose();

	public void addDataSetListener(DataSetListener l);

	public void removeDataSetListener(DataSetListener l);

	public void clear();

	public void fireValueChanged(Matrix m);

	public ListSelectionModel getColumnSelectionModel();

	public int getMatrixCount();

	public Matrix getMatrix(int index);

	public ListSelectionModel getRowSelectionModel();

	public List<DataSet> splitByCount(boolean shuffle, int... count);

	public List<DataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed);

	public void addAllSamples(Collection<Sample> samples);

	public List<DataSet> splitByPercent(boolean shuffle, double... percent);

	public void removeAllSamples();
}
