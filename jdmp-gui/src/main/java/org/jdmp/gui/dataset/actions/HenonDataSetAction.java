/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.gui.dataset.DataSetGUIObject;
import org.ujmp.core.exceptions.MatrixException;

public class HenonDataSetAction extends DataSetAction {
	private static final long serialVersionUID = 3110361907469109630L;

	public HenonDataSetAction(JComponent c, DataSetGUIObject ds) {
		super(c, ds);
		putValue(Action.NAME, "Henon DataSet");
		putValue(Action.SHORT_DESCRIPTION, "Creates a demo DataSet with using a Henon map");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_H,
				InputEvent.ALT_DOWN_MASK));
	}

	
	public Object call() throws MatrixException {
		int sampleCount = -1;
		while (sampleCount <= 0) {
			String s = JOptionPane.showInputDialog(getComponent(),
					"How many samples should the data set contain", "Henon Map DataSet",
					JOptionPane.QUESTION_MESSAGE);
			sampleCount = Integer.parseInt(s);
		}
		int inputLength = -1;
		while (inputLength <= 0) {
			String s = JOptionPane.showInputDialog(getComponent(),
					"How many values are used as input", "Henon Map DataSet",
					JOptionPane.QUESTION_MESSAGE);
			inputLength = Integer.parseInt(s);
		}
		int predictionLength = -1;
		while (predictionLength <= 0) {
			String s = JOptionPane.showInputDialog(getComponent(),
					"How many values must be predicted", "Henon Map DataSet",
					JOptionPane.QUESTION_MESSAGE);
			predictionLength = Integer.parseInt(s);
		}
		DataSet henon = DataSetFactory.HenonMap(sampleCount, inputLength, predictionLength);
		henon.showGUI();
		return henon;
	}
}
