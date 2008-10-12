package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.Algorithm;
import org.ujmp.core.Matrix;

public class Repeat extends AbstractAlgorithm {
	private static final long serialVersionUID = 1469854394519674382L;

	private final int REPEATEDALGORITHM = 0;

	private int repeatCount = 1;

	public Repeat() {
		super("Repeat");
		setDescription("calls another Algorithms several times");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws Exception {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		for (int i = 0; i < repeatCount; i++) {
			result = getAlgorithmList().get(REPEATEDALGORITHM).calculate(input);
		}
		return result;

	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public void setRepeatedAlgorithm(Algorithm a) {
		setAlgorithm(REPEATEDALGORITHM, a);
	}

	public Algorithm getRepeatedAlgorithm() {
		return getAlgorithmList().get(REPEATEDALGORITHM);
	}
}
