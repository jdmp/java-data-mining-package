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

package org.jdmp.gui.algorithm;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdmp.core.algorithm.HasAlgorithms;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.gui.variable.VariableListPanel;
import org.ujmp.gui.panels.AbstractPanel;

public class AlgorithmPanel extends AbstractPanel {
	private static final long serialVersionUID = -1135182245042463188L;

	private final JSplitPane splitPane = new JSplitPane();

	private final JPanel leftPanel = new JPanel();

	private final JPanel rightPanel = new JPanel();

	public AlgorithmPanel(AlgorithmGUIObject a) {
		super(a);

		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBorder(BorderFactory.createTitledBorder("Algorithms and Variables"));

		rightPanel.setLayout(new GridLayout(2, 1));
		rightPanel.add(new VariableListPanel((HasVariables) a.getAlgorithm()));
		rightPanel.add(new AlgorithmListPanel((HasAlgorithms) a.getAlgorithm()));

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		add(splitPane, BorderLayout.CENTER);
	}

}
