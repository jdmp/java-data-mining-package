package org.jdmp.core.algorithm;

import org.jdmp.core.variable.Variable;

public abstract class AlgorithmOneSource extends AbstractAlgorithm {

	public static final String SOURCE = "Source";

	public static final String TARGET = "Target";

	public AlgorithmOneSource(Variable... variables) {
		super();
		addVariableKey(SOURCE);
		addVariableKey(TARGET);
		setEdgeLabel(SOURCE, "Source");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(SOURCE, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
		setVariables(variables);
	}

	public void setSourceVariable(Variable source) {
		setVariable(SOURCE, source);
	}

	public void setTargetVariable(Variable target) {
		setVariable(TARGET, target);
	}

}
