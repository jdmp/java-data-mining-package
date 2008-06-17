package org.jdmp.core.sample;

import org.jdmp.matrix.Matrix;

public class DefaultSample extends AbstractSample {
	private static final long serialVersionUID = -3649758882404748630L;

	public DefaultSample(Matrix m) {
		this(m.getLabel());
		setMatrix(INPUT, m);
	}

	public DefaultSample(String label) {
		super(label);
	}

	public DefaultSample() {
		super();
	}

	public DefaultSample clone() {
		DefaultSample s = new DefaultSample();
		Matrix input = getMatrix(INPUT);
		if (input != null) {
			s.setMatrix(INPUT, input.clone());
		}
		Matrix target = getMatrix(TARGET);
		if (target != null) {
			s.setMatrix(TARGET, target.clone());
		}
		return s;
	}

}
