package org.jdmp.core.module;

import java.util.List;

import org.jdmp.core.CoreObject;
import org.jdmp.core.algorithm.HasAlgorithmList;
import org.jdmp.core.dataset.HasDataSetList;
import org.jdmp.core.interpreter.Command;
import org.jdmp.core.interpreter.Result;
import org.jdmp.core.variable.HasVariableMap;

public interface Module extends CoreObject, HasModuleList, HasAlgorithmList, HasVariableMap,
		HasDataSetList {

	public Result execute(Command... commands);

	public Result execute(List<Command> commands);

	public Result execute(String script);

	public void clear();

}
