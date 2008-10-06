package org.jdmp.core.algorithm.basic;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.Algorithm;
import org.ujmp.core.Matrix;

public class AlgorithmSerialExecution extends AbstractAlgorithm {
	private static final long serialVersionUID = -3635082316760868682L;

	public AlgorithmSerialExecution() {
		super("Serial Execution");
		setDescription("calls other Algorithms in defined order");
	}

	@Override
	public List<Matrix> calculate(List<Matrix> matrices) throws Exception {
		List<Matrix> result = new ArrayList<Matrix>();

		for (Algorithm a : getAlgorithmList()) {
			List<Matrix> r = a.calculate();
			result.addAll(r);
		}

		return result;
	}

	@Override
	public String getEdgeLabelForAlgorithm(int i) {
		return "Task " + (i + 1);
	}

	public int getEdgeDirectionForReference(int i) {
		return OUTGOING;
	}

}
