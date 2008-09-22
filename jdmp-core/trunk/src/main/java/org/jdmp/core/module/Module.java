package org.jdmp.core.module;

import java.util.List;

import org.jdmp.core.CoreObject;
import org.jdmp.core.algorithm.HasAlgorithms;
import org.jdmp.core.dataset.HasDataSets;
import org.jdmp.core.interpreter.Command;
import org.jdmp.core.variable.HasVariableMap;

public interface Module extends CoreObject, HasModuleList, HasAlgorithms, HasVariableMap,
		HasDataSets {

	public void execute(Command... commands);

	public void execute(List<Command> commands);

	public void execute(String script);

	public void clear();

}
