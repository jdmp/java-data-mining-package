package org.jdmp.core.sample;

import org.jdmp.core.util.AbstractEvent;
import org.jdmp.core.util.AbstractGUIObject;

public class SampleListEvent extends AbstractEvent {
	private static final long serialVersionUID = -8389948513596502887L;

	public SampleListEvent(HasSamples source, EventType type, Object... data) {
		super((AbstractGUIObject) source, type, data);
	}
}
