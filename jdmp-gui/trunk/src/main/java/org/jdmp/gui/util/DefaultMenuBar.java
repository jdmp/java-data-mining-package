package org.jdmp.gui.util;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.core.matrix.system.LogMatrix;
import org.jdmp.core.matrix.system.MatrixAvailableProcessors;
import org.jdmp.core.matrix.system.MatrixMemoryUsage;
import org.jdmp.core.matrix.system.MatrixRandomSeed;
import org.jdmp.core.matrix.system.MatrixRunningThreads;
import org.jdmp.core.matrix.system.MatrixSystemEnvironment;
import org.jdmp.core.matrix.system.MatrixSystemProperties;
import org.jdmp.core.matrix.system.MatrixSystemTime;
import org.jdmp.core.matrix.system.MatrixUIDefaults;
import org.jdmp.core.module.Module;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.WorkspaceVariable;
import org.jdmp.gui.actions.AboutAction;
import org.jdmp.gui.actions.ObjectActions;
import org.jdmp.gui.algorithm.actions.AlgorithmActions;
import org.jdmp.gui.dataset.actions.DataSetActions;
import org.jdmp.gui.matrix.actions.MatrixActions;
import org.jdmp.gui.matrix.actions.ShowInFrameAction;
import org.jdmp.gui.module.actions.ModuleActions;
import org.jdmp.gui.sample.actions.SampleActions;
import org.jdmp.gui.variable.actions.VariableActions;
import org.jdmp.matrix.interfaces.GUIObject;

public class DefaultMenuBar extends JMenuBar {
	private static final long serialVersionUID = -6115122804967308915L;

	public DefaultMenuBar(JComponent component, GUIObject o) {

		JMenu menu = new JMenu("" + o.getClass().getSimpleName());

		ObjectActions actions = null;

		if (o instanceof Algorithm) {
			actions = new AlgorithmActions(component, (Algorithm) o);
		} else if (o instanceof DataSet) {
			actions = new DataSetActions(component, (DataSet) o);
		} else if (o instanceof Module) {
			actions = new ModuleActions(component, (Module) o);
		} else if (o instanceof Sample) {
			actions = new SampleActions(component, (Sample) o);
		} else if (o instanceof Variable) {
			actions = new VariableActions(component, (Variable) o);
		} else if (o instanceof MatrixGUIObject) {
			actions = new MatrixActions(component, (MatrixGUIObject) o, null);
		}

		for (JComponent c : actions) {
			menu.add(c);
		}
		add(menu);

		JMenu optionsMenu = new JMenu("Tools");
		optionsMenu.setMnemonic(KeyEvent.VK_T);
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, WorkspaceVariable
				.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, Module.getInstance())));
		optionsMenu.add(new JSeparator());
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixGlobalConfiguration
				.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixSystemProperties
				.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixSystemEnvironment
				.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixUIDefaults
				.getInstance())));
		optionsMenu.add(new JSeparator());
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixMemoryUsage
				.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixRunningThreads
				.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixSystemTime
				.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixRandomSeed
				.getInstance())));
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, MatrixAvailableProcessors
				.getInstance())));
		optionsMenu.add(new JSeparator());
		optionsMenu.add(new JMenuItem(new ShowInFrameAction(component, LogMatrix.getInstance())));
		add(optionsMenu);

		JMenu windowMenu = new JMenu("Window");
		windowMenu.setMnemonic(KeyEvent.VK_W);
		for (JComponent a : FrameManager.getActions()) {
			windowMenu.add(a);
		}
		add(windowMenu);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		helpMenu.add(new AboutAction(component, o));

		add(helpMenu);

	}

}
