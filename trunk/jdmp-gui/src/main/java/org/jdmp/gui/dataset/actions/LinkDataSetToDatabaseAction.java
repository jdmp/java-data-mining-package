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
import javax.swing.JOptionPane;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.dataset.HasDataSetList;
import org.ujmp.core.enums.DB;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public class LinkDataSetToDatabaseAction extends ObjectAction {
	private static final long serialVersionUID = 43057699462223292L;

	public LinkDataSetToDatabaseAction(JComponent c, GUIObject m) {
		super(c, m);
		putValue(Action.NAME, "to Database...");
		putValue(Action.SHORT_DESCRIPTION, "link a dataset to a JDBC database");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
	}

	
	public Object call() {
		try {
			DB type = DB.values()[JOptionPane.showOptionDialog(getComponent(),
					"Select database type", "Link DataSet", JOptionPane.OK_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, DB.values(), DB.MySQL)];

			String host = null;
			while (host == null) {
				host = JOptionPane.showInputDialog("Enter host name:", "localhost");
			}
			int port = 0;
			while (port <= 0) {
				try {
					port = Integer.parseInt(JOptionPane.showInputDialog("Enter port:", "3306"));
				} catch (Exception e) {
				}
			}
			String database = null;
			while (database == null) {
				database = JOptionPane.showInputDialog("Enter database name:", null);
			}
			String sql = null;
			while (sql == null) {
				sql = JOptionPane.showInputDialog("Enter SQL statement:", "SELECT * FROM ");
			}
			String username = null;
			username = JOptionPane.showInputDialog("Enter user name:", "root");
			String password = null;
			password = JOptionPane.showInputDialog("Enter password:", null);

			DataSet ds = DataSetFactory.linkToJDBC(type, host, port, database, sql, username,
					password);
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
