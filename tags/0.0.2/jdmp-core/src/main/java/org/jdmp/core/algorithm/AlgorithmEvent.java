package org.jdmp.core.algorithm;

import org.jdmp.core.util.AbstractEvent;

public class AlgorithmEvent extends AbstractEvent {
	private static final long serialVersionUID = -7396104269866925232L;

	public AlgorithmEvent(Algorithm source, EventType type, Object... objects) {
		super(source, type, objects);
	}
}
