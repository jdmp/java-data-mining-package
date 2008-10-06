package org.jdmp.core.algorithm;

import org.jdmp.core.variable.DefaultVariable;

public abstract class AlgorithmTwoSources extends AbstractAlgorithm {

	public static final int SOURCE1 = 0;

	public static final int SOURCE2 = 1;

	public static final int TARGET = 2;

	public AlgorithmTwoSources(String label) {
		super(label);
	}

	@Override
	public final String getEdgeLabelForVariable(int index) {
		switch (index) {
		case SOURCE1:
			return "Source 1";
		case SOURCE2:
			return "Source 2";
		case TARGET:
			return "Target";
		default:
			return "";
		}
	}

	@Override
	public final int getEdgeDirectionForVariable(int index) {
		switch (index) {
		case SOURCE1:
			return Algorithm.INCOMING;
		case SOURCE2:
			return Algorithm.INCOMING;
		case TARGET:
			return Algorithm.OUTGOING;
		default:
			return Algorithm.NOTCONNECTED;
		}
	}

	@Override
	public void createVariablesAndAlgorithms() {
		if (getVariableList().get(SOURCE1) == null) {
			setVariable(SOURCE1, new DefaultVariable("Source1 Variable"));
		}

		if (getVariableList().get(SOURCE2) == null) {
			setVariable(SOURCE2, new DefaultVariable("Source2 Variable"));
		}

		if (getVariableList().get(TARGET) == null) {
			setVariable(TARGET, new DefaultVariable("Target Variable"));
		}
	}

}
