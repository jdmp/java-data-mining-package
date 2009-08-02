package org.jdmp.gui.module.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.module.HasModules;
import org.jdmp.core.module.Module;

public class RemoveAllModulesAction extends ModuleListAction {
	private static final long serialVersionUID = -6736676532381101792L;

	public RemoveAllModulesAction(JComponent c, HasModules i) {
		super(c, i);
		putValue(Action.NAME, "Delete all Modules");
		putValue(Action.SHORT_DESCRIPTION, "Delete all Modules contained in this objects");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		Module i = null;
		while ((i = getIModules().getModule(0)) != null) {
			getIModules().removeModule(i);
		}
		return null;
	}

}
