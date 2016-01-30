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

import java.io.IOException;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.gui.dataset.DataSetGUIObject;

public class BreastCancerWisconsinDataSetAction extends DataSetAction {
	private static final long serialVersionUID = -147640509730917126L;

	public BreastCancerWisconsinDataSetAction(JComponent c, DataSetGUIObject ds) {
		super(c, ds);
		putValue(Action.NAME, "Breast Cancer Wisconsin DataSet");
		putValue(Action.SHORT_DESCRIPTION,
				"Loads the Breast Cancer Wisconsin Data Set from the UCI Machine Learning Repository");
	}

	public Object call() {
		try {
			ListDataSet ds = ListDataSet.Factory.BREASTCANCERWISCONSIN();
			ds.showGUI();
			return ds;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
