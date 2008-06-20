package org.jdmp.core.sample;

import javax.swing.event.EventListenerList;

import org.jdmp.matrix.Matrix;

public class DefaultSample extends AbstractSample {
	private static final long serialVersionUID = -3649758882404748630L;

	private transient EventListenerList listenerList = null;

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
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		return listenerList;
	}

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
