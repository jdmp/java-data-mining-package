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

package org.jdmp.gui.dataset;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.gui.matrix.MatrixListPanel;
import org.jdmp.gui.util.ObjectListPanel;
import org.ujmp.gui.matrix.panels.AbstractPanel;

public class DataSetPanel extends AbstractPanel {
	private static final long serialVersionUID = -742923218356821961L;

	private final JSplitPane splitPane = new JSplitPane();

	private final JPanel leftPanel = new JPanel();

	private final JPanel rightPanel = new JPanel();

	public DataSetPanel(DataSetGUIObject ds) {
		super(ds);

		rightPanel.setLayout(new GridLayout(3, 1));
		rightPanel.add(new MatrixListPanel(ds.getDataSet()));
		rightPanel.add(new ObjectListPanel((HasVariables) ds.getDataSet()));
		rightPanel.add(new ObjectListPanel((HasSamples) ds.getDataSet()));

		splitPane.setLeftComponent(leftPanel);
		splitPane.setRightComponent(rightPanel);
		add(splitPane, BorderLayout.CENTER);
	}

}
