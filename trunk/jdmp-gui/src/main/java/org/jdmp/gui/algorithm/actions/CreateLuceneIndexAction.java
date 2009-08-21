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

package org.jdmp.gui.algorithm.actions;

import java.lang.reflect.Constructor;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.plugin.LucenePlugin;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public class CreateLuceneIndexAction extends ObjectAction {
	private static final long serialVersionUID = -5523870732468411037L;

	public CreateLuceneIndexAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Create Lucene Index");
		putValue(Action.SHORT_DESCRIPTION, "Creates a lucene index for this index");
		if (new LucenePlugin().isAvailable()) {
			putValue("enabled", true);
		} else {
			putValue("enabled", false);
		}
	}

	
	public Object call() {
		try {
			Class<?> c = Class.forName("org.jdmp.lucene.LuceneIndex");
			Constructor<?> con = c.getConstructor(Index.class);
			Algorithm a = (Algorithm) con.newInstance(getCoreObject());
			a.showGUI();
			return a;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
