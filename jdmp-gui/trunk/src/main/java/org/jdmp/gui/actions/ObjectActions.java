package org.jdmp.gui.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.core.algorithm.HasAlgorithms;
import org.jdmp.core.dataset.HasDataSets;
import org.jdmp.core.module.HasModuleList;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.gui.algorithm.actions.AlgorithmListActions;
import org.jdmp.gui.dataset.actions.DataSetListActions;
import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.gui.matrix.actions.ClearMatrixAction;
import org.jdmp.gui.matrix.actions.DeleteMatrixAction;
import org.jdmp.gui.module.actions.ModuleListActions;
import org.jdmp.gui.variable.actions.VariableListActions;
import org.ujmp.core.interfaces.GUIObject;

public class ObjectActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = 5237673098669230511L;

	private PrintAction printAction = null;

	private ExportAction exportAction = null;

	public ObjectActions(JComponent component, GUIObject o) {

		if (o instanceof MatrixGUIObject) {
			add(new JMenuItem(new ClearMatrixAction(component, (MatrixGUIObject) o, null)));
		} else {
			add(new JMenuItem(new ClearAction(component, o)));
		}

		add(new JMenuItem(new ExportAction(component, o)));

		add(new JSeparator());

		add(new JMenuItem(new CopyAction(component, o)));
		add(new JMenuItem(new PasteAction(component, o)));

		if (o instanceof MatrixGUIObject) {
			add(new JMenuItem(new DeleteMatrixAction(component, (MatrixGUIObject) o, null)));
		} else {
			add(new JMenuItem(new DeleteAction(component, o)));
		}

		add(new JSeparator());

		if (component != null) {
			printAction = new PrintAction(component, o);
			add(new JMenuItem(printAction));
		}

		add(new JSeparator());

		add(new JMenuItem(new SetLabelAction(component, o)));
		add(new JSeparator());

		if (o instanceof HasModuleList) {
			JMenu instanceActions = new JMenu("Modules");
			for (JComponent c : new ModuleListActions(component, (HasModuleList) o)) {
				instanceActions.add(c);
			}
			add(instanceActions);
		}

		if (o instanceof HasVariables) {
			JMenu variableActions = new JMenu("Variables");
			for (JComponent c : new VariableListActions(component, (HasVariables) o)) {
				variableActions.add(c);
			}
			add(variableActions);
		}

		if (o instanceof HasAlgorithms) {
			JMenu algorithmActions = new JMenu("Algorithms");
			for (JComponent c : new AlgorithmListActions(component, (HasAlgorithms) o)) {
				algorithmActions.add(c);
			}
			add(algorithmActions);
		}

		if (o instanceof HasDataSets) {
			JMenu dataSetActions = new JMenu("DataSets");
			for (JComponent c : new DataSetListActions(component, (HasDataSets) o)) {
				dataSetActions.add(c);
			}
			add(dataSetActions);
		}

	}

	public void setComponent(JComponent component) {
		printAction.setComponent(component);
		exportAction.setComponent(component);
	}

}
