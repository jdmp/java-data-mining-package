package org.jdmp.gui.module;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JSeparator;

import org.jdmp.core.algorithm.basic.About;
import org.jdmp.core.algorithm.basic.Copy;
import org.jdmp.core.algorithm.basic.CreateHenon;
import org.jdmp.core.algorithm.basic.CreateIris;
import org.jdmp.core.algorithm.basic.Help;
import org.jdmp.core.algorithm.basic.Paste;
import org.jdmp.core.algorithm.basic.ShowLicense;
import org.jdmp.gui.module.actions.ScriptAction;
import org.ujmp.core.interfaces.GUIObject;

public class ScriptExamplesMenu extends JMenu {
	private static final long serialVersionUID = -5114197669274460868L;

	public ScriptExamplesMenu(JComponent component, ModuleGUIObject o, GUIObject owner) {
		super("Script Examples");
		setMnemonic(KeyEvent.VK_S);
		add(new ScriptAction(null, o, "Copy", Copy.DESCRIPTION, "copy(ans)"));
		add(new ScriptAction(null, o, "Paste", Paste.DESCRIPTION, "paste"));
		add(new JSeparator());
		add(new ScriptAction(null, o, "Iris DataSet", CreateIris.DESCRIPTION, "dataset=iris"));
		add(new ScriptAction(null, o, "Henon Map DataSet", CreateHenon.DESCRIPTION, "dataset=henon"));
		add(new JSeparator());
		add(new ScriptAction(null, o, "Help", Help.DESCRIPTION, "help"));
		add(new ScriptAction(null, o, "About", About.DESCRIPTION, "about"));
		add(new ScriptAction(null, o, "License", ShowLicense.DESCRIPTION, "license"));
	}

}
