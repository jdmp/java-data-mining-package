/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import javax.swing.KeyStroke;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.dataset.HasDataSetList;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public class NewEmptyDataSetAction extends ObjectAction {
	private static final long serialVersionUID = 8692069148375302589L;

	public NewEmptyDataSetAction(JComponent c, GUIObject i) {
		super(c, i);
		putValue(Action.NAME, "Empty DataSet");
		putValue(Action.SHORT_DESCRIPTION, "Create a new empty DataSet");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D,
				KeyEvent.ALT_DOWN_MASK));
	}

	@Override
	public Object call() {
		DataSet ds = DataSetFactory.emptyDataSet();
		if (getCoreObject() instanceof HasDataSetList) {
			try {
				((HasDataSetList) getCoreObject()).getDataSets().add(ds);
			} catch (Exception e) {
				ds.showGUI();
			}
		} else {
			ds.showGUI();
		}
		return ds;
	}

}
