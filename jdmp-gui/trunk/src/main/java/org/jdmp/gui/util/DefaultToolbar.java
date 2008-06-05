package org.jdmp.gui.util;

import javax.swing.JComponent;
import javax.swing.JToolBar;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.gui.actions.ClearAction;
import org.jdmp.gui.actions.CopyAction;
import org.jdmp.gui.actions.DeleteAction;
import org.jdmp.gui.actions.ExportAction;
import org.jdmp.gui.actions.PasteAction;
import org.jdmp.gui.actions.PrintAction;
import org.jdmp.gui.algorithm.actions.CalculateOnceAction;
import org.jdmp.gui.algorithm.actions.StartAction;
import org.jdmp.gui.algorithm.actions.StopAction;
import org.jdmp.matrix.interfaces.GUIObject;

public class DefaultToolbar extends JToolBar {
	private static final long serialVersionUID = 3044233848101292254L;

	public DefaultToolbar(JComponent c, GUIObject o) {

		setFloatable(false);

		add(new ClearAction(c, o));

		addSeparator();

		add(new ExportAction(c, o));

		addSeparator();

		add(new PrintAction(c, o));

		addSeparator();

		add(new CopyAction(c, o));
		add(new PasteAction(c, o));

		if (o instanceof Algorithm) {
			addSeparator();
			add(new CalculateOnceAction(c, (Algorithm) o));
			add(new StartAction(c, (Algorithm) o));
			add(new StopAction(c, (Algorithm) o));
		}

		if (o instanceof DataSet) {
			// addSeparator();
		}

		addSeparator();
		add(new DeleteAction(c, o));

	}

}
