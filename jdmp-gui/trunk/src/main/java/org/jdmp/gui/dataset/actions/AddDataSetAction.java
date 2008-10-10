package org.jdmp.gui.dataset.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.HasDataSetList;
import org.jdmp.core.dataset.HasDataSets;

public class AddDataSetAction extends DataSetListAction {
	private static final long serialVersionUID = 8692069148375302589L;

	private DataSet ds = null;

	public AddDataSetAction(JComponent c, HasDataSets i, DataSet ds) {
		this(c, i);
		this.ds = ds;
	}

	public AddDataSetAction(JComponent c, HasDataSets i) {
		super(c, i);
		putValue(Action.NAME, "Add DataSet...");
		putValue(Action.SHORT_DESCRIPTION, "Add a new DataSet");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
	}

	public Object call() {
		if (ds == null) {
			ds = new ClassificationDataSet();
		}
		((HasDataSetList) getIDataSets()).getDataSetList().add(ds);
		return ds;
	}

}
