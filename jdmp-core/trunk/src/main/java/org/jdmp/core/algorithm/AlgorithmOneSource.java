package org.jdmp.core.algorithm;

public abstract class AlgorithmOneSource extends AbstractAlgorithm {

	public static final String SOURCE = "Source";

	public static final String TARGET = "Target";

	public AlgorithmOneSource(String label) {
		super(label);
		addVariableKey(SOURCE);
		addVariableKey(TARGET);
		setEdgeLabel(SOURCE, "Source");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(SOURCE, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
	}

}
