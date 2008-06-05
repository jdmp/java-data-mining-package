package org.jdmp.core.util;

import java.util.EventObject;

import org.jdmp.matrix.interfaces.GUIObject;

public abstract class AbstractEvent extends EventObject {
	private static final long serialVersionUID = 6308911806902569388L;

	public enum EventType {
		ADDED, REMOVED, UPDATED, ALLUPDATED
	};

	private EventType type = null;

	protected Object[] data = null;

	public AbstractEvent(GUIObject source, EventType type, Object... data) {
		super(source);
		this.type = type;
		this.data = data;
	}

	public final Object[] getDataArray() {
		return data;
	}

	public final Object getData() {
		return data[0];
	}

	public final EventType getType() {
		return type;
	}

	public final String toString() {
		return this.getClass().getName() + "[" + type + "," + data + "]";
	}

}
