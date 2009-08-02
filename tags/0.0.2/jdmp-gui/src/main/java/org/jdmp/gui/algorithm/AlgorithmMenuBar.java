package org.jdmp.gui.algorithm;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;

import org.jdmp.gui.algorithm.actions.AlgorithmActions;
import org.ujmp.gui.util.DefaultMenuBar;

public class AlgorithmMenuBar extends DefaultMenuBar {
	private static final long serialVersionUID = -6724827875524793049L;

	public AlgorithmMenuBar(JComponent component, AlgorithmGUIObject o) {
		super(component, o);
		JMenu menu = new JMenu("" + o.getClass().getSimpleName());
		List<JComponent> actions = new AlgorithmActions(component, o);
		for (JComponent c : actions) {
			menu.add(c);
		}
		add(menu);
		init(component, o);
	}

}
