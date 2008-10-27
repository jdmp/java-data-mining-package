package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public class TanhPlusOne extends AlgorithmOneSource {
	private static final long serialVersionUID = 1972442317406585099L;

	public TanhPlusOne(Variable... variables) {
		super(variables);
		setDescription("target = tanh(source+1)");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();
		Matrix in = input.get(SOURCE);
		result.put(TARGET, in.plus(1.0).tanh(Ret.NEW));
		return result;
	}

}
