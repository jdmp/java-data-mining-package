package org.jdmp.gui.variable;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import org.jdmp.core.variable.Variable;
import org.jdmp.gui.AbstractGUIObject;

public class VariableGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = 9145766876402222560L;

	private Variable variable = null;

	public VariableGUIObject(Variable v) {
		this.variable = v;
	}

	public void clear() {
		variable.clear();
	}

	public Variable getVariable() {
		return variable;
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
					return getVariable().getMatrix();
				}
			};
			JTable table = new JTable(dataModel);
			table.getColumnModel().getColumn(0).setWidth(32);
			table.setRowHeight(32);

			int WIDTH = table.getColumnModel().getColumn(0).getWidth() - 1;
			int HEIGHT = table.getRowHeight(0) - 1;

			Class<?> cl = Class.forName("org.jdmp.gui.matrix.MatrixRenderer");
			DefaultTableCellRenderer mr = (DefaultTableCellRenderer) cl.newInstance();
			Component c = mr.getTableCellRendererComponent(table, getVariable().getMatrix(), false,
					false, 0, 0);
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

	public String getDescription() {
		return variable.getDescription();
	}

	public void setDescription(String description) {
		variable.setDescription(description);
	}

}
