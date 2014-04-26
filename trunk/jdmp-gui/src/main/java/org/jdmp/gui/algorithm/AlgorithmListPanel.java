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

package org.jdmp.gui.algorithm;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.HasAlgorithmMap;
import org.jdmp.gui.util.AbstractListPanel;
import org.ujmp.core.interfaces.CoreObject;

public class AlgorithmListPanel extends AbstractListPanel {
	private static final long serialVersionUID = -8393859163650154815L;

	public AlgorithmListPanel(HasAlgorithmMap iAlgorithms) {
		super();
		this.object = ((CoreObject) iAlgorithms).getGUIObject();

		dataModel = new AlgorithmListTableModel(iAlgorithms);
		dataModel.addTableModelListener(this);
		jTable.setDefaultRenderer(Algorithm.class, new AlgorithmTableCellRenderer());
		jTable.setModel(dataModel);

		updateTitle();
	}

	public void updateTitle() {
		getBorder().setTitle("Algorithms (" + jTable.getRowCount() + ")");
		repaint(1000);
	}
}
