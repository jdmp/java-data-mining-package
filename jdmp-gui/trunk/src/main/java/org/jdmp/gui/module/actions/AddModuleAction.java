package org.jdmp.gui.module.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.module.DefaultModule;
import org.jdmp.core.module.HasModules;
import org.jdmp.core.module.Module;

public class AddModuleAction extends ModuleListAction {
	private static final long serialVersionUID = -7138267828869404341L;

	private Module module = null;

	public AddModuleAction(JComponent c, HasModules i, Module m) {
		this(c, i);
		module = m;
	}

	public AddModuleAction(JComponent c, HasModules i) {
		super(c, i);
		putValue(Action.NAME, "Add Module");
		putValue(Action.SHORT_DESCRIPTION, "Add a new Module");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));
	}

	public Object call() {
		if (module == null) {
			module = new DefaultModule();
		}
		getIModules().getModuleList().add(module);
		return module;
	}

}
