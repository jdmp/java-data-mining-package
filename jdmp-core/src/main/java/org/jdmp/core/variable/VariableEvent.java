package org.jdmp.core.variable;

import org.jdmp.core.util.AbstractEvent;
import org.ujmp.core.Matrix;

public class VariableEvent extends AbstractEvent {
	private static final long serialVersionUID = -8349940514872800903L;

	public VariableEvent(Variable source, EventType type) {
		super(source, type);
	}


	public VariableEvent(Variable source, EventType type, int index, Matrix m) {
		super(source, type, index, m);
	}

	public Matrix getMatrix() {
		if (data.length == 2)
			return (Matrix) data[1];
		else
			return null;
	}

	public int getIndex() {
		if (data.length > 0)
			return (Integer) data[0];
		else
			return 0;
	}
}
