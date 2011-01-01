/*
 * Copyright (C) 2008-2011 by Holger Arndt
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
import org.jdmp.core.algorithm.classification.ConstantClassifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public class ClassifyConstantAction extends ObjectAction {
	private static final long serialVersionUID = 7174404876921126497L;

	public ClassifyConstantAction(JComponent c, GUIObject i) {
		super(c, i);
		putValue(Action.NAME, "Constant Classifiert");
		putValue(Action.SHORT_DESCRIPTION,
				"Classify a DataSet using a Classifier which predicts the most frequent class");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
	}

	public Object call() {
		try {
			Classifier lr = new ConstantClassifier();
			lr.train((ClassificationDataSet) getCoreObject());
			lr.predict((ClassificationDataSet) getCoreObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
