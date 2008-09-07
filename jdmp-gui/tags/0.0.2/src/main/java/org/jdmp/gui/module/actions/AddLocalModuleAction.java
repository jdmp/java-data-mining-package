package org.jdmp.gui.module.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.module.DefaultModule;
import org.jdmp.core.module.HasModuleList;

public class AddLocalModuleAction extends ModuleListAction {
	private static final long serialVersionUID = 5647028718703205997L;

	public AddLocalModuleAction(JComponent c, HasModuleList i) {
		super(c, i);
		putValue(Action.NAME, "Add LocalModule");
		putValue(Action.SHORT_DESCRIPTION, "Add a new LocalModule");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, 0));
	}

	public Object call() {
		getIModules().getModuleList().add(new DefaultModule());
		return null;
	}

}
