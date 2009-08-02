package org.jdmp.core.algorithm;

import org.jdmp.core.variable.DefaultVariable;

public abstract class AlgorithmThreeSources extends AbstractAlgorithm {

	public static final int SOURCE1 = 0;

	public static final int SOURCE2 = 1;

	public static final int SOURCE3 = 2;

	public static final int TARGET = 3;

	public AlgorithmThreeSources(String label) {
		super(label);
	}

	public final String getEdgeLabelForVariable(int index) {
		switch (index) {
		case SOURCE1:
			return "Source 1";
		case SOURCE2:
			return "Source 2";
		case SOURCE3:
			return "Source 3";
		case TARGET:
			return "Target";
		default:
			return "";
		}
	}

	public final int getEdgeDirectionForVariable(int index) {
		switch (index) {
		case SOURCE1:
			return Algorithm.INCOMING;
		case SOURCE2:
			return Algorithm.INCOMING;
		case SOURCE3:
			return Algorithm.INCOMING;
		case TARGET:
			return Algorithm.OUTGOING;
		default:
			return Algorithm.NOTCONNECTED;
		}
	}

	@Override
	public void createVariablesAndAlgorithms() {
		if (getVariable(SOURCE1) == null) {
			setVariable(SOURCE1, new DefaultVariable("Source1 Variable"));
		}

		if (getVariable(SOURCE2) == null) {
			setVariable(SOURCE2, new DefaultVariable("Source2 Variable"));
		}

		if (getVariable(SOURCE3) == null) {
			setVariable(SOURCE3, new DefaultVariable("Source3 Variable"));
		}

		if (getVariable(TARGET) == null) {
			setVariable(TARGET, new DefaultVariable("Target Variable"));
		}
	}

}
