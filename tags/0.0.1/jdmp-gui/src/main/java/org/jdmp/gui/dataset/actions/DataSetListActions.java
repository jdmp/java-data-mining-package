package org.jdmp.gui.dataset.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import org.jdmp.core.dataset.HasDataSets;

public class DataSetListActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = 7368610208934209148L;

	public DataSetListActions(JComponent c, HasDataSets i) {
		this.add(new JMenuItem(new AddDataSetAction(c, i)));
	}

}
