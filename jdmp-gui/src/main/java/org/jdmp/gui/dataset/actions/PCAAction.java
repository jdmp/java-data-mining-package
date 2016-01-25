/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import org.jdmp.core.algorithm.compression.Compressor;
import org.jdmp.core.algorithm.compression.PCA;
import org.jdmp.core.dataset.ListDataSet;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.AbstractObjectAction;
import org.ujmp.gui.util.GUIUtil;

public class PCAAction extends AbstractObjectAction {
	private static final long serialVersionUID = 8353299127855271999L;

	public PCAAction(JComponent c, GUIObject i) {
		super(c, i);
		putValue(Action.NAME, "PCA");
		putValue(Action.SHORT_DESCRIPTION, "Compress a DataSet using Principal Component Analysis");
	}

	public Object call() {
		try {
			ListDataSet ds = (ListDataSet) getCoreObject();
			Compressor c = new PCA(GUIUtil.getInt("number of dimensions", 1, ds.getFeatureCount()));
			c.train(ds);
			c.compress(ds);
			c.decompress(ds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
