package org.jdmp.core.algorithm;

import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;

public abstract class AlgorithmOneSource extends AbstractAlgorithm {

	public AlgorithmOneSource(String label) {
		super(label);
	}

	public static final int SOURCE = 0;

	public static final int TARGET = 1;

	public final String getEdgeLabelForVariable(int index) {
		switch (index) {
		case SOURCE:
			return "Source";
		case TARGET:
			return "Target";
		default:
			return "";
		}
	}

	public final int getEdgeDirectionForVariable(int index) {
		switch (index) {
		case SOURCE:
			return Algorithm.INCOMING;
		case TARGET:
			return Algorithm.OUTGOING;
		default:
			return Algorithm.NOTCONNECTED;
		}
	}

	@Override
	public void createVariablesAndAlgorithms() {
		if (getVariable(SOURCE) == null) {
			setVariable(SOURCE, new DefaultVariable("Source Variable"));
		}

		if (getVariable(TARGET) == null) {
			setVariable(TARGET, new DefaultVariable("Target Variable"));
		}
	}

	public final void setSourceVariable(Variable v) {
		setVariable(SOURCE, v);
	}

	public final void setTargetVariable(Variable v) {
		setVariable(TARGET, v);
	}

}
