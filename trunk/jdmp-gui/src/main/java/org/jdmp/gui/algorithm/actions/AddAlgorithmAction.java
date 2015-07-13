/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.HasAlgorithmMap;

public class AddAlgorithmAction extends AlgorithmListAction {
	private static final long serialVersionUID = 5647028718703205997L;

	private Algorithm algorithm = null;

	public AddAlgorithmAction(JComponent c, HasAlgorithmMap i, Algorithm a) {
		this(c, a);
		algorithm = a;
	}

	public AddAlgorithmAction(JComponent c, HasAlgorithmMap i) {
		super(c, i);
		putValue(Action.NAME, "Add Algorithm...");
		putValue(Action.SHORT_DESCRIPTION, "Add a new Algorithm");
	}

	public Object call() {
		if (algorithm == null) {

			// int index = -1;
			// while (index == -1) {
			// index = JOptionPane.showOptionDialog(null, "Chose the type you
			// want to add:", "Select type",
			// JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
			// Algorithm.AlgorithmType.values(),
			// null);
			// }
			// AlgorithmType type = Algorithm.AlgorithmType.values()[index];
			// algorithm = Algorithm.newInstance(type);
		}

		// getIAlgorithms().getAlgorithmList().add(algorithm);
		return algorithm;
	}
}
