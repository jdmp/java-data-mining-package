/*
 * Copyright (C) 2008-2009 by Holger Arndt
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

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

	
	public boolean equals(Object obj) {
		return ("" + label).equals("" + label);
	}

	
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
