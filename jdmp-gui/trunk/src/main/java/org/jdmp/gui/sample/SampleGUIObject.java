/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.jdmp.gui.sample;

import javax.swing.Icon;

import org.jdmp.core.sample.Sample;
import org.ujmp.gui.AbstractGUIObject;

public class SampleGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = -3436220704455373493L;

	private Sample sample = null;

	public SampleGUIObject(Sample s) {
		this.sample = s;
	}

	public void clear() {
	}

	public Icon getIcon() {
		return null;
	}

	public String getLabel() {
		return sample.getLabel();
	}

	public void setLabel(String label) {
		sample.setLabel(label);
	}

	public String getDescription() {
		return sample.getDescription();
	}

	public void setDescription(String description) {
		sample.setDescription(description);
	}

	@Override
	public String toString() {
		return sample.toString();
	}

	@Override
	public Sample getCoreObject() {
		return sample;
	}

}
