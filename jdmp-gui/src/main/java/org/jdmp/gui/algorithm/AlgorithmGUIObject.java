/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.gui.algorithm;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdmp.core.algorithm.Algorithm;
import org.ujmp.gui.AbstractGUIObject;

public class AlgorithmGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = 2744384245306464106L;

	private Algorithm algorithm = null;

	private transient JFrame frame = null;

	private transient JPanel panel = null;

	public AlgorithmGUIObject(Algorithm a) {
		this.algorithm = a;
	}

	public void clear() {
		algorithm.clear();
	}

	public Algorithm getCoreObject() {
		return algorithm;
	}

	public Icon getIcon() {
		return null;
	}

	public String getLabel() {
		return algorithm.getLabel();
	}

	public void setLabel(String label) {
		algorithm.setLabel(label);
	}

	public Object getLabelObject() {
		return algorithm.getLabelObject();
	}

	public void setLabelObject(Object label) {
		algorithm.setLabelObject(label);
	}

	public String getDescription() {
		return algorithm.getDescription();
	}

	public void setDescription(String description) {
		algorithm.setDescription(description);
	}

	public String toString() {
		return algorithm.toString();
	}

	public JFrame getFrame() {
		if (frame == null) {
			frame = new AlgorithmFrame(this);
		}
		return frame;
	}

	public JPanel getPanel() {
		if (panel == null) {
			panel = new AlgorithmPanel(this);
		}
		return panel;
	}

}
