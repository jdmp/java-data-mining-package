package org.jdmp.core.module;

import java.util.List;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.AlgorithmListEvent;
import org.jdmp.core.algorithm.AlgorithmListListener;
import org.jdmp.core.algorithm.HasAlgorithms;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetListEvent;
import org.jdmp.core.dataset.DataSetListListener;
import org.jdmp.core.dataset.HasDataSets;
import org.jdmp.core.util.CoreObject;
import org.jdmp.core.util.interfaces.HasAlgorithmsAndVariables;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableListEvent;
import org.jdmp.core.variable.VariableListListener;

public interface Module extends CoreObject, HasAlgorithmsAndVariables, HasModules, HasAlgorithms,
		HasVariables, HasDataSets {

	public void fireVariableAdded(VariableListEvent e);

	public void fireVariableRemoved(VariableListEvent e);

	public void fireVariableUpdated(VariableListEvent e);

	public void fireDataSetAdded(DataSetListEvent e);

	public void fireDataSetRemoved(DataSetListEvent e);

	public void fireDataSetUpdated(DataSetListEvent e);

	public void fireModuleAdded(ModuleListEvent e);

	public void fireModuleRemoved(ModuleListEvent e);

	public void fireModuleUpdated(ModuleListEvent e);

	public void fireAlgorithmAdded(AlgorithmListEvent e);

	public void fireAlgorithmUpdated(AlgorithmListEvent e);

	public void fireAlgorithmRemoved(AlgorithmListEvent e);

	public void addAlgorithm(Algorithm a);

	public void addVariable(Variable v);

	public void addModule(Module m);

	public List<Module> getModuleList();

	public void addDataSet(DataSet ds);

	public List<DataSet> getDataSetList();

	public int getIndexOfDataSet(DataSet ds);

	public DataSet getDataSet(int pos);

	public Module getModule(int pos);

	public void addModuleListListener(ModuleListListener l);

	public int getIndexOfModule(Module m);

	public int getModuleCount();

	public void removeAlgorithm(Algorithm algorithm);

	public void removeVariable(Variable variable);

	public void removeDataSet(DataSet ds);

	public void removeModule(Module m);

	public void removeModuleListListener(ModuleListListener l);

	public int getDataSetCount();

	public int getAlgorithmCount();

	public Algorithm getAlgorithm(int pos);

	public int getIndexOfAlgorithm(Algorithm algorithm);

	public void addAlgorithmListListener(AlgorithmListListener l);

	public void removeAlgorithmListListener(AlgorithmListListener l);

	public void addDataSetListListener(DataSetListListener l);

	public void removeDataSetListListener(DataSetListListener l);

	public void addModuleListener(ModuleListener l);

	public void removeModuleListener(ModuleListener l);

	public List<Algorithm> getAlgorithmList();

	public int getVariableCount();

	public Variable getVariable(int pos);

	public List<Variable> getVariableList();

	public int getIndexOfVariable(Variable variable);

	public void addVariableListListener(VariableListListener l);

	public void removeVariableListListener(VariableListListener l);

	public void dispose();

	public void clear();
}
