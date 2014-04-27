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

package org.jdmp.gui.dataset;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdmp.core.dataset.DataSet;
import org.ujmp.gui.AbstractGUIObject;

public class DataSetGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = -329942434062359920L;

	private DataSet dataSet = null;

	private transient JFrame frame = null;

	private transient JPanel panel = null;

	public DataSet getCoreObject() {
		return dataSet;
	}

	public DataSetGUIObject(DataSet d) {
		this.dataSet = d;
	}

	public void clear() {
		dataSet.clear();
	}

	public Icon getIcon() {
		return null;
	}

	public String getLabel() {
		return dataSet.getLabel();
	}

	public void setLabel(Object label) {
		dataSet.setLabel(label);
	}

	public Object getLabelObject() {
		return dataSet.getLabelObject();
	}

	public String getDescription() {
		return dataSet.getDescription();
	}

	public void setDescription(String description) {
		dataSet.setDescription(description);
	}

	public String toString() {
		return dataSet.toString();
	}

	public JFrame getFrame() {
		if (frame == null) {
			frame = new DataSetFrame(this);
		}
		return frame;
	}

	public JPanel getPanel() {
		if (panel == null) {
			panel = new DataSetPanel(this);
		}
		return panel;
	}

}
