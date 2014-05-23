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
import javax.swing.JOptionPane;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.dataset.HasDataSetMap;
import org.ujmp.core.filematrix.FileFormat;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.AbstractObjectAction;

public class ImportDataSetFromClipboardAction extends AbstractObjectAction {
	private static final long serialVersionUID = -4692993310442522430L;

	public ImportDataSetFromClipboardAction(JComponent c, GUIObject i) {
		super(c, i);
		putValue(Action.NAME, "from Clipboard...");
		putValue(Action.SHORT_DESCRIPTION, "Import a DataSet from clipboard");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
	}

	public Object call() {
		try {
			FileFormat fileFormat = FileFormat.values()[JOptionPane.showOptionDialog(
					getComponent(), "Select format", "Import DataSet", JOptionPane.OK_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, FileFormat.values(), FileFormat.CSV)];

			DataSet ds = DataSetFactory.importFromClipboard(fileFormat);

			if (getCoreObject() instanceof HasDataSetMap) {
				try {
					((HasDataSetMap) getCoreObject()).getDataSetMap().add(ds);
				} catch (Exception e) {
					ds.showGUI();
				}
			} else {
				ds.showGUI();
			}
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
