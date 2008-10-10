package org.jdmp.gui.dataset.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.HasDataSets;

public class RemoveDataSetAction extends DataSetListAction {
	private static final long serialVersionUID = -8847037312292718929L;

	private DataSet ds = null;

	public RemoveDataSetAction(JComponent c, HasDataSets i, DataSet ds) {
		this(c, i);
		this.ds = ds;
	}

	public RemoveDataSetAction(JComponent c, HasDataSets i) {
		super(c, i);
		putValue(Action.NAME, "Remove DataSet...");
		putValue(Action.SHORT_DESCRIPTION, "Remove a DataSet");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
	}

	public Object call() {
		if (ds != null) {
			// ()getIDataSets().removeDataSet(ds);
			return ds;
		}
		return null;
	}

}
