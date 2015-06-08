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

package org.jdmp.gui.dataset.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.algorithm.hashing.Hashing;
import org.jdmp.core.algorithm.hashing.LocalitySensitiveHashing;
import org.jdmp.core.dataset.ListDataSet;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.AbstractObjectAction;
import org.ujmp.gui.util.GUIUtil;

public class LocalitySensiveHashingAction extends AbstractObjectAction {
	private static final long serialVersionUID = -4593806093210946692L;

	public LocalitySensiveHashingAction(JComponent c, GUIObject i) {
		super(c, i);
		putValue(Action.NAME, "Locality Sensitive Hashing");
		putValue(Action.SHORT_DESCRIPTION, "Hash a DataSet to signatures using LSH");
	}

	public Object call() {
		try {
			Hashing h = new LocalitySensitiveHashing(GUIUtil.getInt("Number of bits", 1, 65536));
			h.train((ListDataSet) getCoreObject());
			h.hash((ListDataSet) getCoreObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
