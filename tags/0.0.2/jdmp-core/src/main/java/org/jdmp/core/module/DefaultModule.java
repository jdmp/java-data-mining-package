package org.jdmp.core.module;

import javax.swing.event.EventListenerList;

public class DefaultModule extends AbstractModule {
	private static final long serialVersionUID = 4932183248766877797L;

	private String label = "";

	private String description = "";

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public final void setLabel(String label) {
		this.label = label;
	}

	public final EventListenerList getListenerList() {
		return null;
	}

}
