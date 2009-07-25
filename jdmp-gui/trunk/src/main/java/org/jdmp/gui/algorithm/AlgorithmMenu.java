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

package org.jdmp.gui.algorithm;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.jdmp.core.algorithm.index.Index;
import org.jdmp.gui.algorithm.actions.JettyIndexServerAction;
import org.ujmp.core.interfaces.GUIObject;

public class AlgorithmMenu extends JMenu {
	private static final long serialVersionUID = -239974574049471717L;

	public AlgorithmMenu(JComponent component, AlgorithmGUIObject o, GUIObject owner) {
		super("Algorithm");
		setMnemonic(KeyEvent.VK_A);

		if (o.getCoreObject() instanceof Index) {
			JMenuItem jetty = new JMenuItem(new JettyIndexServerAction(component, o));
			if (Boolean.TRUE.equals(jetty.getAction().getValue("enabled"))) {
				jetty.setEnabled(true);
			} else {
				jetty.setEnabled(false);
			}
			add(jetty);
		}

	}
}
