/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.gui.algorithm.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.gui.algorithm.AlgorithmGUIObject;
import org.jdmp.gui.variable.actions.VariableListActions;
import org.ujmp.gui.actions.CopyAction;
import org.ujmp.gui.actions.ExitAction;
import org.ujmp.gui.actions.ExportAction;
import org.ujmp.gui.actions.PasteAction;
import org.ujmp.gui.actions.PrintAction;
import org.ujmp.gui.actions.SetLabelAction;

public class AlgorithmActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = -4508932056911168672L;

	public AlgorithmActions(JComponent component, AlgorithmGUIObject algorithm) {

		add(new JMenuItem(new ExportAction(component, algorithm)));

		add(new JSeparator());

		add(new JMenuItem(new CopyAction(component, algorithm)));
		add(new JMenuItem(new PasteAction(component, algorithm)));

		add(new JSeparator());

		if (component != null) {
			PrintAction printAction = new PrintAction(component, algorithm);
			add(new JMenuItem(printAction));
		}

		add(new JSeparator());

		add(new JMenuItem(new SetLabelAction(component, algorithm)));
		add(new JSeparator());

		JMenu variableActions = new JMenu("Variables");
		for (JComponent c : new VariableListActions(component, algorithm.getAlgorithm())) {
			variableActions.add(c);
		}
		add(variableActions);

		JMenu algorithmActions = new JMenu("Algorithms");
		for (JComponent c : new AlgorithmListActions(component, algorithm.getAlgorithm())) {
			algorithmActions.add(c);
		}
		add(algorithmActions);

		add(new JMenuItem(new CalculateOnceAction(component, algorithm)));

		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(component, algorithm)));
	}
}
