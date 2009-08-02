package org.jdmp.gui.sample;

import javax.swing.table.AbstractTableModel;

import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.sample.Sample;

public class SampleListTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5468178300746964431L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int WEIGHTCOLUMN = 2;

	public static final int INPUTCOLUMN = 3;

	public static final int TARGETCOLUMN = 4;

	public static final int PREDICTEDCOLUMN = 5;

	public static final int DIFFERENCECOLUMN = 6;

	public static final int RMSECOLUMN = 7;

	private HasSamples iSamples = null;

	public SampleListTableModel(HasSamples iSamples) {
		this.iSamples = iSamples;
	}

	public int getRowCount() {
		return iSamples.getSampleList().getSize();
	}

	public int getColumnCount() {
		return 8;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case ICONCOLUMN:
			return "";
		case LABELCOLUMN:
			return "Label";
		case WEIGHTCOLUMN:
			return "Weight";
		case INPUTCOLUMN:
			return "Input";
		case TARGETCOLUMN:
			return "Target";
		case PREDICTEDCOLUMN:
			return "Predicted";
		case DIFFERENCECOLUMN:
			return "Difference";
		case RMSECOLUMN:
			return "RMSE";
		default:
			return "unknown";
		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		default:
			return Sample.class;
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Sample p = iSamples.getSampleList().getElementAt(rowIndex);
		switch (columnIndex) {
		default:
			return p;
		}
	}

}
