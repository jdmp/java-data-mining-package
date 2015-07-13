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

package org.jdmp.gui.dataset.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.gui.dataset.DataSetGUIObject;

public class MNISTTrainAction extends DataSetAction {
	private static final long serialVersionUID = 3129361741996005728L;

	public MNISTTrainAction(JComponent c, DataSetGUIObject ds) {
		super(c, ds);
		putValue(Action.NAME, "MNIST Training DataSet");
		putValue(Action.SHORT_DESCRIPTION, "Loads the MNIST training dataset of handwritten digits");
	}

	public Object call() {
		try {
			DataSet ds = DataSet.Factory.MNISTTrain();
			ds.showGUI();
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
