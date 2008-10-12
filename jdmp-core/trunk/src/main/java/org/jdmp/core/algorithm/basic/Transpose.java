package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class Transpose extends AlgorithmOneSource {
	private static final long serialVersionUID = 7132114769238742740L;

	public Transpose() {
		super("Transpose");
		setDescription("target = source^T");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();
		Matrix source = input.get(SOURCE);
		Matrix target = new org.ujmp.core.objectcalculation.Transpose(source).calcNew();
		result.put(TARGET, target);
		return result;
	}

}
