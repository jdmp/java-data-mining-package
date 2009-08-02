package org.jdmp.gui.matrix;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MatrixTableCellEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 3405924999431274803L;

	public MatrixTableCellEditor() {
		super(new JTextField());
		delegate = new EditorDelegate() {
			private static final long serialVersionUID = 3595116301664542217L;

			public void setValue(Object value) {
				String text = "";
				if (value != null) {
					text = value.toString();
				}
				((JTextField) editorComponent).setText(text);
			}

			public Object getCellEditorValue() {
				String text = ((JTextField) editorComponent).getText();
				return text;
			}

			@Override
			public boolean shouldSelectCell(EventObject anEvent) {
				((JTextField) editorComponent).selectAll();
				return super.shouldSelectCell(anEvent);
			}
		};
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		delegate.setValue(value);
		((JTextField) editorComponent).selectAll();
		return editorComponent;
	}

}
