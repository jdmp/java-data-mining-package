package org.jdmp.gui.module.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.module.HasModules;
import org.jdmp.core.module.Module;

public class RemoveModuleAction extends ModuleListAction {
	private static final long serialVersionUID = 2706158091911195359L;

	private Module module = null;

	public RemoveModuleAction(JComponent c, HasModules i, Module m) {
		this(c, i);
		this.module = m;
	}

	public RemoveModuleAction(JComponent c, HasModules i) {
		super(c, i);
		putValue(Action.NAME, "Remove Module...");
		putValue(Action.SHORT_DESCRIPTION, "Remove a Module");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
	}

	public Object call() {
		if (module != null) {
			getIModules().getModuleList().remove(module);
			return module;
		}
		return null;
	}

}
