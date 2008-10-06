package org.jdmp.core.algorithm;

import org.jdmp.core.variable.DefaultVariable;

public abstract class AlgorithmFourSources extends AbstractAlgorithm {

	public static final int SOURCE1 = 0;

	public static final int SOURCE2 = 1;

	public static final int SOURCE3 = 2;

	public static final int SOURCE4 = 3;

	public static final int TARGET = 4;

	public AlgorithmFourSources(String label) {
		super(label);
	}

	@Override
	public final String getEdgeLabelForVariable(int index) {
		switch (index) {
		case SOURCE1:
			return "Source 1";
		case SOURCE2:
			return "Source 2";
		case SOURCE3:
			return "Source 3";
		case SOURCE4:
			return "Source 4";
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
		case SOURCE3:
			return Algorithm.INCOMING;
		case SOURCE4:
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

		if (getVariableList().get(SOURCE3) == null) {
			setVariable(SOURCE3, new DefaultVariable("Source3 Variable"));
		}

		if (getVariableList().get(SOURCE4) == null) {
			setVariable(SOURCE4, new DefaultVariable("Source4 Variable"));
		}

		if (getVariableList().get(TARGET) == null) {
			setVariable(TARGET, new DefaultVariable("Target Variable"));
		}
	}

}
