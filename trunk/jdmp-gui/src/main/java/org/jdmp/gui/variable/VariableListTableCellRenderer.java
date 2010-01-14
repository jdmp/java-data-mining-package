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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.variable.Variable;
import org.ujmp.gui.renderer.MatrixRenderer;
import org.ujmp.gui.util.UIDefaults;

public class VariableListTableCellRenderer extends JPanel implements TableCellRenderer {
	private static final long serialVersionUID = 6410777671579311509L;

	private final BufferedImage bufferedImage = null;

	private final VariableListImageObserver variableListImageObserver = new VariableListImageObserver();

	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	private List variableList = null;

	private final MatrixRenderer matrixRenderer = new MatrixRenderer();

	private static int PADDINGX = UIManager.getInt("Table.paddingX");

	private static int PADDINGY = UIManager.getInt("Table.paddingY");

	private int width = 0;

	private int height = 0;

	private boolean isSelected;

	private boolean hasFocus;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		this.isSelected = isSelected;
		this.hasFocus = hasFocus;

		if (value instanceof List) {
			variableList = (List) value;
		} else {
			variableList = null;
		}

		width = table.getColumnModel().getColumn(column).getWidth() - (2 * PADDINGX) - 1;
		height = table.getRowHeight(row) - (2 * PADDINGY) - 1;

		if (isSelected) {
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			super.setForeground(table.getForeground());
			super.setBackground(table.getBackground());
		}

		setFont(table.getFont());

		if (hasFocus) {
			Border border = null;
			if (isSelected) {
				border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
			}
			if (border == null) {
				border = UIManager.getBorder("Table.focusCellHighlightBorder");
			}
			setBorder(border);

			if (!isSelected && table.isCellEditable(row, column)) {
				Color col;
				col = UIManager.getColor("Table.focusCellForeground");
				if (col != null) {
					super.setForeground(col);
				}
				col = UIManager.getColor("Table.focusCellBackground");
				if (col != null) {
					super.setBackground(col);
				}
			}
		} else {
			setBorder(noFocusBorder);
		}

		if (variableList != null && variableList.size() > 0) {
			for (Object o : variableList) {
				if (o instanceof Variable) {
					Variable v = (Variable) o;

					this.add(matrixRenderer.getTableCellRendererComponent(table, v.getMatrix(),
							isSelected, hasFocus, row, column));
				}
			}
		}

		return this;
	}

	
	public void paint(Graphics g) {
		super.paint(g);
		if (variableList == null) {
			Graphics2D g2d = (Graphics2D) g;
			if (g2d != null) {
				g2d.addRenderingHints(UIDefaults.AALIAS);
				g2d.setColor(Color.RED);
				g2d.drawLine(PADDINGX, PADDINGY, width - PADDINGX, height - PADDINGY);
				g2d.drawLine(width - PADDINGX, PADDINGY, PADDINGX, height - PADDINGY);
				g2d.dispose();
			}
		} else {
			paintVariableList(g, variableList);
		}
	}

	public void paintVariableList(Graphics g, List variableList) {

		if (variableList == null)
			return;

		int count = variableList.size();

		if (count == 0)
			return;

		int componentWidth = width / count;

		for (Object o : variableList) {
			if (o != null && o instanceof Variable) {
				Variable v = (Variable) o;
				MatrixRenderer.paintMatrix(g, v.getMatrix(), componentWidth, height + PADDINGY
						+ PADDINGY);
				g.translate(componentWidth, 0);
			}
		}
	}

}

class VariableListImageObserver implements ImageObserver {

	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		return false;
	}
}