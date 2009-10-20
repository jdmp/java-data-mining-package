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

import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.dataset.HasDataSetList;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public class ImportDataSetFromFileAction extends ObjectAction {
	private static final long serialVersionUID = -4729154102502117008L;

	public ImportDataSetFromFileAction(JComponent c, GUIObject m) {
		super(c, m);
		putValue(Action.NAME, "from File...");
		putValue(Action.SHORT_DESCRIPTION, "import a dataset from a location on disk");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
	}

	
	public Object call() {
		try {
			File file = null;
			FileFormat fileFormat = null;
			JFileChooser chooser = new JFileChooser();

			for (FileFormat f : FileFormat.values()) {
				chooser.addChoosableFileFilter(f.getFileFilter());
			}

			chooser.setFileFilter(FileFormat.CSV.getFileFilter());
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setDialogTitle("Import");

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				FileFilter filter = chooser.getFileFilter();
				for (FileFormat f : FileFormat.values()) {
					if (filter.equals(f.getFileFilter())) {
						fileFormat = f;
					}
				}

			}

			if (file == null)
				return null;

			DataSet ds = DataSetFactory.importFromFile(fileFormat, file);
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
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
