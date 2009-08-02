package org.jdmp.core.attribute;

import java.io.Serializable;

import org.ujmp.core.interfaces.HasLabel;

public class Attribute implements Serializable, Comparable<Attribute>, HasLabel {
	private static final long serialVersionUID = 4184577547748510833L;

	private String label = null;

	private boolean discrete = false;

	private int valueCount = 0;

	public boolean isDiscrete() {
		return discrete;
	}

	public void setDiscrete(boolean discrete) {
		this.discrete = discrete;
	}

	public Attribute(boolean discrete, int valueCount) {
		this.valueCount = valueCount;
	}

	public Attribute(String label, boolean discrete, int valueCount) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String toString() {
		return label;
	}

	@Override
	public boolean equals(Object obj) {
		return ("" + label).equals("" + label);
	}

	@Override
	public int hashCode() {
		return ("" + label).hashCode();
	}

	public int compareTo(Attribute o) {
		return ("" + label).compareTo("" + o.label);
	}

	public int getValueCount() {
		return valueCount;
	}

}
