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

import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ListDataSet;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.AbstractObjectAction;

public class ClassifyLibSVMAction extends AbstractObjectAction {
	private static final long serialVersionUID = 8012930523004767966L;

	public ClassifyLibSVMAction(JComponent c, GUIObject i) {
		super(c, i);
		putValue(Action.NAME, "LibSVM");
		putValue(Action.SHORT_DESCRIPTION, "Classify DataSet using SVM from LibSVM");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
	}

	public Object call() {
		try {
			Classifier c = (Classifier) Class.forName("org.jdmp.libsvm.LibSVMClassifier")
					.newInstance();
			c.trainAll((ListDataSet) getCoreObject());
			c.predictAll((ListDataSet) getCoreObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
