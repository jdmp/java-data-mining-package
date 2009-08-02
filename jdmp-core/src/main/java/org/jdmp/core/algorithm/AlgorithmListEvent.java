package org.jdmp.core.algorithm;

import org.jdmp.core.util.AbstractEvent;

public class AlgorithmListEvent extends AbstractEvent {
	private static final long serialVersionUID = -511897235503692075L;

	public AlgorithmListEvent(HasAlgorithms source, EventType type, Object... data) {
		super(source, type, data);
	}
}
