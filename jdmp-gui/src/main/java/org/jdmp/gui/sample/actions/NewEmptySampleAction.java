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

package org.jdmp.gui.sample.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.sample.HasSampleList;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleFactory;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public class NewEmptySampleAction extends ObjectAction {
	private static final long serialVersionUID = 3370112393848013976L;

	public NewEmptySampleAction(JComponent c, GUIObject p) {
		super(c, p);
		putValue(Action.NAME, "Empty Sample");
		putValue(Action.SHORT_DESCRIPTION, "Create a new empty Sample");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.ALT_DOWN_MASK));
	}

	
	public Object call() {
		Sample s = SampleFactory.emptySample();
		if (getCoreObject() != null && getCoreObject() instanceof HasSampleList) {
			try {
				((HasSampleList) getCoreObject()).getSamples().add(s);
			} catch (Exception e) {
				s.showGUI();
			}
		} else {
			s.showGUI();
		}
		return s;
	}

}
