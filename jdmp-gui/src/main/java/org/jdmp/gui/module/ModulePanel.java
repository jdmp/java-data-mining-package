/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.gui.module;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdmp.gui.algorithm.AlgorithmListPanel;
import org.jdmp.gui.dataset.DataSetListPanel;
import org.jdmp.gui.sample.SampleListPanel;
import org.jdmp.gui.variable.VariableListPanel;
import org.ujmp.gui.panels.AbstractPanel;

public class ModulePanel extends AbstractPanel {
	private static final long serialVersionUID = -6907850964484891091L;

	private final JSplitPane splitPane = new JSplitPane();

	private final JPanel leftPanel = new JPanel();

	private final JPanel rightPanel = new JPanel();

	public ModulePanel(ModuleGUIObject m) {
		super(m);

		leftPanel.setLayout(new GridLayout(5, 1));
		leftPanel.add(new VariableListPanel(m.getCoreObject()));
		leftPanel.add(new SampleListPanel(m.getCoreObject()));
		leftPanel.add(new DataSetListPanel(m.getCoreObject()));
		leftPanel.add(new ModuleListPanel(m.getCoreObject()));
		leftPanel.add(new AlgorithmListPanel(m.getCoreObject()));

		rightPanel.setLayout(new GridLayout(1, 1));
		rightPanel.add(m.getCommandWindow());

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);

		add(splitPane, BorderLayout.CENTER);
	}
}
