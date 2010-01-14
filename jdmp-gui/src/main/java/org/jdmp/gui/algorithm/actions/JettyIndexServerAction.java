/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.jdmp.gui.algorithm.actions;

import java.lang.reflect.Constructor;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.plugin.JettyPlugin;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public class JettyIndexServerAction extends ObjectAction {
	private static final long serialVersionUID = 8017962132766218927L;

	public JettyIndexServerAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Jetty Index Server");
		putValue(Action.SHORT_DESCRIPTION, "Creates a web server for this index");
		if (new JettyPlugin().isAvailable()) {
			putValue("enabled", true);
		} else {
			putValue("enabled", false);
		}
	}

	
	public Object call() {
		try {
			int port = 0;
			while (port <= 0 || port > 32000) {
				String s = JOptionPane.showInputDialog("Port", "5555");
				try {
					port = Integer.parseInt(s);
				} catch (Exception e) {
				}
			}

			Class<?> c = Class.forName("org.jdmp.jetty.index.JettyIndexServer");
			Constructor<?> con = c.getConstructor(Index.class, Integer.TYPE);
			return con.newInstance(getCoreObject(), port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
