package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.Algorithm;
import org.ujmp.core.Matrix;

public class SerialExecution extends AbstractAlgorithm {
	private static final long serialVersionUID = -3635082316760868682L;

	public SerialExecution() {
		super();
		setDescription("calls other Algorithms in defined order");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws Exception {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		for (Algorithm a : getAlgorithmList()) {
			Map<Object, Matrix> r = a.calculate();
			result.putAll(r);
		}

		return result;
	}

	public int getEdgeDirectionForReference(int i) {
		return OUTGOING;
	}

}
