/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.jdmp.gui.dataset;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.gui.dataset.actions.ClassifyConstantAction;
import org.jdmp.gui.dataset.actions.ClassifyLinearRegressionAction;
import org.jdmp.gui.dataset.actions.ClassifyNaiveBayesAction;
import org.jdmp.gui.dataset.actions.ClassifyRandomAction;
import org.ujmp.core.interfaces.GUIObject;

public class ClassifyJDMPMenu extends JMenu {
	private static final long serialVersionUID = 2340099070389463428L;

	public ClassifyJDMPMenu(JComponent component, DataSetGUIObject o, GUIObject owner) {
		super("JDMP");
		setMnemonic(KeyEvent.VK_J);
		add(new JMenuItem(new ClassifyLinearRegressionAction(component, o)));
		add(new JMenuItem(new ClassifyNaiveBayesAction(component, o)));
		add(new JSeparator());
		add(new JMenuItem(new ClassifyRandomAction(component, o)));
		add(new JMenuItem(new ClassifyConstantAction(component, o)));
	}
}
