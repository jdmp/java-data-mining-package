package org.jdmp.core.algorithm;

import org.jdmp.core.variable.Variable;

public abstract class AlgorithmTwoSources extends AbstractAlgorithm {

	public static final String SOURCE1 = "Source 1";

	public static final String SOURCE2 = "Source 2";

	public static final String TARGET = "Target";

	public AlgorithmTwoSources(Variable... variables) {
		addVariableKey(SOURCE1);
		addVariableKey(SOURCE2);
		addVariableKey(TARGET);
		setEdgeLabel(SOURCE1, "Source 1");
		setEdgeLabel(SOURCE2, "Source 2");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(SOURCE1, EdgeDirection.Incoming);
		setEdgeDirection(SOURCE2, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
		setVariables(variables);
	}

}
