package org.jdmp.core.algorithm;

public abstract class AlgorithmFiveSources extends AbstractAlgorithm {

	public static final String SOURCE1 = "Source 1";

	public static final String SOURCE2 = "Source 2";

	public static final String SOURCE3 = "Source 3";

	public static final String SOURCE4 = "Source 4";

	public static final String SOURCE5 = "Source 5";

	public static final String TARGET = "Target";

	public AlgorithmFiveSources(String label) {
		super(label);
		addVariableKey(SOURCE1);
		addVariableKey(SOURCE2);
		addVariableKey(SOURCE3);
		addVariableKey(SOURCE4);
		addVariableKey(SOURCE5);
		addVariableKey(TARGET);
		setEdgeLabel(SOURCE1, "Source 1");
		setEdgeLabel(SOURCE2, "Source 2");
		setEdgeLabel(SOURCE3, "Source 3");
		setEdgeLabel(SOURCE4, "Source 4");
		setEdgeLabel(SOURCE5, "Source 5");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(SOURCE1, EdgeDirection.Incoming);
		setEdgeDirection(SOURCE2, EdgeDirection.Incoming);
		setEdgeDirection(SOURCE3, EdgeDirection.Incoming);
		setEdgeDirection(SOURCE4, EdgeDirection.Incoming);
		setEdgeDirection(SOURCE5, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
	}

}
