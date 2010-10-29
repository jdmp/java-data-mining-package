/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.jdmp.gui.variable;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import org.jdmp.core.variable.Variable;
import org.ujmp.gui.AbstractGUIObject;

public class VariableGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = 9145766876402222560L;

	private Variable variable = null;

	private transient JFrame frame = null;

	private transient JPanel panel = null;

	public VariableGUIObject(Variable v) {
		this.variable = v;
	}

	public void clear() {
		variable.clear();
	}

	public final Icon getIcon() {
		try {
			TableModel dataModel = new AbstractTableModel() {
				private static final long serialVersionUID = 5562866897873790623L;

				public int getColumnCount() {
					return 1;
				}

				public int getRowCount() {
					return 1;
				}

				public Object getValueAt(int row, int col) {
					return getCoreObject().getMatrix();
				}
			};
			JTable table = new JTable(dataModel);
			table.getColumnModel().getColumn(0).setWidth(32);
			table.setRowHeight(32);

			int WIDTH = table.getColumnModel().getColumn(0).getWidth() - 1;
			int HEIGHT = table.getRowHeight(0) - 1;

			Class<?> cl = Class.forName("org.jdmp.gui.matrix.MatrixRenderer");
			DefaultTableCellRenderer mr = (DefaultTableCellRenderer) cl.newInstance();
			Component c = mr.getTableCellRendererComponent(table, getCoreObject().getMatrix(),
					false, false, 0, 0);
			BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			c.paint(bi.getGraphics());
			return new ImageIcon(bi);

		} catch (Exception e) {
			return new ImageIcon("resources/icons/rebuild.png");
		}
	}

	public String getLabel() {
		return variable.getLabel();
	}

	public void setLabel(String label) {
		variable.setLabel(label);
	}

	public Object getLabelObject() {
		return variable.getLabelObject();
	}

	public void setLabelObject(Object label) {
		variable.setLabelObject(label);
	}

	public String getDescription() {
		return variable.getDescription();
	}

	public void setDescription(String description) {
		variable.setDescription(description);
	}

	public String toString() {
		return variable.toString();
	}

	public Variable getCoreObject() {
		return variable;
	}

	public JFrame getFrame() {
		if (frame == null) {
			frame = new VariableFrame(this);
		}
		return frame;
	}

	public JPanel getPanel() {
		if (panel == null) {
			panel = new VariablePanel(this);
		}
		return panel;
	}

}
