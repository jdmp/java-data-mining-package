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

import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.dataset.HasDataSetMap;
import org.ujmp.core.filematrix.FileFormat;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.AbstractObjectAction;

public class ImportDataSetFromURLAction extends AbstractObjectAction {
	private static final long serialVersionUID = 116250003084148340L;

	public ImportDataSetFromURLAction(JComponent c, GUIObject i) {
		super(c, i);
		putValue(Action.NAME, "from URL...");
		putValue(Action.SHORT_DESCRIPTION, "Import a DataSet from an URL on the web");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
	}

	public Object call() {
		try {
			URL url = null;
			while (url == null) {
				String s = JOptionPane.showInputDialog("Enter URL:", "http://");
				url = new URL(s);
			}
			FileFormat fileFormat = FileFormat.values()[JOptionPane.showOptionDialog(
					getComponent(), "Select format", "Import DataSet", JOptionPane.OK_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, FileFormat.values(), FileFormat.CSV)];

			ListDataSet ds = ListDataSet.Factory.importFromURL(fileFormat, url);

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
