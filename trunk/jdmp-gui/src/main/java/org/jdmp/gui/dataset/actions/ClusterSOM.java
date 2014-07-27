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

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.algorithm.clustering.Clusterer;
import org.jdmp.core.algorithm.clustering.SelfOrganizingMap;
import org.jdmp.core.dataset.DataSet;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.AbstractObjectAction;
import org.ujmp.gui.util.GUIUtil;

public class ClusterSOM extends AbstractObjectAction {
	private static final long serialVersionUID = -5877704478610043843L;

	public ClusterSOM(JComponent c, GUIObject i) {
		super(c, i);
		putValue(Action.NAME, "SOM");
		putValue(Action.SHORT_DESCRIPTION, "Cluster a DataSet using a Self-Organizing Map");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
	}

	public Object call() {
		try {
			Clusterer c = new SelfOrganizingMap(GUIUtil.getInt("number of rows", 1, 100),
					GUIUtil.getInt("number of columns", 1, 100));
			c.train((DataSet) getCoreObject());
			c.predict((DataSet) getCoreObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
