package org.jdmp.core.algorithm;

public abstract class AlgorithmThreeSources extends AbstractAlgorithm {

	public static final String SOURCE1 = "Source 1";

	public static final String SOURCE2 = "Source 2";

	public static final String SOURCE3 = "Source 3";

	public static final String TARGET = "Target";

	public AlgorithmThreeSources(String label) {
		super(label);
		addVariableKey(SOURCE1);
		addVariableKey(SOURCE2);
		addVariableKey(SOURCE3);
		addVariableKey(TARGET);
		setEdgeLabel(SOURCE1, "Source 1");
		setEdgeLabel(SOURCE2, "Source 2");
		setEdgeLabel(SOURCE3, "Source 3");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(SOURCE1, EdgeDirection.Incoming);
		setEdgeDirection(SOURCE2, EdgeDirection.Incoming);
		setEdgeDirection(SOURCE3, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
	}

}
