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

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.dataset.ListDataSet;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.AbstractObjectAction;

public class IndexDataSetLuceneAction extends AbstractObjectAction {
	private static final long serialVersionUID = 7775670958567679425L;

	public IndexDataSetLuceneAction(JComponent c, GUIObject i) {
		super(c, i);
		putValue(Action.NAME, "Lucene");
		putValue(Action.SHORT_DESCRIPTION, "Index a DataSet using Lucene");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
	}

	public Object call() {
		try {
			Class<?> c = Class.forName("org.jdmp.lucene.LuceneIndex");
			Index i = (Index) c.newInstance();
			((Algorithm) i).showGUI();
			i.add((ListDataSet) getCoreObject());
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
